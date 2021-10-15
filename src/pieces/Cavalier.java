package pieces;

import java.util.ArrayList;
import jeu.Cellule;

/**
 * Classe Cavalier pour jeu d'echecs.
 * @author Xavier
 */
public class Cavalier extends Piece {

  /** Constructeur sans argument */
  public Cavalier() {
  }

  /**
   * Constructeur avec argument.
   * @param couleur Couleur du Cavalier
   */
  public Cavalier(String couleur) {
    this.lettre = 'C';
    this.couleur = couleur;
    this.icone = lettre + couleur.substring(0, 1) + ".png";
  }

  /**
   * Constructeur pour copie
   * @param aCopier Cavalier a copier
   */
  public Cavalier(Cavalier aCopier) {
    this.lettre = aCopier.lettre;
    this.couleur = aCopier.couleur;
    this.icone = aCopier.icone;
  }

  /**
   * Deplace le Cavalier a la place indiquee
   * @param x Position x de la Cellule de depart
   * @param y Position y de la Cellule de depart
   * @param p Liste de Piece representant la case d'arrivee du Cavalier
   * @return Booleen de resultat du deplacement; true = deplacement autorise
   */
  @Override
  public boolean deplacementAutorise(int x, int y, ArrayList<Cellule> p) {
    if (p.size() == 1) {
      if ((Math.abs(x - p.get(0).getPosX()) + Math.abs(y - p.get(0).getPosY()) == 3)
        && (x != p.get(0).getPosX()) && (y != p.get(0).getPosY())) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
}
