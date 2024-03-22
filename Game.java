import java.util.List;
import java.util.Scanner;

/**
 * Classe représentant le jeu.
 */
public class Game {
    private Board board;
    private Pion pionRouge, pionBleu, pionBlanc;
    private Pion pionJoueur1, pionJoueur2;
    private int scoreJoueur1 = 0, scoreJoueur2 = 0;

    /**
     * Constructeur de la classe Game.
     * Initialise le plateau et les pions à leurs positions de départ.
     */
    public Game() {
        this.board = new Board();
        this.pionRouge = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        this.pionBlanc = new Pion(Pion.Color.WHITE, 4, Pion.Orientation.HORIZONTAL);
        this.pionBleu = new Pion(Pion.Color.BLUE, 7, Pion.Orientation.HORIZONTAL);
        start();
    }

    /**
     * Méthode pour démarrer le jeu.
     * Demande aux joueurs de choisir une couleur et lance la boucle de jeu.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Joueur 1, choisissez une couleur (R pour Rouge, B pour Bleu): ");
        String couleur = scanner.nextLine().toUpperCase();
        while (!couleur.equals("R") && !couleur.equals("B")) {
            System.out.println("Choix de couleur invalide. Veuillez réessayer.");
            couleur = scanner.nextLine().toUpperCase();
        }
        assignPions(couleur);
        gameloop(scanner);
        scanner.close();
    }

    /**
     * Méthode pour assigner les pions aux joueurs en fonction de leur choix de couleur.
     * @param couleur La couleur choisie par le joueur 1.
     */
    private void assignPions(String couleur) {
        switch (couleur) {
            case "R":
                this.pionJoueur1 = this.pionRouge;
                this.pionJoueur2 = this.pionBleu;
                break;
            case "B":
                this.pionJoueur1 = this.pionBleu;
                this.pionJoueur2 = this.pionRouge;
                break;
            default:
                System.out.println("Choix de couleur invalide.");
                return;
        }
        this.board.placePion(this.pionJoueur1, this.pionJoueur1.getPosition(), this.pionJoueur1.getOrientation());
        this.board.placePion(this.pionBlanc, this.pionBlanc.getPosition(), this.pionBlanc.getOrientation());
        this.board.placePion(this.pionJoueur2, this.pionJoueur2.getPosition(), this.pionJoueur2.getOrientation());
    }

    /**
     * Méthode représentant la boucle principale du jeu.
     * @param scanner L'objet Scanner utilisé pour lire les entrées des joueurs.
     */
    private void gameloop(Scanner scanner) {
        boolean gameOver = false;
        while (!gameOver) {
            if (!executePlayerTurn(scanner, pionJoueur1)) {
                gameOver = true;
                continue;
            }
            executeNeutralTurn(scanner, pionBlanc);
            gameOver = checkWinCondition();
            if (gameOver) break;
            echangerJoueurs();
        }
    }

    /**
     * Méthode pour exécuter le tour d'un joueur.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée du joueur.
     * @param currentPlayerPion Le pion du joueur courant.
     * @return Un booléen indiquant si le tour du joueur a été exécuté avec succès.
     */
    private boolean executePlayerTurn(Scanner scanner, Pion currentPlayerPion) {
        System.out.println("Déplacements possibles pour " + currentPlayerPion.getColor() + ":");
        System.out.println(board.getBoardWithMoveOptions(currentPlayerPion));
        List<Integer> playerMoves = this.board.getAvailableMoves(currentPlayerPion);
        if (playerMoves.isEmpty()) {
            System.out.println("Aucun déplacement possible pour " + currentPlayerPion.getColor() + ". Match nul.");
            return false;
        }
        int playerChoice = obtenirChoix(scanner, currentPlayerPion, playerMoves);
        this.board.effectuerMouvement(playerChoice, currentPlayerPion);
        updateScores();
        return true;
    }

    /**
     * Méthode pour exécuter le tour du pion neutre.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée du joueur.
     * @param neutralPion Le pion neutre.
     */
    private void executeNeutralTurn(Scanner scanner, Pion neutralPion) {
        System.out.println("Déplacements possibles pour le pion neutre :");
        System.out.println(board.getBoardWithMoveOptions(neutralPion));
        List<Integer> neutralMoves = this.board.getAvailableMoves(neutralPion);
        if (!neutralMoves.isEmpty()) {
            int neutralChoice = obtenirChoix(scanner, neutralPion, neutralMoves);
            this.board.effectuerMouvement(neutralChoice, neutralPion);
        }
    }

    /**
     * Méthode pour mettre à jour les scores des joueurs.
     */
    private void updateScores() {
        scoreJoueur1 = 0;
        scoreJoueur2 = 0;

        char[] rightColumnSpots = board.getRightColumnSpots();

        for (char spot : rightColumnSpots) {
            if (spot == pionJoueur1.getColor().getSymbol()) {
                scoreJoueur1++;
            } else if (spot == pionJoueur2.getColor().getSymbol()) {
                scoreJoueur2++;
            }
        }

        System.out.println("Score Joueur 1: " + scoreJoueur1 + " - Score Joueur 2: " + scoreJoueur2);
        board.printBoard();
    }

    /**
     * Méthode pour obtenir le choix du joueur.
     * @param scanner L'objet Scanner utilisé pour lire l'entrée du joueur.
     * @param pion Le pion du joueur courant.
     * @param mouvementsPossibles La liste des mouvements possibles pour le pion.
     * @return Le choix du joueur.
     */
    private int obtenirChoix(Scanner scanner, Pion pion, List<Integer> mouvementsPossibles) {
        int choixIndex = -1;
        do {
            System.out.println("Entrez le numéro de votre choix :");
            if (scanner.hasNextInt()) {
                int choix = scanner.nextInt();
                if (choix > 0 && choix <= mouvementsPossibles.size()) {
                    choixIndex = mouvementsPossibles.get(choix - 1);
                    break;
                }
            } else {
                scanner.next();
            }

        } while (true);

        return choixIndex;
    }

    /**
     * Méthode pour vérifier si les conditions de victoire sont remplies.
     * @return Un booléen indiquant si les conditions de victoire sont remplies.
     */
    boolean checkWinCondition() {
        if (this.scoreJoueur1 >= 12) {
            if (this.scoreJoueur2 < 6) {
                System.out.println("Le joueur 2 gagne car le joueur 1 a atteint 12 points mais le joueur 2 a moins de 6 points.");
                return true;
            } else {
                System.out.println("Le joueur 1 gagne.");
                return true;
            }
        } else if (this.scoreJoueur2 >= 12) {
            if (this.scoreJoueur1 < 6) {
                System.out.println("Le joueur 1 gagne car le joueur 2 a atteint 12 points mais le joueur 1 a moins de 6 points.");
                return true;
            } else {
                System.out.println("Le joueur 2 gagne.");
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode pour échanger les joueurs.
     */
    private void echangerJoueurs() {
        Pion temp = this.pionJoueur1;
        this.pionJoueur1 = this.pionJoueur2;
        this.pionJoueur2 = temp;
    }

    /**
     * Méthode pour terminer le jeu.
     * @return Un booléen indiquant que le jeu est terminé.
     */
    public boolean end() {
        System.out.println("Fin du jeu.");
        return true;
    }

    /**
     * Méthode pour déplacer un pion.
     * @param choix Le choix de mouvement.
     * @return Un booléen indiquant si le mouvement a été effectué avec succès.
     */
    public boolean movePion(int choix) {
        return true;
    }
}