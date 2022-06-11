package io.github.iltotore.mcmill.spigot

/**
 * Represent the complete description of a Spigot plugin (plugin.yml). See [Spigot's page](https://www.spigotmc.org/wiki/plugin-yml/)
 *
 * @param name                   the plugin's name.
 * @param version                the plugin's version.
 * @param mainClass              the class to load when enabling this plugin.
 * @param description            the plugin's description.
 * @param website                the plugin's website. Github and Spigot page are often used.
 * @param load                   the server state when this plugin should be loaded.
 * @param prefix                 the prefix used in the console for this plugin.
 * @param authors                the plugin's authors.
 * @param pluginDependencies     the plugin's dependencies. This plugin won't load if its dependencies are not present.
 * @param pluginSoftDependencies the plugin's soft dependencies. Soft dependencies are optional and loaded before the depending plugin.
 * @param loadBefore             the plugins that should be loaded after this one.
 * @param commands               the plugin's commands.
 * @param permissions            the plugin's permissions.
 * @param downloadIvyDeps        Spigot's experimental `libraries` option. This option allows the developer to pass non-plugin dependencies to be downloaded from Maven Central.
 *                               If true, plugin's ivy dependencies will be added to the plugin.yml.
 */
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
                          downloadIvyDeps: Boolean = false)