public class Player {
    private String name;
    private int score;
    private Pion pion;

    public Player(String name, Pion pion) {
        this.name = name;
        this.pion = pion;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Pion getPion() {
        return pion;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void resetScore() {
        this.score = 0;
    }
}
