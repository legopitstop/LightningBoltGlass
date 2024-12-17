package dev.lpsmods.lightningglass.server;

import com.google.gson.*;
import dev.lpsmods.lightningglass.LightningGlass;
import dev.lpsmods.lightningglass.registry.FulguriteRegistry;
import dev.lpsmods.lightningglass.server.fulgurite.FulguriteType;
import dev.lpsmods.lightningglass.server.fulgurite.Fulgurite;
import com.mojang.serialization.JsonOps;
import dev.lpsmods.lightningglass.server.fulgurite.FulguriteTypes;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;

public class FulguriteLoader implements SimpleSynchronousResourceReloadListener {
    public static final Identifier ID = Identifier.of(LightningGlass.MOD_ID, "fulgurite");
    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public void reload(ResourceManager manager) {
        FulguriteRegistry.clearFulgurites();
        LightningGlass.LOGGER.info("Loading fulgurite...");
        Map<Identifier, Resource> resources = manager.findResources("fulgurite", (identifier) -> identifier.getPath().endsWith(".json"));
        for (Identifier resourceId : resources.keySet()) {
            Identifier id = resourceId.withPath(resourceId.getPath().replace("fulgurite/", "").replace(".json", ""));
            try {
                JsonObject jsonObj = (JsonObject) JsonParser.parseReader(new InputStreamReader(resources.get(resourceId).getInputStream()));
//                String string = JsonHelper.getString(jsonObj, "type");
//                FulguriteType type = FulguriteType.REGISTRY.get(string);
//                Optional<? extends Fulgurite> ful = type.codec().parse(JsonOps.INSTANCE, jsonObj).result();

                Optional<? extends Fulgurite> ful = FulguriteTypes.CODEC.parse(JsonOps.INSTANCE, jsonObj).result();
                ful.ifPresent(fulgurite -> FulguriteRegistry.registerFulgurite(id, fulgurite));
            }  catch (Exception e) {
                LightningGlass.LOGGER.error("Failed to load "+id+": "+e);
            }
        }
        LightningGlass.LOGGER.info("Loaded ("+FulguriteRegistry.sizeFulgurite()+") fulgurite");
    }
}
