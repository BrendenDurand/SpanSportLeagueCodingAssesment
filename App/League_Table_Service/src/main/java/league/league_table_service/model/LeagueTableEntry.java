package league.league_table_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeagueTableEntry {

    private String teamName;
    private int points;

    public LeagueTableEntry (String teamName, int points) {
        this.teamName = teamName;
        this.points = points;
    }
}
