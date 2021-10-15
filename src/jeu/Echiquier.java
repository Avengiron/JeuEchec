package jeu;

import pieces.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.*;

/**
 * Classe Echiquier pour Jeu d'Echecs. Classe principale qui gere tout le jeu.
 * Le plateau de jeu est cree dans une nouvelle fenetre et est a l'ecoute de
 * clics de souris.
 * @author Xavier
 */
public final class Echiquier extends JFrame {
  /** Echiquier de sauvegarde */
  private static Echiquier backUp;
  /** Represente quel joueur doit jouer */
  private boolean joueurBlanc;
  /** Plateau de Cellules */
  private Cellule[][] echiquier;
  /** Cellule de depart et Cellule d'arrivee, pour deplacement */
  private Cellule celluleDepart, celluleArrivee;
  /** Panel pour affichage du plateau de jeu */
  private JPanel plateau;
  /** Liste des cellules portants des Pieces Noires */
  private ArrayList<Cellule> listePiecesNoires;
  /** Liste des cellules portants des Pieces Blanches */
  private ArrayList<Cellule> listePiecesBlanches;

  /** Booleen pour l'ecriture du compte-rendu */
  private boolean petitRoque, grandRoque, prise;
  /** Fichier pour compte-rendu */
  private EcritureFichier compteRendu;
  /** Taille de chaque cellule */
  final int size = 124;
  
