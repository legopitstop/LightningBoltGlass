package dev.lpsmods.lightningglass.server.fulgurite;

import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public interface Fulgurite {
    public FulguriteType getType();

    boolean test(CachedBlockPosition block);

    public void generate(ServerWorld world, BlockPos blockPos);

    @FunctionalInterface
    public static interface Builder {
        public FulguriteType build();
    }
}
