import java.util.*;

/**
 * Classe représentant le plateau de jeu.
 */
public class Board {
    // Le plateau de jeu est représenté par un tableau de 9 cases. Chaque case est soit vide (O) soit contient un pion (R, B, W).
    private char[] spots;

    // Initialisation des pions sur le plateau
    private Pion pionRouge, pionBleu, pionBlanc;

    /**
     * Constructeur pour initialiser le plateau de jeu et les pions sur le plateau.
     */
    public Board() {
        // Initialiser le plateau de jeu avec des cases vides
        this.spots = new char[9];
        // Remplir le tableau avec des cases vides
        Arrays.fill(this.spots, 'O');
        // Initialiser les pions sur le plateau
        initPions();
    }

    /**
     * Initialise les pions sur le plateau de jeu en leurs donnant une position et une orientation initiale.
     * Utilise la méthode placePion pour placer les pions sur le plateau qui met à jour la position et l'orientation du pion.
     */
    private void initPions() {
        // Création des pions avec une couleur, une position et une orientation
        pionRouge = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        pionBleu = new Pion(Pion.Color.WHITE, 4, Pion.Orientation.HORIZONTAL);
        pionBlanc = new Pion(Pion.Color.BLUE, 7, Pion.Orientation.HORIZONTAL);
        // Placement des pions sur le plateau
        placePion(pionRouge, pionRouge.getPosition(), pionRouge.getOrientation());
        placePion(pionBlanc, pionBlanc.getPosition(), pionBlanc.getOrientation());
        placePion(pionBleu, pionBleu.getPosition(), pionBleu.getOrientation());
    }

    /**
     * Affiche le plateau de jeu.
     */
    public void printBoard() {
        // Nous utilisons une boucle for pour parcourir le tableau de cases et afficher le plateau de jeu
        // Car le plateau de jeu est représenté par un tableau de 9 cases
        // Un premier for qui affiche ligne par ligne le plateau de jeu
        // premier for pour afficher la ligne complete du plateau de jeu
        // deuxième for pour construire les cases du plateau de jeu
        // nous mettons les valeurs du tableau dans des cases du plateau de jeu
        System.out.println("* * * * * * * * * * * * *");

        for (int i = 0; i < spots.length; i += 3) {

            System.out.println("*       *       *       *");
            System.out.println("*   " + formatSpot(spots[i]) + "   *   " + formatSpot(spots[i + 1]) + "   *   " + formatSpot(spots[i + 2]) + "   *");
            System.out.println("*       *       *       *");
            if (i < 6) System.out.println("* * * * * * * * * * * * *");
        }
        System.out.println("* * * * * * * * * * * * *");
    }

    /**
     * Affiche le plateau de jeu avec les options de mouvement.
     * Utilise une liste pour stocker les mouvements possibles que nous obtenons avec la méthode getAvailableMoves.
     * Parcourt le tableau de cases et affiche le plateau de jeu avec les options de mouvement.
     * Vérifie si la position est vide ou contient un pion.
     * Si la position est vide nous affichons un espace sinon nous affichons la valeur de la position.
     * @param pionToMove Le pion à déplacer.
     * @return Une chaîne de caractères représentant le plateau de jeu avec les options de mouvement.
     */
    public String getBoardWithMoveOptions(Pion pionToMove) {
        List<Integer> availableMoves = getAvailableMoves(pionToMove);
        String[] displaySpots = new String[spots.length];
        for (int i = 0; i < spots.length; i++) {
            if (i == 2 || i == 5 || i == 8) { // Colonnes les plus à droite
                displaySpots[i] = spots[i] == 'O' ? "O" : String.valueOf(spots[i]);
            } else {
                displaySpots[i] = spots[i] == 'O' ? " " : String.valueOf(spots[i]);
            }
        }

        // Nettoyer la position initiale et la position adjacente si le pion est horizontal.
        int startPosition = pionToMove.getPosition();
        if (pionToMove.getOrientation() == Pion.Orientation.HORIZONTAL && startPosition % 3 < 2) {
            displaySpots[startPosition + 1] = displaySpots[startPosition] = " ";
        }

        // Ajouter les options de mouvement avec des numéros directement basés sur leur ordre dans la liste.
        int moveNumber = 1;
        for (int move : availableMoves) {
            if (move < displaySpots.length) { // Assure que le numéro de mouvement est dans la plage du tableau
                displaySpots[move] = Integer.toString(moveNumber++);
            }
        }

        // Construction et retour de la chaîne de caractères représentant le plateau de jeu
        StringBuilder boardDisplay = new StringBuilder();
        boardDisplay.append("* * * * * * * * * * * * *\n");
        for (int i = 0; i < displaySpots.length; i += 3) {
            boardDisplay.append("*       *       *       *\n"); // Lignes vides pour la séparation
            boardDisplay.append("*   ")
                    .append(displaySpots[i]).append("   *   ")
                    .append(displaySpots[i+1]).append("   *   ")
                    .append(displaySpots[i+2]).append("   *\n");
            boardDisplay.append("*       *       *       *\n");
            if (i < 6) { // Séparateurs entre les lignes de spots, sauf pour la dernière ligne
                boardDisplay.append("* * * * * * * * * * * * *\n");
            }
        }
        boardDisplay.append("* * * * * * * * * * * * *");

        return boardDisplay.toString();
    }

