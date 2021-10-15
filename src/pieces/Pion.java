package pieces;

import java.util.ArrayList;
import jeu.Cellule;

/**
 * Classe Pion pour jeu d'echecs.
 * @author Xavier
 */
public class Pion extends Fou {
  /** Derniere position x de la Piece */
  protected int lastPosX;

  /** Constructeur sans argument */
  public Pion() {
  }

  /**
   * Constructeur avec argument.
   * @param couleur Couleur du Pion
   */
  public Pion(String couleur) {
    super();
    this.lettre = 'P';
    this.couleur = couleur;
    this.icone = lettre + couleur.substring(0, 1) + ".png";
    this.lastPosX = -1;
  }

  /**
   * Constructeur pour copie
   * @param aCopier Pion a copier
   */
  public Pion(Pion aCopier) {
    this.lettre = aCopier.lettre;
    this.couleur = aCopier.couleur;
    this.icone = aCopier.icone;
    this.lastPosX = aCopier.lastPosX;
  }

  /**
   * Obtient la derniere position y du Pion
   * @return Derniere position y
   */
  public int getLastPosX() {
    return lastPosX;
  }

  /**
   * Fixe la derniere position y du Pion
   * @param lastPosX Derniere position x
   */
  public void setLastPosX(int lastPosX) {
    this.lastPosX = lastPosX;
  }

  /**
   * Deplace le Pion a la place indiquee
   * @param x Position x de la Cellule de depart
   * @param y Position y de la Cellule de depart
   * @param p Liste de Piece representant le chemin jusqu'a la case d'arrivee du
   * Pion
   * @return Booleen de resultat du deplacement; true = deplacement autorise
   */
  @Override
  public boolean deplacementAutorise(int x, int y, ArrayList<Cellule> p) {
    switch (p.size()) {
      case 2:
        if ((y == p.get(1).getPosY()) && (p.get(0).getPiece() == null)) {
          p.remove(0);
        } else if ((Math.abs(y - p.get(0).getPosY()) == 1)
          && (p.get(0).getPiece() == null)
          && (p.get(1).getPiece() instanceof Pion)
          && ((Pion) p.get(1).getPiece()).lastPosX != -1
          && (Math.abs(p.get(1).getPosX() - ((Pion) p.get(1).getPiece()).lastPosX) == 2)) {
          return true;
        } else {
          return false;
        }
      case 1:
        if (this.couleur.equals("Blanc")) {
          if (x == p.get(0).getPosX() + 1) {
            if (((y == p.get(0).getPosY()) && (p.get(0).getPiece() == null))
              || ((Math.abs(y - p.get(0).getPosY()) == 1) && (p.get(0).getPiece() != null) && (p.get(0).getPiece().couleur.equals("Noir")))) {
              return true;
            } else {
              return false;
            }
          } else if ((x == p.get(0).getPosX() + 2) && (x == 6) && (p.get(0).getPiece() == null)) {
            return true;
          } else {
            return false;
          }
        } else {
          if (x == p.get(0).getPosX() - 1) {
            if (((y == p.get(0).getPosY()) && (p.get(0).getPiece() == null))
              || ((Math.abs(y - p.get(0).getPosY()) == 1) && (p.get(0).getPiece() != null) && (p.get(0).getPiece().couleur.equals("Blanc")))) {
              return true;
            } else {
              return false;
            }
          } else if ((x == p.get(0).getPosX() - 2) && (x == 1)) {
            return true;
          } else {
            return false;
          }
        }
      default:
        return false;
    }
  }
}
