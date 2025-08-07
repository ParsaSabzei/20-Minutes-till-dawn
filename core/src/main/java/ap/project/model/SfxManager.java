package ap.project.model;

public class SfxManager {
    public static void playShot(){
        if(App.getInstance().getSettings().isSfxOn())
            GameAssetManager.getInstance().shotSound.play(0.5f);
    }

    public static void playReload() {
        if(App.getInstance().getSettings().isSfxOn())
            GameAssetManager.getInstance().reloadSound.play(0.5f);
    }

    public static void playLevelUp() {
        if(App.getInstance().getSettings().isSfxOn())
            GameAssetManager.getInstance().levelUpSound.play(0.5f);
    }

    public static void playPlayerDamage() {
        if(App.getInstance().getSettings().isSfxOn())
            GameAssetManager.getInstance().playerDamageSound.play(0.5f);
    }

    public static void xpGained() {
        if(App.getInstance().getSettings().isSfxOn())
            GameAssetManager.getInstance().xpGainedSound.play(0.5f);
    }
}
