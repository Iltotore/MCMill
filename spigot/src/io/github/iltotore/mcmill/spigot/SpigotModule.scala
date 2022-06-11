package io.github.iltotore.mcmill.spigot

import coursier.maven.MavenRepository
import io.github.iltotore.mcmill.MinecraftModule
import mill._
import mill.api.PathRef
import mill.define.Source
import mill.scalalib._
import org.yaml.snakeyaml.{DumperOptions, Yaml}

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
    val descFile = T.dest / "plugin.yml"

    val desc: mutable.Map[String, Any] = mutable.Map(
      "name" -> spigotMetadata.name,
      "version" -> spigotMetadata.version,
      "main" -> spigotMetadata.mainClass
    )

    if(spigotMetadata.authors.length == 1) desc.put("author", spigotMetadata.authors.head)
    if(spigotMetadata.authors.length > 1) desc.put("authors", spigotMetadata.authors.toArray)
    if(spigotMetadata.description.nonEmpty) desc.put("description", spigotMetadata.description)
    if(spigotMetadata.website.nonEmpty) desc.put("website", spigotMetadata.website)
    if(spigotMetadata.pluginDependencies.nonEmpty) desc.put("depend", spigotMetadata.pluginDependencies.toArray)
    if(spigotMetadata.pluginSoftDependencies.nonEmpty) desc.put("softdepend", spigotMetadata.pluginSoftDependencies.toArray)

    val options = new DumperOptions
    options.setIndent(2)
    options.setPrettyFlow(true)
    options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)

    new Yaml(options).dump(desc.asJava, new FileWriter(descFile.toIO))
    T.dest
  }

}
