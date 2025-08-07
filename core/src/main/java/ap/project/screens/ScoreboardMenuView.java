package ap.project.screens;

import ap.project.Main;
import ap.project.controller.HintMenuController;
import ap.project.controller.MainMenuController;
import ap.project.controller.ScoreboardMenuController;
import ap.project.model.App;
import ap.project.view.HintMenuView;
import ap.project.view.MainMenuView;
import com.badlogic.gdx.Screen;

public class ScoreboardMenuView implements Screen {
    private ap.project.view.ScoreboardMenuView view;
    private ScoreboardMenuController controller;

    public ScoreboardMenuView(){
        view = new ap.project.view.ScoreboardMenuView();
        controller = new ScoreboardMenuController(view);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
