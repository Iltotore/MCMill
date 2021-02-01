import mill._
import mill.api.Loose
import mill.define.{Target, Task}
import mill.scalalib._
import mill.scalalib.publish.{Dependency, Developer, License, PomSettings, VersionControl}


trait PluginModule extends ScalaModule with PublishModule {

  def millVersion = "0.9.4"

  def scalaVersion = "2.13.4"

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    ivy"com.lihaoyi::mill-scalalib:$millVersion",
    ivy"com.lihaoyi::mill-main:$millVersion"
  )

  override def publishXmlDeps: Task[Agg[Dependency]] = super.publishXmlDeps.map(_.filter(!_.artifact.group.equals("com.lihaoyi")))

  def publishVersion = "0.0.2"

  def pomSettings = PomSettings(
    description = "A Mill plugin for Minecraft development",
    organization = "io.github.iltotore",
    url = "https://github.com/Iltotore/MCMill",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("Iltotore", "MCMill"),
    developers = Seq(
      Developer("Iltotore", "Raphaël FROMENTIN","https://github.com/Iltotore")
    )
  )
}

object core extends PluginModule {

  override def artifactName: T[String] = "millmc-core"
}

object sponge extends PluginModule {

  override def artifactName: T[String] = "mcmill-sponge"

  override def moduleDeps: Seq[PublishModule] = Seq(core)

}

object spigot extends PluginModule {

  override def artifactName: T[String] = "mcmill-spigot"

  override def moduleDeps: Seq[PublishModule] = Seq(core)

  override def ivyDeps: Target[Loose.Agg[Dep]] = super.ivyDeps() ++ Agg(
    ivy"org.yaml:snakeyaml:1.27"
  )
}