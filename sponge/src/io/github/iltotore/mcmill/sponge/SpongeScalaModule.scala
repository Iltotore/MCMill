package io.github.iltotore.mcmill.sponge

import mill.Agg
import mill.api.Loose
import mill.define.Target
import mill.modules.Assembly
import mill.modules.Assembly.Rule.{ExcludePattern, Relocate}
import mill.scalalib.{Dep, DepSyntax, ScalaModule}

/**
 * A module for Sponge development using the SpongeScala library.
 * It automatically add SpongeScala dependency and relocate Scala lib to avoid clashes with Forge.
 * Note: You need to also extends from [[ScalaModule]] to use Scala for this plugin.
 */
trait SpongeScalaModule extends SpongeModule {

  def spongeScalaVersion: String

  override def compileIvyDeps: Target[Loose.Agg[Dep]] = super.compileIvyDeps() ++ Agg(ivy"io.github.iltotore::sponge-scala:$spongeScalaVersion")

  override def assemblyRules: Seq[Assembly.Rule] = super.assemblyRules ++ Seq(
    ExcludePattern("scala/.*"),
    Relocate("scala.**", "io.github.iltotore.spongescala.library.@1")
  )

}