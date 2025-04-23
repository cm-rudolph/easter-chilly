package de.famiru.ctriddle.easterchilly.graph;

import de.famiru.ctriddle.easterchilly.game.Board;
import de.famiru.ctriddle.easterchilly.game.Coordinates;

public class GraphCreator {
    private final NodeFactory nodeFactory;

    public GraphCreator(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public Graph create(Board board, int playerX, int playerY) {
        Coordinates exit = board.getExit();
        Graph graph = new Graph(exit.x(), exit.y());
        graph.insertNode(nodeFactory.createNode(playerX, playerY));

        insertNode(board, graph, playerX, playerY);

        return graph;
    }

    private void insertNode(Board board, Graph graph, int x, int y) {
        if (board.isExit(x, y)) {
            return;
        }

        Node currentNode = graph.getNodeAt(x, y);

        if (board.canMoveRight(x, y)) {
            Coordinates coordinates = board.moveRight(x, y);
            Node node = createOrGet(board, graph, coordinates);
            board.coinAtMoveRight(x, y)
                    .ifPresent(c -> currentNode.setCoinRight(mapCoordinates(c)));
            currentNode.setRight(node);
            if (graph.insertNode(node)) {
                insertNode(board, graph, coordinates.x(), coordinates.y());
            }
        }

        if (board.canMoveLeft(x, y)) {
            Coordinates coordinates = board.moveLeft(x, y);
            Node node = createOrGet(board, graph, coordinates);
            board.coinAtMoveLeft(x, y)
                    .ifPresent(c -> currentNode.setCoinLeft(mapCoordinates(c)));
            currentNode.setLeft(node);
            if (graph.insertNode(node)) {
                insertNode(board, graph, coordinates.x(), coordinates.y());
            }
        }

        if (board.canMoveUp(x, y)) {
            Coordinates coordinates = board.moveUp(x, y);
            Node node = createOrGet(board, graph, coordinates);
            board.coinAtMoveUp(x, y)
                    .ifPresent(c -> currentNode.setCoinUp(mapCoordinates(c)));
            currentNode.setUp(node);
            if (graph.insertNode(node)) {
                insertNode(board, graph, coordinates.x(), coordinates.y());
            }
        }

        if (board.canMoveDown(x, y)) {
            Coordinates coordinates = board.moveDown(x, y);
            Node node = createOrGet(board, graph, coordinates);
            board.coinAtMoveDown(x, y)
                    .ifPresent(c -> currentNode.setCoinDown(mapCoordinates(c)));
            currentNode.setDown(node);
            if (graph.insertNode(node)) {
                insertNode(board, graph, coordinates.x(), coordinates.y());
            }
        }
    }

    private GraphCoordinates mapCoordinates(Coordinates coin) {
        return new GraphCoordinates(coin.x(), coin.y());
    }

    private Node createOrGet(Board board, Graph graph, Coordinates target) {
        Node node = graph.getNodeAt(target.x(), target.y());
        if (node == null) {
            if (board.isWormhole(target.x(), target.y())) {
                return nodeFactory.createWormholeNode(target.x(), target.y());
            } else {
                return nodeFactory.createNode(target.x(), target.y());
            }
        }
        return node;
    }
}
