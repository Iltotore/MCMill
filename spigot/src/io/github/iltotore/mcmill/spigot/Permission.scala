package io.github.iltotore.mcmill.spigot

/**
 * Represent a permission node. See [Spigot's page](https://www.spigotmc.org/wiki/plugin-yml/#permissions)
 * @param name the permission's name.
 * @param description the permission's description.
 * @param default the default value of the permission.
 * @param children the children of this permission. A child permission's value depends on its parents.
 */
case class Permission(name: String,
                      description: String = "",
                      default: PermissionDefault = PermissionDefault.Op,
                      children: Map[String, Boolean])
