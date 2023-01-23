#Summon a new armor stand to act as gravestone entity. 
summon minecraft:marker ~0.5 ~0.5 ~0.5 {Tags:["Gravestone","NewGrave"]}
execute positioned ~0.5 ~0.5 ~0.5 as @e[type=minecraft:marker,limit=1,sort=nearest,tag=Gravestone,tag=NewGrave] at @s run function gravestone:new_grave/setup_new_grave