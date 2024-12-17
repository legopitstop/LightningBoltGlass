package dev.lpsmods.lightningglass.server.fulgurite;

import com.mojang.serialization.Codec;
import dev.lpsmods.lightningglass.server.fulgurite.type.ReplaceSingleBlockFulgurite;
import dev.lpsmods.lightningglass.server.fulgurite.type.RootSystemFulgurite;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FulguriteTypes {
    public static final Codec<FulguriteType<?>> TYPE_CODEC = FulguriteType.REGISTRY.getCodec();
    public static final Codec<Fulgurite> CODEC = TYPE_CODEC.dispatch("type", Fulgurite::getType, FulguriteType::getCodec);

    public static final FulguriteType<RootSystemFulgurite> ROOT_SYSTEM = register("root_system", new FulguriteType<>(RootSystemFulgurite.CODEC));
    public static final FulguriteType<ReplaceSingleBlockFulgurite> REPLACE_SINGLE_BLOCK = register("replace_single_block", new FulguriteType<>(ReplaceSingleBlockFulgurite.CODEC));

    public static <T extends Fulgurite> FulguriteType<T> register(String id, FulguriteType<T> fulguriteType) {
        return Registry.register(FulguriteType.REGISTRY, Identifier.of(id), fulguriteType);
    }

}
