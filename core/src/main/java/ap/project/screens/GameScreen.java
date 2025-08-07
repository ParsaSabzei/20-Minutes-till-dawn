package ap.project.screens;

import ap.project.Main;
import ap.project.controller.GameController;
import ap.project.controller.PreGameMenuController;
import ap.project.controller.RegisterMenuController;
import ap.project.controller.SettingsMenuController;
import ap.project.model.App;
import ap.project.model.Game;
import ap.project.model.GameAssetManager;
import ap.project.model.enums.GameState;
import ap.project.view.GameView;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
    private GameView view;
    private GameController controller;

    public GameScreen(){
        view = new GameView();
        controller = new GameController(view,
            GameAssetManager.getInstance().playersIdleAnimation.get(0).getKeyFrame(0).getRegionWidth(),
            GameAssetManager.getInstance().playersIdleAnimation.get(0).getKeyFrame(0).getRegionHeight(),
            GameAssetManager.getInstance().playerScaleFactor);
    }

    public GameController getController() {
        return controller;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(Game.getInstance().getGameState().equals(GameState.normal))
            controller.update(delta);
        if(App.getInstance().getSettings().isBlackWhiteGame())
            view.batch.setShader(Main.instance.grayscaleShader);
        view.render(delta);
        if(App.getInstance().getSettings().isBlackWhiteGame())
            view.batch.setShader(null);
    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
