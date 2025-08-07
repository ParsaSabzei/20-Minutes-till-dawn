package ap.project.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Gem {
    private int xp = 3;
    private Vector2 position;
    private Rectangle rectangle;
    private transient float scaleFactor = 3;

    public Gem(Vector2 position) {
        this.position = position;
        this.rectangle = new Rectangle(position.x, position.y,
            GameAssetManager.getInstance().gem.getRegionWidth() * scaleFactor,
            GameAssetManager.getInstance().gem.getRegionHeight() * scaleFactor);
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2 getPosition() {
        return position;
    }
}
