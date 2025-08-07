package ap.project.view;

import ap.project.model.GameAssetManager;
import ap.project.model.enums.BackgroundMusics;
import ap.project.model.enums.Languages;
import ap.project.model.enums.Texts;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

public class SettingsMenuView extends Menu implements View {
    public Slider musicVolumeSlider;
    public Label musicVolumeLabel;
    public CheckBox isSfxOnCheckBox;
    public SelectBox<String> backgroundMusicSelector;
    public TextButton moveUpButton;
    public TextButton moveDownButton;
    public TextButton moveLeftButton;
    public TextButton moveRightButton;
    public TextButton reloadButton;
    public CheckBox isAutoRealoadOnCheckBox;
    public SelectBox<String> blackWhiteSelector;
    public SelectBox<String> languageSelector;
    public TextButton backButton;

    public SettingsMenuView() {
        super();
        drawUI();
    }

    private void drawUI() {
        float screenWidth = stage.getViewport().getWorldWidth();
        float screenHeight = stage.getViewport().getWorldHeight();

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        Image background = new Image(GameAssetManager.getInstance().simpleBackground);
        background.setFillParent(true);
        background.setScaling(Scaling.stretch);

        musicVolumeSlider = new Slider(0, 100, 1, false, skin);
        musicVolumeLabel = new Label("", UIUtils.getLabelStyle(40));

        backgroundMusicSelector = new SelectBox<>(skin);
        backgroundMusicSelector.setItems(BackgroundMusics.getNamesList());

        isSfxOnCheckBox = new CheckBox(Texts.SfxOn.getText(), skin);
        isAutoRealoadOnCheckBox = new CheckBox(Texts.AutoReload.getText(), skin);

        moveUpButton = new TextButton("", skin);
        moveDownButton = new TextButton("", skin);
        moveLeftButton = new TextButton("", skin);
        moveRightButton = new TextButton("", skin);
        reloadButton = new TextButton("", skin);

        blackWhiteSelector = new SelectBox<>(skin);
        blackWhiteSelector.setItems(Texts.Yes.getText(), Texts.No.getText());

        languageSelector = new SelectBox<>(skin);
        Array<String> langs = new Array<>();
        for(Languages lan : Languages.values())
            langs.add(lan.toString());
        languageSelector.setItems(langs);

        table.add(musicVolumeLabel).colspan(5).center().padTop(screenHeight / 12);
        table.row();
        table.add(musicVolumeSlider).colspan(5).center().width(screenWidth / 3);
        table.row();

        table.add(new Label(Texts.SelectBackgroundMusic.getText(), UIUtils.getLabelStyle(40))).colspan(4).center()
            .padTop(screenHeight / 13);
        table.row();
        table.add(backgroundMusicSelector).colspan(5).center().width(screenWidth/4).padTop(20);
        table.row();

        table.add(isSfxOnCheckBox).colspan(5).center().padTop(screenHeight / 13);
        table.row();

        table.add(new Label(Texts.ChangeKeyboardKeys.getText(), UIUtils.getLabelStyle(40))).colspan(4).center()
            .padTop(screenHeight / 13);
        table.row();

        table.add(new Label(Texts.Up.getText(), UIUtils.getLabelStyle(25))).padTop(screenHeight / 15);
        table.add(new Label(Texts.Down.getText(), UIUtils.getLabelStyle(25))).padTop(screenHeight / 15);
        table.add(new Label(Texts.Left.getText(), UIUtils.getLabelStyle(25))).padTop(screenHeight / 15);
        table.add(new Label(Texts.Right.getText(), UIUtils.getLabelStyle(25))).padTop(screenHeight / 15);
        table.add(new Label(Texts.Reload.getText(), UIUtils.getLabelStyle(25))).padTop(screenHeight / 15);
        table.row();
        table.row();

        table.add(moveUpButton);
        table.add(moveDownButton);
        table.add(moveLeftButton);
        table.add(moveRightButton);
        table.add(reloadButton);
        table.row();

        table.add(isAutoRealoadOnCheckBox).colspan(5).center().padTop(screenHeight / 13);
        table.row();

        table.add(new Label(Texts.Language.getText(), UIUtils.getLabelStyle(30))).colspan(2).center()
            .padTop(screenHeight / 13);
        table.add(new Label(Texts.BlackWhite.getText(), UIUtils.getLabelStyle(30))).colspan(3).center()
            .padTop(screenHeight / 13);
        table.row();

        table.add(languageSelector).colspan(2).center().width(screenWidth/8).padTop(20);
        table.add(blackWhiteSelector).colspan(3).center().width(screenWidth/8).padTop(20);

        Table backSaveTable = new Table();
        backSaveTable.setFillParent(true);
        backSaveTable.top().left();

        backButton = new TextButton(Texts.Back.getText(), skin);

        backSaveTable.add(backButton).top().left().pad(40);

        stage.addActor(background);
        stage.addActor(backSaveTable);
        stage.addActor(table);
    }

    public void setMusicVolume(int volume){
        musicVolumeLabel.setText(Texts.MusicVolume.getText() + ": " + volume);

        musicVolumeSlider.setValue(volume);
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
