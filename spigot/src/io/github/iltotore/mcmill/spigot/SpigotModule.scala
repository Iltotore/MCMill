package io.github.iltotore.mcmill.spigot

import coursier.Repository
import coursier.maven.MavenRepository
import io.github.iltotore.mcmill.MinecraftModule
import mill._
import mill.define.{Source, Target, Task}
import mill.scalalib._
import org.simpleyaml.configuration.file.YamlFile

import scala.jdk.CollectionConverters.MapHasAsJava
import scala.util.matching.Regex

/**
 * A module for Spigot plugin development.
 */
trait SpigotModule extends MinecraftModule {

  /**
   * The Spigot version to use.
   */
  def spigotVersion: String

  /**
   * The plugin description (plugin.yml).
   */
  def spigotMetadata: SpigotMetadata

  private val versionPattern: Regex = raw"^(\d)\.(\d{1,2})(\.(\d{1,2}))?-R(\d*)(\.(\d))*-SNAPSHOT".r

  def formattedVersion: SpigotVersion = {
    val raw = versionPattern
      .findFirstMatchIn(spigotVersion)
      .getOrElse(throw new IllegalArgumentException(s"$spigotVersion is not a correct version. Should be x.xx.xx-Rx.x-SNAPSHOT"))

    SpigotVersion(raw.group(1).toInt, raw.group(2).toInt, Option(raw.group(4)).map(_.toInt), (raw.group(5).toInt, raw.group(7).toInt))
  }

  /**
   * The major API version used by this plugin. Automatically set according to `spigotVersion`
   */
  def apiVersion: T[String] = {
    if(formattedVersion.major >= 13) s"${formattedVersion.minecraft}.${formattedVersion.major}"
    else "legacy"
  }

  /**
   * Plugin dependencies to add to the plugin description. See [[SpigotMetadata.downloadIvyDeps]]
   */
  def pluginIvyDeps: T[Agg[Dep]] = allIvyDeps() ++ runIvyDeps()

  override def repositoriesTask: Task[Seq[Repository]] = T.task {
    super.repositoriesTask() ++ Seq(
      MavenRepository("https://hub.spigotmc.org/nexus/content/repositories/snapshots/"),
      MavenRepository("https://oss.sonatype.org/content/repositories/snapshots"),
    )
  }

  override def minecraftApiDeps: Target[Agg[Dep]] = Agg(ivy"org.spigotmc:spigot-api:$spigotVersion")

  override def pluginDescription: Source = T.source {
    val descFile = new YamlFile((T.dest / "plugin.yml").toIO)
    if(!descFile.exists()) descFile.createNewFile()
    
    descFile.set("name", spigotMetadata.name)
    descFile.set("version", spigotMetadata.version)
    descFile.set("main", spigotMetadata.mainClass)
    descFile.set("api-version", apiVersion())

    if(spigotMetadata.description.nonEmpty) descFile.set("description", spigotMetadata.description)
    if(spigotMetadata.website.nonEmpty) descFile.set("website", spigotMetadata.website)
    if(spigotMetadata.load != Load.PostWorld) descFile.set("load", spigotMetadata.load.toString)
    if(spigotMetadata.prefix.nonEmpty) descFile.set("prefix", spigotMetadata.prefix)
    if(spigotMetadata.authors.length == 1) descFile.set("author", spigotMetadata.authors.head)
    if(spigotMetadata.authors.length > 1) descFile.set("authors", spigotMetadata.authors.toArray)
    if(spigotMetadata.pluginDependencies.nonEmpty) descFile.set("depend", spigotMetadata.pluginDependencies.toArray)
    if(spigotMetadata.pluginSoftDependencies.nonEmpty) descFile.set("softdepend", spigotMetadata.pluginSoftDependencies.toArray)
    if(spigotMetadata.loadBefore.nonEmpty) descFile.set("loadbefore", spigotMetadata.loadBefore.toArray)

    if(spigotMetadata.commands.nonEmpty) {
      val commandSection = descFile.createSection("commands")

      for(command <- spigotMetadata.commands) {
        val section = commandSection.createSection(command.name)
        if(command.description.nonEmpty) section.set("description", command.description)
        if(command.permissionName.nonEmpty) section.set("permission", command.permissionName)
        if(command.permissionMessage.nonEmpty) section.set("permission-message", command.permissionMessage)
        if(command.usage.nonEmpty) section.set("usage", command.usage)
        if(command.aliases.nonEmpty) section.set("aliases", command.aliases.toArray)
      }
    }

    if(spigotMetadata.permissions.nonEmpty) {
      val permissionSection = descFile.createSection("permissions")

      for(permission <- spigotMetadata.permissions) {
        val section = permissionSection.createSection(permission.name)
        if(permission.description.nonEmpty) section.set("description", permission.description)
        if(permission.default != PermissionDefault.Op) section.set("default", permission.default.toString)
        if(permission.children.nonEmpty) section.createSection("children", permission.children.asJava)
      }
    }

    if(spigotMetadata.downloadIvyDeps) {
      val resolver = resolveCoursierDependency()
      val deps = pluginIvyDeps().map(resolver).map(d => s"${d.module.repr}:${d.version}")
      if(deps.iterator.nonEmpty) descFile.set("libraries", deps.iterator.toArray)
    }

    descFile.save()

    T.dest
  }

}
