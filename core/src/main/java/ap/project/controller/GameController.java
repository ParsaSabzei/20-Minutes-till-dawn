package ap.project.controller;

import ap.project.Main;
import ap.project.model.App;
import ap.project.model.Bullet;
import ap.project.model.Game;
import ap.project.model.enums.BackgroundMusics;
import ap.project.model.enums.GameState;
import ap.project.screens.*;
import ap.project.view.GameView;
import ap.project.view.MainMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameController implements InputProcessor {
    private PlayerController playerController;
    private GunController gunController;
    private MonsterController monsterController;
    private AbilityController abilityController;
    private PauseController pauseController;
    public EndGameController endGameController;
    private GameView view;

    public GameController(GameView view, float playerWidth, float playerHeight, float scale) {
        this.view = view;
        Gdx.input.setInputProcessor(this);
        pauseController = new PauseController(view.pauseView, this);
        playerController = new PlayerController(playerWidth, playerHeight, scale,view);
        gunController = new GunController(view);
        monsterController = new MonsterController(view);
        abilityController = new AbilityController(view, this);
        endGameController = new EndGameController(view, this);
    }


    public void update(float delta) {
        if(!Game.getInstance().getGameState().equals(GameState.normal)) {
            return;
        }
        if(Game.getInstance().getPlayer().getHP() <= 0){
            endGame();
            return;
        }
        if(Game.getInstance().getTimeElapsed() > Game.game.getTimeControl()*60) {
            endGame();
            return;
        }
        Game.getInstance().advanceTime(delta);
        playerController.update(delta);
        gunController.update(delta);
        monsterController.update(delta);
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.SPACE) {
            Game.getInstance().setAutoAim(!Game.getInstance().isAutoAim());
        }
        if(keycode == Input.Keys.ESCAPE && Game.getInstance().getGameState() == GameState.normal) {
            pauseGame();
        }

        if(!Game.getInstance().getGameState().equals(GameState.normal)) {
            return false;
        }
        if(keycode == App.getInstance().getSettings().getInputSettings().reload) {
            Game.getInstance().getPlayer().getGun().reload();
        }
        if(keycode == Input.Keys.M) {
            Game.getInstance().advanceTime(60);
        }
        if(keycode == Input.Keys.N) {
            playerController.advanceLevel();
        }
        if(keycode == Input.Keys.L) {
            if (Game.getInstance().getPlayer().getHP() < Game.getInstance().getPlayer().getMaxHP()) {
                Game.getInstance().getPlayer().setHP(Game.getInstance().getPlayer().getHP() + 1);
            }
        }
        if(keycode == Input.Keys.P) {
            monsterController.setAllowElder();
            System.out.println("hello");
        }
        if(keycode == Input.Keys.O) {
            Game.getInstance().getPlayer().getGun().setFireRate(0);
        }
        return false;
    }

    public void pauseGame() {
        Gdx.input.setInputProcessor(view.pauseView.pauseStage);
        view.buildPauseMenu();
        Game.getInstance().setGameState(GameState.pause);
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button != 0)
            return false;
        gunController.shot();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return true;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public void endGame() {
        endGameController.sendData();
        view.endGameView.buildUI();
        Game.getInstance().setGameState(GameState.winOrLose);
        Gdx.input.setInputProcessor(view.endGameView.stage);
        Game.getInstance().deleteSavedGame();
    }

    public void saveAndQuit() {
        Game.getInstance().saveToFile();
        Game.getInstance().clearGame();
        Main.instance.changeScreen(new MainMenuScreen());
        App.getInstance().getSettings().continuePlaying();
    }
}
