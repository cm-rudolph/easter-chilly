package de.famiru.ctriddle.easterchilly;

import de.famiru.ctriddle.easterchilly.game.BoardFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private final BoardFactory boardFactory;

    public Main(BoardFactory boardFactory) {
        this.boardFactory = boardFactory;
    }

    public static void main(String[] args) {
        BoardFactory boardFactory = new BoardFactory();
        new Main(boardFactory).run();
    }

    private void run() {
        BoardFactory.BoardAndPlayer boardAndPlayer = boardFactory.loadLevel("level1.txt");

    }
}
