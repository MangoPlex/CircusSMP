#Make sure the gravestone block (smooth stone slab) doesn't drop to prevent infinite slabs. 
execute as @e[type=minecraft:item,nbt={Item:{id:"minecraft:smooth_stone_slab",Count:1b}}] run kill @s

#Copy grave owner's UUID from armor stand to world storage. 
data modify storage minecraft:gravestone GraveOwner set from entity @s data.Owner

#Try to overwrite the world storage with the UUID of the player who tried to break the gravestone. 
execute as @a[limit=1,scores={breakgravestone=1..}] if data storage minecraft:gravestone GraveOwner store success score @s graveowner run data modify storage minecraft:gravestone GraveOwner set from entity @s UUID

#If overwrite was unsuccessful (i.e. the UUIDs match), destroy the grave and drop contents. 
execute if entity @a[limit=1,scores={breakgravestone=1..,graveowner=0}] run function gravestone:current_grave/break_grave

#Otherwise, replace the slab.
execute if entity @a[limit=1,scores={breakgravestone=1..,graveowner=1}] run setblock ~ ~ ~ minecraft:smooth_stone_slab replace

#If in creative mode, destroy the grave anyway. 
execute if entity @p[gamemode=creative] run function gravestone:current_grave/break_grave
