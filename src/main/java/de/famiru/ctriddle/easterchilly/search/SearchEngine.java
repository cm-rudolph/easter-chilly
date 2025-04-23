package de.famiru.ctriddle.easterchilly.search;

import de.famiru.ctriddle.easterchilly.graph.Graph;
import de.famiru.ctriddle.easterchilly.graph.GraphCoordinates;
import de.famiru.ctriddle.easterchilly.graph.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SearchEngine {
    private static final Logger LOGGER = LogManager.getLogger(SearchEngine.class);

    public String findLongestPath(Graph graph, int x, int y) {
        List<Character> path = new ArrayList<>();
        AtomicReference<String> result = new AtomicReference<>("");
        doFindLongestPath(graph, graph.getNodeAt(x, y), 0, 0, path, result);
        return result.get();
    }

    private int doFindLongestPath(Graph graph, Node node, int maxDepthSoFar, int currentDepth, List<Character> path,
                                  AtomicReference<String> result) {
        if (node == null) {
            return currentDepth;
        }
        if (node == graph.getExitNode()) {
            if (graph.allCoinsVisited()) {
                if (maxDepthSoFar < currentDepth) {
                    LOGGER.debug("New maximum depth: {}", currentDepth);
                    result.set(convertPath(path).substring(0, currentDepth));
                    LOGGER.debug("Path: {}", result.get());
                }
                return currentDepth;
            } else {
                return 0;
            }
        }
        if (currentDepth == path.size()) path.add(' ');

        if (node.canMoveUp()) {
            node.moveUp();
            GraphCoordinates coin = node.getCoinUp();
            boolean collectedCoin = collectCoin(graph, coin);
            path.set(currentDepth, 'U');
            int depth = doFindLongestPath(graph, node.getUp(), maxDepthSoFar, currentDepth + 1, path, result);
            if (collectedCoin) undoCollectCoin(graph, coin);
            node.undoMoveUp();
            if (depth > maxDepthSoFar) {
                maxDepthSoFar = depth;
            }
        }
        if (node.canMoveRight()) {
            node.moveRight();
            GraphCoordinates coin = node.getCoinRight();
            boolean collectedCoin = collectCoin(graph, coin);
            path.set(currentDepth, 'R');
            int depth = doFindLongestPath(graph, node.getRight(), maxDepthSoFar, currentDepth + 1, path, result);
            if (collectedCoin) undoCollectCoin(graph, coin);
            node.undoMoveRight();
            if (depth > maxDepthSoFar) {
                maxDepthSoFar = depth;
            }
        }
        if (node.canMoveDown()) {
            node.moveDown();
            GraphCoordinates coin = node.getCoinDown();
            boolean collectedCoin = collectCoin(graph, coin);
            path.set(currentDepth, 'D');
            int depth = doFindLongestPath(graph, node.getDown(), maxDepthSoFar, currentDepth + 1, path, result);
            if (collectedCoin) undoCollectCoin(graph, coin);
            node.undoMoveDown();
            if (depth > maxDepthSoFar) {
                maxDepthSoFar = depth;
            }
        }
        if (node.canMoveLeft()) {
            node.moveLeft();
            GraphCoordinates coin = node.getCoinLeft();
            boolean collectedCoin = collectCoin(graph, coin);
            path.set(currentDepth, 'L');
            int depth = doFindLongestPath(graph, node.getLeft(), maxDepthSoFar, currentDepth + 1, path, result);
            if (collectedCoin) undoCollectCoin(graph, coin);
            node.undoMoveLeft();
            if (depth > maxDepthSoFar) {
                maxDepthSoFar = depth;
            }
        }
        return maxDepthSoFar;
    }

    private String convertPath(List<Character> path) {
        StringBuilder builder = new StringBuilder(path.size());
        for (Character c : path) {
            builder.append(c);
        }
        return builder.toString();
    }

    private boolean collectCoin(Graph graph, GraphCoordinates coin) {
        if (coin != null) {
            return graph.markCoinVisited(coin);
        }
        return false;
    }

    private void undoCollectCoin(Graph graph, GraphCoordinates coin) {
        if (coin != null) {
            graph.unmarkCoinVisited(coin);
        }
    }
}
