package league.league_table_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResult {

    private String team1Name;
    private int team1Score;
    private String team1Result;

    private String team2Name;
    private int team2Score;
    private String team2Result;

}