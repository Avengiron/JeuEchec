# JeuEchec
Jeu d'échec fait en 2018 quand j'ai appris le java

Projet complet qui fait manipuler de l'héritage avec tout un système de classe, une gestion de l'interface graphique avec 
des JFrames, JPanels et JLabels, ainsi que de l'action sur réponse évènementielle d'un clic de souris. 

### Tour de jeu
Un tour de jeu se déroule de la manière suivante :
- Une cellule de départ est sélectionnée 
- Une cellule d'arrivée est sélectionnée OU la cellule de départ est désélectionnée
- Lorsque les deux cellules sont correctement sélectionnées, on vérifie que le déplacement de la pièce est autorisé
  - Si non, on reprend le cycle depuis le début
- On procède au déplacement
- On vérifie si un coup spécial doit être fait (roque, prise en passant)
- On met à jour les données complémentaires de la pièce (pour pion, tour et roi)
- On vérifie si une promotion doit être effectuée
- On met à jour la liste des pièces encore en jeu
- On vérifie si le déplacement effectué ne met pas en danger notre propre roi
  - Si oui, on annule le déplacement en remettant le plateau de jeu a l'état stable précédent
- On vérifie si le déplacement effectué met en danger le roi adverse (échec)
- On vérifie si l'échec du roi adverse peut être contré par n'importe quel déplacement de l'adversaire
  - Si non, annonce la fin du jeu
- On change le joueur qui doit jouer

Une sous-fenêtre est créée pour que le joueur choisisse sa pièce en cas de promotion. Idem en cas de fin de partie.
### Organisation des classes
Le schéma ci-dessous montre l'organisation des classes. Une classe abstraite Piece gère toutes les informations communes à toutes
les pièces (son type, sa couleur et son image). Des informations complémentaires sont ajoutées sur le pion, dont on a besoin de savoir 
sa précédente position pour le premier déplacement et la prise en passant, ainsi que pour la tour et le roi, dont on a besoin de savoir 
s'ils ont déjà été déplacés pour gérer le roque. Chaque pièce implémente la méthode deplacementAutorise qui calcule si le déplacement est légal 
de la cellule de départ vers la cellule d'arrivée (chaque pièce ayant sa propre marche).


![Test](https://github.com/Avengiron/HostReadMeImages/blob/main/JeuEchec/ClassOrgaTransparent.png)

### Compte-rendu
Chaque coup jugé légal (ayant passé toutes les étapes du cycle décrit dans **Tour de jeu**) est écrit dans un compte-rendu de partie, qui répertorie les déplacements mais également les prises, 
les coups spéciaux et les mises en échec.


