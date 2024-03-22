import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    // Teste si la méthode printBoard s'exécute sans lever d'exception
    @Test
    void printBoard_printsCorrectly() {
        Board board = new Board();
        assertDoesNotThrow(() -> board.printBoard());
    }

    // Teste si la méthode getBoardWithMoveOptions retourne les options correctes
    @Test
    void getBoardWithMoveOptions_returnsCorrectOptions() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        assertDoesNotThrow(() -> board.getBoardWithMoveOptions(pion));
    }

    // Teste si la méthode formatSpot formate correctement les spots
    @Test
    void formatSpot_formatsCorrectly() {
        Board board = new Board();
        assertEquals(" ", board.formatSpot('O'));
        assertEquals("R", board.formatSpot('R'));
        assertEquals("B", board.formatSpot('B'));
        assertEquals("W", board.formatSpot('W'));
    }

    // Teste si la méthode isPionSpot retourne correctement si une case est un spot de pion
    @Test
    void isPionSpot_returnsCorrectly() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        assertFalse(board.isPionSpot(0, pion));
        assertTrue(board.isPionSpot(1, pion));
    }

    // Teste si la méthode canMoveHorizontal retourne correctement si un pion peut se déplacer horizontalement
    @Test
    void canMoveHorizontal_returnsCorrectly() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        assertFalse(board.canMoveHorizontal(2, pion));
        assertTrue(board.canMoveHorizontal(1, pion));
    }

    // Teste si la méthode canMoveVertical retourne correctement si un pion peut se déplacer verticalement
    @Test
    void canMoveVertical_returnsCorrectly() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.VERTICAL);
        assertFalse(board.canMoveVertical(0, pion));
        assertTrue(board.canMoveVertical(4, pion));
    }

    // Teste si la méthode placePion place correctement un pion
    @Test
    void placePion_placesPionCorrectly() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        board.placePion(pion, 3, Pion.Orientation.HORIZONTAL);
        assertEquals(3, pion.getPosition());
    }

    // Teste si la méthode effectuerMouvement déplace correctement un pion
    @Test
    void effectuerMouvement_movesPionCorrectly() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        board.effectuerMouvement(0, pion);
        assertEquals(0, pion.getPosition());
    }

    // Teste si la méthode calculateNewOrientation retourne correctement la nouvelle orientation
    @Test
    void calculateNewOrientation_returnsCorrectOrientation() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        assertEquals(Pion.Orientation.HORIZONTAL, board.calculateNewOrientation(0, pion));
    }

    // Teste si la méthode calculateNewPosition retourne correctement la nouvelle position
    @Test
    void calculateNewPosition_returnsCorrectPosition() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        assertEquals(0, board.calculateNewPosition(0, Pion.Orientation.HORIZONTAL, pion));
    }

    // Teste si la méthode canPlaceHorizontal retourne correctement si un pion peut être placé horizontalement
    @Test
    void canPlaceHorizontal_returnsCorrectly() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        assertFalse(board.canPlaceHorizontal(2, pion));
        assertTrue(board.canPlaceHorizontal(1, pion));
    }

    // Teste si la méthode canPlaceVertical retourne correctement si un pion peut être placé verticalement
    @Test
    void canPlaceVertical_returnsCorrectly() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.VERTICAL);
        assertFalse(board.canPlaceVertical(0, pion));
        assertTrue(board.canPlaceVertical(4, pion));
    }

    // Teste si la méthode isEmpty retourne correctement si une case est vide
    @Test
    void isEmpty_returnsCorrectly() {
        Board board = new Board();
        assertTrue(board.isEmpty(0));
        assertFalse(board.isEmpty(1));
    }

    // Teste si la méthode clearPionPosition efface correctement la position d'un pion
    @Test
    void clearPionPosition_clearsCorrectly() {
        Board board = new Board();
        Pion pion = new Pion(Pion.Color.RED, 1, Pion.Orientation.HORIZONTAL);
        board.clearPionPosition(pion);
        assertTrue(board.isEmpty(1));
    }
}