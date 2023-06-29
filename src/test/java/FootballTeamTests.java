import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FootballTeamTests {

    private static final int ANY_NUMBER = 123;

    @Test
    void shouldBePossibleToCompareTeams(){
        FootballTeam footballTeam = new FootballTeam(ANY_NUMBER);
        assertTrue(footballTeam instanceof Comparable<?>,"Football team should implemenent Comparable");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void constructorShouldSetGamesWon(int values) {
        FootballTeam footballTeam = new FootballTeam(values);
        assertEquals(values, footballTeam.getGamesWon());
    }

    @ParameterizedTest
    @ValueSource(ints = {-10,-1})
    public void constructorShouldThrowExceptionForIllegalNumberOfGamesWon(int values){
        new FootballTeam(values);
    }

    @Test
    void teamsWithMoreMatchesWonShouldBeGreater(){
        FootballTeam team_2 = new FootballTeam(2);
        FootballTeam team_3 = new FootballTeam(3);
        assertTrue(team_3.compareTo(team_2) > 0);
    }

    @Test
    void teamsWithLessMatchesWonShouldBeLesser(){
        FootballTeam team_2 = new FootballTeam(2);
        FootballTeam team_3 = new FootballTeam(3);
        assertTrue(team_2.compareTo(team_3)<0);
    }

    @Test
    void teamsWithSameNumberOfMatchesWonShouldBeEqual(){
        FootballTeam team_2 = new FootballTeam(2);
        FootballTeam team_3 = new FootballTeam(2);
        assertTrue(team_2.compareTo(team_3)==0);
    }
}
