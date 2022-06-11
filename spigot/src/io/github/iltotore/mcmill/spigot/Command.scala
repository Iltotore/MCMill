package io.github.iltotore.mcmill.spigot

case class Command(name: String,
                   description: String = "",
                   permissionName: String = "",
                   permissionMessage: String = "",
                   usage: String = "",
                   aliases: Seq[String] = Seq.empty)