package io.github.iltotore.mcmill.sponge

import mill.{Agg, T}
import mill.api.Loose
import mill.define.Target
import mill.modules.Assembly
import mill.modules.Assembly.Rule.{Exclude, ExcludePattern, Relocate}
import mill.scalalib.{Dep, DepSyntax}

trait SpongeScalaModule extends SpongeModule {

  def spongeScalaVersion: String

  override def compileIvyDeps: Target[Loose.Agg[Dep]] = super.compileIvyDeps() ++ Agg(ivy"io.github.iltotore::sponge-scala:$spongeScalaVersion")

  override def assemblyRules: Seq[Assembly.Rule] = super.assemblyRules ++ Seq(
    Relocate("scala.**", "io.github.iltotore.spongescala.library"),
    Exclude("scala-library")
  )

}