package league.league_table_service.model.enums;

public enum GameResultEnum {
    W("W"),
    D("D"),
    L("L");

    private String gameResultValue;

    GameResultEnum (String gameResultValue) {
        this.gameResultValue = gameResultValue;
    }

    public String getGameResultValue () {
        return gameResultValue;
    }

}
