package ap.project.model;

import ap.project.model.enums.Guns;

public class Gun {
    private Guns type;
    private int ammo;
    private int maxAmmo;
    private float reloadTimeRemain = 0;
    private int projectile;
    private float timeSpentFromLastShot = 0;
    private float fireRate;
    public Guns getType() {
        return type;
    }

    public void setType(Guns type) {
        ammo = type.getMaxAmmo();
        maxAmmo = ammo;
        projectile = type.getProjectile();
        this.type = type;
        this.fireRate = type.getFireRate();
    }

    public int getAmmo() {
        return ammo;
    }

    public void reload() {
        SfxManager.playReload();
        reloadTimeRemain = type.getTimeReload();
        ammo = maxAmmo;
    }
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public float getReloadTimeRemain() {
        return reloadTimeRemain;
    }

    public void setReloadTimeRemain(float reloadTimeRemain) {
        this.reloadTimeRemain = reloadTimeRemain;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public int getProjectile() {
        return projectile;
    }

    public void setProjectile(int projectile) {
        this.projectile = projectile;
    }

    public float getTimeSpentFromLastShot() {
        return timeSpentFromLastShot;
    }

    public void setTimeSpentFromLastShot(float timeSpentFromLastShot) {
        this.timeSpentFromLastShot = timeSpentFromLastShot;
    }

    public float getFireRate() {
        return fireRate;
    }

    public void setFireRate(float fireRate) {
        this.fireRate = fireRate;
    }
}
