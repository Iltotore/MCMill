package io.github.iltotore.mcmill.sponge

import mill.{Agg, T}
import mill.api.Loose
import mill.define.Target
import mill.modules.Assembly
import mill.modules.Assembly.Rule.Relocate
import mill.scalalib.{Dep, DepSyntax}

trait SpongeScalaModule extends SpongeModule {

  def spongeScalaVersion: String

  override def ivyDeps: Target[Loose.Agg[Dep]] = T {
    super.ivyDeps() ++ Agg(ivy"io.github.iltotore::sponge-scala:$spongeScalaVersion")
  }

  override def assemblyRules: Seq[Assembly.Rule] =
    super.assemblyRules :+ Relocate("scala.**", "io.github.iltotore.spongescala.library")
}
