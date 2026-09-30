# Jeu de l'Oie en Java

Implémentation en console du jeu de l'oie classique (63 cases), de 2 à 6 joueurs.

## Lancer le jeu

**Avec Maven**
```bash
mvn package
java -jar target/jeu-de-l-oie.jar
```

**Sans Maven (JDK 17+)**
```bash
javac -d out $(find src -name "*.java")
java -cp out com.hendrix.jeudeloie.Main
```

Mode simulation (3 joueurs, sans interaction) :
```bash
java -cp out com.hendrix.jeudeloie.Main --auto
```

## Règles implémentées

| Case | Effet |
|------|-------|
| Oies (5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59) | On rejoue la même valeur de dés (les oies s'enchaînent) |
| 6 – Pont | Direction la case 12 |
| 19 – Hôtel | On passe 2 tours |
| 31 – Puits | Bloqué jusqu'à ce qu'un autre joueur vienne prendre sa place |
| 42 – Labyrinthe | Retour à la case 30 |
| 52 – Prison | Bloqué jusqu'à ce qu'un autre joueur vienne prendre sa place |
| 58 – Tête de mort | Retour au départ |
| 63 – Arrivée | Il faut tomber pile dessus, sinon on recule de l'excédent |

Premier lancer : un 9 obtenu par 6+3 envoie en case 26, par 5+4 en case 53.

## Structure

```
src/main/java/com/hendrix/jeudeloie/
├── Main.java      # Point d'entrée, saisie des joueurs
├── Partie.java    # Boucle de jeu et application des règles
├── Plateau.java   # Cases spéciales et calcul des déplacements
├── Joueur.java    # État d'un joueur
├── Des.java       # Lancer de deux dés
└── TypeCase.java  # Types de cases
```

## Auteur

Emmanuel Kadiebwe
