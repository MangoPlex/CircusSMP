package xyz.mangostudio.smp.mod;

import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

public class LootTableModifiers {
    private static final Identifier FISHERMAN_SEA_TARRAGON = new Identifier("fisher_man", "epic/sea_tarragon");

    static void register() {
        LootTableEvents.REPLACE.register(((resourceManager, lootManager, id, original, source) -> {
            if (FISHERMAN_SEA_TARRAGON.equals(id)) {
                LootPool.Builder poolBuilder = new LootPool.Builder();

                for (LootPool pool : original.pools) {
                    for (LootPoolEntry entry : pool.entries) {
                        if (entry instanceof ItemEntry itemEntry && itemEntry.item == Items.PLAYER_HEAD) continue;

                        poolBuilder.with(entry);
                    }
                }

                return new LootTable.Builder().pool(poolBuilder.build()).build();
            }

            return original;
        }));
    }
}
