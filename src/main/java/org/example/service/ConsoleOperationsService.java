package org.example.service;

import org.apache.commons.lang3.StringUtils;
import org.example.model.Team;

import java.util.Scanner;

public class ConsoleOperationsService {

    private static final String START_MATCH_COMMAND_NAME = "start-match";
    private static final String UPDATE_SCORE_COMMAND_NAME = "update-score";
    private static final String GET_SUMMARY_COMMAND_NAME = "summary";
    private static final String HELP_COMMAND_NAME = "help";
    private static final String REMOVE_PAIR_COMMAND_NAME = "remove-match";
    private static final String HOME_TEAM_NAME_ARGUMENT_NAME = "home-team-name=";
    private static final String HOME_TEAM_SCORE_ARGUMENT_NAME = "home-team-score=";
    private static final String AWAY_TEAM_NAME_ARGUMENT_NAME = "away-team-name=";
    private static final String AWAY_TEAM_SCORE_ARGUMENT_NAME = "away-team-score=";

    private final ScoreboardService scoreboardService;

    public ConsoleOperationsService(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }

    public void waitForInput() {
        try(Scanner scan=new Scanner(System.in)){
            processCommand(scan.nextLine());
            //recursion
            waitForInput();
        }
    }

    public void processCommand(String command) {
        command.trim();
        if (command.startsWith(START_MATCH_COMMAND_NAME)) {
            handleStartMatchCommand(command);
            return;
        }
        if (command.startsWith(UPDATE_SCORE_COMMAND_NAME)) {
            handleUpdateScoreCommand(command);
            return;
        }
        if (command.startsWith(GET_SUMMARY_COMMAND_NAME)) {
            handleGetSummaryCommand();
            return;
        }
        if (command.startsWith(HELP_COMMAND_NAME)) {
            printHelpInformation();
            return;
        }
        if (command.startsWith(REMOVE_PAIR_COMMAND_NAME)) {
            handleRemovePairCommand(command);
            return;
        }
        System.out.println("Command not found");
    }

    private void handleStartMatchCommand(String command) {
        String hostTeamName = getArgumentFromCommand(command, HOME_TEAM_NAME_ARGUMENT_NAME);
        String awayTeamName = getArgumentFromCommand(command, AWAY_TEAM_NAME_ARGUMENT_NAME);

        if (hostTeamName == null || awayTeamName == null) {
            System.out.println("Error: incorrect arguments specification");
            return;
        }

        Team homeTeam = new Team(hostTeamName);
        Team awayTeam = new Team(awayTeamName);

        scoreboardService.saveTeamPairToScoreBoard(homeTeam, awayTeam);
    }

    private void handleUpdateScoreCommand(String command) {
        int hostTeamScore = 0;
        int awayTeamScore = 0;
        try {
            hostTeamScore = Integer.parseInt(getArgumentFromCommand(command, HOME_TEAM_SCORE_ARGUMENT_NAME));
            awayTeamScore = Integer.parseInt(getArgumentFromCommand(command, AWAY_TEAM_SCORE_ARGUMENT_NAME));
        } catch (NumberFormatException e) {
            System.out.println("Error: team score must be integer");
            return;
        }
        String hostTeamName = getArgumentFromCommand(command, HOME_TEAM_NAME_ARGUMENT_NAME);

        if (hostTeamName == null) {
            System.out.println("Error: incorrect arguments specification");
            return;
        }

        Team homeTeam = new Team(hostTeamName);
        Team awayTeam = scoreboardService.getAwayTeamByHostTeam(homeTeam);
        awayTeam.setScore(awayTeamScore);
        homeTeam.setScore(hostTeamScore);

        scoreboardService.updateTeamPairInScoreBoard(homeTeam, awayTeam);
    }

    private void handleGetSummaryCommand() {
        System.out.println(scoreboardService.getFormattedScoreBoard());
    }

    private void handleRemovePairCommand(String command) {
        String hostTeamName = getArgumentFromCommand(command, HOME_TEAM_NAME_ARGUMENT_NAME);

        if (hostTeamName == null) {
            System.out.println("Error: incorrect arguments specification");
            return;
        }

        Team teamToRemove = new Team(hostTeamName);

        scoreboardService.removeFromScoreboard(teamToRemove);
    }

    private String getArgumentFromCommand(String command, String argumentName) {
        command = command + " ";
        return StringUtils.substringBetween(command, argumentName, " ");
    }

    private void printHelpInformation() {
        System.out.println("help info \n" +
                START_MATCH_COMMAND_NAME  + " --home-team-name=ExampleTeam --away-team-name=ExampleTeam #start new match with specified teams names, sore will be 0 for both teams \n" +
                UPDATE_SCORE_COMMAND_NAME + " --home-team-name=ExampleTeam --home-team-score=0 --away-team-score=0 #update score for team pair you should identify host team name and score for both teams\n" +
                REMOVE_PAIR_COMMAND_NAME + " --home-team-name=ExampleTeam #will remove team pair from scoreboard, you should specify host team name pf the team pair\n" +
                GET_SUMMARY_COMMAND_NAME + " #this command will pretty-print scoreboard\n" +
                HELP_COMMAND_NAME + " # this command will print information about commands available\n");
    }

}
