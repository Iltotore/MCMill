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

object spigot extends SpigotModule {

  def spigotVersion = "1.19-R0.1-SNAPSHOT"

  def spigotMetadata = SpigotMetadata(
    name = "plugin name",
    version = "0.1",
    mainClass = "path.to.your.MainClass"
  )
}
```

See scaladoc for details.

### Automatic plugin dependency downloading

Spigot has an experimental `libraries` option in the `plugin.yml` file.
This option allows the developer to pass libraries to download before loading the plugin.

If `SpigotMetadata#downloadIvyDeps` is `true` (by default: `false`), MCMill will automatically add dependencies returned
by `pluginDeps` in the generated `plugin.yml`. Plugin dependencies are all runtime dependencies by default but can be
overridden:
```scala
object spigot extends SpigotModule {

  def spigotVersion = "1.19-R0.1-SNAPSHOT"

  def spigotMetadata = SpigotMetadata(
    name = "plugin name",
    version = "0.1",
    mainClass = "path.to.your.MainClass",
    downloadIvyDeps = true
  )
  
  def pluginIvyDeps = Agg( //Default: allIvyDeps() ++ runIvyDeps()
    ivy"org.group:special-dep-needed-before-enabling-this-plugin:version"
  )
}
```

## Sponge Support

You can set up a simple Sponge plugin by inheriting from the `SpongeModule` trait:

```scala
import mill._, scalalib._

import $ivy.`io.github.iltotore::mcmill-sponge:version`
import io.github.iltotore.mcmill.sponge._

object main extends SpongeModule {

  def spongeVersion = "9.0.0"

  def spongeMetadata = SpongeMetadata(
    modId = "modid",
    name = "YourPluginName",
    version = "0.1"
  )
}
```

See scaladoc for details.

**Note: If you use [SpongeScala](https://github.com/Iltotore/SpongeScala), you can use the SpongeScalaModule**