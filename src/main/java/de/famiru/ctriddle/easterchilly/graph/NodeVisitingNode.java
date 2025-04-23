package de.famiru.ctriddle.easterchilly.graph;

class NodeVisitingNode extends Node {
    private boolean visited;

    NodeVisitingNode(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveUp() {
        return isUnvisited(up);
    }

    @Override
    public boolean canMoveRight() {
        return isUnvisited(right);
    }

    @Override
    public boolean canMoveDown() {
        return isUnvisited(down);
    }

    @Override
    public boolean canMoveLeft() {
        return isUnvisited(left);
    }

    @Override
    public void moveUp() {
        visit(up);
    }

    @Override
    public void moveRight() {
        visit(right);
    }

    @Override
    public void moveDown() {
        visit(down);
    }

    @Override
    public void moveLeft() {
        visit(left);
    }

    @Override
    public void undoMoveUp() {
        unvisit(up);
    }

    @Override
    public void undoMoveRight() {
        unvisit(right);
    }

    @Override
    public void undoMoveDown() {
        unvisit(down);
    }

    @Override
    public void undoMoveLeft() {
        unvisit(left);
    }

    private boolean isUnvisited(Node node) {
        if (node instanceof NodeVisitingNode castedNode) {
            return !castedNode.visited;
        }
        return false;
    }

    private void visit(Node node) {
        if (node instanceof NodeVisitingNode castedNode) {
            castedNode.visited = true;
        }
    }

    private void unvisit(Node node) {
        if (node instanceof NodeVisitingNode castedNode) {
            castedNode.visited = false;
        }
    }
}
