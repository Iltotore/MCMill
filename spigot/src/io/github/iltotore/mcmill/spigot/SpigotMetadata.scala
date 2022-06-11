package io.github.iltotore.mcmill.spigot

case class SpigotMetadata(name: String,
                          version: String,
                          mainClass: String,
                          description: String = "",
                          website: String = "",
                          authors: Seq[String] = Seq.empty,
                          pluginDependencies: Seq[String] = Seq.empty,
                          pluginSoftDependencies: Seq[String] = Seq.empty)