package de.famiru.ctriddle.easterchilly.graph;

class EdgeVisitingNode extends Node {
    private boolean upVisited;
    private boolean rightVisited;
    private boolean downVisited;
    private boolean leftVisited;

    EdgeVisitingNode(int x, int y) {
        super(x, y);
    }

    @Override
    public boolean canMoveUp() {
        return up != null && !upVisited;
    }

    @Override
    public boolean canMoveRight() {
        return right != null && !rightVisited;
    }

    @Override
    public boolean canMoveDown() {
        return down != null && !downVisited;
    }

    @Override
    public boolean canMoveLeft() {
        return left != null && !leftVisited;
    }

    @Override
    public void moveUp() {
        upVisited = true;
    }

    @Override
    public void moveRight() {
        rightVisited = true;
    }

    @Override
    public void moveDown() {
        downVisited = true;
    }

    @Override
    public void moveLeft() {
        leftVisited = true;
    }

    @Override
    public void undoMoveUp() {
        upVisited = false;
    }

    @Override
    public void undoMoveRight() {
        rightVisited = false;
    }

    @Override
    public void undoMoveDown() {
        downVisited = false;
    }

    @Override
    public void undoMoveLeft() {
        leftVisited = false;
    }

    @Override
    protected void doReset() {
        upVisited = false;
        rightVisited = false;
        downVisited = false;
        leftVisited = false;
    }
}
