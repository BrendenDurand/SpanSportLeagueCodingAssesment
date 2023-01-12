package league.league_table_service.model.comparators;

import league.league_table_service.model.LeagueTableEntry;

import java.util.Comparator;

public class TeamPointsComparator implements Comparator<LeagueTableEntry> {
    @Override
    public int compare (LeagueTableEntry o1, LeagueTableEntry o2) {
        return Integer.compare(o2.getPoints(), o1.getPoints());
    }
}