    /**
     * Formate les cases du plateau de jeu.
     * @param spot La case à formater.
     * @return Une chaîne de caractères représentant la case formatée.
     */
    public String formatSpot(char spot) {
        return spot == 'O' ? " " : String.valueOf(spot);
    }

    /**
     * Vérifie si la position est un spot de pion.
     * @param position La position à vérifier.
     * @param pion Le pion à vérifier.
     * @return Un booléen indiquant si la position est un spot de pion.
     */
    public boolean isPionSpot(int position, Pion pion) {
        return spots[position] == pion.getColor().getSymbol();
    }

    /**
     * Obtient les spots de la colonne la plus à droite.
     * Utilisé pour mettre à jour les scores et l'affichage du plateau de jeu 'O' pour les spots vides.
     * @return Un tableau de caractères représentant les spots de la colonne la plus à droite.
     */
    public char[] getRightColumnSpots() {
        return new char[] {spots[2], spots[5], spots[8]};
    }

    /**
     * Obtient les mouvements possibles pour un pion donné.
     * Utilise une liste pour stocker les mouvements possibles.
     * Parcourt le tableau de cases et obtient les mouvements possibles.
     * Utilise les méthodes canMoveHorizontal et canMoveVertical pour vérifier les mouvements possibles.
     * @param pion Le pion pour lequel obtenir les mouvements possibles.
     * @return Une liste d'entiers représentant les mouvements possibles.
     */
    public List<Integer> getAvailableMoves(Pion pion) {
        List<Integer> availableMoves = new ArrayList<>();

        // Mouvements horizontaux possibles pour des positions qui ne sont pas en fin de ligne
        for (int i = 0; i < spots.length; i++) {
            if (i % 3 < 2) { // 0 ou 1 ou 3 ou 4 ou 6 ou 7 (donc pas 2, 5, ou 8)
                if (canMoveHorizontal(i, pion)) {
                    availableMoves.add(i);
                }
            }
        }

        // Mouvements verticaux possibles pour des positions qui ne sont pas dans la première ligne
        for (int i = 3; i < spots.length; i++) {
            if (canMoveVertical(i, pion)) {
                availableMoves.add(i); // La position - 3 représente le mouvement vertical vers le haut
            }
        }

        return availableMoves;
    }

    /**
     * Vérifie si un pion peut se déplacer horizontalement à une position donnée.
     * Exclut les mouvements à partir de la dernière colonne car ils ne sont pas possibles grâce à la vérification position % 3 == 2.
     * Vérifie si la case actuelle est vide et si la case directement à droite est vide ou contient le pion actuel.
     * Si la case actuelle est vide et la suivante est vide ou contient le pion actuel alors le déplacement horizontal est possible.
     * Sinon le déplacement horizontal n'est pas possible.
     * @param position La position à vérifier.
     * @param pion Le pion à vérifier.
     * @return Un booléen indiquant si le pion peut se déplacer horizontalement à la position donnée.
     */
    public boolean canMoveHorizontal(int position, Pion pion) {
        if (position % 3 == 2) return false; // Empêche de vérifier à l'extrême droite.

        // Vérifie si la case actuelle est vide et si la case directement à droite est vide ou contient le pion actuel.
        char currentSpot = spots[position];
        char nextSpot = spots[position + 1];

        // Pour un déplacement horizontal valide, la position actuelle doit être vide et la suivante doit être vide ou contenir le pion actuel.
        return currentSpot == 'O' && (nextSpot == 'O' || nextSpot == pion.getColor().getSymbol());
    }

