package com.hendrix.jeudeloie;

import java.util.Set;

/**
 * Plateau classique du jeu de l'oie : 63 cases.
 */
public class Plateau {

    public static final int DEPART = 0;
    public static final int ARRIVEE = 63;

    public static final int PONT = 6;
    public static final int PONT_DESTINATION = 12;
    public static final int HOTEL = 19;
    public static final int PUITS = 31;
    public static final int LABYRINTHE = 42;
    public static final int LABYRINTHE_DESTINATION = 30;
    public static final int PRISON = 52;
    public static final int MORT = 58;

    private static final Set<Integer> OIES =
            Set.of(5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59);

    /** Retourne le type de la case numéro {@code numero}. */
    public TypeCase getType(int numero) {
        if (numero == DEPART) return TypeCase.DEPART;
        if (numero == ARRIVEE) return TypeCase.ARRIVEE;
        if (OIES.contains(numero)) return TypeCase.OIE;
        return switch (numero) {
            case PONT -> TypeCase.PONT;
            case HOTEL -> TypeCase.HOTEL;
            case PUITS -> TypeCase.PUITS;
            case LABYRINTHE -> TypeCase.LABYRINTHE;
            case PRISON -> TypeCase.PRISON;
            case MORT -> TypeCase.MORT;
            default -> TypeCase.NORMALE;
        };
    }

    /**
     * Calcule la position après un déplacement, avec rebond si on dépasse la case 63.
     * Un déplacement négatif fait reculer le pion (sans descendre sous 0).
     */
    public int deplacer(int position, int pas) {
        int nouvelle = position + pas;
        if (nouvelle > ARRIVEE) {
            nouvelle = ARRIVEE - (nouvelle - ARRIVEE);
        }
        return Math.max(DEPART, nouvelle);
    }

    /** Vrai si le déplacement fait rebondir le pion sur la case d'arrivée. */
    public boolean rebondit(int position, int pas) {
        return position + pas > ARRIVEE;
    }

    public String decrire(int numero) {
        TypeCase type = getType(numero);
        return type == TypeCase.NORMALE
                ? "case " + numero
                : "case " + numero + " (" + type.getLibelle() + ")";
    }
}
