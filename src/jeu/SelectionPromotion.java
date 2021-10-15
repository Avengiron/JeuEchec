package jeu;

import pieces.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe SelectionPromotion pour promotion d'un Pion. Ouvre une fenetre fille
 * pour que l'utilisateur choisisse la nouvelle Piece qui remplacera le Pion.
 * @author Xavier
 */
public final class SelectionPromotion extends JFrame {
  /** Echiquier de la fenetre principale */
  private Echiquier fenetrePrincipale;
  /** Tableau de Cellule pour faire son choix */
  private Cellule[] selection;
  /** Cellule choisie pour la promotion */
  private Cellule choix;
  /** Panel pour affichage graphique */
  private JPanel contenu;
  /** Bouton pour validation du choix, et sortie de la fenetre Promotion */
  private JButton sortie;
  
  /**
   * Constructeur avec argument
   * @param e Echiquier principal pour synchronisation
   */
  public SelectionPromotion(Echiquier e) {
    fenetrePrincipale = e;
    selection = new Cellule[4];
    contenu = new JPanel(new GridLayout(1, 4));
    contenu.setPreferredSize(new Dimension(5 * 124, 1 * 124));

    int x = fenetrePrincipale.getCelluleArrivee().getPosX();
    int y = fenetrePrincipale.getCelluleArrivee().getPosY();

    if (fenetrePrincipale.isBlanc()) {
      selection[0] = new Cellule(x, y, new Tour("Blanc"));
      selection[1] = new Cellule(x, y, new Cavalier("Blanc"));
      selection[2] = new Cellule(x, y, new Fou("Blanc"));
      selection[3] = new Cellule(x, y, new Dame("Blanc"));
    } else {
      selection[0] = new Cellule(x, y, new Tour("Noir"));
      selection[1] = new Cellule(x, y, new Cavalier("Noir"));
      selection[2] = new Cellule(x, y, new Fou("Noir"));
      selection[3] = new Cellule(x, y, new Dame("Noir"));
    }

    for (int i = 0; i < 4; i++) {
      selection[i].addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent evt) {
          selection(evt);
        }
      });
      contenu.add(selection[i]);
    }

    sortie = new JButton();
    sortie.setText("Selection");
    sortie.setEnabled(false);
    sortie.addActionListener((ActionEvent evt) -> {
      sortieActionPerformed(evt);
    });

    contenu.add(sortie);

    this.setTitle("Promotion du Pion !");
    this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    this.getContentPane().add(contenu);
    this.setResizable(false);
    this.setVisible(true);
    this.pack();

    int posX = (fenetrePrincipale.getX() + fenetrePrincipale.getWidth()) / 2 - (this.getWidth() / 2);
    int posY = (fenetrePrincipale.getY() + fenetrePrincipale.getHeight()) / 2 - (this.getHeight() / 2);
    this.setLocation(new java.awt.Point(posX, posY));
  }

  /**
   * Met en valeur la Cellule selectionnee, rend neutre les autres
   * @param e Clic de souris
   */
  public void selection(MouseEvent e) {
    if (choix == null) {
      sortie.setEnabled(true);
    } else {
      choix.setBackground(new Color(choix.getCouleurCase()));
    }
    choix = (Cellule) e.getSource();
    choix.setBackground(new Color(0x7F00));
  }

  /**
   * Transfert la Cellule choisie vers le plateau principal. Ferme la fenetre de
   * promotion
   * @param evt Clic de souris
   */
  private void sortieActionPerformed(ActionEvent evt) {
    fenetrePrincipale.setCellulePromotion(choix);
    this.dispose();
  }
}
