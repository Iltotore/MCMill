# MCMill

MCMill is a [Mill](https://github.com/com-lihaoyi/mill) plugin for Minecraft Development. It actually supports the
Spigot and Sponge APIs.

[Mill](https://github.com/com-lihaoyi/mill) is a lightweight and simple build tool using Scala for build scripts and
supports Java and Scala out of the box.

# Setup

## Import

You can import MCMIll in your buildscript by using the $ivy import syntax:

```scala
import $ivy.`io.github.iltotore::mcmill-<api>:<version>`
import io.github.iltotore.mcmill.<api>._
```

<details>
<summary>Example</summary>

```scala
import $ivy.`io.github.iltotore::mcmill-spigot:1.0.0`
import io.github.iltotore.mcmill.spigot._
```

</details>

## Spigot Support

You can set up a simple Spigot plugin by inheriting from the `SpigotModule` trait:

```scala
import $ivy.`io.github.iltotore::mcmill-spigot:version`
import io.github.iltotore.mcmill.spigot._

object spigot extends ScalaModule with SpigotModule {

  def scalaVersion = "2.13.8"

  def spigotVersion = "1.16.5-R0.1-SNAPSHOT"

  def spigotMetadata = SpigotMetadata(
    name = "plugin name",
    version = "0.1",
    mainClass = "path.to.your.MainClass"
  )

```

See scaladoc for details.

## Sponge Support

You can set up a simple Sponge plugin by inheriting from the `SpongeModule` trait:

```scala
import mill._, scalalib._

import $ivy.`io.github.iltotore::mcmill-sponge:version`
import io.github.iltotore.mcmill.sponge._

object main extends ScalaModule with SpongeModule {

  def scalaVersion = "2.13.8"

  def spongeVersion = "7.3.0"

  def spongeMetadata = SpongeMetadata(
    modId = "modid",
    name = "YourPluginName",
    version = "0.1"
  )
}
```

See scaladoc for details.

**Note: If you use [SpongeScala](https://github.com/Iltotore/SpongeScala), you can use the SpongeScalaModule**