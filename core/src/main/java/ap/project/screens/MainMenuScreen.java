package ap.project.screens;

import ap.project.Main;
import ap.project.controller.MainMenuController;
import ap.project.model.App;
import ap.project.view.MainMenuView;
import com.badlogic.gdx.Screen;

public class MainMenuScreen implements Screen {
    private MainMenuView view;
    private MainMenuController controller;

    public MainMenuScreen(){
        view = new MainMenuView();
        controller = new MainMenuController(view);
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
