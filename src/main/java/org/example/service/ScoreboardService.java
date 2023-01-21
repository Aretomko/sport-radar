package org.example.service;

import org.example.model.Team;
import org.example.repository.ScoreboardRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

public class ScoreboardService {

    private static final String TEAM_PAIR_SCORE_FORMAT = "%s %s - %s %s \n";

    private final ScoreboardRepository scoreboardRepository;

    public ScoreboardService(ScoreboardRepository scoreboardRepository) {
        this.scoreboardRepository = scoreboardRepository;
    }

    public void saveTeamPairToScoreBoard(Team hostTeam, Team awayTeam) {
        scoreboardRepository.saveTeamPair(hostTeam, awayTeam);
    }

    public String getFormattedScoreBoard() {
        return formatScoreboard(scoreboardRepository.getScoreboard());
    }

    public void removeFromScoreboard(Team hostTeam) {
        scoreboardRepository.removeFromScoreboard(hostTeam);
    }

    public Team getAwayTeamByHostTeam(Team hostTeam) {
        return scoreboardRepository.getByHostTeam(hostTeam);
    }

    public void updateTeamPairInScoreBoard(Team hostTeam, Team awayTeam) {
        scoreboardRepository.updateTeamPairScore(hostTeam, awayTeam);
    }

    private String formatScoreboard(LinkedHashMap<Team, Team> scoreboard) {
        StringBuilder scoreboardStringBuilder = new StringBuilder();
        ListIterator<Map.Entry<Team, Team>> iterator = new ArrayList<>(scoreboard.entrySet()).listIterator(scoreboard.size());
        while (iterator.hasPrevious()) {
             scoreboardStringBuilder.append(formatTeamPair(iterator.previous()));
        }
        return scoreboardStringBuilder.toString();
    }

    private String formatTeamPair(Map.Entry<Team, Team> entry) {
        Team hostTeam = entry.getKey();
        Team awayTeam = entry.getValue();
        return TEAM_PAIR_SCORE_FORMAT.formatted(hostTeam.getName(), hostTeam.getScore(), awayTeam.getName(), awayTeam.getScore());
    }

}
