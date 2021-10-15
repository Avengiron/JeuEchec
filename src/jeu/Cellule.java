package jeu;

import pieces.*;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * Classe Cellule pour jeu d'Echecs. L'echiquier est compose de 8x8 Cellules. Si
 * une Cellule porte une Piece, l'image est chargee pour l'affichage graphique.
 *
 * @author Xavier
 */
public class Cellule extends JPanel {
  /** Label pour l'image representant la Piece portee par la Cellule */
  private JLabel cases;
  /** Position x de la Cellule */
  private int posX;
  /** Position y de la Cellule */
  private int posY;
  /** Couleur originale de la Cellule */
  private final int couleur;
  /** Piece portee par la Cellule */
  private Piece p;

  /**
   * Constructeur avec arguments Si la Piece portee est concrete, genere les
   * attribus graphiques
   * @param x Position x de la Cellule
   * @param y Position y de la Cellule
   * @param p Piece portee par la Cellule
   */
  public Cellule(int x, int y, Piece p) {
    this.posX = x;
    this.posY = y;
    if ((x + y) % 2 == 0) {
      this.couleur = 0xFFCE9E; // Beige
    } else {
      this.couleur = 0xD18B47; // Marron
    }
    this.setBackground(new Color(this.couleur));
    if (p != null) {
      setPiece(p);
    } else {
      p = null;
    }
  }

  /**
   * Constructeur avec argument pour copie
   * @param aCopier Cellule a copier
   */
  public Cellule(Cellule aCopier) {
    this.posX = aCopier.posX;
    this.posY = aCopier.posY;
    this.couleur = aCopier.couleur;

    if (aCopier.p instanceof Roi) {
      setPiece(new Roi((Roi) aCopier.p));
    } else if (aCopier.p instanceof Tour) {
      setPiece(new Tour((Tour) aCopier.p));
    } else if (aCopier.p instanceof Dame) {
      setPiece(new Dame((Dame) aCopier.p));
    } else if (aCopier.p instanceof Pion) {
      setPiece(new Pion((Pion) aCopier.p));
    } else if (aCopier.p instanceof Fou) {
      setPiece(new Fou((Fou) aCopier.p));
    } else if (aCopier.p instanceof Cavalier) {
      setPiece(new Cavalier((Cavalier) aCopier.p));
    } else {
      this.p = null;
    }

    this.setBackground(new Color(this.couleur));
  }

  /** Supprime la Piece de la Cellule, ainsi que tous les attribus graphiques */
  public void supprimerPiece() {
    this.p = null;
    this.remove(cases);
  }

  /**
   * Obtient la position x de la Cellule
   * @return Position x
   */
  public int getPosX() {
    return posX;
  }

  /**
   * Obtient la position y de la Cellule
   * @return Position y
   */
  public int getPosY() {
    return posY;
  }

  /**
   * Obtient la couleur initiale de la Cellule
   * @return Couleur initiale
   */
  public int getCouleurCase() {
    return this.couleur;
  }

  /**
   * Obtient la Piece que contient la Cellule
   * @return Piece
   */
  public Piece getPiece() {
    return this.p;
  }

  /**
   * Obtient le JLabel d'affichage
   * @return JLabel
   */
  public JLabel getCases() {
    return this.cases;
  }

  /**
   * Fixe la Piece sur la Cellule courante Charge l'image correspondante a la
   * Piece
   * @param p Piece sur la Cellule
   */
  public void setPiece(Piece p) {
    this.p = p;
    ImageIcon img = new ImageIcon("src/icones/" + p.getImage());
    this.cases = new JLabel(img, JLabel.CENTER);
    this.cases.setPreferredSize(new Dimension(124, 110));
    this.add(cases);
  }
}
