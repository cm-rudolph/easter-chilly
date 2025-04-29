package de.famiru.ctriddle.easterchilly.search;

import de.famiru.ctriddle.easterchilly.graph.Graph;
import de.famiru.ctriddle.easterchilly.graph.GraphCoordinates;
import de.famiru.ctriddle.easterchilly.graph.Node;

import java.util.*;

class PuzzleUnsolvableChecker {
    public boolean isPuzzleUnsolvable(Graph graph, Node startNode) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(startNode);
        boolean exitFound = false;
        Set<GraphCoordinates> missingCoins = new HashSet<>(graph.findUnvisitedCoins());
        Deque<Node> moveUpNodes = new LinkedList<>();
        Deque<Node> moveRightNodes = new LinkedList<>();
        Deque<Node> moveDownNodes = new LinkedList<>();
        Deque<Node> moveLeftNodes = new LinkedList<>();
        Node exitNode = graph.getExitNode();

        while (!queue.isEmpty()) {
            Node node = queue.remove();
            if (exitNode == node) {
                exitFound = true;
            }

            // Remove coins even if they might not be reachable.
            // Non-reachability might be caused by doing a breadth-first search here when using the NodeVisitingNodes.
            removeIfNonNull(node.getCoinUp(), missingCoins);
            removeIfNonNull(node.getCoinRight(), missingCoins);
            removeIfNonNull(node.getCoinDown(), missingCoins);
            removeIfNonNull(node.getCoinLeft(), missingCoins);

            if (node.canMoveUp()) {
                node.moveUp();
                moveUpNodes.addFirst(node);
                if (!queue.contains(node.getUp())) {
                    queue.add(node.getUp());
                }
            }
            if (node.canMoveRight()) {
                node.moveRight();
                moveRightNodes.addFirst(node);
                if (!queue.contains(node.getRight())) {
                    queue.add(node.getRight());
                }
            }
            if (node.canMoveDown()) {
                node.moveDown();
                moveDownNodes.addFirst(node);
                if (!queue.contains(node.getDown())) {
                    queue.add(node.getDown());
                }
            }
            if (node.canMoveLeft()) {
                node.moveLeft();
                moveLeftNodes.addFirst(node);
                if (!queue.contains(node.getLeft())) {
                    queue.add(node.getLeft());
                }
            }
        }

        moveUpNodes.forEach(Node::undoMoveUp);
        moveRightNodes.forEach(Node::undoMoveRight);
        moveDownNodes.forEach(Node::undoMoveDown);
        moveLeftNodes.forEach(Node::undoMoveLeft);

        return !exitFound || !missingCoins.isEmpty();
    }

    private void removeIfNonNull(GraphCoordinates coin, Set<GraphCoordinates> missingCoins) {
        if (coin != null) missingCoins.remove(coin);
    }
}
