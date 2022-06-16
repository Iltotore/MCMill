import coursier.maven.MavenRepository
import mill._
import mill.api.PathRef
import mill.modules.Jvm
import scalalib._
import scalalib.publish._

trait PluginModule extends ScalaModule with PublishModule {

  def millVersion = "0.10.4"

  def scalaVersion = "2.13.6"

  def compileIvyDeps = Agg(
    ivy"com.lihaoyi::mill-scalalib:$millVersion",
    ivy"com.lihaoyi::mill-main:$millVersion"
  )

  def publishVersion = "1.0.1"

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

  def artifactName: T[String] = "mcmill-core"
}

object sponge extends PluginModule {

  override def artifactName: T[String] = "mcmill-sponge"

  override def moduleDeps: Seq[PublishModule] = Seq(core)

}

object spigot extends PluginModule {

  def artifactName = "mcmill-spigot"

  def moduleDeps = Seq(core)

  def repositoriesTask = T.task {
    super.repositoriesTask() ++ Seq(MavenRepository("https://jitpack.io/"))
  }

  def shadedIvyDeps: T[Agg[Dep]] = Agg(
    ivy"me.carleslc.Simple-YAML:Simple-Yaml:1.8"
  )

  def resolvedShadedIvyDeps: T[Agg[PathRef]] = resolveDeps(shadedIvyDeps)()

  def resolvedIvyDeps = super.resolvedIvyDeps() ++ resolvedShadedIvyDeps()

  def jar = T {
    Jvm.createAssembly(
      localClasspath().concat(resolvedShadedIvyDeps()).map(_.path).filter(os.exists),
      manifest()
    )
  }
}