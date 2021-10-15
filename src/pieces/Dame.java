package pieces;

import java.util.ArrayList;
import jeu.Cellule;

/**
 * Classe Dame pour jeu d'echecs.
 * @author Xavier
 */
public class Dame extends Piece {

  /** Constructeur sans argument */
  public Dame() {
  }

  /**
   * Constructeur avec argument.
   * @param couleur Couleur de la Dame
   */
  public Dame(String couleur) {
    this.lettre = 'D';
    this.couleur = couleur;
    this.icone = lettre + couleur.substring(0, 1) + ".png";
  }

  /**
   * Constructeur pour copie
   * @param aCopier Dame a copier
   */
  public Dame(Dame aCopier) {
    this.lettre = aCopier.lettre;
    this.couleur = aCopier.couleur;
    this.icone = aCopier.icone;
  }

  /**
   * Deplace la Dame a la place indiquee
   * @param x Position x de la Cellule de depart
   * @param y Position y de la Cellule de depart
   * @param p Liste de Piece representant le chemin jusqu'a la case d'arrivee de
   * la Dame
   * @return Booleen de resultat du deplacement; true = deplacement autorise
   */
  @Override
  public boolean deplacementAutorise(int x, int y, ArrayList<Cellule> p) {
    if (p.size() > 0) {
      if (p.size() > 1) {
        for (int i = 0; i <= p.size() - 2; i++) {
          if (p.get(i).getPiece() != null) {
            return false;
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }
}
