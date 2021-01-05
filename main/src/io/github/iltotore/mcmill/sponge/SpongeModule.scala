package io.github.iltotore.mcmill.sponge

import coursier.Repository
import coursier.maven.MavenRepository
import mill.{Agg, T}
import mill.scalalib.{Dep, DepSyntax, JavaModule}
import ujson._
import io.github.iltotore.mcmill.IO._
import mill.api.Loose
import mill.define.{Source, Sources, Target, Task}
import mill.eval.PathRef
import mill.modules.Jvm.createJar
import os.Path

import java.io.FileOutputStream

trait SpongeModule extends JavaModule {

  def spongeVersion: String

  def spongeMetadata: SpongeMetadata

  override def repositoriesTask: Task[Seq[Repository]] = T.task {
    super.repositoriesTask() :+ MavenRepository("https://repo.spongepowered.org/maven")
  }

  override def ivyDeps: Target[Loose.Agg[Dep]] = T {
    super.ivyDeps() ++ Agg(ivy"org.spongepowered:spongeapi:$spongeVersion")
  }

  def generateModInfo: Target[PathRef] = T {
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
    PathRef(mcMod)
  }

  def pluginJar: Target[PathRef] = T {
    createJar(
      localClasspath().appended(generateModInfo()).map(_.path).filter(os.exists),
      manifest()
    )
  }
}
