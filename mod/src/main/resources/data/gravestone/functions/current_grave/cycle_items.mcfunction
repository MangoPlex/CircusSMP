execute if data entity @s data.Items[0] run summon minecraft:item ~ ~ ~ {Item:{id:"minecraft:barrier",Count:1b},Tags:["GraveStoredItem"]}
data modify entity @e[type=minecraft:item,limit=1,sort=nearest,tag=GraveStoredItem] Item set from entity @s data.Items[0]
tag @e[type=minecraft:item,limit=1,sort=nearest,tag=GraveStoredItem] remove GraveStoredItem
data remove entity @s data.Items[0]
execute if data entity @s data.Items[0] as @s run function gravestone:current_grave/cycle_items