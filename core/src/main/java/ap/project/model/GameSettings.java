package ap.project.model;

import ap.project.model.enums.BackgroundMusics;
import ap.project.model.enums.Languages;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameSettings {
    private int musicVolume = 50;

    private BackgroundMusics backgroundMusicType = BackgroundMusics.Default;
    private transient Music backgroundMusic = null;

    private InputSettings inputSettings = new InputSettings();

    private boolean sfxOn = true;
    private boolean autoReloadOn = false;

    private boolean blackWhiteGame = false;

    private Languages language = Languages.English;

    public GameSettings() {
        changeMusic(backgroundMusicType);
    }
    public int getMusicVolume() {
        return musicVolume;
    }

    public BackgroundMusics getBackgroundMusicType() {
        return backgroundMusicType;
    }

    public void setMusicVolume(int musicVolume) {
        this.musicVolume = musicVolume;
        backgroundMusic.setVolume((float) ((float)(musicVolume) / 100.0));
    }
    public void playMonsterMusic() {
        backgroundMusic.setLooping(false);
        backgroundMusic.stop();
        backgroundMusic.dispose();
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(BackgroundMusics.Western.getPath()));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(1);
        backgroundMusic.play();
    }
    public void continuePlaying() {
        backgroundMusic.setLooping(false);
        backgroundMusic.stop();
        backgroundMusic.dispose();
        changeMusic(backgroundMusicType);
    }
    public void changeMusic(BackgroundMusics music) {
        if (backgroundMusic != null){
            backgroundMusic.stop();
            backgroundMusic.dispose();
        }
        backgroundMusicType = music;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(music.getPath()));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume((float) ((float)(musicVolume) / 100.0));
        backgroundMusic.play();
    }
    public boolean isSfxOn() {
        return sfxOn;
    }

    public void setSfxOn(boolean sfxOn) {
        this.sfxOn = sfxOn;
    }

    public InputSettings getInputSettings() {
        return inputSettings;
    }

    public void setInputSettings(InputSettings inputSettings) {
        this.inputSettings = inputSettings;
    }

    public boolean isAutoReloadOn() {
        return autoReloadOn;
    }

    public void setAutoReloadOn(boolean autoReloadOn) {
        this.autoReloadOn = autoReloadOn;
    }

    public boolean isBlackWhiteGame() {
        return blackWhiteGame;
    }

    public void setBlackWhiteGame(boolean blackWhiteGame) {
        this.blackWhiteGame = blackWhiteGame;
    }

    public Languages getLanguage() {
        return language;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }

}
