package dev.lpsmods.lightningglass.registry;

import dev.lpsmods.lightningglass.server.fulgurite.Fulgurite;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.concurrent.ConcurrentHashMap;

public class FulguriteRegistry {
    public static final ConcurrentHashMap<Identifier, Fulgurite> FULGURITE = new ConcurrentHashMap<>();
    public static void registerFulgurite(String id, Fulgurite fulgurite) {
        FulguriteRegistry.FULGURITE.put(Identifier.of(id), fulgurite);
    }
    public static void registerFulgurite(Identifier id, Fulgurite fulgurite) {
        FulguriteRegistry.FULGURITE.put(id, fulgurite);
    }

    public static void clearFulgurites() {
        FulguriteRegistry.FULGURITE.clear();
    }

    public static void removeFulgurite(Identifier id) {
        FulguriteRegistry.FULGURITE.remove(id);
    }

    public static int sizeFulgurite() {
        return FulguriteRegistry.FULGURITE.size();
    }

    public static void generateFulgurite(ServerWorld world, CachedBlockPosition cachedBlock) {
        for (Fulgurite fulgurite : FulguriteRegistry.FULGURITE.values()) {
            if (fulgurite.test(cachedBlock)) {
                fulgurite.generate(world, cachedBlock.getBlockPos());
                break;
            }
        }
    }
}
