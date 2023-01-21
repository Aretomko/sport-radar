package org.example.repository;

import org.example.model.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;

public class ScoreboardRepository {

    LinkedHashMap<Team, Team> scoreboard = new LinkedHashMap<>();

    public void saveTeamPair(Team hostTeam, Team awayTeam) {
        if (scoreboard.containsKey(hostTeam) || scoreboard.containsValue(awayTeam)) {
            System.out.println("Error: team name already exist on scoreboard");
        }
        scoreboard.put(hostTeam, awayTeam);
    }

    public void updateTeamPairScore(Team hostTeam, Team awayTeam) {
        ListIterator<Map.Entry<Team, Team>> iterator = new ArrayList<>(scoreboard.entrySet()).listIterator(scoreboard.size());
        while (iterator.hasPrevious()) {
            Map.Entry<Team, Team> teamPair = iterator.previous();
            if (teamPair.getKey().equals(hostTeam)) {
                teamPair.getKey().setScore(hostTeam.getScore());
                teamPair.getValue().setScore(awayTeam.getScore());
            }
        }
    }

    public LinkedHashMap<Team, Team> getScoreboard() {
        return scoreboard;
    }

    public void removeFromScoreboard(Team hostTeam) {
        scoreboard.remove(hostTeam);
    }

    public Team getByHostTeam(Team team) {
        return scoreboard.get(team);
    }

}
