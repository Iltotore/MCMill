package io.github.iltotore.mcmill.sponge

import coursier.maven.MavenRepository
import io.github.iltotore.mcmill.IO._
import io.github.iltotore.mcmill.MinecraftModule
import mill.eval.PathRef
import mill.scalalib.DepSyntax
import mill.{Agg, T}

import java.io.FileOutputStream

trait SpongeModule extends MinecraftModule {

  def spongeVersion: String

  def spongeMetadata: SpongeMetadata

  override def repositoriesTask = T.task {
    super.repositoriesTask() :+ MavenRepository("https://repo.spongepowered.org/repository/maven-public/")
  }

  override def compileIvyDeps  = super.compileIvyDeps() ++ Agg(
    ivy"org.spongepowered:spongeapi:$spongeVersion" withConfiguration "compile"
  )


  override def pluginDescription = T.source {
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
    T.dest
  }

}
