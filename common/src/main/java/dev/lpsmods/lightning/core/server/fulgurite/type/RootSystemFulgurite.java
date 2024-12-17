package dev.lpsmods.lightningglass.server.fulgurite.type;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.lpsmods.lightningglass.server.fulgurite.Fulgurite;
import dev.lpsmods.lightningglass.server.fulgurite.FulguriteType;
import dev.lpsmods.lightningglass.server.fulgurite.FulguriteTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.loot.LootTable;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;

public record RootSystemFulgurite(BlockPredicate predicate, BlockState block, int maxColumnHeight,  int radius, int placementAttempts) implements Fulgurite {
    public static final MapCodec<RootSystemFulgurite> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
        return instance.group(
                BlockPredicate.CODEC.fieldOf("predicate").forGetter(RootSystemFulgurite::predicate),
                BlockState.CODEC.fieldOf("block").forGetter(RootSystemFulgurite::block),
                Codec.intRange(1, 4096).fieldOf("column_max_height").orElse(100).forGetter(RootSystemFulgurite::maxColumnHeight),
                Codec.intRange(1, 64).fieldOf("radius").orElse(3).forGetter(RootSystemFulgurite::radius),
                Codec.intRange(1, 256).fieldOf("placement_attempts").orElse(20).forGetter(RootSystemFulgurite::placementAttempts)
        ).apply(instance, RootSystemFulgurite::new);
    });

    public RootSystemFulgurite(BlockPredicate predicate, BlockState block, int maxColumnHeight, int radius, int placementAttempts) {
        this.predicate = predicate;
        this.block = block;
        this.maxColumnHeight = maxColumnHeight;
        this.radius = radius;
        this.placementAttempts = placementAttempts;
    }

    @Override
    public FulguriteType<RootSystemFulgurite> getType() {
        return FulguriteTypes.ROOT_SYSTEM;
    }

    @Override
    public boolean test(CachedBlockPosition block) {
        return this.predicate.test((ServerWorld)block.getWorld(), block.getBlockPos());
    }

    // All the below code is a modified version of net.minecraft.world.gen.feature.RootSystemFeature

    @Override
    public void generate(ServerWorld world, BlockPos blockPos) {
        Random random = world.getRandom();
        generateRootsColumn(blockPos, blockPos.getY() - this.maxColumnHeight, world, random);
    }

    private void generateRootsColumn(BlockPos pos, int maxY, StructureWorldAccess world, Random random) {
        int i = pos.getX();
        int j = pos.getZ();
        BlockPos.Mutable mutable = pos.mutableCopy();
        for (int k = pos.getY(); k > maxY; k--) {
            generateRoots(world, random, i, j, mutable.set(i, k, j));
        }
    }

    private void generateRoots(StructureWorldAccess world, Random random, int x, int z, BlockPos.Mutable mutablePos) {
        int i = this.radius;
        for(int j = 0; j < this.placementAttempts; ++j) {
            mutablePos.set(mutablePos, random.nextInt(i) - random.nextInt(i), 0, random.nextInt(i) - random.nextInt(i));
            if (this.predicate.test((ServerWorld)world, mutablePos)) {
                world.setBlockState(mutablePos, this.block, 2);
            }
            mutablePos.setX(x);
            mutablePos.setZ(z);
        }
    }
}
