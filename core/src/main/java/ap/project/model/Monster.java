package ap.project.model;

import ap.project.model.enums.MonsterTypes;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Monster {
    private Vector2 position;
    private MonsterTypes type;
    private int HP;
    private Rectangle rectangle;

    // for eyebat
    private float timeElapsedFromLastShot = 0;
    private float shotTime = 3;
    private ArrayList<Bullet> projectiles = new ArrayList<>();

    // for elder
    private boolean isWalking = true;
    private boolean isRaging = false;
    private boolean isAttacking = false;
    private transient float moveSpeedWhileAttacking = 1500;
    private float timer = 0;
    private Vector2 direction;

    private ArrayList<Force> forces = new ArrayList<>();

    public Monster(MonsterTypes type, Vector2 position) {
        this.type = type;
        this.position = position;
        HP = type.getHP();
    }

    public MonsterTypes getType() {
        return type;
    }

    public void setType(MonsterTypes type) {
        this.type = type;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setProjectiles(ArrayList<Bullet> projectiles) {
        this.projectiles = projectiles;
    }

    public void setShotTime(float shotTime) {
        this.shotTime = shotTime;
    }

    public boolean damage(int damage) {
        HP -= damage;
        if(HP <= 0) {
            Game.getInstance().getMonsters().remove(this);
            return true;
        }
        return false;
    }

    public static Rectangle createRectangle(float x, float y, float width, float height) {
        return new Rectangle(x, y, width - width / 2.5f, height - height / 2.5f);
    }

    public ArrayList<Bullet> getProjectiles() {
        return projectiles;
    }
    public void addBullet(Bullet bullet) {
        bullet.setSpeed(500);
        bullet.setRectangle(createRectangle(bullet.getPosition().x, bullet.getPosition().y,
            GameAssetManager.getInstance().eyeMonsterProjectile.getRegionWidth()*3,
            GameAssetManager.getInstance().eyeMonsterProjectile.getRegionHeight()*3));
        projectiles.add(bullet);
    }
    public float getTimeElapsedFromLastShot() {
        return timeElapsedFromLastShot;
    }

    public void setTimeElapsedFromLastShot(float timeElapsedFromLastShot) {
        this.timeElapsedFromLastShot = timeElapsedFromLastShot;
    }

    public float getShotTime() {
        return shotTime;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public boolean isRaging() {
        return isRaging;
    }

    public void setRaging(boolean raging) {
        isRaging = raging;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public float getTimer() {
        return timer;
    }

    public void setTimer(float ragingTime) {
        this.timer = ragingTime;
    }

    public float getMoveSpeedWhileAttacking() {
        return moveSpeedWhileAttacking;
    }

    public float getSpeed() {
        return type.getSpeed();
    }

    public Vector2 getDirection() {
        return direction;
    }

    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public ArrayList<Force> getForces() {
        return forces;
    }

    public void setForces(ArrayList<Force> forces) {
        this.forces = forces;
    }
}
