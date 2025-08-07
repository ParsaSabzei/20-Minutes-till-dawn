package ap.project.controller;

import ap.project.Main;
import ap.project.model.App;
import ap.project.model.Game;
import ap.project.model.enums.GameState;
import ap.project.model.enums.Texts;
import ap.project.screens.*;
import ap.project.view.MainMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuController {
    private MainMenuView view;
    public MainMenuController(MainMenuView view){
        this.view = view;
        Gdx.input.setInputProcessor(view.stage);
        setupListeners();
    }
    private void setupListeners() {
        view.logoutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                logoutButtonClicked(event, x, y);
            }
        });
        view.hintMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                hintButtonClicked(event, x, y);
            }
        });
        view.settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                settingsButtonClicked(event, x, y);
            }
        });
        view.scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                scoreboardButtonClicked(event, x, y);
            }
        });
        view.playSavedGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                playSavedGameButtonClicked(event, x, y);
            }
        });
        view.playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                playButtonClicked(event, x, y);
            }
        });
        view.profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                profileButtonClicked(event, x, y);
            }
        });
    }

    private void profileButtonClicked(InputEvent event, float x, float y) {
        if(App.getInstance().getLoggedInUser().isGuest()){
            view.showAlert(Texts.NotLoggedIn.getText(), Texts.OK.getText());
            return;
        }
        Main.instance.changeScreen(new ProfileMenuScreen());
    }

    private void settingsButtonClicked(InputEvent event, float x, float y) {
        Main.instance.changeScreen(new SettingsMenuScreen());
    }

    private void scoreboardButtonClicked(InputEvent event, float x, float y) {
        Main.instance.changeScreen(new ScoreboardMenuView());
    }

    private void playSavedGameButtonClicked(InputEvent event, float x, float y) {
        if(!Game.getInstance().savedGameExists())
            return;
        Game.getInstance().loadFromFile();
        Game.getInstance().setGameState(GameState.normal);
        if(Game.getInstance().isElderSpawned())
            App.getInstance().getSettings().playMonsterMusic();
        Main.instance.changeScreen(new GameScreen());
    }

    private void playButtonClicked(InputEvent event, float x, float y) {
        Main.instance.changeScreen(new PreGameMenuScreen());
    }

    private void hintButtonClicked(InputEvent event, float x, float y) {
        Main.instance.changeScreen(new HintMenuScreen());
    }

    private void logoutButtonClicked(InputEvent event, float x, float y){
        App.getInstance().setLoggedInUser(null);
        Main.instance.changeScreen(new RegisterMenuScreen());
    }


    public void update(float delta) {

    }
}
