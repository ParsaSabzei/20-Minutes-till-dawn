package ap.project.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet {
    private Vector2 position;
    private Vector2 direction;
    private Rectangle rectangle;
    private int speed = 2000;

    public Bullet(Vector2 position, Vector2 direction) {
        this.position = position;
        this.direction = direction;
    }

    public static Rectangle createRectangle(float x, float y, float bulletWidth, float bulletHeight) {
        return new Rectangle(x, y, bulletWidth - bulletWidth / 3, bulletHeight - bulletHeight / 3);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
