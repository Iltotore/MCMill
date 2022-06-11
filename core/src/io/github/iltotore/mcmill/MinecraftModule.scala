package io.github.iltotore.mcmill

import mill.T
import mill.define.{Source, Sources}
import mill.scalalib.JavaModule

trait MinecraftModule extends JavaModule {

  def pluginDescription: Source

  override def resources: Sources = T.sources {
    super.resources() :+ pluginDescription()
  }
}
