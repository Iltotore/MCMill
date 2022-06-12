package io.github.iltotore.mcmill.spigot

import mill.Agg
import mill.define.Target
import mill.scalalib._

/**
 * A module for Paper plugin development.
 */
trait PaperModule extends SpigotModule {

  override def minecraftApiDeps: Target[Agg[Dep]] = Agg(
    if (formattedVersion.major >= 17) ivy"io.papermc.paper:paper-api:$spigotVersion"
    else ivy"com.destroystokyo.paper:paper-api:$spigotVersion"
  )
}
