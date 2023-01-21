package service;

import org.example.model.Team;
import org.example.repository.ScoreboardRepository;
import org.example.service.ScoreboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ScoreboardServiceTest {

    ScoreboardRepository scoreboardRepository;

    ScoreboardService scoreboardService;

    @BeforeEach
    public void init() {
        scoreboardRepository = Mockito.mock(ScoreboardRepository.class);
        scoreboardService = new ScoreboardService(scoreboardRepository);
    }

    @Test
    public void saveTeamPairToScoreBoard_teamPairSuccessfullySaved_Test() {
        final String exampleHostTeamName = "HostTeam";
        final String exampleAwayTeamName = "HostTeam";
        final Team hostTeam = new Team(exampleHostTeamName);
        final Team awayTeam = new Team(exampleAwayTeamName);

        scoreboardService.saveTeamPairToScoreBoard(hostTeam, awayTeam);

        Mockito.verify(scoreboardRepository, Mockito.times(1)).saveTeamPair(hostTeam, awayTeam);
    }

    @Test
    public void getFormattedScoreBoard_formattedScoreboardSuccessfullyReturned_Test() {
        final String exampleHostTeamName = "HostTeam";
        final String exampleAwayTeamName = "HostTeam";
        final Team hostTeam = new Team(exampleHostTeamName);
        final Team awayTeam = new Team(exampleAwayTeamName);
        final LinkedHashMap exampleScoreboard = new LinkedHashMap();
        exampleScoreboard.put(hostTeam, awayTeam);

        when(scoreboardRepository.getScoreboard()).thenReturn(exampleScoreboard);

        assertEquals("HostTeam 0 - HostTeam 0 \n", scoreboardService.getFormattedScoreBoard());
    }

    @Test
    public void removeFrmScoreboard_TeamPairSuccessfullyRemovedFromScoreboard_Test() {
        final String exampleHostTeamName = "HostTeam";
        final Team hostTeam = new Team(exampleHostTeamName);

        scoreboardService.removeFromScoreboard(hostTeam);

        verify(scoreboardRepository, times(1)).removeFromScoreboard(hostTeam);
    }

    @Test
    public void getAwayTeamByHostTeam_SuccessfullyGetAwayTeamByHostTeam_Test() {
        final String exampleHostTeamName = "HostTeam";
        final Team hostTeam = new Team(exampleHostTeamName);

        scoreboardService.removeFromScoreboard(hostTeam);

        verify(scoreboardRepository, times(1)).getByHostTeam(hostTeam);
    }

    @Test
    public void updateTeamPairInScoreBoard_SuccessfullyUpdateTeamPairScore_Test() {
        final String exampleHostTeamName = "HostTeam";
        final String exampleAwayTeamName = "HostTeam";
        final Team hostTeam = new Team(exampleHostTeamName);
        final Team awayTeam = new Team(exampleAwayTeamName);

        scoreboardService.updateTeamPairInScoreBoard(hostTeam, awayTeam);

        verify(scoreboardRepository, times(1)).updateTeamPairScore(hostTeam, awayTeam);
    }

}
