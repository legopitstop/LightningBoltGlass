package dev.lpsmods.lightning;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class LightningBoltGlass {

    public LightningBoltGlass(IEventBus eventBus) {
        Bootstrap.init();
    }
}