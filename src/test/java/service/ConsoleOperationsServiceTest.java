package service;

import org.example.model.Team;
import org.example.service.ConsoleOperationsService;
import org.example.service.ScoreboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class ConsoleOperationsServiceTest {

    ScoreboardService scoreboardService;

    ConsoleOperationsService consoleOperationsService;

    @BeforeEach
    public void init() {
        scoreboardService = Mockito.mock(ScoreboardService.class);
        consoleOperationsService = new ConsoleOperationsService(scoreboardService);
    }

    @Test
    public void processCommand_SuccessfullyProcessStartMatchCommand_Test() {
        final String exampleStartMatchCommand = "start-match --home-team-name=Dynamo --away-team-name=Legia";
        ArgumentCaptor<Team> exampleHostTeamCaptor = ArgumentCaptor.forClass(Team.class);
        ArgumentCaptor<Team> exampleAwayTeamCaptor = ArgumentCaptor.forClass(Team.class);

        consoleOperationsService.processCommand(exampleStartMatchCommand);

        Mockito.verify(scoreboardService, Mockito.times(1))
                .saveTeamPairToScoreBoard(exampleHostTeamCaptor.capture(), exampleAwayTeamCaptor.capture());

        assertEquals(0, exampleHostTeamCaptor.getValue().getScore());
        assertEquals(0, exampleAwayTeamCaptor.getValue().getScore());
        assertEquals("Legia", exampleAwayTeamCaptor.getValue().getName());
        assertEquals("Dynamo", exampleHostTeamCaptor.getValue().getName());
    }

    @Test
    public void processCommand_InvalidParametersSpecificationStartMatch_Test() {
        final String exampleStartMatchCommand = "start-match --home-team-me=Dynamo --away-team-name=Legia";

        consoleOperationsService.processCommand(exampleStartMatchCommand);

        Mockito.verifyNoInteractions(scoreboardService);
    }

    @Test
    public void processCommand_SuccessfullyProcessUpdateScoreCommand_Test() {
        final String exampleUpdateScoreCommand = "update-score --home-team-name=Dynamo --home-team-score=4 --away-team-score=3";
        final Team exampleAwayTeam = new Team("Legia", 2);
        ArgumentCaptor<Team> exampleHostTeamCaptor = ArgumentCaptor.forClass(Team.class);
        ArgumentCaptor<Team> exampleAwayTeamCaptor = ArgumentCaptor.forClass(Team.class);

        Mockito.when(scoreboardService.getAwayTeamByHostTeam(any()))
                .thenReturn(exampleAwayTeam);

        consoleOperationsService.processCommand(exampleUpdateScoreCommand);

        Mockito.verify(scoreboardService, Mockito.times(1))
                .updateTeamPairInScoreBoard(exampleHostTeamCaptor.capture(), exampleAwayTeamCaptor.capture());

        assertEquals(4, exampleHostTeamCaptor.getValue().getScore());
        assertEquals(3, exampleAwayTeamCaptor.getValue().getScore());
        assertEquals("Legia", exampleAwayTeamCaptor.getValue().getName());
        assertEquals("Dynamo", exampleHostTeamCaptor.getValue().getName());
    }

    @Test
    public void processCommand_InvalidParametersSpecificationUpdateScore_Test() {
        final String exampleUpdateScoreCommand = "update-score --home-team-me=Dynamo --away-team-name=Legia";

        consoleOperationsService.processCommand(exampleUpdateScoreCommand);

        Mockito.verifyNoInteractions(scoreboardService);
    }


    @Test
    public void processCommand_SuccessfullyProcessSummaryCommand_Test() {
        final String exampleSummaryCommand = "summary";

        consoleOperationsService.processCommand(exampleSummaryCommand);

        Mockito.verify(scoreboardService, Mockito.times(1)).getFormattedScoreBoard();
    }

    @Test
    public void processCommand_SuccessfullyProcessRemoveCommand_Test() {
        final String exampleRemovePairCommand = "remove-match --home-team-name=Dynamo";
        ArgumentCaptor<Team> exampleHostTeamCaptor = ArgumentCaptor.forClass(Team.class);

        consoleOperationsService.processCommand(exampleRemovePairCommand);

        Mockito.verify(scoreboardService, Mockito.times(1))
                .removeFromScoreboard(exampleHostTeamCaptor.capture());

        assertEquals("Dynamo", exampleHostTeamCaptor.getValue().getName());
    }

    @Test
    public void processCommand_InvalidParameterSpecificationRemoveTeamPair_Team() {
        final String exampleRemovePairCommand = "remove-match --home-am-name=Dynamo";

        consoleOperationsService.processCommand(exampleRemovePairCommand);

        Mockito.verifyNoInteractions(scoreboardService);
    }

}
