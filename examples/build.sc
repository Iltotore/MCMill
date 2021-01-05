import mill._
import mill.scalalib._

import $ivy.`io.github.iltotore::mc-mill:0.1`
import io.github.iltotore.mcmill.sponge._

object sponge extends ScalaModule with SpongeModule {

  def scalaVersion = "2.13.4"

  def spongeVersion = "7.2.0"

  def spongeMetadata = SpongeMetadata(
    modId = "mcmill",
    name = "MCMill",
    version = "0.1",
    description = "This is a simple MCMill test for Sponge"
  )
}