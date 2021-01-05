import mill._
import mill.api.Loose
import mill.define.Target
import mill.scalalib._
import mill.scalalib.publish.{Developer, License, PomSettings, VersionControl}


object main extends ScalaModule with PublishModule {

  def millVersion = "0.9.4-13-75bc5a"

  def scalaVersion = "2.13.4"

  def publishVersion = "0.1"

  override def artifactName: T[String] = "mc-mill"

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    ivy"com.lihaoyi::mill-scalalib:$millVersion", //withConfiguration "provided",
    ivy"com.lihaoyi::mill-main:$millVersion" //withConfiguration "provided"
  )

  def pomSettings = PomSettings(
    description = "A Mill plugin for Minecraft development",
    organization = "io.github.iltotore",
    url = "https://github.com/Iltotore/MCMill",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("Iltotore", "https://github.com/Iltotore/MCMill.git"),
    developers = Seq(
      Developer("Iltotore", "Raphaël FROMENTIN","https://github.com/Iltotore")
    )
  )
}