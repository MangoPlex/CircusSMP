#This function is run directly if grave ownership is off. Otherwise, first check if the person breaking the grave is the owner of the grave. 

execute as @e[type=minecraft:item,nbt={Item:{id:"minecraft:smooth_stone_slab",Count:1b}}] run kill @s

execute if data entity @s data.Items[0] as @s run function gravestone:current_grave/cycle_items

#Kill the armor stand
execute positioned ~ ~-1.45 ~ as @e[type=minecraft:armor_stand,limit=1,sort=nearest,tag=GravestoneMarker] run kill @s
kill @s