package com.hendrix.jeudeloie;

/**
 * Un joueur et son état sur le plateau.
 */
public class Joueur {

    private final String nom;
    private int position = Plateau.DEPART;
    private int toursAPasser = 0;
    private boolean bloque = false; // puits ou prison
    private boolean premierLancer = true;

    public Joueur(String nom) {
        this.nom = nom;
    }

    public String getNom() { return nom; }

    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public int getToursAPasser() { return toursAPasser; }
    public void setToursAPasser(int toursAPasser) { this.toursAPasser = toursAPasser; }
    public void decrementerToursAPasser() { if (toursAPasser > 0) toursAPasser--; }

    public boolean estBloque() { return bloque; }
    public void setBloque(boolean bloque) { this.bloque = bloque; }

    public boolean estPremierLancer() { return premierLancer; }
    public void setPremierLancer(boolean premierLancer) { this.premierLancer = premierLancer; }

    @Override
    public String toString() {
        return nom;
    }
}
