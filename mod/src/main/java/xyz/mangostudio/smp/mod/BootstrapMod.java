package xyz.mangostudio.smp.mod;

import java.util.List;

import net.minecraft.world.GameRules;

import net.fabricmc.api.DedicatedServerModInitializer;

public class BootstrapMod implements DedicatedServerModInitializer {
    public static final List<String> BYPASS_ONLINE_MODE = List.of(
            "djchip"
    );

    @Override
    public void onInitializeServer() {
    }
}
