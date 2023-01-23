data modify entity @s data.Owner set from entity @p[scores={deadplayer=1..}] UUID
data modify entity @s data.Items set value []

#Tag nearby items and copy their data to the armor stand's data. 
tag @e[type=minecraft:item,distance=..5] add GraveItem
execute as @e[type=minecraft:item,distance=..5,tag=GraveItem] run function gravestone:new_grave/copy_items_to_grave

#Backup grave storage to world storage in case something goes wrong haha :)
data modify storage minecraft:gravestone GraveBackups append from entity @s data.Items

tag @s remove NewGrave
summon minecraft:armor_stand ~ ~-1.45 ~ {Tags:["GravestoneMarker"],NoGravity:1,ArmorItems:[{},{},{},{id:"minecraft:player_head",Count:1b}],Invulnerable:1b,Invisible:1b,Marker:1b}

setblock ~ ~ ~ minecraft:smooth_stone_slab destroy