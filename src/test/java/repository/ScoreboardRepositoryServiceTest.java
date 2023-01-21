package repository;

import org.example.model.Team;
import org.example.repository.ScoreboardRepository;
import org.junit.jupiter.api.Test;

import javax.sound.midi.ShortMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ScoreboardRepositoryServiceTest {

    @Test
    public void saveTeamPair_successfullySave_Test() {
        final String exampleHostTeamName = "HostTeam";
        final String exampleAwayTeamName = "HostTeam";
        final Team hostTeam = new Team(exampleHostTeamName);
        final Team awayTeam = new Team(exampleAwayTeamName);

        ScoreboardRepository scoreboardRepository = new ScoreboardRepository();
        scoreboardRepository.saveTeamPair(hostTeam, awayTeam);

        assertEquals(exampleAwayTeamName, scoreboardRepository.getScoreboard().get(hostTeam).getName());
        assertEquals(1, scoreboardRepository.getScoreboard().size());
    }

    @Test
    public void saveTeamPair_teamAlreadyExistTeamPairNotSaved_Test() {
        final String exampleHostTeamName = "HostTeam";
        final String exampleAwayTeamName = "HostTeam";
        final Team hostTeam = new Team(exampleHostTeamName);
        final Team awayTeam = new Team(exampleAwayTeamName);

        ScoreboardRepository scoreboardRepository = new ScoreboardRepository();
        scoreboardRepository.saveTeamPair(hostTeam, awayTeam);
        scoreboardRepository.saveTeamPair(hostTeam, awayTeam);

        assertEquals(exampleAwayTeamName, scoreboardRepository.getScoreboard().get(hostTeam).getName());
        assertEquals(1, scoreboardRepository.getScoreboard().size());
    }

    @Test
    public void updateTeamPairScore_teamPairScoreSuccessfullyUpdated_Test() {
        final String exampleHostTeamName = "HostTeam";
        final String exampleAwayTeamName = "HostTeam";
        final int exampleHostTeamUpdateScore = 1;
        final int exampleAwayTeamUpdateScore = 3;
        final Team exampleHostTeam = new Team(exampleHostTeamName);
        final Team exampleHostTeamWithUpdatedScore = new Team(exampleAwayTeamName, exampleHostTeamUpdateScore);
        final Team exampleAwayTeam = new Team(exampleAwayTeamName);
        final Team exampleAwayTeamWithUpdatedScore = new Team(exampleAwayTeamName, exampleAwayTeamUpdateScore);

        ScoreboardRepository scoreboardRepository = new ScoreboardRepository();
        scoreboardRepository.saveTeamPair(exampleHostTeam, exampleAwayTeam);

        scoreboardRepository.updateTeamPairScore(exampleHostTeamWithUpdatedScore, exampleAwayTeamWithUpdatedScore);

        assertEquals(exampleAwayTeamName, scoreboardRepository.getScoreboard().get(exampleHostTeam).getName());
        assertEquals(1, scoreboardRepository.getScoreboard().size());

        assertEquals(exampleAwayTeamUpdateScore, scoreboardRepository.getScoreboard().get(exampleHostTeam).getScore());
    }

    @Test
    public void getScoreboard_scoreboardSuccessfullyReturned_Test() {
        final String exampleHostTeamName = "HostTeam";
        final String exampleAwayTeamName = "HostTeam";
        final Team exampleHostTeam = new Team(exampleHostTeamName);
        final Team exampleAwayTeam = new Team(exampleAwayTeamName);

        ScoreboardRepository scoreboardRepository = new ScoreboardRepository();
        scoreboardRepository.saveTeamPair(exampleHostTeam, exampleAwayTeam);

        assertEquals(exampleAwayTeam, scoreboardRepository.getScoreboard().get(exampleHostTeam));
        assertTrue(scoreboardRepository.getScoreboard().keySet().contains(exampleHostTeam));
    }

    @Test
    public void removeFromScoreboard_successfullyRemovedFromScoreboard_Test() {
        final String exampleHostTeamName = "HostTeam";
        final String exampleAwayTeamName = "HostTeam";
        final Team exampleHostTeam = new Team(exampleHostTeamName);
        final Team exampleAwayTeam = new Team(exampleAwayTeamName);

        ScoreboardRepository scoreboardRepository = new ScoreboardRepository();
        scoreboardRepository.saveTeamPair(exampleHostTeam, exampleAwayTeam);

        scoreboardRepository.removeFromScoreboard(exampleHostTeam);

        assertTrue(scoreboardRepository.getScoreboard().keySet().isEmpty());
    }

    @Test
    public void getByHostTeam_successfullyGetAwayTeamByHostTeam_Test() {
        final String exampleHostTeamName = "HostTeam";
        final String exampleAwayTeamName = "HostTeam";
        final Team exampleHostTeam = new Team(exampleHostTeamName);
        final Team exampleAwayTeam = new Team(exampleAwayTeamName);

        ScoreboardRepository scoreboardRepository = new ScoreboardRepository();
        scoreboardRepository.saveTeamPair(exampleHostTeam, exampleAwayTeam);

        assertEquals(exampleAwayTeam, scoreboardRepository.getByHostTeam(exampleHostTeam));
    }

}
