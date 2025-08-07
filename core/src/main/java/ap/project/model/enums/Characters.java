package ap.project.model.enums;

import ap.project.model.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;

public enum Characters {
    Shana("Shana", 4, 4, GameAssetManager.getInstance().shanaChar),
    Diamond("Diamond", 7, 1, GameAssetManager.getInstance().diamondChar),
    Scarlett("Scarlett", 3, 5, GameAssetManager.getInstance().scarlettChar),
    Lilith("Lilith", 5, 3, GameAssetManager.getInstance().lilithChar),
    Dasher("Dasher", 2, 8, GameAssetManager.getInstance().dasherChar),
    ;

    private final String name;
    private final int HP;
    private final int speed;
    private final Texture portrateTexture;

    Characters(String name, int HP, int speed, Texture portrateTexture) {
        this.name = name;
        this.HP = HP;
        this.speed = speed;
        this.portrateTexture = portrateTexture;
    }

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public int getSpeed() {
        return speed;
    }

    public Texture getPortrateTexture() {
        return portrateTexture;
    }
    public static Characters fromName(String name){
        for(Characters bm : Characters.values())
            if(bm.getName().equals(name))
                return bm;
        return Lilith;
    }
    public static int getIndex(Characters bm){
        int id = 0;
        for(Characters b : Characters.values()) {
            if (b.equals(bm)) {
                return id;
            }
            id += 1;
        }
        return id;
    }
}
