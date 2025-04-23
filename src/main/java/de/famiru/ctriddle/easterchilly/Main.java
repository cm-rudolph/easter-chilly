package de.famiru.ctriddle.easterchilly;

import de.famiru.ctriddle.easterchilly.game.BoardFactory;
import de.famiru.ctriddle.easterchilly.graph.EdgeVisitingNodeFactory;
import de.famiru.ctriddle.easterchilly.graph.Graph;
import de.famiru.ctriddle.easterchilly.graph.GraphCreator;
import de.famiru.ctriddle.easterchilly.search.SearchEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private final BoardFactory boardFactory;
    private final GraphCreator graphCreator;
    private final SearchEngine searchEngine;

    public Main(BoardFactory boardFactory, GraphCreator graphCreator, SearchEngine searchEngine) {
        this.boardFactory = boardFactory;
        this.graphCreator = graphCreator;
        this.searchEngine = searchEngine;
    }

    public static void main(String[] args) {
        BoardFactory boardFactory = new BoardFactory();
        new Main(boardFactory, new GraphCreator(new EdgeVisitingNodeFactory()), new SearchEngine()).run();
    }

    private void run() {
        BoardFactory.BoardAndPlayer boardAndPlayer = boardFactory.loadLevel("level3.txt");
        Graph graph = graphCreator.create(boardAndPlayer.board(), boardAndPlayer.playerX(), boardAndPlayer.playerY());
        searchEngine.findLongestPath(graph, boardAndPlayer.playerX(), boardAndPlayer.playerY());
    }
}
