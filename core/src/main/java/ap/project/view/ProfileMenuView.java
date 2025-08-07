package ap.project.view;

import ap.project.model.GameAssetManager;
import ap.project.model.enums.Avatars;
import ap.project.model.enums.Texts;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

public class ProfileMenuView extends Menu implements View {
    public Image avatar;
    public Label usernameLabel;
    public Label scoreLabel;
    public TextButton changeUsernameButton;
    public TextField usernameField;
    public TextButton changePasswordButton;
    public TextField passwordField;
    public TextButton backButton;
    public SelectBox<String> avatarSelector;
    public TextButton deleteUserButton;

    private Image background;
    public ProfileMenuView() {
        super();
        usernameLabel = new Label("", UIUtils.getLabelStyle(50));
        scoreLabel = new Label("", UIUtils.getLabelStyle(30));
        usernameField = new TextField("", skin);
        changeUsernameButton = new TextButton(Texts.ChangeUsername.getText(), skin);
        passwordField = new TextField("", skin);
        changePasswordButton = new TextButton(Texts.ChangePassword.getText(), skin);
        avatarSelector = new SelectBox<>(skin);
        avatarSelector.setItems(Avatars.getNamesList());
        avatar = new Image();
        avatar.setScaling(Scaling.stretch);
        deleteUserButton = new TextButton(Texts.DeleteUser.getText(), skin);
        backButton = new TextButton(Texts.Back.getText(), skin);

    }

    public void drawUI() {
        Table table = new Table();
        table.setFillParent(true);
        table.top();

        Image background = new Image(GameAssetManager.getInstance().simpleBackground);
        background.setFillParent(true);
        background.setScaling(Scaling.stretch);

        float width = stage.getViewport().getWorldWidth();
        float height = stage.getViewport().getWorldHeight();


        table.add(avatar).colspan(2).center().width(height / 5).height(height / 5);
        table.row();
        table.add(usernameLabel).colspan(2).center();
        table.row();
        table.add(scoreLabel).colspan(2).center();
        table.row();
        table.add(usernameField).padTop(height / 14).center().width(width / 4).padRight(width / 20);
        table.add(changeUsernameButton).padTop(height / 14).width(width / 4).center().padLeft(width / 20);
        table.row();
        table.add(passwordField).padTop(height / 14).center().width(width / 4).padRight(width / 20);
        table.add(changePasswordButton).padTop(height / 14).width(width / 4).center().padLeft(width / 20);
        table.row();
        table.add(new Label(Texts.Avatar.getText(), UIUtils.getLabelStyle(40))).padTop(height / 14).colspan(2).center();
        table.row();
        table.add(avatarSelector).padTop(10).colspan(2).center();
        table.row();
        table.add(deleteUserButton).padTop(height / 14).colspan(2).center().width(width / 5).height(height / 10);

        Table backSaveTable = new Table();
        backSaveTable.setFillParent(true);
        backSaveTable.top().left();

        backSaveTable.add(backButton).top().left().pad(40);

        stage.addActor(background);
        stage.addActor(backSaveTable);
        stage.addActor(table);
    }


    public void changeAvatar(Texture avatar) {
        this.avatar.setDrawable(new TextureRegionDrawable(new TextureRegion(avatar)));
    }
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
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