  /** Constructeur sans argument */
  public Echiquier() {
    joueurBlanc = true;
    echiquier = new Cellule[8][8];
    plateau = new JPanel(new GridLayout(8, 8));
    plateau.setPreferredSize(new Dimension(8 * size, 8 * size));

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Piece p = selectionPiece(i, j);
        echiquier[i][j] = new Cellule(i, j, p);
        plateau.add(echiquier[i][j]);
      }
    }

    listePiecesNoires = new ArrayList<>();
    listePiecesBlanches = new ArrayList<>();
    backUp = new Echiquier(this);

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent evt) {
        partieJeuMousePressed(evt);
      }
    });

    this.setTitle("Jeu d'Echecs");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.getContentPane().add(plateau);
    this.setResizable(false);
    this.setVisible(true);
    this.pack();

    petitRoque = false;
    grandRoque = false;
    prise = false;
    compteRendu = new EcritureFichier("compteRendu.txt");
  }

  /**
   * Constructeur avec argument, pour copie
   * @param aCopier Echiquier a copier
   */
  public Echiquier(Echiquier aCopier) {
    this.joueurBlanc = aCopier.joueurBlanc;
    this.echiquier = new Cellule[8][8];
    this.plateau = new JPanel(new GridLayout(8, 8));
    this.plateau.setPreferredSize(new Dimension(8 * size, 8 * size));

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (aCopier.echiquier[i][j] != null) {
          this.echiquier[i][j] = new Cellule(aCopier.echiquier[i][j]);
        } else {
          this.echiquier[i][j] = null;
        }
        plateau.add(echiquier[i][j]);
      }
    }

    this.listePiecesBlanches = new ArrayList<>(aCopier.listePiecesBlanches);
    this.listePiecesNoires = new ArrayList<>(aCopier.listePiecesNoires);

    this.petitRoque = aCopier.petitRoque;
    this.grandRoque = aCopier.grandRoque;
    this.prise = aCopier.prise;
  }

  /** Point d'entree de l'application */
  public static void main(String[] args) {
    Echiquier jeu = new Echiquier();
  }

  /**
   * Initialise le plateau de jeu selon les coordonnees de la Cellule
   * @param x Coordonnee x de la Cellule
   * @param y Coordonnee y de la Cellule
   * @return Piece a mettre dans la Cellule porteuse
   */
  private Piece selectionPiece(int x, int y) {
    switch (x) {
      case 0 :
        switch (y) {
          case 0 : return new Tour    ("Noir");
          case 1 : return new Cavalier("Noir");
          case 2 : return new Fou     ("Noir");
          case 3 : return new Dame    ("Noir");
          case 4 : return new Roi     ("Noir");
          case 5 : return new Fou     ("Noir");
          case 6 : return new Cavalier("Noir");
          case 7 : return new Tour    ("Noir");
          default : return null;
        }
      case 1 : return new Pion("Noir");
      case 6 : return new Pion("Blanc");
      case 7 :
        switch (y) {
          case 0 : return new Tour    ("Blanc");
          case 1 : return new Cavalier("Blanc");
          case 2 : return new Fou     ("Blanc");
          case 3 : return new Dame    ("Blanc");
          case 4 : return new Roi     ("Blanc");
          case 5 : return new Fou     ("Blanc");
          case 6 : return new Cavalier("Blanc");
          case 7 : return new Tour    ("Blanc");
          default : return null;
        }
      default : return null;
    }
  }

  /**
   * Methode qui gere le clic de la souris sur le plateau de jeu. Instancie la
   * Cellule de depart si elle etait vide auparavant. Instancie la Cellule
   * d'arrivee si elle etait vide auparavant. Si les deux Cellules sont
   * instanciees, gere le deplacement et tout ce qu'il implique.
   * @param e Clic de souris
   */
  private void partieJeuMousePressed(MouseEvent e) {
    // Localise le clic de souris pour identifier la Cellule ciblee
    int eventX = e.getX() - 3;
    int eventY = e.getY() - 26;
    int posY = eventX / size;
    int posX = eventY / size;

    // Selectionne la Cellule de depart si elle n'a pas ete precedemment 
    // selectionnee. La selection est valide si la Cellule porte une Piece 
    // de la bonne couleur. Met la Cellule en valeur. 
    if (celluleDepart == null) {
      celluleDepart = echiquier[posX][posY];
      if (celluleDepart.getPiece() == null
        || (joueurBlanc && celluleDepart.getPiece().getCouleur().equals("Noir"))
        || (!joueurBlanc && celluleDepart.getPiece().getCouleur().equals("Blanc"))) {
        celluleDepart = null;
        return;
      }
      celluleDepart.setBackground(new Color(0x007F00));

      // Selectionne la Cellule d'arrivee si elle n'a pas ete precedemment 
      // selectionnee. Implique que la Cellule de depart a ete selectionnee. 
      // La selection est valide si la Cellule d'arrivee ne porte pas de Piece 
      // de la meme couleur que la Cellule de depart. Si la Cellule d'arrivee 
      // est la meme que la Cellule de depart, deselectionne la Cellule. 
    } else if (celluleDepart != null && celluleArrivee == null) {
      celluleArrivee = echiquier[posX][posY];
      if (celluleArrivee.getPiece() != null
        && celluleArrivee.getPosX() == celluleDepart.getPosX()
        && celluleArrivee.getPosY() == celluleDepart.getPosY()) {
        if (celluleDepart.getPiece() instanceof Roi && !((Roi) celluleDepart.getPiece()).getListeMenace().isEmpty()) {
          celluleDepart.setBackground(new Color(0x7F0000));
        } else {
          celluleDepart.setBackground(new Color(celluleDepart.getCouleurCase()));
        }
        celluleDepart = null;
        celluleArrivee = null;
        return;
      }
      if ((celluleArrivee.getPiece() != null)
        && ((joueurBlanc && celluleArrivee.getPiece().getCouleur().equals("Blanc"))
        || (!joueurBlanc && celluleArrivee.getPiece().getCouleur().equals("Noir")))) {
        celluleArrivee = null;
        return;
      }

      // Lorsque les Cellules de depart et d'arrivee ont ete selectionnees, 
      // procede au jeu : Piece CD -> CA
      // - Verifie que le deplacement est valide. 
      // - Procede au deplacement
      // - Verifie si un coup special doit etre fait 
      // - Met a jour les donnees complementaires sur la Piece 
      // - Verifie la Promotion de la Piece
      // - Met a jour la liste des Pieces encore en jeu
      // - Verifie si le deplacement ne met pas en danger son propre Roi
      // - Verifie si le deplacement met en danger le Roi adverse
      // - Verifie si l'echec du Roi adverse est contrable par le jeu adverse
      // - Annonce la fin du jeu en cas de mat
      ArrayList<Cellule> listeCellule = creationListeCases(celluleDepart, celluleArrivee);
      if (celluleDepart.getPiece().deplacementAutorise(celluleDepart.getPosX(), celluleDepart.getPosY(), listeCellule)) {
        deplacement(celluleDepart, celluleArrivee);
        verificationCoupsSpeciaux(listeCellule);

        if (celluleArrivee.getPiece() instanceof Pion) {
          ((Pion) celluleArrivee.getPiece()).setLastPosX(celluleDepart.getPosX());
        }
        if (celluleArrivee.getPiece() instanceof Tour) {
          ((Tour) celluleArrivee.getPiece()).setaDejaBouge(true);
        }
        if (celluleArrivee.getPiece() instanceof Roi) {
          ((Roi) celluleArrivee.getPiece()).setaDejaBouge(true);
        }

        promotion();
        miseAJourListesPieces();
        menaceMonRoi();
        if (menaceMonRoiAugmente()) {
          retablissementPlateauDeJeu();
          joueurBlanc = !joueurBlanc;
        } else {
          getRoi(joueurBlanc).setBackground(new Color(getRoi(joueurBlanc).getCouleurCase()));
          ecritureCompteRendu();
          menaceRoiAdverse();
          backUp = new Echiquier(this);
          if (echecEtMat()) {
            System.out.print("#");
            compteRendu.ecriture("#");
            compteRendu.fermeture();
            finDuJeu();
          }
        }
        actualisationAffichage();
        complementAffichage();
        joueurBlanc = !joueurBlanc;
      }

      System.out.println("");
      compteRendu.ecriture(System.getProperty("line.separator"));
      celluleDepart.setBackground(new Color(celluleDepart.getCouleurCase()));
      celluleDepart = null;
      celluleArrivee = null;
    }
  }

  /**
   * Creation d'une liste de cases entre la Cellule de Depart et la Cellule
   * d'arrivee. N'inclut pas la Cellule de Depart. Inclut la Cellule d'arrivee.
   * @param s Cellule source
   * @param d Cellule destination
   * @return Liste de Cellules
   */
  private ArrayList<Cellule> creationListeCases(Cellule s, Cellule d) {
    ArrayList<Cellule> liste = new ArrayList<>();

    int xD = s.getPosX();
    int yD = s.getPosY();
    int xA = d.getPosX();
    int yA = d.getPosY();

    if (s.getPiece() instanceof Cavalier) {
      // Deplacement Cavalier
      liste.add(echiquier[xA][yA]);
    } else {
      // Deplacement vertical
      if (xD == xA) {
        if (yD < yA) {
          for (int i = yD + 1; i <= yA; i++) {
            liste.add(echiquier[xD][i]);
          }
        } else if (yD > yA) {
          for (int i = yD - 1; i >= yA; i--) {
            liste.add(echiquier[xD][i]);
          }
        }

        // Deplacement horizontal    
      } else if (yD == yA) {
        if (xD < xA) {
          for (int i = xD + 1; i <= xA; i++) {
            liste.add(echiquier[i][yD]);
          }
        } else if (xD > xA) {
          for (int i = xD - 1; i >= xA; i--) {
            liste.add(echiquier[i][yD]);
          }
        }

        // Deplacement diagonal    
      } else if (Math.abs(xD - xA) == Math.abs(yD - yA)) {
        if ((xD < xA) && (yD < yA)) {
          for (int i = 1; i <= (xA - xD); i++) {
            liste.add(echiquier[xD + i][yD + i]);
          }
        } else if ((xD < xA) && (yD > yA)) {
          for (int i = 1; i <= (xA - xD); i++) {
            liste.add(echiquier[xD + i][yD - i]);
          }
        } else if ((xD > xA) && (yD < yA)) {
          for (int i = 1; i <= (yA - yD); i++) {
            liste.add(echiquier[xD - i][yD + i]);
          }
        } else if ((xD > xA) && (yD > yA)) {
          for (int i = 1; i <= (xD - xA); i++) {
            liste.add(echiquier[xD - i][yD - i]);
          }
        }

        // Prise en passant pour Pion
        if ((s.getPiece() instanceof Pion)
          && (d.getPiece() == null)
          && (liste.size() == 1)) {
          liste.add(echiquier[xD][yA]);
        }
      }
    }

    return liste;
  }

  /**
   * Gere le deplacement de la Cellule source vers la Cellule de destination.
   * @param s Cellule Source
   * @param d Cellule Destination
   */
  private void deplacement(Cellule s, Cellule d) {
    prise = false;
    if (d.getPiece() != null) {
      d.supprimerPiece();
      prise = true;
    }
    d.setPiece(s.getPiece());
    s.supprimerPiece();
  }

  /**
   * Verifie si le deplacement est un coup special Coup special #1 - Roque Coup
   * special #2 - Prise en passant
   * @param l Liste de Cellules
   */
  private void verificationCoupsSpeciaux(ArrayList<Cellule> l) {
    verificationRoque(l);
    verificationPriseEnPassant(l);
  }

  /**
   * Verifie si le coup special est un Roque. Le deplacement du Roi a deja ete
   * fait - deplace la Tour associee
   * @param l Liste de Cellules
   */
  private void verificationRoque(ArrayList<Cellule> l) {
    if (l.size() == 2 && celluleArrivee.getPiece().getLettre() == 'R' && !((Roi) celluleArrivee.getPiece()).isaDejaBouge()) {
      petitRoque = false;
      grandRoque = false;

      switch (celluleArrivee.getPosY()) {
        case 2:
          switch (celluleArrivee.getPosX()) {
            case 0:
              if (echiquier[0][1].getPiece() == null
                && echiquier[0][0].getPiece().getLettre() == 'T'
                && !((Tour) echiquier[0][0].getPiece()).isaDejaBouge()) {
                deplacement(echiquier[0][0], echiquier[0][3]);
                grandRoque = true;
              }
              break;
            case 7:
              if (echiquier[7][1].getPiece() == null
                && echiquier[7][0].getPiece().getLettre() == 'T'
                && !((Tour) echiquier[7][0].getPiece()).isaDejaBouge()) {
                deplacement(echiquier[7][0], echiquier[7][3]);
                grandRoque = true;
              }
              break;
          }
          break;
        case 6:
          switch (celluleArrivee.getPosX()) {
            case 0:
              if (echiquier[0][7].getPiece().getLettre() == 'T'
                && !((Tour) echiquier[0][7].getPiece()).isaDejaBouge()) {
                deplacement(echiquier[0][7], echiquier[0][5]);
                petitRoque = true;
              }
              break;
            case 7:
              if (echiquier[7][7].getPiece().getLettre() == 'T'
                && !((Tour) echiquier[7][7].getPiece()).isaDejaBouge()) {
                deplacement(echiquier[7][7], echiquier[7][5]);
                petitRoque = true;
              }
              break;
          }
          break;
      }
    }
  }

  /**
   * Verifie si le coup special est une prise en passant. Le Pion a deja ete
   * deplace - Supprime le Pion adverse
   * @param l Liste de Cellules
   */
  private void verificationPriseEnPassant(ArrayList<Cellule> l) {
    if ((l.get(l.size() - 1).getPosX() != celluleArrivee.getPosX()) || (l.get(l.size() - 1).getPosY() != celluleArrivee.getPosY())) {
      l.get(l.size() - 1).supprimerPiece();
    }
  }

  /** Rafraichit le plateau de jeu */
  public void actualisationAffichage() {
    this.getContentPane().remove(plateau);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        plateau.remove(echiquier[i][j]);
        this.echiquier[i][j] = new Cellule(echiquier[i][j]);
        plateau.add(echiquier[i][j]);
      }
    }
    this.getContentPane().add(plateau);
    pack();
  }

  /** Complete l'affichage. Si un Roi est en danger, sa Cellule est mise en
   * valeur */
  public void complementAffichage() {
    if (!((Roi) getRoi(!joueurBlanc).getPiece()).getListeMenace().isEmpty()) {
      getRoi(!joueurBlanc).setBackground(new Color(0x7F0000));
    }
  }

  /**
   * Gere la promotion d'un Pion. Ouvre une fenetre secondaire pour que le
   * joueur puisse faire son choix.
   */
  private void promotion() {
    if (celluleArrivee.getPiece() instanceof Pion
      && (celluleArrivee.getPosX() == 0 || celluleArrivee.getPosX() == 7)) {
      SelectionPromotion fprom = new SelectionPromotion(this);
    }
  }

  /**
   * Attribue une nouvelle Piece sur la Cellule d'arrivee en cas de promotion
   * d'un Pion. Comme pour un deplacement classique, verifie si le deplacement
   * est autorise vis-a-vis de son Roi, estime la nouvelle menace sur le Roi
   * adverse et lance la fin du Jeu si necessaire. Met a jour l'affichage du
   * plateau de jeu.
   * @param c Cellule promue
   */
  public void setCellulePromotion(Cellule c) {
    plateau.remove(echiquier[c.getPosX()][c.getPosY()]);
    echiquier[c.getPosX()][c.getPosY()].supprimerPiece();
    echiquier[c.getPosX()][c.getPosY()] = new Cellule(c);
    plateau.add(echiquier[c.getPosX()][c.getPosY()]);

    joueurBlanc = !joueurBlanc;
    menaceMonRoi();
    joueurBlanc = !joueurBlanc;
    if (menaceMonRoiAugmente()) {
      retablissementPlateauDeJeu();
      actualisationAffichage();
      joueurBlanc = !joueurBlanc;
      complementAffichage();
      joueurBlanc = !joueurBlanc;
    } else {
      actualisationAffichage();
      complementAffichage();
      ArrayList<Cellule> listeCellule = creationListeCases(c, getRoi(joueurBlanc));
      if (c.getPiece().deplacementAutorise(c.getPosX(), c.getPosY(), listeCellule)) {
        ((Roi) getRoi(joueurBlanc).getPiece()).menace(c.getPiece());
        getRoi(joueurBlanc).setBackground(new Color(0x7F0000));
      }
      backUp = new Echiquier(this);
      joueurBlanc = !joueurBlanc;
      if (echecEtMat()) {
        finDuJeu();
        actualisationAffichage();
        complementAffichage();
      }
      joueurBlanc = !joueurBlanc;
    }

  }

  /**
   * Dresse la liste de toutes les Pieces Blanches actuellement en jeu. Dresse
   * la liste de toutes les Pieces Noires actuellement en jeu.
   */
  private void miseAJourListesPieces() {
    listePiecesNoires.clear();
    listePiecesBlanches.clear();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (echiquier[i][j].getPiece() != null && echiquier[i][j].getPiece().getCouleur().equals("Blanc")) {
          listePiecesBlanches.add(echiquier[i][j]);
        } else if (echiquier[i][j].getPiece() != null && echiquier[i][j].getPiece().getCouleur().equals("Noir")) {
          listePiecesNoires.add(echiquier[i][j]);
        }
      }
    }
  }

  /**
   * Regarde la menace sur le Roi adverse dans le cas ou le deplacement est
   * definitivement valide.
   */
  public void menaceRoiAdverse() {
    Cellule c;
    ArrayList<Cellule> listePieces;
    ArrayList<Cellule> listeCellule;
    if (joueurBlanc) {
      listePieces = new ArrayList<>(listePiecesBlanches);
    } else {
      listePieces = new ArrayList<>(listePiecesNoires);
    }
    ((Roi) getRoi(!joueurBlanc).getPiece()).clearListeMenace();
    for (int i = 0; i < listePieces.size(); i++) {
      c = listePieces.get(i);
      listeCellule = creationListeCases(c, getRoi(!joueurBlanc));
      if (c.getPiece().deplacementAutorise(c.getPosX(), c.getPosY(), listeCellule)) {
        ((Roi) getRoi(!joueurBlanc).getPiece()).menace(c.getPiece());
      }
    }
  }

  /**
   * Regarde la menace sur le Roi du joueur courant. Si la menace augmente, le
   * deplacement a expose le Roi et devra donc etre annule.
   */
  public void menaceMonRoi() {
    Cellule c;
    ArrayList<Cellule> listePieces;
    ArrayList<Cellule> listeCellule;
    if (joueurBlanc) {
      listePieces = new ArrayList<>(listePiecesNoires);
    } else {
      listePieces = new ArrayList<>(listePiecesBlanches);
    }
    ((Roi) getRoi(joueurBlanc).getPiece()).clearListeMenace();
    for (int i = 0; i < listePieces.size(); i++) {
      c = listePieces.get(i);
      listeCellule = creationListeCases(c, getRoi(joueurBlanc));
      if (c.getPiece().deplacementAutorise(c.getPosX(), c.getPosY(), listeCellule)) {
        ((Roi) getRoi(joueurBlanc).getPiece()).menace(c.getPiece());
      }
    }
  }

  /**
   * Regarde si la menace sur le Roi du joueur courant a augmente. Deux cas
   * possibles : Si le Roi n'etait pas menace avant le deplacement, et qu'il est
   * menace apres. Si le Roi etait deja menace avant le deplacement (echec), et
   * que la menace n'a pas ete contree.
   * @return True si la menace a augmente ou n'a pas diminue. False s'il n'y a
   * pas de menace
   */
  public boolean menaceMonRoiAugmente() {
    if (((Roi) backUp.getRoi(!isBlanc()).getPiece()).getListeMenace().isEmpty()
      && !((Roi) this.getRoi(isBlanc()).getPiece()).getListeMenace().isEmpty()) {
      return true;
    } else if (!((Roi) backUp.getRoi(!isBlanc()).getPiece()).getListeMenace().isEmpty()
      && !((Roi) this.getRoi(isBlanc()).getPiece()).getListeMenace().isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Si le mouvement effectue est interdit, le plateau de jeu est ramene a son
   * etat precedent. Le joueur courant doit rejouer.
   */
  public void retablissementPlateauDeJeu() {
    this.petitRoque = backUp.petitRoque;
    this.grandRoque = backUp.grandRoque;
    this.prise = backUp.prise;

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        this.plateau.remove(echiquier[i][j]);
        this.echiquier[i][j] = new Cellule(backUp.echiquier[i][j]);
        this.plateau.add(echiquier[i][j]);
      }
    }

    this.listePiecesBlanches = new ArrayList<>(backUp.listePiecesBlanches);
    this.listePiecesNoires = new ArrayList<>(backUp.listePiecesNoires);
  }

  /**
   * Obtient le statut du joueur devant jouer
   * @return Joueur; true = blanc; false = noir
   */
  public boolean isBlanc() {
    return joueurBlanc;
  }

  /**
   * Obtient la Cellule portant le Roi selon la couleur.
   * @param blanc Couleur du Roi a recuperer
   * @return Cellule portant le Roi
   */
  public Cellule getRoi(boolean blanc) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (blanc && echiquier[i][j].getPiece() instanceof Roi && echiquier[i][j].getPiece().getCouleur().equals("Blanc")) {
          return echiquier[i][j];
        } else if (!blanc && echiquier[i][j].getPiece() instanceof Roi && echiquier[i][j].getPiece().getCouleur().equals("Noir")) {
          return echiquier[i][j];
        }
      }
    }
    return null;
  }

  /**
   * Obtient la Cellule d'arrivee
   * @return Cellule d'arrivee
   */
  public Cellule getCelluleArrivee() {
    return celluleArrivee;
  }

  /**
   * Si un Roi est en echec, teste toutes les possibilites de deplacement pour
   * les Pieces de la meme couleur que ce Roi. Regarde si la menace peut etre
   * evitee. Si c'est le cas, le jeu continu. Sinon, le Roi est Mat, et la fin
   * du jeu doit etre lancee.
   * @return true = Mat; false = Esquivable
   */
  public boolean echecEtMat() {
    Cellule c;
    ArrayList<Cellule> listePieces;
    ArrayList<Cellule> listeDeplacement;

    if (!((Roi) getRoi(!joueurBlanc).getPiece()).getListeMenace().isEmpty()) {

      if (getRoi(!joueurBlanc).getPiece().getCouleur().equals("Blanc")) {
        listePieces = new ArrayList<>(listePiecesBlanches);
      } else {
        listePieces = new ArrayList<>(listePiecesNoires);
      }

      for (int h = 0; h < listePieces.size(); h++) {
        c = listePieces.get(h);
        for (int i = 0; i < 8; i++) {
          for (int j = 0; j < 8; j++) {
            if (echiquier[i][j].getPiece() == null || !echiquier[i][j].getPiece().getCouleur().equals(getRoi(!joueurBlanc).getPiece().getCouleur())) {
              listeDeplacement = creationListeCases(c, echiquier[i][j]);
              if (c.getPiece().deplacementAutorise(c.getPosX(), c.getPosY(), listeDeplacement)) {
                deplacement(c, echiquier[i][j]);
                miseAJourListesPieces();
                joueurBlanc = !joueurBlanc;
                menaceMonRoi();
                if (menaceMonRoiAugmente()) {
                  joueurBlanc = !joueurBlanc;
                  retablissementPlateauDeJeu();
                  c = echiquier[c.getPosX()][c.getPosY()];
                } else {
                  joueurBlanc = !joueurBlanc;
                  retablissementPlateauDeJeu();
                  return false;
                }
              }
            }
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }

  /** Ouvre une fenetre secondaire pour annoncer le gagnant et la fin du jeu */
  public void finDuJeu() {
    FinDuJeu fdj = new FinDuJeu(this);
  }

  /** Procede a l'ecriture du compte-rendu de la partie */
  public void ecritureCompteRendu() {
    String s = new String();
    if (petitRoque) {
      s += "O-O";
    } else if (grandRoque) {
      s += "O-O-O";
    } else {
      if (celluleArrivee.getPiece().getLettre() != 'P') {
        s += celluleArrivee.getPiece().getLettre();
      }
      s += (char) (celluleDepart.getPosY() + 'a') + "" + (8 - celluleDepart.getPosX());
      if (prise) {
        s += "x";
      } else {
        s += "-";
      }
      s += (char) (celluleArrivee.getPosY() + 'a') + "" + (8 - celluleArrivee.getPosX());
    }
    System.out.print(s);
    compteRendu.ecriture(s);
  }
}
