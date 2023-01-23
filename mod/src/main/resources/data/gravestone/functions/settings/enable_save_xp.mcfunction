scoreboard players set $save_xp graveowner 1
execute if score $save_xp graveowner matches 1 run tellraw @s ["",{"text":"Grave XP saving is currently "},{"text":"[Enabled]","color":"green"},{"text":"."}]
execute if score $save_xp graveowner matches 0 run tellraw @s ["",{"text":"Grave XP saving is currently "},{"text":"[Disabled]","color":"red"},{"text":"."}]