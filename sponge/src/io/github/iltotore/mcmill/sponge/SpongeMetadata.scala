package io.github.iltotore.mcmill.sponge

/**
 * Represent the description of a Sponge plugin (mcmod.info).
 * @param modId the plugin's id.
 * @param name the plugin's display name.
 * @param version the plugin's version.
 * @param description the plugin's description.
 * @param url the plugin's website. Github or Ore page are often used.
 * @param authors the plugin's authors.
 * @param pluginDependencies the plugin's dependencies.
 */
case class SpongeMetadata(modId: String,
                          name: String,
                          version: String,
                          description: String = "",
                          url: String = "",
                          authors: Seq[String] = Seq.empty,
                          pluginDependencies: Seq[String] = Seq.empty)