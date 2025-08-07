package ap.project.controller;

import ap.project.model.App;
import ap.project.model.Game;
import ap.project.model.enums.GameState;
import ap.project.view.GameView;
import ap.project.view.PauseView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseController {
    private PauseView view;
    private GameController gameController;
    public PauseController(PauseView view, GameController gameController) {
        this.view = view;
        setupListeners();
        this.gameController = gameController;
    }

    private void setupListeners() {
        view.resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.input.setInputProcessor(gameController);
                Game.getInstance().setGameState(GameState.normal);
            }
        });
        view.blackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.input.setInputProcessor(gameController);
                Game.getInstance().setGameState(GameState.normal);
                App.getInstance().getSettings().setBlackWhiteGame(!App.getInstance().getSettings().isBlackWhiteGame());
            }
        });
        view.giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameController.endGame();
            }
        });
        view.saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                gameController.saveAndQuit();
            }
        });

    }

}
