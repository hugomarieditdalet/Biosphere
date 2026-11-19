Mon IA fonctionne avec un système de bonus/Malus. Mon IA peut faire 5 actions différentes : Planter au bord pour éviter de se faire étouffer, Planter au centre pour accroître la vitalité de mes plantes, Fertiliser, Couper, ainsi que l’action Plante Tueuse qui consiste à planter tout en étouffant la plante adverse.

Au début du tour, mon IA va attribuer des bonus ou des malus à ces 5 actions en fonction du coup de l’adversaire. Par exemple, si l'adversaire coupe une de mes plantes durant ce tour, mon IA va éviter de fertiliser, il va donc attribuer un malus de -2 à l’action fertiliser. Ou encore si l'adversaire plante autour de mes plantes pour pouvoir les étouffer, je vais attribuer un bonus de +1 pour l’action “Planter au bord” pour forcer mon IA à planter au bord et ainsi éviter les étouffements.
Petite subtilité, au dernier tour je remets tous les bonus à 0. Pour prendre l’action qui me rapporte le plus de vitalité

Ensuite, mon IA parcourt chaque case du tableau et calcule la vitalité que je vais recevoir si je plante, je fertilise ou je coupe sur cette case. Bien évidemment, les actions impossibles à réaliser sur une case ne sont pas traitées. Ensuite, en fonction de l’action, je lui ajoute le bonus ou le malus qui lui convient. À la fin, je prends l’action qui a le plus de points et je la renvoie au grand ordonnateur.

J’ai réutilisé la classe voisine que j’ai codée durant la phase 1. Cette classe prend en paramètre une case du plateau et permet d’avoir des informations utiles comme par exemple le nombre de plantes voisines que possède une case.
