package de.famiru.ctriddle.easterchilly.search;

import de.famiru.ctriddle.easterchilly.graph.Graph;
import de.famiru.ctriddle.easterchilly.graph.GraphCoordinates;
import de.famiru.ctriddle.easterchilly.graph.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SearchEngine {
    private static final Logger LOGGER = LogManager.getLogger(SearchEngine.class);

    private final Graph graph;
    private final PuzzleUnsolvableChecker puzzleUnsolvableChecker;
    private String result = "";
    private int maxDepthSoFar = 0;
    private long steps = 0;
    private boolean currentlyUnsolvable = false;

    public SearchEngine(Graph graph) {
        this.graph = graph;
        this.puzzleUnsolvableChecker = new PuzzleUnsolvableChecker();
    }

    public String findLongestPath(int x, int y) {
        List<Character> path = new ArrayList<>();
        doFindLongestPath(graph.getNodeAt(x, y), 0, path);
        LOGGER.info("Internal steps: {}", steps);
        return result;
    }

    private int doFindLongestPath(Node node, int currentDepth, List<Character> path) {
        if (node == null) {
            return currentDepth;
        }
        if (node == graph.getExitNode()) {
            if (graph.allCoinsVisited()) {
                if (maxDepthSoFar < currentDepth) {
                    LOGGER.debug("New maximum depth: {}, {} steps so far", currentDepth, steps);
                    result = convertPath(path).substring(0, currentDepth);
                    LOGGER.debug("Path: {}", result);
                }
                return currentDepth;
            } else {
                return 0;
            }
        }
        if (currentDepth == path.size()) path.add(' ');

        steps++;
        if ((steps & 0x8FFL) == 0) {
            currentlyUnsolvable = puzzleUnsolvableChecker.isPuzzleUnsolvable(graph, node);
        }
        if (currentlyUnsolvable) {
            return maxDepthSoFar;
        }

        if (node.canMoveUp()) {
            node.moveUp();
            GraphCoordinates coin = node.getCoinUp();
            boolean collectedCoin = collectCoin(graph, coin);
            path.set(currentDepth, 'U');
            int depth = doFindLongestPath(node.getUp(), currentDepth + 1, path);
            if (collectedCoin) undoCollectCoin(graph, coin);
            node.undoMoveUp();
            if (depth > maxDepthSoFar) {
                maxDepthSoFar = depth;
            }
            if (currentlyUnsolvable) {
                currentlyUnsolvable = puzzleUnsolvableChecker.isPuzzleUnsolvable(graph, node);
                if (currentlyUnsolvable) {
                    return maxDepthSoFar;
                }
            }
        }
        if (node.canMoveRight()) {
            node.moveRight();
            GraphCoordinates coin = node.getCoinRight();
            boolean collectedCoin = collectCoin(graph, coin);
            path.set(currentDepth, 'R');
            int depth = doFindLongestPath(node.getRight(), currentDepth + 1, path);
            if (collectedCoin) undoCollectCoin(graph, coin);
            node.undoMoveRight();
            if (depth > maxDepthSoFar) {
                maxDepthSoFar = depth;
            }
            if (currentlyUnsolvable) {
                currentlyUnsolvable = puzzleUnsolvableChecker.isPuzzleUnsolvable(graph, node);
                if (currentlyUnsolvable) {
                    return maxDepthSoFar;
                }
            }
        }
        if (node.canMoveDown()) {
            node.moveDown();
            GraphCoordinates coin = node.getCoinDown();
            boolean collectedCoin = collectCoin(graph, coin);
            path.set(currentDepth, 'D');
            int depth = doFindLongestPath(node.getDown(), currentDepth + 1, path);
            if (collectedCoin) undoCollectCoin(graph, coin);
            node.undoMoveDown();
            if (depth > maxDepthSoFar) {
                maxDepthSoFar = depth;
            }
            if (currentlyUnsolvable) {
                currentlyUnsolvable = puzzleUnsolvableChecker.isPuzzleUnsolvable(graph, node);
                if (currentlyUnsolvable) {
                    return maxDepthSoFar;
                }
            }
        }
        if (node.canMoveLeft()) {
            node.moveLeft();
            GraphCoordinates coin = node.getCoinLeft();
            boolean collectedCoin = collectCoin(graph, coin);
            path.set(currentDepth, 'L');
            int depth = doFindLongestPath(node.getLeft(), currentDepth + 1, path);
            if (collectedCoin) undoCollectCoin(graph, coin);
            node.undoMoveLeft();
            if (depth > maxDepthSoFar) {
                maxDepthSoFar = depth;
            }
            if (currentlyUnsolvable) {
                currentlyUnsolvable = puzzleUnsolvableChecker.isPuzzleUnsolvable(graph, node);
                if (currentlyUnsolvable) {
                    return maxDepthSoFar;
                }
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