    /**
     * Méthode pour vérifier si un pion peut se déplacer verticalement à une position donnée.
     * Nous excluons les mouvements à partir de la première ligne car ils ne sont pas possibles.
     * Nous utilisons une vérification pour savoir si la position actuelle est vide et si la case au-dessus est vide ou contient le pion actuel.
     * Si la position actuelle est vide et la case au-dessus est vide ou contient le pion actuel alors le déplacement vertical est possible.
     * Sinon le déplacement vertical n'est pas possible.
     * @param position La position à vérifier.
     * @param pion Le pion à vérifier.
     * @return Un booléen indiquant si le pion peut se déplacer verticalement à la position donnée.
     */
    public boolean canMoveVertical(int position, Pion pion) {
        if (position < 3) return false;

        char currentSpot = spots[position];
        char nextSpot = spots[position - 3];

        return currentSpot == 'O' && (nextSpot == 'O' || nextSpot == pion.getColor().getSymbol());
    }

    /**
     * Méthode pour placer un pion à une nouvelle position et orientation.
     * Nous utilisons la méthode clearPionPosition pour effacer l'ancienne position du pion.
     * Nous utilisons une vérification pour savoir si le placement ne déborde pas sur une nouvelle ligne.
     * Nous utilisons une vérification pour savoir si le placement ne déborde pas en bas du plateau.
     * Nous utilisons les méthodes isEmpty et isPionSpot pour vérifier la validité du placement.
     * Alors nous mettons à jour la position et l'orientation du pion avec les nouvelles valeurs.
     * @param pion Le pion à placer.
     * @param nouvellePosition La nouvelle position du pion.
     * @param nouvelleOrientation La nouvelle orientation du pion.
     */
    public void placePion(Pion pion, int nouvellePosition, Pion.Orientation nouvelleOrientation) {
        clearPionPosition(pion);

        int deuxiemeCase;

        if (nouvelleOrientation == Pion.Orientation.HORIZONTAL) {
            if (nouvellePosition % 3 < 2) {
                deuxiemeCase = nouvellePosition + 1;
            } else {
                return;
            }
        } else {
            if (nouvellePosition >= 3) {
                deuxiemeCase = nouvellePosition - 3;
            } else {
                return;
            }
        }
        if ((isEmpty(nouvellePosition) || isPionSpot(nouvellePosition, pion)) &&
                (isEmpty(deuxiemeCase) || isPionSpot(deuxiemeCase, pion))) {
            spots[nouvellePosition] = pion.getColor().getSymbol();
            if (nouvelleOrientation == Pion.Orientation.HORIZONTAL && nouvellePosition % 3 < 2) {
                spots[deuxiemeCase] = pion.getColor().getSymbol();
            } else if (nouvelleOrientation == Pion.Orientation.VERTICAL && nouvellePosition >= 3) {
                spots[deuxiemeCase] = pion.getColor().getSymbol();
            }
        } else {
            return;
        }
        pion.setPosition(nouvellePosition);
        pion.setOrientation(nouvelleOrientation);
    }

    /**
     * Méthode pour effectuer un mouvement.
     * Nous utilisons les méthodes calculateNewOrientation et calculateNewPosition pour obtenir la nouvelle position et orientation.
     * Nous utilisons les méthodes canMoveHorizontal et canMoveVertical pour vérifier la validité du mouvement.
     * Nous utilisons la méthode placePion pour placer le pion à la nouvelle position et orientation.
     * @param choix Le choix de mouvement.
     * @param pion Le pion à déplacer.
     */
    public void effectuerMouvement(int choix, Pion pion) {
        Pion.Orientation desiredOrientation = calculateNewOrientation(choix, pion);
        int nouvellePosition = calculateNewPosition(choix, desiredOrientation, pion);

        boolean movePossible = (desiredOrientation == Pion.Orientation.HORIZONTAL && canMoveHorizontal(nouvellePosition, pion)) ||
                (desiredOrientation == Pion.Orientation.VERTICAL && canMoveVertical(nouvellePosition, pion));
        if (movePossible) {
            placePion(pion, nouvellePosition, desiredOrientation);
        }
    }

