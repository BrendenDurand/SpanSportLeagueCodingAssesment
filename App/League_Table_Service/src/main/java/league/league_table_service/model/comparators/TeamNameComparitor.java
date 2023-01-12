package league.league_table_service.model.comparators;


import league.league_table_service.model.LeagueTableEntry;

import java.util.Comparator;

public class TeamNameComparitor implements Comparator<LeagueTableEntry> {

    @Override
    public int compare (LeagueTableEntry o1, LeagueTableEntry o2) {
        return o1.getTeamName().compareTo(o2.getTeamName());
    }
}
