package ap.project.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class Tree {
    private Vector2 position;
    private Rectangle rectangle;
    public Tree(Vector2 position, Rectangle rectangle) {
        this.position = position;
        this.rectangle = rectangle;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
