package de.famiru.ctriddle.easterchilly;

import de.famiru.ctriddle.easterchilly.game.BoardFactory;
import de.famiru.ctriddle.easterchilly.graph.EdgeVisitingNodeFactory;
import de.famiru.ctriddle.easterchilly.graph.Graph;
import de.famiru.ctriddle.easterchilly.graph.GraphCreator;
import de.famiru.ctriddle.easterchilly.search.SearchEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.Instant;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private final BoardFactory boardFactory;
    private final GraphCreator graphCreator;

    public Main(BoardFactory boardFactory, GraphCreator graphCreator) {
        this.boardFactory = boardFactory;
        this.graphCreator = graphCreator;
    }

    public static void main(String[] args) {
        BoardFactory boardFactory = new BoardFactory();
        new Main(boardFactory, new GraphCreator(new EdgeVisitingNodeFactory())).run();
    }

    private void run() {
        BoardFactory.BoardAndPlayer boardAndPlayer = boardFactory.loadLevel("level3.txt");
        Graph graph = graphCreator.create(boardAndPlayer.board(), boardAndPlayer.playerX(), boardAndPlayer.playerY());
        Instant start = Instant.now();
        String solution = new SearchEngine(graph).findLongestPath(boardAndPlayer.playerX(), boardAndPlayer.playerY());
        LOGGER.info("Solving took {} ms", Duration.between(start, Instant.now()).toMillis());
        LOGGER.info("Solution: {} ({} steps)", solution, solution.length());
    }
}
