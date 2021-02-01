package io.github.iltotore.mcmill.sponge

import coursier.Repository
import coursier.maven.MavenRepository
import io.github.iltotore.mcmill.IO._
import mill.api.Loose
import mill.define.{Source, Target, Task}
import mill.eval.PathRef
import mill.modules.Jvm.{createAssembly, createJar}
import mill.scalalib.{Dep, DepSyntax, JavaModule}
import mill.{Agg, T}

import java.io.FileOutputStream

trait SpongeModule extends JavaModule {

  def spongeVersion: String

  def spongeMetadata: SpongeMetadata

  override def repositoriesTask: Task[Seq[Repository]] = T.task {
    super.repositoriesTask() :+ MavenRepository("https://repo.spongepowered.org/maven")
  }

  override def compileIvyDeps: Target[Loose.Agg[Dep]] = super.compileIvyDeps() ++ Agg(
    ivy"org.spongepowered:spongeapi:$spongeVersion" withConfiguration "compile"
  )


  def generateModInfo = T {
    val mcMod = T.dest / "mcmod.info"
    val json = ujson.Arr(
      ujson.Obj(
        "modid" -> spongeMetadata.modId,
        "name" -> spongeMetadata.name,
        "version" -> spongeMetadata.version,
        "url" -> spongeMetadata.url,
        "authorList" -> spongeMetadata.authors,
        "dependencies" -> spongeMetadata.pluginDependencies,
        "requiredMods" -> spongeMetadata.pluginDependencies
      )
    ).toString()
    withResources(new FileOutputStream(mcMod.toIO))(_.write(json.getBytes()))
    PathRef(T.dest)
  }

  def spongeJar: Target[PathRef] = T {
    createAssembly(
      inputPaths = Agg.from(localClasspath().appended(generateModInfo()).map(_.path)),
      manifest = manifest(),
      base = Some(upstreamAssembly().path),
      assemblyRules = assemblyRules
    )
  }
}
