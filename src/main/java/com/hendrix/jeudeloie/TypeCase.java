package com.hendrix.jeudeloie;

/**
 * Les différents types de cases du plateau.
 */
public enum TypeCase {
    DEPART("Départ"),
    NORMALE("Case"),
    OIE("Oie"),
    PONT("Pont"),
    HOTEL("Hôtel"),
    PUITS("Puits"),
    LABYRINTHE("Labyrinthe"),
    PRISON("Prison"),
    MORT("Tête de mort"),
    ARRIVEE("Jardin de l'oie");

    private final String libelle;

    TypeCase(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
