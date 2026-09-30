package com.hendrix.jeudeloie;

import java.util.List;
import java.util.Scanner;

/**
 * Gère le déroulement d'une partie et l'application des règles.
 */
public class Partie {

    private final List<Joueur> joueurs;
    private final Plateau plateau = new Plateau();
    private final Des des;
    private final Scanner scanner;
    private final boolean automatique;

    public Partie(List<Joueur> joueurs, Des des, Scanner scanner, boolean automatique) {
        this.joueurs = joueurs;
        this.des = des;
        this.scanner = scanner;
        this.automatique = automatique;
    }

    /** Lance la partie et retourne le gagnant. */
    public Joueur jouer() {
        int tour = 1;
        while (true) {
            System.out.println("\n========== Tour " + tour + " ==========");
            libererSiToutLeMondeEstBloque();

            for (Joueur joueur : joueurs) {
                if (jouerTour(joueur)) {
                    System.out.println("\n" + joueur.getNom() + " atteint la case 63 et gagne la partie !");
                    return joueur;
                }
            }
            afficherPositions();
            tour++;
        }
    }

    /** Joue le tour d'un joueur. Retourne vrai s'il a gagné. */
    private boolean jouerTour(Joueur joueur) {
        System.out.println("\n>> Au tour de " + joueur.getNom() + " (" + plateau.decrire(joueur.getPosition()) + ")");

        if (joueur.getToursAPasser() > 0) {
            joueur.decrementerToursAPasser();
            System.out.println("  " + joueur.getNom() + " se repose à l'hôtel. Tours restants : " + joueur.getToursAPasser());
            return false;
        }
        if (joueur.estBloque()) {
            System.out.println("  " + joueur.getNom() + " est coincé et attend qu'un autre joueur vienne le délivrer.");
            return false;
        }

        attendreEntree(joueur);
        int[] lancer = des.lancer();
        int total = lancer[0] + lancer[1];
        System.out.println("  " + lancer[0] + " + " + lancer[1] + " = " + total);

        // Règle spéciale du premier lancer : un 9 envoie directement en 26 ou 53
        if (joueur.estPremierLancer()) {
            joueur.setPremierLancer(false);
            if (total == 9) {
                int destination = (lancer[0] == 3 || lancer[1] == 3) ? 26 : 53;
                joueur.setPosition(destination);
                System.out.println("  Premier lancer de 9 ! " + joueur.getNom() + " file directement en case " + destination + ".");
                return false;
            }
        }

        boolean recul = plateau.rebondit(joueur.getPosition(), total);
        joueur.setPosition(plateau.deplacer(joueur.getPosition(), total));
        if (recul) {
            System.out.println("  Trop loin ! Le pion rebondit sur la case 63.");
        }
        System.out.println("  " + joueur.getNom() + " arrive en " + plateau.decrire(joueur.getPosition()) + ".");

        return appliquerEffets(joueur, total, recul);
    }

    /**
     * Applique l'effet de la case sur laquelle se trouve le joueur.
     * Une oie peut enchaîner sur une autre oie, d'où la boucle.
     */
    private boolean appliquerEffets(Joueur joueur, int total, boolean recul) {
        while (true) {
            int position = joueur.getPosition();
            switch (plateau.getType(position)) {
                case OIE -> {
                    int pas = recul ? -total : total;
                    if (!recul && plateau.rebondit(position, pas)) {
                        recul = true;
                    }
                    joueur.setPosition(plateau.deplacer(position, pas));
                    System.out.println("  Oie ! " + joueur.getNom() + " avance encore de " + total
                            + (recul ? " (en reculant)" : "") + " -> " + plateau.decrire(joueur.getPosition()) + ".");
                    continue; // on réévalue la nouvelle case
                }
                case PONT -> {
                    joueur.setPosition(Plateau.PONT_DESTINATION);
                    System.out.println("  Le pont ! " + joueur.getNom() + " traverse jusqu'à la case 12.");
                }
                case HOTEL -> {
                    joueur.setToursAPasser(2);
                    System.out.println("  L'hôtel ! " + joueur.getNom() + " passe 2 tours.");
                }
                case PUITS, PRISON -> gererPiege(joueur, position);
                case LABYRINTHE -> {
                    joueur.setPosition(Plateau.LABYRINTHE_DESTINATION);
                    System.out.println("  Le labyrinthe ! " + joueur.getNom() + " retourne en case 30.");
                }
                case MORT -> {
                    joueur.setPosition(Plateau.DEPART);
                    System.out.println("  La tête de mort ! " + joueur.getNom() + " retourne au départ.");
                }
                case ARRIVEE -> {
                    return true;
                }
                default -> { }
            }
            return false;
        }
    }

    /** Puits ou prison : le nouvel arrivant délivre celui qui y était et prend sa place. */
    private void gererPiege(Joueur joueur, int position) {
        String nomPiege = plateau.getType(position).getLibelle().toLowerCase();
        for (Joueur autre : joueurs) {
            if (autre != joueur && autre.getPosition() == position && autre.estBloque()) {
                autre.setBloque(false);
                System.out.println("  " + autre.getNom() + " est délivré(e) du " + nomPiege + " !");
            }
        }
        joueur.setBloque(true);
        System.out.println("  " + joueur.getNom() + " tombe dans le " + nomPiege + " et attend qu'on le délivre.");
    }

    /** Évite un blocage infini si tous les joueurs sont coincés (puits/prison). */
    private void libererSiToutLeMondeEstBloque() {
        boolean tousBloques = joueurs.stream().allMatch(Joueur::estBloque);
        if (tousBloques) {
            joueurs.forEach(j -> j.setBloque(false));
            System.out.println("Tous les joueurs sont coincés : tout le monde est libéré !");
        }
    }

    private void attendreEntree(Joueur joueur) {
        if (automatique) return;
        System.out.print("  Appuie sur Entrée pour lancer les dés...");
        scanner.nextLine();
    }

    private void afficherPositions() {
        System.out.println("\n--- Positions ---");
        for (Joueur j : joueurs) {
            int pos = j.getPosition();
            int largeur = 30;
            int rempli = pos * largeur / Plateau.ARRIVEE;
            String barre = "#".repeat(rempli) + "-".repeat(largeur - rempli);
            System.out.printf("  %-12s [%s] %2d/63%n", j.getNom(), barre, pos);
        }
    }
}
