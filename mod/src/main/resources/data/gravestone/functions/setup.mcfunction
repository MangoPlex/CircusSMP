scoreboard objectives add graveowner dummy
scoreboard objectives add settings trigger
scoreboard objectives add breakgravestone minecraft.mined:minecraft.smooth_stone_slab
scoreboard objectives add deadplayer deathCount
execute unless score $grave_ownership graveowner matches 0 run scoreboard players set $grave_ownership graveowner 1
execute unless score $end_fallthrough graveowner matches 0 run scoreboard players set $end_fallthrough graveowner 1
execute unless score $glowing_graves graveowner matches 0 run scoreboard players set $glowing_graves graveowner 1