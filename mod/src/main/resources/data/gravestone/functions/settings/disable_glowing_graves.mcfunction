scoreboard players set $glowing_graves graveowner 0
execute if score $glowing_graves graveowner matches 1 run tellraw @s ["",{"text":"Grave glow effect is currently "},{"text":"[Enabled]","color":"green"},{"text":"."}]
execute if score $glowing_graves graveowner matches 0 run tellraw @s ["",{"text":"Grave glow effect is currently "},{"text":"[Disabled]","color":"red"},{"text":"."}]