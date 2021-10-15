# JeuEchec
Jeu d'échec fait en 2018 quand j'ai appris le java

Projet complet qui fait manipuler les JFrames, JPanels et JLabels (gestion de l’interface graphique), ainsi que de l'action sur réponse évènementielle d'un clic de souris. 

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

### Compte-rendu
Chaque coup jugé légal est écrit dans un compte-rendu de partie, qui répertorie les déplacements mais également les prises, 
les coups spéciaux et les mises en échec.
