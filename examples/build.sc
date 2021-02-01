import mill._
import mill.scalalib._
import $ivy.`io.github.iltotore::mcmill-sponge:0.0.2`, $ivy.`io.github.iltotore::mcmill-spigot:0.0.2`
import io.github.iltotore.mcmill.sponge._
import io.github.iltotore.mcmill.spigot._
import mill.api.Loose
import mill.define.Target

object sponge extends ScalaModule with SpongeScalaModule {

  def scalaVersion = "2.13.4"

  def spongeScalaVersion = "0.1"

  def spongeVersion = "7.2.0"

  def spongeMetadata = SpongeMetadata(
    modId = "mcmill",
    name = "MCMill",
    version = "0.1",
    description = "This is a simple MCMill test for Sponge",
    authors = Seq("Il_totore")
  )

  override def compileIvyDeps: Target[Loose.Agg[Dep]] = super.compileIvyDeps() ++ Agg(
    ivy"io.github.iltotore::ec-client:2.0"
  )
}

/*object spigot extends ScalaModule with SpigotModule {

  def scalaVersion = "2.13.4"

  def spigotVersion = "1.16.5-R0.1-SNAPSHOT"

  def spigotMetadata = SpigotMetadata(
    name = "MCMill",
    version = "0.1",
    description = "This is a simple MCMill test for Spigot",
    authors = Seq("Il_totore")
  )
}*/