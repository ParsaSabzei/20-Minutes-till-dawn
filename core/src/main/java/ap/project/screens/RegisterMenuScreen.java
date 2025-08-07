package ap.project.screens;

import ap.project.Main;
import ap.project.controller.RegisterMenuController;
import ap.project.model.App;
import ap.project.view.RegisterMenuView;
import com.badlogic.gdx.Screen;

public class RegisterMenuScreen implements Screen {
    private RegisterMenuView view;
    private RegisterMenuController controller;

    public RegisterMenuScreen(){
        view = new RegisterMenuView();
        controller = new RegisterMenuController(view);
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
