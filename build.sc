import mill._, scalalib._, scalalib.publish._

trait PluginModule extends ScalaModule with PublishModule {

  def millVersion = "0.10.4"

  def scalaVersion = "2.13.6"

  def compileIvyDeps = Agg(
    ivy"com.lihaoyi::mill-scalalib:$millVersion",
    ivy"com.lihaoyi::mill-main:$millVersion"
  )

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

  def artifactName: T[String] = "millmc-core"
}

object sponge extends PluginModule {

  override def artifactName: T[String] = "mcmill-sponge"

  override def moduleDeps: Seq[PublishModule] = Seq(core)

}

object spigot extends PluginModule {

  def artifactName = "mcmill-spigot"

  def moduleDeps = Seq(core)

  def ivyDeps = super.ivyDeps() ++ Agg(
    ivy"org.yaml:snakeyaml:1.27"
  )
}