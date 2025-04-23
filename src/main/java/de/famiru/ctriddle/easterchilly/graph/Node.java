package de.famiru.ctriddle.easterchilly.graph;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter(AccessLevel.MODULE)
public abstract class Node {
    private final int x;
    private final int y;
    protected Node up;
    private GraphCoordinates coinUp;
    protected Node right;
    private GraphCoordinates coinRight;
    protected Node down;
    private GraphCoordinates coinDown;
    protected Node left;
    private GraphCoordinates coinLeft;

    protected Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract boolean canMoveUp();

    public abstract boolean canMoveRight();

    public abstract boolean canMoveDown();

    public abstract boolean canMoveLeft();

    public abstract void moveUp();

    public abstract void moveRight();

    public abstract void moveDown();

    public abstract void moveLeft();

    public abstract void undoMoveUp();

    public abstract void undoMoveRight();

    public abstract void undoMoveDown();

    public abstract void undoMoveLeft();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
