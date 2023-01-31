package xyz.mangostudio.smp.mod.patch;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetNbtLootFunction;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

public class LootTableModifiers {
    public static void register() {
        LootTableEvents.REPLACE.register(((resourceManager, lootManager, id, original, source) -> {
            if (id.getNamespace().equalsIgnoreCase("fisher_man") || id.getPath().contains("gameplay/fishing")) {
                LootTable.Builder tableBuilder = LootTable.builder()
                        .type(original.getType())
                        .apply(List.of(original.functions));

                Arrays.stream(original.pools).forEach(pool -> {
                    LootPool.Builder poolBuilder = LootPool.builder();
                    AtomicBoolean isPoolModified = new AtomicBoolean(false);

                    Arrays.stream(pool.entries)
                            .forEach(entry -> {
                                if (entry instanceof ItemEntry)
                                    Arrays.stream(((ItemEntry) entry).functions).forEach(function -> {
                                        if (function instanceof SetNbtLootFunction lootFunction)
                                            if (!lootFunction.nbt.getBoolean("hydra")) poolBuilder.with(entry);
                                        else poolBuilder.with(entry);
                                    });
                                else poolBuilder.with(entry);
                            });

                    if (!isPoolModified.get()) tableBuilder.pool(pool);
                    else {
                        tableBuilder.pool(poolBuilder
                                .conditionally(List.of(pool.conditions))
                                .apply(List.of(pool.functions))
                                .rolls(pool.rolls)
                                .bonusRolls(pool.bonusRolls)
                                .build()
                        );
                    }
                });

                return tableBuilder.build();
            }

            return original;
        }));
    }
}
