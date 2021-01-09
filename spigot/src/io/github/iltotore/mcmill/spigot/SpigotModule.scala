package io.github.iltotore.mcmill.spigot

import coursier.Repository
import coursier.maven.MavenRepository
import mill._
import mill.api.{Loose, PathRef}
import mill.define.{Target, Task}
import mill.modules.Jvm.createJar
import mill.scalalib._
import org.yaml.snakeyaml.Yaml

import java.io.FileWriter
import scala.jdk.CollectionConverters.MapHasAsJava

trait SpigotModule extends JavaModule {

  def spigotVersion: String

  def spigotMetadata: SpigotMetadata

  override def repositoriesTask: Task[Seq[Repository]] = T.task {
    super.repositoriesTask() ++ Seq(
      MavenRepository("https://hub.spigotmc.org/nexus/content/repositories/snapshots/"),
      MavenRepository("https://oss.sonatype.org/content/repositories/snapshots"),
    )
  }

  override def ivyDeps: Target[Loose.Agg[Dep]] = T {
    super.ivyDeps() ++ Agg(ivy"org.spigotmc:spigot-api:$spigotVersion")
  }

  def generatePluginDescription: Target[PathRef] = T {
    val descFile = T.dest / "plugin.yml"
    new Yaml().dump(Map(
      "name" -> spigotMetadata.name,
      "version" -> spigotMetadata.version,
      "description" -> spigotMetadata.description,
      "website" -> spigotMetadata.website,
      "authors" -> spigotMetadata.authors.toArray,
      "depend" -> spigotMetadata.pluginDependencies.toArray,
      "softdepend" -> spigotMetadata.pluginSoftDependencies.toArray
    ).asJava, new FileWriter(descFile.toIO))
    PathRef(descFile)
  }

  def spigotJar: Target[PathRef] = T {
    createJar(
      localClasspath().appended(generatePluginDescription()).map(_.path).filter(os.exists),
      manifest()
    )
  }
}
