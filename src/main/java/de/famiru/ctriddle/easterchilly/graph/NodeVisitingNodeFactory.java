package de.famiru.ctriddle.easterchilly.graph;

public class NodeVisitingNodeFactory implements NodeFactory {
    @Override
    public Node createNode(int x, int y) {
        return new NodeVisitingNode(x, y);
    }
}
