public class FootballTeam implements Comparable<FootballTeam> {
    private final int gamesWon;

    public FootballTeam(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    @Override
    public int compareTo(FootballTeam otherTeam) {
        return gamesWon - otherTeam.gamesWon;
    }
}
