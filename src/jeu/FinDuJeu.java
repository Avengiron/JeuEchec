package jeu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;

/**
 * Fenetre de fin de jeu. Annonce le gagnant et ferme la fenetre principale a la
 * fermeture.
 * @author Xavier
 */
public final class FinDuJeu extends JFrame {
  // Champs de la classe FinDuJeu --------------------------------------------

  /** Echiquier de la fenetre principale */
  private Echiquier fenetrePrincipale;
  /** Cellule du Roi gagnant */
  private Cellule roi;
  /** Annonce du gagnant */
  private JLabel gagnant;
  /** Panel pour affichage graphique */
  private JPanel contenu;

  // Constructeurs -----------------------------------------------------------
  /**
   * Constructeur avec argument
   * @param e Echiquier principal pour synchronisation
   */
  public FinDuJeu(Echiquier e) {
    fenetrePrincipale = e;
    roi = new Cellule(fenetrePrincipale.getRoi(fenetrePrincipale.isBlanc()));
    String texte = "Joueur " + roi.getPiece().getCouleur() + " gagnant !";
    gagnant = new JLabel(texte, JLabel.CENTER);
    gagnant.setFont(new Font("Monotype Corsiva", 0, 25));
    contenu = new JPanel(new GridLayout(2, 1));
    contenu.setPreferredSize(new Dimension(1 * 300, 2 * 124));
    contenu.add(roi);
    contenu.add(gagnant);
    contenu.setBackground(new Color(roi.getCouleurCase()));

    this.setTitle("Victoire !");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.getContentPane().add(contenu);
    this.setResizable(false);
    this.setVisible(true);
    this.pack();

    int posX = (fenetrePrincipale.getX() + fenetrePrincipale.getWidth()) / 2 - (this.getWidth() / 2);
    int posY = (fenetrePrincipale.getY() + fenetrePrincipale.getHeight()) / 2 - (this.getHeight() / 2);
    this.setLocation(new java.awt.Point(posX, posY));
  }

  // Methodes ----------------------------------------------------------------
}
