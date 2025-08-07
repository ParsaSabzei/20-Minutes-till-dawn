package ap.project.model.enums;

import ap.project.model.GameAssetManager;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Guns {
    Revolver("Revolver", 20, 1, 1, 6, 0.4f, GameAssetManager.getInstance().getRevolverGun()),
    Shotgun("Shotgun", 10, 4, 1, 2, 0.5f,GameAssetManager.getInstance().getShotgun()),
    SmgDual("SMGs Dual", 8, 1, 2, 24, 0.15f, GameAssetManager.getInstance().getSmgGun()),

    ;
    private String name;
    private int damage;
    private int projectile;
    private int timeReload;
    private int maxAmmo;
    private float fireRate;
    private TextureRegion standTexture;
    Guns(String name, int damage, int projectile, int timeReload, int maxAmmo, float fireRate,TextureRegion standTexture) {
        this.name = name;
        this.damage = damage;
        this.projectile = projectile;
        this.timeReload = timeReload;
        this.maxAmmo = maxAmmo;
        this.fireRate = fireRate;
        this.standTexture = standTexture;
    }
    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getProjectile() {
        return projectile;
    }

    public int getTimeReload() {
        return timeReload;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public TextureRegion getStandTexture() {
        return standTexture;
    }

    public static Guns fromName(String name){
        for(Guns bm : Guns.values())
            if(bm.getName().equals(name))
                return bm;
        return Revolver;
    }

    public float getFireRate() {
        return fireRate;
    }
}
