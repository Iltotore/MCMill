package io.github.iltotore.mcmill.spigot

case class SpigotMetadata(name: String,
                          version: String,
                          mainClass: String,
                          description: String = "",
                          website: String = "",
                          load: Load = Load.PostWorld,
                          prefix: String = "",
                          authors: Seq[String] = Seq.empty,
                          pluginDependencies: Seq[String] = Seq.empty,
                          pluginSoftDependencies: Seq[String] = Seq.empty,
                          loadBefore: Seq[String] = Seq.empty,
                          commands: Seq[Command] = Seq.empty,
                          permissions: Seq[Permission] = Seq.empty,
                          downloadExternalLibs: Boolean = false)