package xyz.mangostudio.smp.mod.patch;

import java.util.Arrays;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetNbtLootFunction;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

public class LootTableModifiers {
    public static void register() {
        LootTableEvents.REPLACE.register(((resourceManager, lootManager, id, original, source) -> {
            if (id.getNamespace().equalsIgnoreCase("fisher_man") || id.getPath().contains("gameplay/fishing")) {
                LootPool.Builder poolBuilder = new LootPool.Builder();

                Arrays.stream(original.pools).flatMap(pool -> Arrays.stream(pool.entries))
                        .filter(entry -> entry instanceof ItemEntry)
                        .forEach(entry -> {
                            Arrays.stream(((ItemEntry) entry).functions).filter(function -> function instanceof SetNbtLootFunction)
                                    .forEach(lootFunction -> {
                                        if (((SetNbtLootFunction) lootFunction).nbt.getBoolean("hydra")) poolBuilder.with(entry);
                                    });
                        });

                return new LootTable.Builder().pool(poolBuilder.build()).build();
            }

            return original;
        }));
    }
}
