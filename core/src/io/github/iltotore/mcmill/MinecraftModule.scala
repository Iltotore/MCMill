package io.github.iltotore.mcmill

import mill.T
import mill.define.{Source, Sources}
import mill.scalalib.JavaModule

/**
 * An abstract module for Minecraft development.
 */
trait MinecraftModule extends JavaModule {

  /**
   * The description file of this plugin. For example, `plugin.yml` (Spigot) or `mcmod.info` (Sponge)
   */
  def pluginDescription: Source

  override def resources: Sources = T.sources {
    super.resources() :+ pluginDescription()
  }
}
