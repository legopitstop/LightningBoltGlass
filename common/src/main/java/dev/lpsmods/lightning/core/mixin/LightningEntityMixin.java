package dev.lpsmods.lightningglass.mixin;

import dev.lpsmods.lightningglass.api.LightningHitCallback;
import dev.lpsmods.lightningglass.registry.FulguriteRegistry;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningEntity.class)
public class LightningEntityMixin {
    @Shadow
    private int ambientTick;
    private boolean doFulguite = true;

    @Inject(at=@At("TAIL"), method="tick()V")
    private void injectMethod(CallbackInfo info) {
        LightningEntity entity = (LightningEntity)(Object)this;
       if (this.ambientTick < 0 && this.doFulguite) {
           spawnBlock(entity);
           this.doFulguite = false;
       }
    }

    private void spawnBlock(LightningEntity entity) {
        if (entity.getWorld() instanceof ServerWorld) {
            BlockPos pos = entity.getBlockPos().down();
            CachedBlockPosition cachedBlock = new CachedBlockPosition(entity.getWorld(), pos, false);
            LightningHitCallback.EVENT.invoker().onHit(entity, cachedBlock);
        }
    }
}