    /**
     * Méthode pour calculer la nouvelle position basée sur le choix et l'orientation du pion.
     * Nous utilisons une vérification pour savoir si le choix est valide.
     * Nous utilisons une vérification pour savoir si le choix est horizontal ou vertical.
     * Nous utilisons les méthodes canPlaceHorizontal et canPlaceVertical pour vérifier la validité du choix.
     * Nous utilisons la méthode isEmpty pour vérifier si la position est vide.
     * Nous mettons à jour la nouvelle position avec la valeur du choix.
     * @param choix Le choix de position.
     * @param orientation L'orientation du pion.
     * @param pion Le pion à déplacer.
     * @return La nouvelle position calculée.
     */
    public int calculateNewPosition(int choix, Pion.Orientation orientation, Pion pion) {
        int newPosition = choix;
        return newPosition;
    }

    /**
     * Méthode pour vérifier si un pion peut être placé horizontalement à une position donnée.
     * Assurez-vous que la position n'est pas dans la dernière colonne du plateau et que les deux cases consécutives sont vides ou contiennent le pion actuel.
     * @param position La position à vérifier.
     * @param pion Le pion à vérifier.
     * @return Un booléen indiquant si le pion peut être placé horizontalement à la position donnée.
     */
    public boolean canPlaceHorizontal(int position, Pion pion) {
        return position % 3 < 2 && (isEmpty(position) || isPionSpot(position, pion)) && (isEmpty(position + 1) || isPionSpot(position + 1, pion));
    }

    /**
     * Méthode pour vérifier si un pion peut être placé verticalement à une position donnée.
     * @param position La position à vérifier.
     * @param pion Le pion à vérifier.
     * @return Un booléen indiquant si le pion peut être placé verticalement à la position donnée.
     */
    public boolean canPlaceVertical(int position, Pion pion) {
        return position >= 3 && (isEmpty(position - 3) || isPionSpot(position - 3, pion)) && (isEmpty(position) || isPionSpot(position, pion));
    }

    /**
     * Méthode pour vérifier si une position est vide.
     * @param position La position à vérifier.
     * @return Un booléen indiquant si la position est vide.
     */
    public boolean isEmpty(int position) {
        return spots[position] == 'O';
    }

    /**
     * Méthode pour effacer la position du pion.
     * Nous utilisons une vérification pour savoir si l'orientation est horizontale.
     * Nous utilisons une vérification pour savoir si l'orientation est verticale.
     * Nous utilisons la méthode isEmpty pour vérifier si la position est vide.
     * Nous mettons à jour la position avec la valeur 'O'.
     * @param pion Le pion dont la position doit être effacée.
     */
    public void clearPionPosition(Pion pion) {
        int position = pion.getPosition();
        spots[position] = 'O';
        if (pion.getOrientation() == Pion.Orientation.HORIZONTAL && position % 3 < 2) {
            spots[position + 1] = 'O';
        } else if (pion.getOrientation() == Pion.Orientation.VERTICAL && position >= 3) {
            spots[position - 3] = 'O';
        }
    }

    /**
     * Méthode pour calculer la nouvelle orientation basée sur le choix et l'orientation du pion.
     * Nous utilisons une vérification pour savoir si le choix est valide.
     * Nous utilisons les méthodes canPlaceHorizontal et canPlaceVertical pour vérifier la validité du choix.
     * Nous mettons à jour la nouvelle orientation avec la valeur de l'orientation actuelle du pion.
     * @param choix Le choix d'orientation.
     * @param pion Le pion dont l'orientation doit être calculée.
     * @return La nouvelle orientation calculée.
     */
    public Pion.Orientation calculateNewOrientation(int choix, Pion pion) {
        boolean horizontalPossible = canPlaceHorizontal(choix, pion);
        boolean verticalPossible = canPlaceVertical(choix, pion);

        Pion.Orientation newOrientation;
        if (horizontalPossible && !verticalPossible) {
            newOrientation = Pion.Orientation.HORIZONTAL;
        } else {
            newOrientation = Pion.Orientation.VERTICAL;
        }

        return newOrientation;
    }


}