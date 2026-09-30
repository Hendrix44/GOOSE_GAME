package com.hendrix.jeudeloie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Point d'entrée du jeu de l'oie en console.
 * Utilisation : java -jar jeu-de-l-oie.jar [--auto]
 */
public class Main {

    public static void main(String[] args) {
        boolean automatique = args.length > 0 && args[0].equalsIgnoreCase("--auto");
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                ================================
                          JEU DE L'OIE
                ================================
                """);

        int nombre = automatique ? 3 : demanderNombreJoueurs(scanner);
        List<Joueur> joueurs = new ArrayList<>();
        for (int i = 1; i <= nombre; i++) {
            String nom = automatique ? "Joueur " + i : demanderNom(scanner, i);
            joueurs.add(new Joueur(nom));
        }

        new Partie(joueurs, new Des(), scanner, automatique).jouer();
    }

    private static int demanderNombreJoueurs(Scanner scanner) {
        while (true) {
            System.out.print("Nombre de joueurs (2 à 6) : ");
            String saisie = scanner.nextLine().trim();
            try {
                int n = Integer.parseInt(saisie);
                if (n >= 2 && n <= 6) return n;
            } catch (NumberFormatException ignored) {
                // on redemande
            }
            System.out.println("Merci d'entrer un nombre entre 2 et 6.");
        }
    }

    private static String demanderNom(Scanner scanner, int numero) {
        System.out.print("Nom du joueur " + numero + " : ");
        String nom = scanner.nextLine().trim();
        return nom.isEmpty() ? "Joueur " + numero : nom;
    }
}
