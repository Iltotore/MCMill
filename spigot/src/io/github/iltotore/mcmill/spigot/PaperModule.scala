package io.github.iltotore.mcmill.spigot

import coursier.Repository
import coursier.maven.MavenRepository
import mill.{Agg, T}
import mill.define.{Target, Task}
import mill.scalalib._

/**
 * A module for Paper plugin development.
 */
trait PaperModule extends SpigotModule {

  override def repositoriesTask: Task[Seq[Repository]] = T.task {
    super.repositoriesTask() ++ Seq(
      MavenRepository("https://repo.papermc.io/repository/maven-public/")
    )
  }

  override def minecraftApiDeps: Target[Agg[Dep]] = Agg(
    if (formattedVersion.major >= 17) ivy"io.papermc.paper:paper-api:$spigotVersion"
    else ivy"com.destroystokyo.paper:paper-api:$spigotVersion"
  )
}
