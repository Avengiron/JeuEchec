package pieces;

import java.util.ArrayList;
import jeu.Cellule;

/**
 * Classe Fou pour jeu d'echecs.
 * @author Xavier
 */
public class Fou extends Piece {

  /** Constructeur sans argument */
  public Fou() {
  }

  /**
   * Constructeur avec argument.
   * @param couleur Couleur du Fou
   */
  public Fou(String couleur) {
    this.lettre = 'F';
    this.couleur = couleur;
    this.icone = lettre + couleur.substring(0, 1) + ".png";
  }

  /**
   * Constructeur pour copie
   * @param aCopier Fou a copier
   */
  public Fou(Fou aCopier) {
    this.lettre = aCopier.lettre;
    this.couleur = aCopier.couleur;
    this.icone = aCopier.icone;
  }

  /**
   * Deplace le Fou a la place indiquee
   * @param x Position x de la Cellule de depart
   * @param y Position y de la Cellule de depart
   * @param p Liste de Piece representant le chemin jusqu'a la case d'arrivee du
   * Fou
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
      int lastIndice = p.size() - 1;
      if ((x != p.get(lastIndice).getPosX()) && (y != p.get(lastIndice).getPosY())) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
}
