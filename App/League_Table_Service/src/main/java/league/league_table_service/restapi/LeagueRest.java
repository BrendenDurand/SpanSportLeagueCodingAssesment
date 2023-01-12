package league.league_table_service.restapi;

import league.league_table_service.service.LeagueTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeagueRest {

    @Autowired
    LeagueTableService leagueTableService;

    @GetMapping (value = "/ordered/results", consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String sayHello(@RequestBody String matchResults) {

        return leagueTableService.populateLeagueTable(matchResults);
    }

}
