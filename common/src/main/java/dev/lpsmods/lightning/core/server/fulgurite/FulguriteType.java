package dev.lpsmods.lightningglass.server.fulgurite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import dev.lpsmods.lightningglass.LightningGlass;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public record FulguriteType<T extends Fulgurite>(MapCodec<T> codec)  {
    public static final Registry<FulguriteType<?>> REGISTRY = new SimpleRegistry<>(
            RegistryKey.ofRegistry(Identifier.of(LightningGlass.MOD_ID, "fulgurite_types")), Lifecycle.stable());

    public FulguriteType(MapCodec<T> codec) {
        this.codec = codec;
    }

    public MapCodec<T> getCodec() {
        return this.codec;
    }
}
