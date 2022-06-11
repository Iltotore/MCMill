package io.github.iltotore.mcmill.spigot

/**
 * The default values of a permission. Spigot's default is [[PermissionDefault.NotOp]]
 */
object PermissionDefault extends Enumeration {

  val True = Value("true")
  val False = Value("false")
  val Op = Value("op")
  val NotOp = Value("not op")
}
