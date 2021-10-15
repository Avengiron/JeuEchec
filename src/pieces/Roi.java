package pieces;

import java.util.ArrayList;
import jeu.Cellule;

/**
 * Classe Roi pour jeu d'echecs.
 * @author Xavier
 */
public class Roi extends Tour {
  /** Liste de Piece qui mettent le Roi en echec */
  protected ArrayList<Piece> listeMenace;

  /** Constructeur sans argument */
  public Roi() {
  }

  /**
   * Constructeur avec argument.
   * @param couleur Couleur du Roi
   */
  public Roi(String couleur) {
    super();
    this.lettre = 'R';
    this.couleur = couleur;
    this.icone = lettre + couleur.substring(0, 1) + ".png";
    this.aDejaBouge = false;
    this.listeMenace = new ArrayList<>();
  }

  /**
   * Constructeur pour copie
   * @param aCopier Roi a copier
   */
  public Roi(Roi aCopier) {
    this.lettre = aCopier.lettre;
    this.couleur = aCopier.couleur;
    this.icone = aCopier.icone;
    this.aDejaBouge = aCopier.aDejaBouge;
    this.listeMenace = new ArrayList<>(aCopier.listeMenace);
  }

  /**
   * Obtient la liste de Piece menacant le Roi
   * @return Liste de Piece
   */
  public ArrayList<Piece> getListeMenace() {
    return this.listeMenace;
  }

  /**
   * Ajoute une Piece qui menace le Roi
   * @param p Piece menacante
   */
  public void menace(Piece p) {
    this.listeMenace.add(p);
  }

  /** Efface la liste de Pieces qui menace le Roi */
  public void clearListeMenace() {
    this.listeMenace.clear();
  }

  /**
   * Deplace le Roi a la place indiquee
   * @param x Position x de la Cellule de depart
   * @param y Position y de la Cellule de depart
   * @param p Liste de Piece representant le chemin jusqu'a la case d'arrivee du
   * Roi
   * @return Booleen de resultat du deplacement; true = deplacement autorise
   */
  @Override
  public boolean deplacementAutorise(int x, int y, ArrayList<Cellule> p) {
    switch (p.size()) {
      case 2:
        if (!this.aDejaBouge
          && this.listeMenace.isEmpty()
          && p.get(0).getPiece() == null
          && x == p.get(1).getPosX()
          && this.couleur.equals("Blanc")
          && p.get(1).getPiece() == null) {
          return true;
        } else if (!this.aDejaBouge
          && this.listeMenace.isEmpty()
          && p.get(0).getPiece() == null
          && x == p.get(1).getPosX()
          && this.couleur.equals("Noir")
          && p.get(1).getPiece() == null) {
          return true;
        } else {
          return false;
        }
      case 1:
        return true;
      default:
        return false;
    }
  }
}
