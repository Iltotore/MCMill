package io.github.iltotore.mcmill.sponge

import coursier.Repository
import coursier.maven.MavenRepository
import io.github.iltotore.mcmill.IO._
import io.github.iltotore.mcmill.MinecraftModule
import mill.api.Loose
import mill.define.{Source, Target, Task}
import mill.scalalib.{Dep, DepSyntax}
import mill.{Agg, T}

import java.io.FileOutputStream

/**
 * A module for Sponge plugin development.
 */
trait SpongeModule extends MinecraftModule {

  /**
   * The Sponge version to use.
   */
  def spongeVersion: String

  /**
   * The plugin description (mcmod.info).
   */
  def spongeMetadata: SpongeMetadata

  override def repositoriesTask: Task[Seq[Repository]] = T.task {
    super.repositoriesTask() :+ MavenRepository("https://repo.spongepowered.org/repository/maven-public/")
  }

  override def compileIvyDeps: Target[Loose.Agg[Dep]] = super.compileIvyDeps() ++ Agg(
    ivy"org.spongepowered:spongeapi:$spongeVersion" withConfiguration "compile"
  )


  override def pluginDescription: Source = T.source {
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
