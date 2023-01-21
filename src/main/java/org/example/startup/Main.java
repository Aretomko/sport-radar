package org.example.startup;

import org.example.repository.ScoreboardRepository;
import org.example.service.ConsoleOperationsService;
import org.example.service.ScoreboardService;

public class Main {
    public static void main(String[] args) {
        ScoreboardRepository scoreboardRepository = new ScoreboardRepository();
        ScoreboardService scoreboardService = new ScoreboardService(scoreboardRepository);
        ConsoleOperationsService consoleOperationsService = new ConsoleOperationsService(scoreboardService);

        consoleOperationsService.waitForInput();
    }

}
