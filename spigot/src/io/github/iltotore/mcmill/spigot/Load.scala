package io.github.iltotore.mcmill.spigot

/**
 * Represent the state when a plugin should be loaded. Spigot's default is [[Load.PostWorld]]
 */
object Load extends Enumeration {

  val PostWorld = Value("postworld")
  val Startup = Value("startup")
}
