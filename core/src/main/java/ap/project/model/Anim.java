package ap.project.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Anim {
    private Animation<TextureRegion> animation;
    private float stateTime = 0f;
    private Vector2 position;
    private boolean finished = false;
    private float scaleFactor = 1.7f;
    public Anim(Vector2 position, Animation<TextureRegion> animation) {
        this.animation = animation;
        this.position = position;
    }

    public Anim(Vector2 position, Animation<TextureRegion> animation, float scaleFactor) {
        this.animation = animation;
        this.position = position;
        this.scaleFactor = scaleFactor;
    }
    public TextureRegion update(float delta) {
        stateTime += delta;
        TextureRegion region = animation.getKeyFrame(stateTime, false);
        if(animation.isAnimationFinished(stateTime)) {
            finished = true;
        }
        return region;
    }

    public boolean isFinished() {
        return finished;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }
}
