package io.github.iltotore.mcmill.spigot

case class Permission(name: String,
                      description: String = "",
                      default: PermissionDefault = PermissionDefault.Op,
                      children: Map[String, Boolean])
