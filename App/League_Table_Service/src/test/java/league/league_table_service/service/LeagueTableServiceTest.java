package league.league_table_service.service;

import league.league_table_service.model.GameResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeagueTableServiceTest {

    @Autowired
    private LeagueTableService leagueTableService;

    @Test
    void calculatePointsGained () {
        String matchResults = "W";

        int pointsGained = leagueTableService.calculatePointsGained(matchResults);
        assertEquals(3, pointsGained);

        matchResults = "D";

        pointsGained = leagueTableService.calculatePointsGained(matchResults);
        assertEquals(1, pointsGained);

        matchResults = "L";

        pointsGained = leagueTableService.calculatePointsGained(matchResults);
        assertEquals(0, pointsGained);
    }

    @Test
    void createGameResultObjectFromInput () {
        String gameResult = "Lions 3, Snakes 3";

        GameResult gameResultObject = LeagueTableService.createGameResultObjectFromInput(gameResult);
        assertNotNull(gameResultObject);
        assertNotNull(gameResultObject.getTeam1Name());
        assertNotNull(gameResultObject.getTeam1Score());
        assertNotNull(gameResultObject.getTeam1Result());
        assertNotNull(gameResultObject.getTeam2Name());
        assertNotNull(gameResultObject.getTeam2Score());
        assertNotNull(gameResultObject.getTeam2Result());
    }
}