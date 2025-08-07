package ap.project.view;

import ap.project.Main;
import ap.project.controller.AbilityController;
import ap.project.controller.MonsterController;
import ap.project.model.*;
import ap.project.model.enums.*;
import ap.project.utils.UIUtils;
import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView implements View{
    public SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    public float stateTime = 0f;
    private float secondCounter = 0f;
    public OrthographicCamera camera;
    public List<Anim> animations = new ArrayList();
    private BitmapFont timeLabel, levelLabel, ammoLabel, totalKillLabel;

    public Stage abilityStage;
    public Ability[] chosenAbilities = new Ability[3];
    public TextButton[] abilityImages = new TextButton[3];
    private Label[] abilityBottomLabels = new Label[3];
    private Label[] abilityTopLabels = new Label[3];
    public PauseView pauseView = new PauseView();
    public EndGameView endGameView = new EndGameView();

    private RayHandler rayHandler;
    private World world;
    private Light light;

    public GameView(){
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        timeLabel = UIUtils.getBitmapFont(70);
        levelLabel = UIUtils.getBitmapFont(30);
        ammoLabel = UIUtils.getBitmapFont(20);
        totalKillLabel = UIUtils.getBitmapFont(40);
        buildAbilityUI();

        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        RayHandler.useDiffuseLight(true);
        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.6f, 0.6f, 0.6f, 0.8f);


        light = new PointLight(rayHandler, 128);
        light.setColor(1f, 1f, 0.9f, 1f);
        light.setDistance(Gdx.graphics.getWidth() / 5f);
        light.setSoftnessLength(40f);
        light.setStaticLight(false);
    }

    private void buildAbilityUI() {
        Table table = new Table();
        table.setFillParent(true);
        abilityStage = new Stage();
        for(int i = 0; i < 3; i++){
            abilityImages[i] = new TextButton("", GameAssetManager.getInstance().uiSkin);
            abilityBottomLabels[i] = new Label("", UIUtils.getLabelStyle(50));
            abilityTopLabels[i] = new Label("", UIUtils.getLabelStyle(35));
        }
        for(int i = 0; i < 3; i++) {
            table.add(abilityTopLabels[i]).center().expandX();
        }
        table.row();
        for(int i = 0; i <3; i++)
            table.add(abilityImages[i]).center().expandX()
                .padTop(Gdx.graphics.getHeight() / 5f).padBottom(Gdx.graphics.getHeight() / 5f);
        table.row();
        for(int i = 0; i < 3; i++) {
            table.add(abilityBottomLabels[i]);
        }
        abilityStage.addActor(table);
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stateTime += delta;

        batch.begin();


        updateCamera(delta);
        drawMap();
        drawPlayer();
        drawTrees();
        drawElectricWall();
        drawMonsters();
        drawBullets();
        drawMonsterBullets();
        drawAnimations(delta);
        drawGems();
        drawHearts();

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        showXpProgress();
        shapeRenderer.end();

        batch.begin();
        drawCurrentLevel();
        batch.end();

        if(Game.getInstance().getGameState().equals(GameState.abilityChoosing)) {
            abilityStage.act(delta);
            abilityStage.draw();
        }else if(Game.getInstance().getGameState().equals(GameState.pause)) {
            pauseView.pauseStage.act(delta);
            pauseView.pauseStage.draw();
        }else if(Game.getInstance().getGameState().equals(GameState.winOrLose)) {
            endGameView.stage.act(delta);
            endGameView.stage.draw();
        }

        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.updateAndRender();
        light.setPosition(Game.getInstance().getPlayer().getPosition().x, Game.getInstance().getPlayer().getPosition().y);

    }
    private void drawElectricWall() {
        if(Game.getInstance().getElectricWallRadius() < 0)
            return;
        float minX = Game.getInstance().getElectricWallStartPoint().x - Game.getInstance().getElectricWallRadius();
        float maxX = Game.getInstance().getElectricWallStartPoint().x + Game.getInstance().getElectricWallRadius();
        float minY = Game.getInstance().getElectricWallStartPoint().y - Game.getInstance().getElectricWallRadius();
        float maxY = Game.getInstance().getElectricWallStartPoint().y + Game.getInstance().getElectricWallRadius();

        TextureRegion elecTexture = GameAssetManager.getInstance().electericWallAnimation.getKeyFrame(stateTime, true);

        float start = minX;
        while(start <= maxX) {
            batch.draw(elecTexture, start, minY);
            batch.draw(elecTexture, start, maxY);
            start  += elecTexture.getRegionWidth();
        }
        start = minY;
        while(start <= maxY) {
            batch.draw(elecTexture, minX, start, elecTexture.getRegionWidth() / 2f, elecTexture.getRegionHeight() / 2f,
                elecTexture.getRegionWidth(), elecTexture.getRegionHeight(), 1, 1, 90);
            batch.draw(elecTexture, maxX, start, elecTexture.getRegionWidth() / 2f, elecTexture.getRegionHeight() / 2f,
                elecTexture.getRegionWidth(), elecTexture.getRegionHeight(), 1, 1, 90);
            start  += elecTexture.getRegionWidth();
        }
    }
    public void buildLevelUpMenu() {
        List<Integer> chosen = new ArrayList<>();
        while (chosen.size() < 3) {
            while (true) {
                Integer selected = new Random().nextInt(Abilities.values().length);
                if (chosen.contains(selected)) {
                    continue;
                }
                chosen.add(selected);
                break;
            }
        }
        for (int i = 0; i < chosen.size(); i++) {
            chosenAbilities[i] = Abilities.values()[chosen.get(i)].getAbility();
            abilityImages[i].setText(chosenAbilities[i].getType().name);
        }
        Gdx.input.setInputProcessor(abilityStage);
        for(int i = 0; i < chosen.size(); i++) {
            abilityTopLabels[i].setText(chosenAbilities[i].getType().name);
        }
    }
    private void drawCurrentLevel() {
        Vector3 position = new Vector3(Gdx.graphics.getWidth() / 2f - 50,
            Gdx.graphics.getHeight() / 100f, 0);
        position = camera.unproject(position);
        levelLabel.draw(batch, Texts.LEVEL.getText() + " " + Game.getInstance().getPlayer().getCurrentLevel(), position.x, position.y);
    }
    private void showXpProgress() {
        shapeRenderer.setColor(Color.DARK_GRAY);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight() / 28f;
        float x = 0;
        float y = Gdx.graphics.getHeight() - height;
        shapeRenderer.rect(x, y, width, height);

        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(x, y, width * Game.getInstance().getPlayer().getProgress(), height);
    }
    private void drawGems() {
        TextureRegion texture = GameAssetManager.getInstance().gem;
        for(Gem gem : Game.getInstance().getGems()) {
            batch.draw(texture,
                gem.getPosition().x, gem.getPosition().y,
                gem.getScaleFactor() * texture.getRegionWidth(), gem.getScaleFactor() * texture.getRegionHeight());
        }
    }
    public void drawMonsterBullets() {
        for(Monster monster : Game.getInstance().getMonsters()) {
            for (Bullet bullet : monster.getProjectiles()) {
                TextureRegion bulletTexture = GameAssetManager.getInstance().eyeMonsterProjectile;
                batch.draw(bulletTexture, bullet.getPosition().x, bullet.getPosition().y,
                    bulletTexture.getRegionWidth() * 3, bulletTexture.getRegionHeight() * 3);
            }
        }
    }
    float monsterScale = 3.5f;
    private void drawMonsters() {
        for(Monster monster : Game.getInstance().getMonsters()) {
            if(isInCamera(monster.getPosition().x, monster.getPosition().y)) {
                TextureRegion texture = monster.getType().getMonsterAnimation().getKeyFrame(stateTime, true);
                if(monster.getType() == MonsterTypes.Elder && monster.isRaging())
                    texture = GameAssetManager.getInstance().elderRageAnimation.getKeyFrame(stateTime, false);
                else if(monster.getType() == MonsterTypes.Elder && monster.isAttacking())
                    texture = GameAssetManager.getInstance().elderAttackAnimation.getKeyFrame(stateTime, true);

                boolean flip = Game.getInstance().getPlayer().getPosition().x < monster.getPosition().x;
                if(monster.getType() == MonsterTypes.Elder && monster.isWalking() && flip != texture.isFlipX()) {
                    texture.flip(true, false);
                }
                batch.draw(texture,
                        monster.getPosition().x - texture.getRegionWidth()*monsterScale/2.0f, monster.getPosition().y - texture.getRegionHeight()*monsterScale/2.0f,
                        texture.getRegionWidth() * monsterScale, texture.getRegionHeight() * monsterScale);
            }
        }
    }
    private void drawHearts() {
        int space = Gdx.graphics.getWidth() / 50;
        int height = Gdx.graphics.getHeight();
        float scale = 3;
        TextureRegion text = GameAssetManager.getInstance().heartAnimation.getKeyFrame(stateTime,true);
        for(int i = 0; i < Game.getInstance().getPlayer().getMaxHP(); i++) {
            Vector3 pos = new Vector3(space * (i + 1) + scale * i * text.getRegionWidth(), height / 9f, 0);
            pos = camera.unproject(pos);
            if(i < Game.getInstance().getPlayer().getHP())
                batch.draw(text, pos.x, pos.y, text.getRegionWidth() * scale, text.getRegionHeight() * scale);
            else
                batch.draw(GameAssetManager.getInstance().brokenHeart,
                    pos.x, pos.y, text.getRegionWidth() * scale, text.getRegionHeight() * scale);
        }

        int secondsRemain = (int) (Game.getInstance().getTimeControl() * 60 - Game.getInstance().getTimeElapsed());
        int minutes = secondsRemain / 60;
        int seconds = secondsRemain % 60;

        Vector3 pos = new Vector3(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 12f, 0);
        pos = camera.unproject(pos);

        timeLabel.draw
            (batch,minutes + ":" + seconds,pos.x, pos.y);

        pos = new Vector3(Game.getInstance().getPlayer().getPosition().x ,
            Game.getInstance().getPlayer().getPosition().y+ Gdx.graphics.getHeight()/16f, 0);
        ammoLabel.draw(
            batch, Game.getInstance().getPlayer().getGun().getAmmo()+"", pos.x, pos.y);

        pos = new Vector3(Gdx.graphics.getWidth() *6 / 7f ,
            Gdx.graphics.getHeight()  / 15f , 0);
        camera.unproject(pos);
        totalKillLabel.draw(
            batch, Texts.Kills.getText()+": " + Game.getInstance().getTotalKills(), pos.x, pos.y);
    }
    private void drawAnimations(float delta) {
        for(int i = animations.size() - 1; i >= 0; i--) {
            Anim animation = animations.get(i);
            TextureRegion texture = animation.update(delta);
            float scale = animation.getScaleFactor();
            batch.draw(texture, animation.getPosition().x, animation.getPosition().y,
                texture.getRegionWidth() * scale, texture.getRegionHeight() * scale);
            if(animation.isFinished())
                animations.remove(i);
        }
    }
    float bulletScale = 1.2f;
    private void drawBullets() {
        Player player = Game.getInstance().getPlayer();
        TextureRegion gunTexture = player.getGun().getType().getStandTexture();
        float gunOriginX = (float) gunTexture.getRegionWidth() / 2;
        float gunOriginY = (float) gunTexture.getRegionHeight() / 2;

        for(Bullet bullet : Game.getInstance().getBullets()){
            TextureRegion texture = GameAssetManager.getInstance().bullet;
            float rad = (float) Math.atan2(bullet.getDirection().y, bullet.getDirection().x);
            float angle = (float) Math.toDegrees(rad);
            float gunOffsetX = (float) (55 * Math.sin(rad)), gunOffsetY = (float) (-4f - 55 * Math.cos(rad));
            batch.draw(
                texture,
                bullet.getPosition().x +gunOffsetX, bullet.getPosition().y +gunOffsetY,
                gunOriginX - 10f, gunOriginY,
                texture.getRegionWidth(), texture.getRegionHeight(),
                bulletScale, bulletScale,
                angle
            );
        }
    }
    private void drawTrees() {
        TextureRegion treeTexture = GameAssetManager.getInstance().tree;

        float scale = 5;
        float textureSizeW =  treeTexture.getRegionWidth(), textureSizeH =  treeTexture.getRegionHeight();

        int minX = (int) ((camera.position.x - camera.viewportWidth / 2 * camera.zoom) / (tileSize*scale)) - 10;
        int maxX = (int) ((camera.position.x + camera.viewportWidth / 2 * camera.zoom) / (tileSize*scale)) + 10;
        int minY = (int) ((camera.position.y - camera.viewportHeight / 2 * camera.zoom) / (tileSize*scale)) - 10;
        int maxY = (int) ((camera.position.y + camera.viewportHeight / 2 * camera.zoom) / (tileSize*scale)) + 10;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                Tree isTree = Game.getInstance().isTreeAt(x, y, x * tileSize * scale + scale * textureSizeW / 2.7f,
                    y * tileSize * scale + textureSizeW * scale / 10,
                    textureSizeW * scale / 3f, textureSizeH * scale / 2);
                if(isTree != null)
                    batch.draw(treeTexture, x * tileSize * scale, y * tileSize * scale,
                        textureSizeW * scale, textureSizeH * scale);
            }
        }
    }
    float tileSize = 32f;
    float tileScale = 5f;
    private void drawMap() {
        int minX = (int) ((camera.position.x - camera.viewportWidth / 2 * camera.zoom) / (tileSize*tileScale)) - 1;
        int maxX = (int) ((camera.position.x + camera.viewportWidth / 2 * camera.zoom) / (tileSize*tileScale)) + 1;
        int minY = (int) ((camera.position.y - camera.viewportHeight / 2 * camera.zoom) / (tileSize*tileScale)) - 1;
        int maxY = (int) ((camera.position.y + camera.viewportHeight / 2 * camera.zoom) / (tileSize*tileScale)) + 1;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                int tileIndex = Game.getInstance().getTileAt(x, y);
                TextureRegion tile = GameAssetManager.getInstance().tiles.get(tileIndex);
                batch.draw(tile, x * tileSize * tileScale, y * tileSize * tileScale, tileSize * tileScale, tileSize * tileScale);
            }
        }
    }
    private void updateCamera(float delta) {
        Player player = Game.getInstance().getPlayer();
        Vector2 target = player.getPosition();
        float lerp = 2f;
        camera.position.x += (target.x - camera.position.x) * lerp * delta;
        camera.position.y += (target.y - camera.position.y) * lerp * delta;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }
    private void drawPlayer() {
        Player player = Game.getInstance().getPlayer();

        TextureRegion playerTexture = player.isMoving() ? player.getWalkFrame(stateTime) : player.getIdleFrame(stateTime);

        TextureRegion gunTexture = player.getGun().getType().getStandTexture();
        if(player.getGun().getReloadTimeRemain() > 0.01f)
            gunTexture = GameAssetManager.getInstance().gunReloadAnimation.getKeyFrame(stateTime, true);

        if(player.isFaceRight() == playerTexture.isFlipX()) {
            playerTexture.flip(true, false);
        }
        float scale = GameAssetManager.getInstance().playerScaleFactor;
        float originX = (float) playerTexture.getRegionWidth() / 2;
        float originY = (float) playerTexture.getRegionHeight() / 2;
        float drawX = player.getPosition().x - originX * scale;
        float drawY = player.getPosition().y - originY * scale;
        batch.draw(playerTexture,
            drawX, drawY,
            0, 0,
            playerTexture.getRegionWidth(), playerTexture.getRegionHeight(),
            scale, scale,
            0);

        float gunOriginX = (float) gunTexture.getRegionWidth() / 2;
        float gunOriginY = (float) gunTexture.getRegionHeight() / 2;

        Vector2 direction = new Vector2(Gdx.input.getX(), -Gdx.input.getY());

        direction.sub(Gdx.graphics.getWidth()/2f, -Gdx.graphics.getHeight()/2f);
        float angle = (float) Math.toDegrees(Math.atan2(direction.y, direction.x));

        float gunOffsetX = 0f, gunOffsetY = -4f;

        boolean flipGun = angle >= 90 || angle <= -90;
        if(gunTexture.isFlipY() != flipGun)
            gunTexture.flip(false, true);
        batch.draw(
            gunTexture,
            player.getPosition().x +gunOffsetX, player.getPosition().y +gunOffsetY,
            gunOriginX - 10f, gunOriginY,
            gunTexture.getRegionWidth(), gunTexture.getRegionHeight(),
            scale, scale,
            angle
        );
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

    public boolean isInCamera(float x, float y) {
        float camLeft = camera.position.x - camera.viewportWidth / 2 * camera.zoom;
        float camRight = camera.position.x + camera.viewportWidth / 2 * camera.zoom;
        float camBottom = camera.position.y - camera.viewportHeight / 2 * camera.zoom;
        float camTop = camera.position.y + camera.viewportHeight / 2 * camera.zoom;

        if (x < camLeft || x > camRight ||
            y < camBottom || y > camTop) {
            return false;
        }
        return true;
    }

    public float getBulletWidth() {
        return GameAssetManager.getInstance().bullet.getRegionWidth() * bulletScale;
    }
    public float getBulletHeight() {
        return GameAssetManager.getInstance().bullet.getRegionHeight() * bulletScale;
    }

    public Vector2 getMonsterWidthHeight() {
        return new Vector2(GameAssetManager.getInstance().brainMonsterAnimation.getKeyFrame(stateTime, true).getRegionWidth() * monsterScale,
            GameAssetManager.getInstance().brainMonsterAnimation.getKeyFrame(stateTime, true).getRegionHeight()* monsterScale);
    }

    public Vector2 getElderWidthHeight() {
        return new Vector2(GameAssetManager.getInstance().elderWalkAnimation.getKeyFrame(stateTime, true).getRegionWidth() * monsterScale,
            GameAssetManager.getInstance().elderWalkAnimation.getKeyFrame(stateTime, true).getRegionHeight()* monsterScale);
    }

    public void buildPauseMenu() {
        pauseView.buildUI();
    }
}
