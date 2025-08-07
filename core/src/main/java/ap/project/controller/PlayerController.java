package ap.project.controller;

import ap.project.model.*;
import ap.project.model.enums.Abilities;
import ap.project.model.enums.GameState;
import ap.project.view.GameView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PlayerController {
    private final float playerWidth;
    private final float playerHeight;
    private float scale;
    float timer = 0;
    boolean levelChanged = false;

    private GameView view;
    public PlayerController(float playerWidth, float playerHeight, float scale, GameView view) {
        this.playerHeight = playerHeight;
        this.playerWidth = playerWidth;
        this.scale = scale;
        this.view = view;
    }

    public void advanceLevel() {
        view.animations.add(new Anim(Game.getInstance().getPlayer().getPosition().cpy().sub(
            GameAssetManager.getInstance().levelUpAnimation.getKeyFrame(0, true).getRegionHeight()*3, 0),
            GameAssetManager.getInstance().levelUpAnimation, 10f));
        timer = 0;
        this.levelChanged = true;
    }
    public void update(float delta) {
        if(this.levelChanged && timer > 1.5f){
            Game.getInstance().setGameState(GameState.abilityChoosing);
            view.buildLevelUpMenu();
            this.levelChanged = false;
        }
        timer += delta;
        Player player = Game.getInstance().getPlayer();
        for(int i = player.getAbilities().size() - 1; i >= 0; i--) {
            Ability abb = player.getAbilities().get(i);
            abb.setRemainingTime(abb.getRemainingTime() - delta);
            if(abb.getRemainingTime() <= 0)
                player.getAbilities().remove(i);
        }

        if(player.getInvisibleTime() > 0)
            player.setInvisibleTime(player.getInvisibleTime() - delta);

        float dx = 0, dy = 0;
        if(Gdx.input.isKeyPressed(App.getInstance().getSettings().getInputSettings().keyUp)) {
            dy = 1f;
        }
        if(Gdx.input.isKeyPressed(App.getInstance().getSettings().getInputSettings().keyDown)) {
            dy = -1f;
        }
        if(Gdx.input.isKeyPressed(App.getInstance().getSettings().getInputSettings().keyLeft)) {
            dx = -1f;
            player.setFaceRight(false);
        }
        if(Gdx.input.isKeyPressed(App.getInstance().getSettings().getInputSettings().keyRight)) {
            dx = +1f;
            player.setFaceRight(true);
        }

        Vector2 vec = new Vector2(dx, dy);
        Vector2 nextPos = player.getPosition().cpy().add(player.move(vec, delta));
        Rectangle rec = new Rectangle(nextPos.x,
            nextPos.y, playerWidth, playerHeight);
        boolean overlapWithTree = false;
        for(Tree tree : Game.getInstance().getTrees().values()) {
            if(tree != null && tree.getRectangle().overlaps(rec))
                overlapWithTree = true;
        }
        if(overlapWithTree) {
            player.damage();
            return;
        }
        if(Game.getInstance().getElectricWallRadius() > 0) {
            float wallMinX = Game.getInstance().getElectricWallStartPoint().x - Game.getInstance().getElectricWallRadius();
            float wallMaxX = Game.getInstance().getElectricWallStartPoint().x + Game.getInstance().getElectricWallRadius();
            float wallMinY = Game.getInstance().getElectricWallStartPoint().y - Game.getInstance().getElectricWallRadius();
            float wallMaxY = Game.getInstance().getElectricWallStartPoint().y + Game.getInstance().getElectricWallRadius();
            if (Math.abs(wallMinX - nextPos.x) < rec.getWidth() || Math.abs(wallMaxX - nextPos.x) < rec.getWidth()
                || Math.abs(wallMinY - nextPos.y) < rec.getHeight() || Math.abs(wallMaxY - nextPos.y) < rec.getHeight()) {
                player.death();
            }
        }
        for(int i = Game.getInstance().getGems().size() - 1; i >= 0; i--) {
            Gem gem = Game.getInstance().getGems().get(i);
            if(gem.getRectangle().overlaps(rec)) {
                SfxManager.xpGained();
                Game.getInstance().getGems().remove(i);
                boolean levelChanged = player.setXpGained(player.getXpGained() + gem.getXp());
                if(levelChanged) {
                    SfxManager.playLevelUp();
                    advanceLevel();
                }
                break;
            }
        }

        player.setMoving(vec.len() > 0);
        player.setPosition(nextPos);
        player.setCollider(rec);
    }
}
