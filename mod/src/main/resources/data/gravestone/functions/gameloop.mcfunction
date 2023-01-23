#Create new graves on player death
#execute as @a[scores={deadplayer=1..}] at @s if block ~ ~ ~ #gravestone:grave_dont_replace align xyz positioned ~ ~1 ~ run function gravestone:new_grave/summon_grave
#execute as @a[scores={deadplayer=1..}] at @s unless block ~ ~ ~ #gravestone:grave_dont_replace align xyz run function gravestone:new_grave/summon_grave
execute as @a[scores={deadplayer=1..}] at @s align xyz run function gravestone:new_grave/summon_grave

#Check grave owner on block destruction. Bypass this check if grave ownership is off. 
execute as @e[type=minecraft:marker,tag=Gravestone] at @s unless block ~ ~ ~ minecraft:smooth_stone_slab if score $grave_ownership graveowner matches 1 run function gravestone:current_grave/check_owner
execute as @e[type=minecraft:marker,tag=Gravestone] at @s unless block ~ ~ ~ minecraft:smooth_stone_slab if score $grave_ownership graveowner matches 0 run function gravestone:current_grave/break_grave


#Make gravestone markers glow.
execute if score $glowing_graves graveowner matches 1 as @e[type=minecraft:armor_stand,tag=GravestoneMarker] unless entity @s[nbt={ActiveEffects:[{Id:24b}]}] if entity @p[distance=..300] run effect give @s minecraft:glowing 100 1 false

#Clear gravestone backup cache
execute if data storage minecraft:gravestone GraveBackups[15] run data remove storage minecraft:gravestone GraveBackups[0]

#Reset gameplay and grave management scoreboards
scoreboard players reset @a[scores={deadplayer=1..}] deadplayer 
scoreboard players reset @a[scores={graveowner=0..}] graveowner
data remove storage minecraft:gravestone GraveOwner
scoreboard players reset @a[scores={breakgravestone=1..}] breakgravestone

#Manage scoreboards involved in triggering and displaying settings
scoreboard players enable @a[gamemode=creative] settings
execute as @a[scores={settings=1..}] run function gravestone:settings/settings
scoreboard players reset @a[scores={settings=1..}] settings


#Save players from the void in the End
execute as @a at @s if entity @s[y=-8,dy=2,nbt={Dimension:"minecraft:the_end"}] if score $end_fallthrough graveowner matches 1 in minecraft:overworld run teleport ~ 400 ~