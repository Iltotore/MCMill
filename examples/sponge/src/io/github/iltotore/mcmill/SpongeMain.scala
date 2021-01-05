package io.github.iltotore.mcmill

import com.google.inject.Inject
import org.slf4j.Logger
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameInitializationEvent
import org.spongepowered.api.plugin.Plugin

@Plugin(id = "mcmill")
class SpongeMain @Inject()(logger: Logger) {


  @Listener
  def onInit(event: GameInitializationEvent): Unit = logger.info("MCMill by Il_totore")
}