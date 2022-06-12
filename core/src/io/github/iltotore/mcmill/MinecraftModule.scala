package io.github.iltotore.mcmill

import mill.{Agg, T}
import mill.define.{Source, Sources}
import mill.scalalib.{Dep, JavaModule}

/**
 * An abstract module for Minecraft development.
 */
trait MinecraftModule extends JavaModule {

  def minecraftApiDeps: T[Agg[Dep]] = Agg.empty[Dep]

  override def compileIvyDeps: T[Agg[Dep]] = T { super.compileIvyDeps() ++ minecraftApiDeps() }

  /**
   * The description file of this plugin. For example, `plugin.yml` (Spigot) or `mcmod.info` (Sponge)
   */
  def pluginDescription: Source

  override def resources: Sources = T.sources {
    super.resources() :+ pluginDescription()
  }
}
