package ap.project.model.enums;

import ap.project.model.Ability;
import ap.project.model.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Abilities {
    Vitality("Vitality", "Increase hearts limit by one", GameAssetManager.getInstance().vitality, 1000000000),
    Damager("Damager", "Increase damage for 10 seconds by 25%", GameAssetManager.getInstance().damager, 10),
    Procrease("Procrease", "Increase gun projectile by one", GameAssetManager.getInstance().procrease, 1000000000),
    Amocrease("Amocrease", "Increase max ammo by 5", GameAssetManager.getInstance().amocrease, 1000000000),
    Speedy("Speedy", "Make player's speed 2x for 10 seconds", GameAssetManager.getInstance().speedy, 10),
    ;
    public String name;
    public String description;
    public TextureRegion textureRegion;
    public int time;
    Abilities(String name, String description, TextureRegion textureRegion, int time) {
        this.name = name;
        this.description = description;
        this.textureRegion = textureRegion;
        this.time = time;
    }
    public Ability getAbility(){
        return new Ability(time, this);
    }
}
