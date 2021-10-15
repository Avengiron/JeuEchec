package pieces;

import java.util.ArrayList;
import jeu.Cellule;

/**
 * Classe Tour pour jeu d'echecs.
 * @author Xavier
 */
public class Tour extends Dame {
  /** Booleen indiquant si la piece a deja bouge */
  protected boolean aDejaBouge;

  /** Constructeur sans argument */
  public Tour() {
  }

  /**
   * Constructeur avec argument.
   * @param couleur Couleur de la Tour
   */
  public Tour(String couleur) {
    super();
    this.lettre = 'T';
    this.couleur = couleur;
    this.icone = lettre + couleur.substring(0, 1) + ".png";
    this.aDejaBouge = false;
  }

  /**
   * Constructeur pour copie
   * @param aCopier Tour a copier
   */
  public Tour(Tour aCopier) {
    this.lettre = aCopier.lettre;
    this.couleur = aCopier.couleur;
    this.icone = aCopier.icone;
    this.aDejaBouge = aCopier.aDejaBouge;
  }

  /**
   * Obtient le statut de deplacement de la Tour
   * @return true = la Tour a deja ete bougee
   */
  public boolean isaDejaBouge() {
    return aDejaBouge;
  }

  /**
   * Modifie le statut de deplacement de la Tour
   * @param aDejaBouge Statut de deplacement
   */
  public void setaDejaBouge(boolean aDejaBouge) {
    this.aDejaBouge = aDejaBouge;
  }

  /**
   * Deplace la Tour a la place indiquee
   * @param x Position x de la Cellule de depart
   * @param y Position y de la Cellule de depart
   * @param p Liste de Piece representant le chemin jusqu'a la case d'arrivee de
   * la Tour
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

      if (x != p.get(lastIndice).getPosX() && y == p.get(lastIndice).getPosY()) {
        return true;
      } else if (y != p.get(lastIndice).getPosY() && x == p.get(lastIndice).getPosX()) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
}
