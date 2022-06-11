package io.github.iltotore.mcmill.spigot

/**
 * Represent a command node. See [Spigot's page](https://www.spigotmc.org/wiki/plugin-yml/#commands).
 * @param name the command's name.
 * @param description the command's description.
 * @param permissionName the name of the permission needed to execute this command.
 * @param permissionMessage the message sent to the user if it doesn't have the right permission.
 * @param usage a usage description of the command. Often looks like `/command <arg1> <arg2> [optArg1] ...`
 * @param aliases alternative names for this command.
 */
case class Command(name: String,
                   description: String = "",
                   permissionName: String = "",
                   permissionMessage: String = "",
                   usage: String = "",
                   aliases: Seq[String] = Seq.empty)