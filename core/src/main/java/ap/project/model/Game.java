package ap.project.model;

import ap.project.model.enums.GameState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class Game {
    public static Game game;

    private Player player;
    private int timeControl;
    private Map<Point, Integer> tileMap = new HashMap<>();
    private Map<Point, Tree> trees = new HashMap<>();
    private List<Bullet> bullets = new ArrayList<>();
    private List<Monster> monsters = new ArrayList<>();
    private boolean autoAim = false;
    private float timeElapsed = 0;
    private List<Gem> gems = new ArrayList<>();
    private GameState gameState = GameState.normal;
    private boolean elderSpawned  = false;

    private float electricWallRadius = -1;
    private Vector2 electricWallStartPoint = new Vector2(0,0);
    private final float timeElectricWall = 90;

    private int totalKills = 0;

    public static Game getInstance() {
        if(game == null)
            game = new Game();
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getTimeControl() {
        return timeControl;
    }

    public void setTimeControl(int timeControl) {
        this.timeControl = timeControl;
    }

    public int getTileAt(int x, int y) {
        Point p = new Point(x, y);
        if(tileMap.containsKey(p)) {
            return tileMap.get(p);
        }
        int simple = new Random().nextInt(30), tileIndex;
        if(simple != 0)
            tileIndex = 0;
        else
             tileIndex = new Random().nextInt(GameAssetManager.getInstance().tilesCount);
        tileMap.put(p, tileIndex);
        return tileMap.get(p);
    }
    public Tree isTreeAt(int x, int y, float rectangleX, float rectangleY, float width, float height){
        Point p = new Point(x, y);
        if(trees.containsKey(p)) {
            return trees.get(p);
        }
        int rand = new Random().nextInt(150);
        if(rand == 0) {
            Tree tree = new Tree(new Vector2(x, y), new Rectangle(rectangleX, rectangleY, width, height));
            if(tree.getRectangle().overlaps(player.getCollider()))
                return null;
            trees.put(p, tree);
        }else
            trees.put(p, null);
        return trees.get(p);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    public Map<Point, Tree> getTrees() {
        return trees;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public boolean isAutoAim() {
        return autoAim;
    }

    public void setAutoAim(boolean autoAim) {
        this.autoAim = autoAim;
    }

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public void advanceTime(float timeElapsed) {
        this.timeElapsed += timeElapsed;
    }

    public List<Gem> getGems() {
        return gems;
    }

    public float getProgress() {
        return 0;
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public boolean isElderSpawned() {
        return elderSpawned;
    }

    public void setElderSpawned(boolean elderSpawned) {
        this.elderSpawned = elderSpawned;
    }

    public Vector2 getElectricWallStartPoint() {
        return electricWallStartPoint;
    }

    public void setElectricWallStartPoint(Vector2 electricWallStartPoint) {
        this.electricWallStartPoint = electricWallStartPoint;
    }

    public float getElectricWallRadius() {
        return electricWallRadius;
    }

    public void setElectricWallRadius(float electricWallRadius) {
        this.electricWallRadius = electricWallRadius;
    }


    public float getTimeElectricWall() {
        return timeElectricWall;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public void clearGame() {
        tileMap.clear();
        trees.clear();
        bullets.clear();
        monsters.clear();
        autoAim = false;
        timeElapsed = 0;
        gems.clear();
        gameState = GameState.normal;
        totalKills = 0;
        electricWallRadius = -1;
        electricWallStartPoint = new Vector2(0,0);
    }

    public void saveToFile() {
        if(App.getInstance().getLoggedInUser().isGuest())
            return;
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Point.class, new PointAdapter())
            .registerTypeAdapter(new TypeToken<Map<Point, Integer>>(){}.getType(), new MapPointAdapter<>(Integer.class))
            .registerTypeAdapter(new TypeToken<Map<Point, Tree>>(){}.getType(), new MapPointAdapter<>(Tree.class))
            .setPrettyPrinting()
            .create();

        String json = gson.toJson(this);

        FileHandle file = Gdx.files.local("game_data"+App.getInstance().getLoggedInUser().getUsername()+".json");
        file.writeString(json, false);
    }

    public boolean savedGameExists() {
        FileHandle file = Gdx.files.local("game_data"+App.getInstance().getLoggedInUser().getUsername()+".json");
        return file.exists();
    }
    public void loadFromFile() {
        FileHandle file = Gdx.files.local("game_data"+App.getInstance().getLoggedInUser().getUsername()+".json");
        if (!file.exists()) return;

        String json = file.readString();
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Point.class, new PointAdapter())
            .registerTypeAdapter(new TypeToken<Map<Point, Integer>>(){}.getType(), new MapPointAdapter<>(Integer.class))
            .registerTypeAdapter(new TypeToken<Map<Point, Tree>>(){}.getType(), new MapPointAdapter<>(Tree.class))
            .setPrettyPrinting()
            .create();
        Game loaded = gson.fromJson(json, Game.class);
        copyFrom(loaded);
    }
    public void deleteSavedGame() {
        FileHandle file = Gdx.files.local("game_data"+App.getInstance().getLoggedInUser().getUsername()+".json");
        if (file.exists()) file.delete();
    }
    public void copyFrom(Game other) {
        this.player = other.player;
        this.timeControl = other.timeControl;
        this.tileMap = other.tileMap;
        this.trees = other.trees;
        this.bullets = other.bullets;
        this.monsters = other.monsters;
        this.autoAim = other.autoAim;
        this.timeElapsed = other.timeElapsed;
        this.gems = other.gems;
        this.gameState = other.gameState;
        this.elderSpawned = other.elderSpawned;
        this.electricWallRadius = other.electricWallRadius;
        this.electricWallStartPoint = other.electricWallStartPoint;
        this.totalKills = other.totalKills;
    }
}
