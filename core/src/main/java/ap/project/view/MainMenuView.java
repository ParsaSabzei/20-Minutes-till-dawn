package ap.project.view;

import ap.project.model.App;
import ap.project.model.GameAssetManager;
import ap.project.model.enums.Texts;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import org.w3c.dom.Text;

public class MainMenuView extends Menu implements View{

    public TextButton playButton;
    public TextButton playSavedGameButton;
    public TextButton scoreboardButton;
    public TextButton settingsButton;
    public TextButton hintMenuButton;
    public TextButton logoutButton;
    public TextButton profileButton;

    private Image abbyIm;

    float time = 0;
    public MainMenuView() {
        super();
        drawUI();
    }

    private void drawUI() {
        float screenWidth = stage.getViewport().getWorldWidth();
        float screenHeight = stage.getViewport().getWorldHeight();

        Table table = new Table();
        Table rightLeafTable = new Table();
        Table leftLeafTable = new Table();


        playButton = new TextButton(Texts.PLAY.getText(), skin);
        playSavedGameButton = new TextButton(Texts.PLAY_SAVED_GAME.getText(), skin);
        scoreboardButton = new TextButton(Texts.SCOREBOARD.getText(), skin);
        settingsButton = new TextButton(Texts.SETTINGS.getText(), skin);
        hintMenuButton = new TextButton(Texts.HINT_MENU.getText(), skin);
        logoutButton = new TextButton(Texts.LOGOUT.getText(), skin);
        profileButton = new TextButton(Texts.PROFILE.getText(), skin);

        Image leftLeaf = new Image(GameAssetManager.getInstance().leftLeaf);
        Image rightLeaf = new Image(GameAssetManager.getInstance().rightLeaf);
        Image logo = new Image(GameAssetManager.getInstance().logo);
        Image avatar = new Image(UIUtils.createCircularTexture(App.getInstance().getUserAvatar()));
        Image background = new Image(GameAssetManager.getInstance().simpleBackground);
        background.setFillParent(true);
        background.setScaling(Scaling.stretch);

        table.setFillParent(true);
        table.top();

        //Images modification
        leftLeaf.setScaling(Scaling.fillY);
        rightLeaf.setScaling(Scaling.fillY);
        logo.setScaling(Scaling.stretch);

        //Leaves tables
        leftLeafTable.setFillParent(true);
        leftLeafTable.top().left();
        leftLeafTable.add(leftLeaf).top().fillY().expandY();
        rightLeafTable.setFillParent(true);
        rightLeafTable.top().right();
        rightLeafTable.add(rightLeaf).top().fillY().expandY();

        table.add(new Label(Texts.WELCOME.getText() + " " + App.getInstance().getLoggedInUser().getUsername(), skin));
        table.add(new Label(Texts.SCORE.getText() + App.getInstance().getLoggedInUser().getScore(), skin ));

        table.add(avatar).right().top().width(screenHeight / 9).height(screenHeight / 9).row();

        table.add(logo).colspan(2).center().row();

        table.add(playButton).padTop(screenHeight / 10).width(screenWidth / 6).height(screenHeight / 13).center();
        table.add().width(screenWidth / 3).row();


        table.add(playSavedGameButton).padTop(40).width(screenWidth / 6).height(screenHeight / 13).center();
        table.add().width(screenWidth / 3).row();

        table.add(scoreboardButton).padTop(40).width(screenWidth / 6).height(screenHeight / 13).center();
        table.add().width(screenWidth / 3).row();

        table.add(settingsButton).padTop(40).width(screenWidth / 6).height(screenHeight / 13).center();
        table.add().width(screenWidth / 3).row();

        table.add(hintMenuButton).padTop(40).width(screenWidth / 6).height(screenHeight / 13).center();
        table.add().width(screenWidth / 3).row();

        table.add(logoutButton).padTop(40).width(screenWidth / 6).height(screenHeight / 13).center();
        table.add().width(screenWidth / 3).row();

        Table profileButtonTable = new Table();
        profileButtonTable.setFillParent(true);
        profileButtonTable.top().right();
        profileButtonTable.add(profileButton).pad(40);

        abbyIm = new Image(GameAssetManager.getInstance().abby);
        abbyIm.setScaling(Scaling.stretch);
        float aspectRatio = abbyIm.getWidth() / abbyIm.getHeight();

        Table abby = new Table();
        abby.setFillParent(true);
        abby.add(abbyIm).padRight(screenWidth / 5).width(screenWidth / 3.4f).
            height(screenWidth / (3.4f * aspectRatio)).padTop(screenHeight/3);
        abby.center().right();

        Stack stack = new Stack();
        stack.setFillParent(true);
        stack.add(table);
        stack.add(leftLeafTable);
        stack.add(rightLeafTable);

        stage.addActor(background);

        stage.addActor(stack);

        stage.addActor(profileButtonTable);
        stage.addActor(abby);

    }

    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        time += delta;
        float period = 5, maxYChange = 1; // in seconds

        float difY = (float) Math.sin(2 * Math.PI * time / period) * maxYChange;
        abbyIm.setY(abbyIm.getY() + difY);
        stage.act(delta);
        stage.draw();
    }
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    public void dispose() {
        stage.dispose();
    }
}
