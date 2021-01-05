# MCMill
MCMill is a Mill plugin for Minecraft Development. It actually supports the Sponge API.

# Setup
## Import
You can import MCMIll in your buildscript by using the $ivy import syntax:
```scala
import $ivy.`io.github.iltotore::mc-mill:version`
```

## Sponge Support
You can setup a simple Sponge plugin by inheriting from the `SpongeModule` trait:

```scala
import mill._, scalalib._

import $ivy.`io.github.iltotore::mc-mill:version`
import io.github.iltotore.mcmill.sponge._

object main extends ScalaModule with SpongeModule {
  
  def scalaVersion = "2.13.4"
  
  def spongeVersion = "7.3.0"
  
  def spongeMetadata = SpongeMetadata(
    modId = "modid",
    name = "YourPluginName",
    version = "0.1"
  )
}
```
**Note: If you use [SpongeScala](https://github.com/Iltotore/SpongeScala), you can use the SpongeScalaModule**