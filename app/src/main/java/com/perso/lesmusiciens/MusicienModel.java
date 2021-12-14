package com.perso.lesmusiciens;

public class MusicienModel {
    // Déclaration des variables membres
    private int id;
    private String nomMusicien;
    private int nbEtoiles;
    private boolean isActive;

    // Constructeur vide nécessaire au fonctionnement des automatismes
    public MusicienModel() {
    }

    // Constructeur
    public MusicienModel(int id, String nomMusicien, int nbEtoiles, boolean isActive) {
        this.id = id;
        this.nomMusicien = nomMusicien;
        this.nbEtoiles = nbEtoiles;
        this.isActive = isActive;
    }

    // Methodes supplémentaires
    // Redéfinition de la méthode toString()


    @Override
    public String toString() {
        return "MusicienModel{" +
                "id=" + id +
                ", nomMusicien='" + nomMusicien + '\'' +
                ", nbEtoiles=" + nbEtoiles +
                ", isActive=" + isActive +
                '}';
    }

    // GETTERS
    public int getId()              {return id;}
    public String getNomMusicien()  {return nomMusicien;}
    public int getNbEtoiles()       {return nbEtoiles;}
    public boolean isActive()       {return isActive;}

    // SETTERS
    public void setId           (int id)                {this.id = id;}
    public void setNomMusicien  (String nomMusicien)    {this.nomMusicien = nomMusicien;}
    public void setNbEtoiles    (int nbEtoiles)         {this.nbEtoiles = nbEtoiles;}
    public void setActive       (boolean active)        {isActive = active;}
}
