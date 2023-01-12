package league.league_table_service.service;

import league.league_table_service.model.GameResult;
import league.league_table_service.model.LeagueTableEntry;
import league.league_table_service.model.comparators.TeamNameComparitor;
import league.league_table_service.model.comparators.TeamPointsComparator;
import league.league_table_service.model.enums.GameResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class LeagueTableService {
    public String populateLeagueTable (String matchResults) {

        if (matchResults == null || matchResults.isBlank()) {
            return "You have not entered any match results to validate";
        }

        List<GameResult> listOfGameResults = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(matchResults);
            while (scanner.hasNext()) {
                listOfGameResults.add(createGameResultObjectFromInput(scanner.nextLine()));
            }
        } catch (RuntimeException e) {
            return "There is an error with the match results you have inputted please not that your data must be in the following format \n" +
                    "Team1Name Score, Team2Name Score \n" +
                    "Team3Name Score, Team4Name Score";
        }

        HashMap<String, Integer> tempLeagueTable = new HashMap<>();

        for (GameResult gameResult : listOfGameResults) {
            tempLeagueTable = addTeamToTempLeagueMap(gameResult.getTeam1Name(), gameResult.getTeam1Result(), tempLeagueTable);
            tempLeagueTable = addTeamToTempLeagueMap(gameResult.getTeam2Name(), gameResult.getTeam2Result(), tempLeagueTable);
        }

        List<LeagueTableEntry> orderedLeagueTable = sortLeagueListByPointsAndTeamName(tempLeagueTable);

        StringBuilder responsebuilder = new StringBuilder();
        for (LeagueTableEntry entry : orderedLeagueTable) {
            responsebuilder.append(entry.getTeamName() + ", "+ entry.getPoints() + "pts \n" );
        }

        return responsebuilder.toString();
    }

    /**
     * @param tempLeagueTable
     * @return List<LeaguetableEntry>
     * <p>
     * This method is used in order to sort the league table while it is not ordered
     * We first sort the list by alphabet and secondly we sort the league by points
     * The result will be a league table ordered by points and where points are equal
     * between multiple teams, they are ordered alphabetically.
     */
    public List<LeagueTableEntry> sortLeagueListByPointsAndTeamName (HashMap<String, Integer> tempLeagueTable) {
        List<LeagueTableEntry> orderedLeagueTable = new ArrayList<>();
        //now to sort the table
        for (Map.Entry<String, Integer> set : tempLeagueTable.entrySet()) {
            orderedLeagueTable.add(new LeagueTableEntry(set.getKey(), set.getValue()));
        }

        TeamNameComparitor teamNameComparitor = new TeamNameComparitor();
        TeamPointsComparator teamPointsComparator = new TeamPointsComparator();

        orderedLeagueTable.sort(teamNameComparitor);
        orderedLeagueTable.sort(teamPointsComparator);

        return orderedLeagueTable;
    }

    /**
     * @param gameResult
     * @return int: pointsGained
     * <p>
     * Method is used to determine the number of points a team
     * has gained based on their game result
     * <p>
     * A win is 3 points gained
     * A draw is 1 point gained
     * A lost is 0 points gained
     */
    public int calculatePointsGained (String gameResult) {
        int pointsGained = 0;

        switch (GameResultEnum.valueOf(gameResult)) {
            case W:
                pointsGained = 3;
                break;

            case L:
                pointsGained = 0;
                break;

            default:
                pointsGained = 1;
                break;
        }

        return pointsGained;
    }

    private HashMap<String, Integer> addTeamToTempLeagueMap (String teamName, String teamResult, HashMap<String, Integer> tempLeagueTable) {
        int teamTotalPoints = 0;
        if (tempLeagueTable.containsKey(teamName)) {
            teamTotalPoints = tempLeagueTable.get(teamName);
        }

        int teamPointsGained = calculatePointsGained(teamResult);
        tempLeagueTable.put(teamName, teamTotalPoints + teamPointsGained);

        return tempLeagueTable;
    }

    public static GameResult createGameResultObjectFromInput (String gameResultString) {

        GameResult gameResult = new GameResult();

        gameResultString = gameResultString.replaceAll("\\s+", "");

        String team1Result = gameResultString.substring(0, gameResultString.indexOf(','));
        String team2Result = gameResultString.substring(gameResultString.indexOf(',') + 1);


        gameResult.setTeam1Name(team1Result.replaceAll("[0-9]", ""));
        gameResult.setTeam1Score(Integer.parseInt(team1Result.replaceAll("[^\\d.]", "")));

        gameResult.setTeam2Name(team2Result.replaceAll("[0-9]", ""));
        gameResult.setTeam2Score(Integer.parseInt(team2Result.replaceAll("[^\\d.]", "")));


        /*We use the below to determine if a team won or lost their game in order to set the result
          To a Win, Loss or Draw. This will make it easier to later determine a generic point
          calculating method based on the game result
         */
        if (gameResult.getTeam1Score() > gameResult.getTeam2Score()) {
            gameResult.setTeam1Result(GameResultEnum.W.getGameResultValue());
            gameResult.setTeam2Result(GameResultEnum.L.getGameResultValue());
        } else if (gameResult.getTeam1Score() < gameResult.getTeam2Score()) {
            gameResult.setTeam1Result(GameResultEnum.L.getGameResultValue());
            gameResult.setTeam2Result(GameResultEnum.W.getGameResultValue());
        } else {
            gameResult.setTeam1Result(GameResultEnum.D.getGameResultValue());
            gameResult.setTeam2Result(GameResultEnum.D.getGameResultValue());
        }

        return gameResult;
    }
}
