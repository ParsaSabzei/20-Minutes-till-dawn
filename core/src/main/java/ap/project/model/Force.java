package ap.project.model;

import com.badlogic.gdx.math.Vector2;

public class Force {
    private Vector2 direction;
    private float power;
    private float timeRemaining;

    public Force(float power, Vector2 direction, float timeRemaining) {
        this.power = power;
        this.direction = direction;
        this.timeRemaining = timeRemaining;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(float timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
