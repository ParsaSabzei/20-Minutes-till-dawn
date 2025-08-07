package ap.project.view;

import ap.project.model.GameAssetManager;
import ap.project.model.enums.BackgroundMusics;
import ap.project.model.enums.Characters;
import ap.project.model.enums.Guns;
import ap.project.model.enums.Texts;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

public class PreGameMenuView extends Menu implements View {

    public Image[] charactersImage = new Image[Characters.values().length];
    public Image[] gunsImage = new Image[Guns.values().length];
    public TextButton[] timesButtons = new TextButton[4];
    public TextButton backButton;
    public Label currentTimeLabel;
    public Label currentGunLabel;
    public Label currentCharactersLabel;

    public TextButton playButton;

    public PreGameMenuView() {
        super();
        drawUI();
    }

    private void drawUI() {
        float width = stage.getViewport().getWorldWidth();
        float height = stage.getViewport().getWorldHeight();

        Table table = new Table();
        table.setFillParent(true);
        table.top();

        Image background = new Image(GameAssetManager.getInstance().simpleBackground);
        background.setFillParent(true);
        background.setScaling(Scaling.stretch);

        currentGunLabel = new Label("", UIUtils.getLabelStyle(50));
        currentTimeLabel = new Label("", UIUtils.getLabelStyle(50));
        currentCharactersLabel = new Label("", UIUtils.getLabelStyle(50));

        playButton = new TextButton(Texts.Play.getText(), skin);

        setChar("");
        setGun("");
        setTime("");

        table.add(new Label(Texts.ChooseCharacterAndGun.getText(), UIUtils.getLabelStyle(50))).colspan(5).center().padTop(height/30);
        table.row();

        for(int i = 0; i < Characters.values().length; i++){
            Characters character = Characters.values()[i];
            charactersImage[i] = new Image(character.getPortrateTexture());
            charactersImage[i].setScaling(Scaling.stretch);
            charactersImage[i].setName(character.getName());
            float aspectRatio = (float)character.getPortrateTexture().getWidth() / character.getPortrateTexture().getHeight();
            table.add(charactersImage[i]).width(width/7).height(width/(7*aspectRatio)).padLeft(width / 40).padRight(width / 40).padTop(height / 20);
        }
        table.row();
        for(int i = 0; i < Characters.values().length; i++){
            Characters character = Characters.values()[i];
            Label label = new Label(character.getName(), UIUtils.getLabelStyle(30));
            table.add(label).padLeft(width / 40).padRight(width / 40).padTop(height / 20);
        }
        table.row();
        for(int i = 0; i < Characters.values().length; i++){
            Characters character = Characters.values()[i];
            Label label = new Label(Texts.HP.getText()+": "+character.getHP() + "  "+Texts.SPEED.getText()+": " +character.getSpeed(), UIUtils.getLabelStyle(30));
            table.add(label).padLeft(width / 40).padRight(width / 40).padTop(height / 40);
        }
        table.row();

        Table gunsTable = new Table();
        gunsTable.top();
        gunsTable.add(new Label(Texts.Guns.getText(), UIUtils.getLabelStyle(30))).colspan(3).center().padTop(height/30);
        gunsTable.row();
        for(int i = 0; i < Guns.values().length; i++){
            Guns gun = Guns.values()[i];
            gunsImage[i] = new Image(gun.getStandTexture());
            gunsImage[i].setName(gun.getName());
            gunsTable.add(gunsImage[i]).width(width/20).height(width/20).padLeft(width / 100).padRight(width / 100);
        }
        gunsTable.row();
        for(int i = 0; i < Guns.values().length; i++){
            Guns gun = Guns.values()[i];
            gunsTable.add(new Label(gun.getName(), UIUtils.getLabelStyle(20))).width(width/20).height(width/20)
                .padLeft(width / 100).padRight(width / 100);
        }
        table.add(gunsTable).colspan(2).center();

        Table timeTable = new Table();
        timeTable.top();
        timeTable.add(new Label(Texts.GameTime.getText(), UIUtils.getLabelStyle(50))).colspan(4).center()
            .padTop(height/25).center();
        timeTable.row();
        for(int i = 0; i < timesButtons.length; i++){
            String time = "";
            if(i == 0)
                time = "2";
            else if(i == 1)
                time = "5";
            else if(i == 2)
                time = "10";
            else if(i == 3)
                time = "20";
            timesButtons[i] = new TextButton(time, skin);
            timeTable.add(timesButtons[i]).pad(height / 20);
        }
        table.add(timeTable).colspan(3).row();

        Table currentsTable = new Table();
        currentsTable.top();
        currentsTable.add(currentCharactersLabel).center().expandX();
        currentsTable.add(currentGunLabel).center().expandX();
        currentsTable.add(currentTimeLabel).center().expandX();
        table.add(currentsTable).colspan(5).fillX().expandX();
        table.row();
        table.add(playButton).width(width / 3).height(height / 12).padTop(height / 30).colspan(5).center();

        Table backSaveTable = new Table();
        backSaveTable.setFillParent(true);
        backSaveTable.top().left();
        backButton = new TextButton(Texts.Back.getText(), skin);
        backSaveTable.add(backButton).top().left().pad(40);

        stage.addActor(background);
        stage.addActor(backSaveTable);

        stage.addActor(table);
    }

    public void setGun(String gunName){
        currentGunLabel.setText(Texts.SelectedGun.getText() + gunName);
    }
    public void setChar(String charName){
        currentCharactersLabel.setText(Texts.SelectedCharacter.getText() + charName);
    }
    public void setTime(String time){
        currentTimeLabel.setText(Texts.SelectedGameTime.getText() + time);
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
