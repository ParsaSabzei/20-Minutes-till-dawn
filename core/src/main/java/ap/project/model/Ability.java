package ap.project.model;

import ap.project.model.enums.Abilities;

public class Ability {
    private Abilities type;
    private float remainingTime;

    public Ability(float remainingTime, Abilities type) {
        this.remainingTime = remainingTime;
        this.type = type;
    }

    public Abilities getType() {
        return type;
    }

    public void setType(Abilities type) {
        this.type = type;
    }

    public float getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(float remainingTime) {
        this.remainingTime = remainingTime;
    }
}
