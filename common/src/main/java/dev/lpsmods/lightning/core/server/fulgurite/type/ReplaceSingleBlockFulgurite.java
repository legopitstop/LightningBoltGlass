package dev.lpsmods.lightningglass.server.fulgurite.type;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lpsmods.lightningglass.server.fulgurite.Fulgurite;
import dev.lpsmods.lightningglass.server.fulgurite.FulguriteType;
import dev.lpsmods.lightningglass.server.fulgurite.FulguriteTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public record ReplaceSingleBlockFulgurite(BlockPredicate predicate, BlockState block) implements Fulgurite {
    public static final MapCodec<ReplaceSingleBlockFulgurite> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(
                BlockPredicate.CODEC.fieldOf("predicate").forGetter(ReplaceSingleBlockFulgurite::predicate),
                BlockState.CODEC.fieldOf("block").forGetter(ReplaceSingleBlockFulgurite::block)
        ).apply(instance, ReplaceSingleBlockFulgurite::new);
    });

    public ReplaceSingleBlockFulgurite(BlockPredicate predicate, BlockState block) {
        this.predicate = predicate;
        this.block = block;
    }

    @Override
    public FulguriteType<ReplaceSingleBlockFulgurite> getType() {
        return FulguriteTypes.REPLACE_SINGLE_BLOCK;
    }

    @Override
    public boolean test(CachedBlockPosition block) {
        return this.predicate.test((ServerWorld)block.getWorld(), block.getBlockPos());
    }

    @Override
    public void generate(ServerWorld world, BlockPos blockPos) {
        world.setBlockState(blockPos, this.block);
    }
}
