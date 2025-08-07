package ap.project.model;

import ap.project.model.enums.Characters;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;

public class GameAssetManager {
    private static final GameAssetManager gameAssetManager = new GameAssetManager();
    public Skin uiSkin;
    public Texture signupMenuLogo, background, leftLeaf, rightLeaf, logo, simpleBackground, cursor, background2,background3;
    public Texture shanaChar, dasherChar, diamondChar, lilithChar, scarlettChar;
    public Texture alpha, abby;

    private Texture revolverGun, shotgun, smgGun;
    public TextureRegion player;
    public TextureRegion tree;
    public TextureRegion bullet;
    public TextureRegion gem;
    public TextureRegion eyeMonsterProjectile;

    public TextureRegion vitality;
    public TextureRegion damager;
    public TextureRegion procrease;
    public TextureRegion amocrease;
    public TextureRegion speedy;

    public List<Animation<TextureRegion>> playersWalkAnimation = new ArrayList<>();
    public List<Animation<TextureRegion>> playersIdleAnimation = new ArrayList<>();
    public List<TextureRegion> tiles = new ArrayList<>();
    public Animation<TextureRegion> gunReloadAnimation;
    public Animation<TextureRegion> explosionAnimation;
    public Animation<TextureRegion> heartAnimation;
    public Animation<TextureRegion> brainMonsterAnimation;
    public Animation<TextureRegion> monsterDeathAnimation;
    public Animation<TextureRegion> eyeMonsterAnimation;
    public Animation<TextureRegion> electericWallAnimation;
    public Animation<TextureRegion> levelUpAnimation;

    public Animation<TextureRegion> elderWalkAnimation, elderRageAnimation, elderAttackAnimation;
    public TextureRegion brokenHeart;

    public float playerScaleFactor = 4;
    public int tilesCount = 19;

    public Sound shotSound;
    public Sound reloadSound;
    public Sound levelUpSound;
    public Sound playerDamageSound;
    public Sound xpGainedSound;

    public void load(){
        uiSkin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
        alpha=  new Texture("alpha.png");
        signupMenuLogo =new Texture("menus/signUpAndLogin.png");
        leftLeaf = new Texture("menus/LeavesLeft.png");
        rightLeaf = new Texture("menus/LeavesRight.png");
        background = new Texture("background.jpg");
        logo = new Texture("menus/T_20Logo.png");
        simpleBackground = new Texture("menus/simpleBackground.png");
        background2 = new Texture("menus/background2.png");
        background3 = new Texture("menus/background3.png");
        abby = new Texture("menus/abby.png");
        cursor = new Texture("cursor.png");
        shanaChar = new Texture("characters/Shana.png");
        dasherChar = new Texture("characters/Dasher.png");
        diamondChar = new Texture("characters/Diamond.png");
        lilithChar = new Texture("characters/Lilith.png");
        scarlettChar = new Texture("characters/Scarlett.png");
        revolverGun = (new Texture("guns/revolver.png"));
        shotgun = (new Texture("guns/shotgun.png"));
        smgGun = (new Texture("guns/smg.png"));
        player = new TextureRegion(new Texture("player/idleRight.png"));
        tree = new TextureRegion(new Texture("map/Tree.png"));
        bullet = new TextureRegion(new Texture("guns/bullet.png"));

        eyeMonsterProjectile = new TextureRegion(new Texture("monsters/eyeMonsterBullet.png"));
        gem = new TextureRegion(new Texture("monsters/gem.png"));

        vitality = new TextureRegion(new Texture("abilities/Vitality.png"));
        damager = new TextureRegion(new Texture("abilities/Damager.png"));
        procrease = new TextureRegion(new Texture("abilities/Procrease.png"));
        amocrease = new TextureRegion(new Texture("abilities/Amocrease.png"));
        speedy = new TextureRegion(new Texture("abilities/Speedy.png"));

        loadAnimations();
        loadTiles();

        shotSound = Gdx.audio.newSound(Gdx.files.internal("sfx/singleShot.wav"));
        reloadSound = Gdx.audio.newSound(Gdx.files.internal("sfx/gunReload.wav"));
        levelUpSound = Gdx.audio.newSound(Gdx.files.internal("sfx/levelUp.wav"));
        playerDamageSound = Gdx.audio.newSound(Gdx.files.internal("sfx/playerDamage.wav"));
        xpGainedSound = Gdx.audio.newSound(Gdx.files.internal("sfx/xpGained.wav"));
    }

    private void loadTiles() {
        for(int i = 0; i < tilesCount; i++){
            TextureRegion txt = new TextureRegion(new Texture("ground/T_ForestTiles_"+i+".png"));
            tiles.add(txt);
        }
    }

