package de.famiru.ctriddle.easterchilly.graph;

public class EdgeVisitingNodeFactory implements NodeFactory {
    @Override
    public Node createNode(int x, int y) {
        return new EdgeVisitingNode(x, y);
    }
}
