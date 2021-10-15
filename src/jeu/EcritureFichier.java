package jeu;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe EcritureFichier pour faire le compte rendu officiel de la partie 
 * @author Xavier
 */
public class EcritureFichier {
  /** FileWriter pour ecrire dans un fichier */
  private FileWriter fichier;

  /**
   * Constructeur avec argument
   * @param path Chemin sur le disque ou creer le fichier
   */
  public EcritureFichier(String path) {
    try { this.fichier = new FileWriter(path); }
    catch (IOException ioe) {}
  }

  /**
   * Ecrit une chaine de caractere dans le fichier
   * @param aEcrire Chaine de caractere a Ecrire
   */
  public void ecriture(String aEcrire) {
    try { this.fichier.write(aEcrire); } 
    catch (IOException ioe) {}
  }

  /** Ferme le flux */
  public void fermeture() {
    try { this.fichier.close(); }
    catch (IOException ioe) {}
  }
}