    private void loadAnimations() {
        TextureRegion[] levelUpTexture = new TextureRegion[9];
        for(int i = 0; i < 9; i++)
            levelUpTexture[i] = new TextureRegion(new Texture("player/T_LevelUpFX_"+i+".png"));
        levelUpAnimation = new Animation<>(0.15f,levelUpTexture);

        TextureRegion[] elecWall = new TextureRegion[3];
        for(int i = 0; i < 3; i++)
            elecWall[i] = new TextureRegion(new Texture("map/electricWall"+i+".png"));
        electericWallAnimation = new Animation<>(0.15f,elecWall);

        TextureRegion[] elderWalk = new TextureRegion[3];
        for(int i = 0; i < 3; i++)
            elderWalk[i] = new TextureRegion(new Texture("monsters/elder/walk"+i+".png"));
        elderWalkAnimation = new Animation<>(0.15f,elderWalk);

        TextureRegion[] elderRage = new TextureRegion[5];
        for(int i = 0; i < 5; i++)
            elderRage[i] = new TextureRegion(new Texture("monsters/elder/rage"+i+".png"));
        elderRageAnimation = new Animation<>(0.15f,elderRage);

        TextureRegion[] elderAttack = new TextureRegion[3];
        for(int i = 0; i < 3; i++)
            elderAttack[i] = new TextureRegion(new Texture("monsters/elder/attack"+i+".png"));
        elderAttackAnimation = new Animation<>(0.15f,elderAttack);

        TextureRegion[] eyeMonsterTextures = new TextureRegion[3];
        for(int i = 0; i < 3; i++)
            eyeMonsterTextures[i] = new TextureRegion(new Texture("monsters/EyeMonster_"+i+".png"));
        eyeMonsterAnimation = new Animation<>(0.15f,eyeMonsterTextures);

        Texture death = new Texture("monsters/death.png");
        TextureRegion[] deathFrames = new TextureRegion[4];
        int widthEach = death.getWidth() / 4;
        TextureRegion[][] splitDeath = TextureRegion.split(death, widthEach, death.getHeight());
        System.arraycopy(splitDeath[0], 0, deathFrames, 0, 4);
        monsterDeathAnimation = new Animation<>(0.15f,deathFrames);

        Texture hearts = new Texture("player/hearts.png");
        TextureRegion[] heartsFrames = new TextureRegion[3];
        widthEach = hearts.getWidth() / 4;
        TextureRegion[][] splitHearts = TextureRegion.split(hearts, widthEach, hearts.getHeight());
        for(int i = 0; i < 3; i++)
            heartsFrames[i] = splitHearts[0][i];
        brokenHeart = splitHearts[0][3];
        heartAnimation = new Animation<>(0.1f,heartsFrames);

        Texture brainMonsters = new Texture("monsters/brainMonster.png");
        TextureRegion[] brainFrames = new TextureRegion[4];
        widthEach = brainMonsters.getWidth() / 4;
        TextureRegion[][] splitBrains = TextureRegion.split(brainMonsters, widthEach, brainMonsters.getHeight());
        System.arraycopy(splitBrains[0], 0, brainFrames, 0, 4);
        brainMonsterAnimation = new Animation<>(0.15f,brainFrames);

        Texture explosions =new Texture("guns/explosion.png");
        TextureRegion[] explosionFrames = new TextureRegion[6];
        widthEach = explosions.getWidth() / 6;
        TextureRegion[][] splitEx = TextureRegion.split(explosions, widthEach, explosions.getHeight());
        System.arraycopy(splitEx[0], 0, explosionFrames, 0, 6);
        explosionAnimation = new Animation<>(0.05f, explosionFrames);

        TextureRegion[] frames = new TextureRegion[3];
        for(int i = 0; i < 3; i++){
            TextureRegion txt = new TextureRegion(new Texture("guns/reload"+(i+1)+".png"));
            frames[i] = txt;
        }
        gunReloadAnimation = new Animation<>(0.15f, frames);

        for(Characters character : Characters.values()) {
            Animation<TextureRegion> playerWalkAnimation, playerIdleAnimation;
            int walkCount = 4, idleCount = 6;
            TextureRegion[] walkFrames = new TextureRegion[walkCount];
            for (int i = 0; i < walkCount; i++) {
                walkFrames[i] = new TextureRegion(new Texture("player/animations/"+character.getName()+"/run" + (i + 1) + ".png"));
            }
            playerWalkAnimation = new Animation<>(0.15f, walkFrames);

            TextureRegion[] idleFrames = new TextureRegion[idleCount];
            for (int i = 0; i < idleCount; i++) {
                idleFrames[i] = new TextureRegion(new Texture("player/animations/"+character.getName()+"/idle" + (i + 1) + ".png"));
            }
            playerIdleAnimation = new Animation<>(0.15f, idleFrames);
            playersIdleAnimation.add(playerIdleAnimation);
            playersWalkAnimation.add(playerWalkAnimation);
        }
    }

    public void dispose(){
        uiSkin.dispose();
    }

    public static GameAssetManager getInstance() {
        return gameAssetManager;
    }

    public TextureRegion getRevolverGun() {
        return new TextureRegion(revolverGun);
    }

    public TextureRegion getShotgun() {
        return new TextureRegion(shotgun);
    }

    public TextureRegion getSmgGun() {
        return new TextureRegion(smgGun);
    }
}
