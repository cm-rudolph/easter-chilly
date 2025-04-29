package de.famiru.ctriddle.easterchilly.graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    private final Map<GraphCoordinates, Node> nodes = new HashMap<>();
    private final Map<GraphCoordinates, Boolean> collectedCoins = new HashMap<>();
    private final GraphCoordinates exitNode;

    Graph(int exitX, int exitY) {
        exitNode = new GraphCoordinates(exitX, exitY);
    }

    public Node getNodeAt(int x, int y) {
        return nodes.get(new GraphCoordinates(x, y));
    }

    public Node getExitNode() {
        return getNodeAt(exitNode.x(), exitNode.y());
    }

    /**
     * @return true, if the node is the first one at its coordinates
     */
    boolean insertNode(Node node) {
        GraphCoordinates coordinates = new GraphCoordinates(node.getX(), node.getY());
        addCoinPosition(node.getCoinUp());
        addCoinPosition(node.getCoinRight());
        addCoinPosition(node.getCoinDown());
        addCoinPosition(node.getCoinLeft());
        Node previousNode = nodes.put(coordinates, node);

        return previousNode == null;
    }

    private void addCoinPosition(GraphCoordinates coordinates) {
        if (coordinates != null) {
            collectedCoins.put(coordinates, false);
        }
    }

    public boolean markCoinVisited(GraphCoordinates coordinates) {
        return !collectedCoins.put(coordinates, true);
    }

    public void unmarkCoinVisited(GraphCoordinates coordinates) {
        collectedCoins.put(coordinates, false);
    }

    public boolean allCoinsVisited() {
        return collectedCoins.values()
                .stream()
                .allMatch(Boolean::booleanValue);
    }

    public Collection<GraphCoordinates> findUnvisitedCoins() {
        return collectedCoins.entrySet().stream()
                .filter(e -> !e.getValue())
                .map(Map.Entry::getKey)
                .toList();
    }
}
