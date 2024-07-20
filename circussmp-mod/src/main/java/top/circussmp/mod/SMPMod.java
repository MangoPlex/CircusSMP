package top.circussmp.mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.circussmp.mod.patch.EntityAttributeModifiers;

import net.fabricmc.api.DedicatedServerModInitializer;

public class SMPMod implements DedicatedServerModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Circus-SMP");

    @Override
    public void onInitializeServer() {
        EntityAttributeModifiers.register();
    }
}
