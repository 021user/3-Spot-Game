/**
 * Classe représentant un pion dans le jeu.
 */
public class Pion {
    /**
     * Enumération représentant les couleurs possibles d'un pion.
     */
    public enum Color {
        RED('R'),
        BLUE('B'),
        WHITE('W'),
        SPECIAL('O');

        private final char symbol;

        /**
         * Constructeur de l'énumération Color.
         * @param symbol Le symbole représentant la couleur.
         */
        Color(char symbol) {
            this.symbol = symbol;
        }

        /**
         * Méthode pour obtenir le symbole de la couleur.
         * @return Le symbole de la couleur.
         */
        public char getSymbol() {
            return this.symbol;
        }
    }

    /**
     * Enumération représentant les orientations possibles d'un pion.
     */
    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private Color color;
    private int position;
    private Orientation orientation;
    private final int taillePion = 2;

    /**
     * Constructeur de la classe Pion.
     * @param color La couleur du pion.
     * @param position La position du pion sur le plateau.
     * @param orientation L'orientation du pion.
     */
    public Pion(Color color, int position, Orientation orientation) {
        this.color = color;
        this.position = position;
        this.orientation = orientation;
    }

    /**
     * Méthode pour obtenir la couleur du pion.
     * @return La couleur du pion.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Méthode pour définir l'orientation du pion.
     * @param orientation La nouvelle orientation du pion.
     */
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * Méthode pour obtenir la position du pion.
     * @return La position du pion.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Méthode pour définir la position du pion.
     * @param position La nouvelle position du pion.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Méthode pour obtenir l'orientation du pion.
     * @return L'orientation du pion.
     */
    public Orientation getOrientation() {
        return orientation;
    }
}