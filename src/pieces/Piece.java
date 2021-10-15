package pieces;

import java.util.ArrayList;
import jeu.Cellule;

/**
 * Classe Piece pour jeu d'echecs. Creer une CaseVide
 * @author Xavier
 */
public abstract class Piece {
  /** Lettre representant la piece */
  protected char lettre;
  /** Couleur de la piece */
  protected String couleur;
  /** Image pour affichage */
  protected String icone;

  /**
   * Obtient la lettre correspondant a la Piece
   * @return Lettre
   */
  public char getLettre() {
    return lettre;
  }

  /**
   * Obtient la couleur de la Piece
   * @return Couleur
   */
  public String getCouleur() {
    return couleur;
  }

  public String getImage() {
    return icone;
  }

  /**
   * Fixe la lettre de la Piece
   * @param lettre Lettre
   */
  public void setLettre(char lettre) {
    this.lettre = lettre;
  }

  /**
   * Fixe la couleur de la Piece
   * @param couleur Couleur de la Piece
   */
  public void setCouleur(String couleur) {
    this.couleur = couleur;
  }

  /**
   * Methode Abstraite pour verifier le deplacement d'une Piece
   * @param x Position x de la Cellule de depart
   * @param y Position y de la Cellule de depart
   * @param p Liste de Pieces
   * @return Booleen, true = deplacement autorise
   */
  public abstract boolean deplacementAutorise(int x, int y, ArrayList<Cellule> p);
}
