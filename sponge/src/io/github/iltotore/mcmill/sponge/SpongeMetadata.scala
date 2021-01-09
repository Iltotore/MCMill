package io.github.iltotore.mcmill.sponge

case class SpongeMetadata(modId: String,
                          name: String,
                          version: String,
                          description: String = "",
                          url: String = "",
                          authors: Seq[String] = Seq.empty,
                          pluginDependencies: Seq[String] = Seq.empty)