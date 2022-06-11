package io.github.iltotore.mcmill.spigot

import coursier.maven.MavenRepository
import io.github.iltotore.mcmill.MinecraftModule
import mill._
import mill.api.PathRef
import mill.define.Source
import mill.scalalib._
import org.simpleyaml.configuration.file.YamlFile

import java.io.FileWriter
import scala.collection.mutable
import scala.jdk.CollectionConverters.MapHasAsJava

trait SpigotModule extends MinecraftModule {

  def spigotVersion: String

  def spigotMetadata: SpigotMetadata

  override def repositoriesTask = T.task {
    super.repositoriesTask() ++ Seq(
      MavenRepository("https://hub.spigotmc.org/nexus/content/repositories/snapshots/"),
      MavenRepository("https://oss.sonatype.org/content/repositories/snapshots"),
    )
  }

  override def compileIvyDeps = super.compileIvyDeps() ++ Agg(ivy"org.spigotmc:spigot-api:$spigotVersion")

  override def pluginDescription: Source = T.source {
    val descFile = new YamlFile((T.dest / "plugin.yml").toIO)
    if(!descFile.exists()) descFile.createNewFile()
    
    descFile.set("name", spigotMetadata.name)
    descFile.set("version", spigotMetadata.version)
    descFile.set("main", spigotMetadata.mainClass)

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

    descFile.save()

    T.dest
  }

}
