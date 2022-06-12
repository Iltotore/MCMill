import mill._, scalalib._
import $ivy.`io.github.iltotore::mcmill-sponge:1.0.0`, $ivy.`io.github.iltotore::mcmill-spigot:1.0.0`
import io.github.iltotore.mcmill.sponge._
import io.github.iltotore.mcmill.spigot._

object spigot extends SpigotModule {

  def spigotVersion = "1.19-R0.1-SNAPSHOT/"

  def spigotMetadata = SpigotMetadata(
    name = "MCMill",
    version = "0.1",
    mainClass = "io.github.iltotore.mcmill.SpigotMain",
    description = "This is a simple MCMill test for Spigot",
    authors = Seq("Il_totore"),
    downloadIvyDeps = true
  )

}

object sponge extends SpongeModule {

  def spongeVersion = "7.2.0"

  def spongeMetadata = SpongeMetadata(
    modId = "mcmill",
    name = "MCMill",
    version = "0.1",
    description = "This is a simple MCMill test for Sponge",
    authors = Seq("Il_totore")
  )
}