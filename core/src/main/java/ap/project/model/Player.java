package ap.project.model;

import ap.project.model.enums.Abilities;
import ap.project.model.enums.Characters;
import ap.project.model.enums.Guns;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player {
    private User user;
    private Characters character;
    private Vector2 position = new Vector2((float) Gdx.graphics.getWidth() / 2, (float) Gdx.graphics.getHeight() / 2);
    private boolean faceRight = true;
    private boolean isMoving = false;
    private Gun gun;
    private Rectangle collider = new Rectangle();
    private int HP;
    private int maxHP;
    private float invisibleTime = 0;
    private int currentLevel = 1;
    private int xpGained;
    private ArrayList<Ability> abilities = new ArrayList<>();
    private ArrayList<Ability> totalAbilities = new ArrayList<>();

    public void setAbilities(ArrayList<Ability> abilities) {
        this.abilities = abilities;
    }

    public void setTotalAbilities(ArrayList<Ability> totalAbilities) {
        this.totalAbilities = totalAbilities;
    }

    public ArrayList<Ability> getTotalAbilities() {
        return totalAbilities;
    }

    public Characters getCharacter() {
        return character;
    }

    public void setCharacter(Characters character) {
        this.character = character;
        this.HP = character.getHP();
        this.maxHP = HP;
    }

    public Gun getGun() {
        return gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }


    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public boolean isFaceRight() {
        return faceRight;
    }

    public void setFaceRight(boolean faceRight) {
        this.faceRight = faceRight;
    }

    public Vector2 move(Vector2 sp, float delta) {
        float speed = (float) (Math.pow(character.getSpeed(), 0.4f) * 200.0f);
        if(isSpeedy()) speed *= 1.5f;
        sp.scl(speed * delta);
        return sp;
    }


    public TextureRegion getIdleFrame(float frame) {
        int id = Characters.getIndex(character);
        return GameAssetManager.getInstance().playersIdleAnimation.get(id).getKeyFrame(frame, true);
    }
    public TextureRegion getWalkFrame(float frame) {
        int id = Characters.getIndex(character);
        return GameAssetManager.getInstance().playersWalkAnimation.get(id).getKeyFrame(frame, true);
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Rectangle getCollider() {
        return collider;
    }

    public void setCollider(Rectangle collider) {
        this.collider = collider;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public float getInvisibleTime() {
        return invisibleTime;
    }

    public void setInvisibleTime(float invisibleTime) {
        this.invisibleTime = invisibleTime;
    }

    public void damage() {
        if(invisibleTime > 0.05f)
            return;
        SfxManager.playPlayerDamage();
        HP -= 1;
        invisibleTime = 1; // in seconds
    }

    public int getXpGained() {
        return xpGained;
    }

    public boolean setXpGained(int xpGained) {
        this.xpGained = xpGained;
        if(xpGained >= xpNeedToNextLevel()) {
            this.xpGained -= xpNeedToNextLevel();
            currentLevel++;
            return true;
        }
        return false;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int xpNeedToNextLevel() {
        return currentLevel * 20;
    }
    public float getProgress() {
        return xpGained / (float)xpNeedToNextLevel();
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void addAbility(Ability abb) {
        abilities.add(abb);
    }

    public void applyAbility(Ability ability) {
        totalAbilities.add(ability);
        if(ability.getType() == Abilities.Amocrease) {
            gun.setMaxAmmo(gun.getMaxAmmo() + 5);
        }else if(ability.getType() == Abilities.Procrease) {
            gun.setProjectile(gun.getProjectile() + 1);
        }else if(ability.getType() == Abilities.Vitality) {
            maxHP++;
        }else {
            addAbility(ability);
        }
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getGunDamage() {
        int damage = gun.getType().getDamage();
        for(Ability ability : abilities) {
            if(ability.getType() == Abilities.Damager) {
                damage = damage * 5 / 4;
                break;
            }
        }
        return damage;
    }
    public boolean isSpeedy() {
        for(Ability ability : abilities) {
            if(ability.getType() == Abilities.Speedy) {
                return true;
            }
        }
        return false;
    }

    public void death() {
        HP = 0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
