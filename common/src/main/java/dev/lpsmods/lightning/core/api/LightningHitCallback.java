package dev.lpsmods.lightningglass.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LightningEntity;

/**
 * Callback for when lightning hits a block.
 */
public interface LightningHitCallback {
    Event<LightningHitCallback> EVENT = EventFactory.createArrayBacked(LightningHitCallback.class, (listeners) -> (LightningEntity entity, CachedBlockPosition cachedBlock) -> {
        for (LightningHitCallback listener : listeners) {
            listener.onHit(entity, cachedBlock);
        }
    });

    void onHit(LightningEntity entity, CachedBlockPosition cachedBlock);
}
