package io.github.iltotore.mcmill.spigot

import coursier.Repository
import coursier.maven.MavenRepository
import io.github.iltotore.mcmill.MinecraftModule
import mill._
import mill.api.Loose
import mill.define.{Source, Target, Task}
import mill.scalalib._
import org.simpleyaml.configuration.file.YamlFile

import scala.jdk.CollectionConverters.MapHasAsJava

trait SpigotModule extends MinecraftModule {

  def spigotVersion: String

  def spigotMetadata: SpigotMetadata

  def apiVersion: String = {
    val splat = spigotVersion.split('.')
    val majorVersion = splat(1).toInt
    if(majorVersion >= 13) s"${splat(0)}.${splat(1)}"
    else "legacy"
  }

  def pluginIvyDeps: T[Agg[Dep]] = allIvyDeps()

  override def repositoriesTask: Task[Seq[Repository]] = T.task {
    super.repositoriesTask() ++ Seq(
      MavenRepository("https://hub.spigotmc.org/nexus/content/repositories/snapshots/"),
      MavenRepository("https://oss.sonatype.org/content/repositories/snapshots"),
    )
  }

  override def compileIvyDeps: Target[Loose.Agg[Dep]] = super.compileIvyDeps() ++ Agg(ivy"org.spigotmc:spigot-api:$spigotVersion")

  override def pluginDescription: Source = T.source {
    val descFile = new YamlFile((T.dest / "plugin.yml").toIO)
    if(!descFile.exists()) descFile.createNewFile()
    
    descFile.set("name", spigotMetadata.name)
    descFile.set("version", spigotMetadata.version)
    descFile.set("main", spigotMetadata.mainClass)
    descFile.set("api-version", apiVersion)

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
