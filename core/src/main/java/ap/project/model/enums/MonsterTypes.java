package ap.project.model.enums;

import ap.project.model.GameAssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum MonsterTypes {
    BrainMonster(25, 150, GameAssetManager.getInstance().brainMonsterAnimation),
    Eyebat(50, 80, GameAssetManager.getInstance().eyeMonsterAnimation),
    Elder(600, 500, GameAssetManager.getInstance().elderWalkAnimation);

    private int HP;
    private int speed;
    private Animation<TextureRegion> monsterAnimation;

    MonsterTypes(int HP, int speed, Animation<TextureRegion> monsterAnimation) {
        this.speed = speed;
        this.HP = HP;
        this.monsterAnimation = monsterAnimation;
    }

    public int getHP() {
        return HP;
    }

    public int getSpeed() {
        return speed;
    }

    public Animation<TextureRegion> getMonsterAnimation() {
        return monsterAnimation;
    }
}
