package ap.project.view;

import ap.project.model.App;
import ap.project.model.GameAssetManager;
import ap.project.model.enums.Abilities;
import ap.project.model.enums.Characters;
import ap.project.model.enums.Texts;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class HintMenuView extends Menu implements View {
    public TextButton backButton;

    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    public HintMenuView() {
        super();
        drawUI();
    }

    private void drawUI() {
        float height = stage.getViewport().getWorldHeight();
        Table table = new Table();
        table.setFillParent(true);

        table.add(new Label(Texts.HEROS.getText(), UIUtils.getLabelStyle(60))).padBottom(20).expandX().center();
        table.row();
        for (Characters character : Characters.values()) {
            table.add(new Label(character.getName() + ":  " + Texts.HP.getText() + ":" + character.getHP() + ",  " + Texts.SPEED.getText() + ":" + character.getSpeed(),
                UIUtils.getLabelStyle(35))).padBottom(10);
            table.row();
        }
        table.row();
        table.add(new Label(Texts.KEY_SETTINGS.getText(), UIUtils.getLabelStyle(60))).padTop(height / 20).padBottom(20).expandX().center();
        table.row();
        table.add(new Label(
            Texts.KEY_UP.getText() + ": " + Input.Keys.toString(App.getInstance().getSettings().getInputSettings().keyUp) +
                "   " + Texts.KEY_DOWN.getText() + ": " + Input.Keys.toString(App.getInstance().getSettings().getInputSettings().keyDown) +
                "  " + Texts.KEY_LEFT.getText() + ": " + Input.Keys.toString(App.getInstance().getSettings().getInputSettings().keyLeft) +
                "  " + Texts.KEY_RIGHT.getText() + ": " + Input.Keys.toString(App.getInstance().getSettings().getInputSettings().keyRight) +
                "   " + Texts.RELOAD.getText() + ": " + Input.Keys.toString(App.getInstance().getSettings().getInputSettings().reload),
            UIUtils.getLabelStyle(40)));
        table.row();
        table.add(new Label(Texts.CHEAT_CODES.getText(), UIUtils.getLabelStyle(60))).padTop(height / 20).padBottom(20).expandX().center();
        table.row();
        List<String> cheatCodes = new ArrayList<>();
        cheatCodes.add(Texts.CHEAT_M.getText());
        cheatCodes.add(Texts.CHEAT_N.getText());
        cheatCodes.add(Texts.CHEAT_L.getText());
        cheatCodes.add(Texts.CHEAT_P.getText());
        cheatCodes.add(Texts.CHEAT_O.getText());
        for (String cheatCode : cheatCodes) {
            table.add(new Label(cheatCode, UIUtils.getLabelStyle(35))).padBottom(10);
            table.row();
        }
        table.add(new Label(Texts.ABILITIES.getText(), UIUtils.getLabelStyle(60))).padTop(height / 20).padBottom(20).expandX().center();
        table.row();
        for (Abilities abb : Abilities.values()) {
            table.add(new Label(abb.name() + ": " + abb.description, UIUtils.getLabelStyle(30))).padBottom(10);
            table.row();
        }

        Image background = new Image(GameAssetManager.getInstance().simpleBackground);
        background.setFillParent(true);
        background.setScaling(Scaling.stretch);

        Table backSaveTable = new Table();
        backSaveTable.setFillParent(true);
        backSaveTable.top().left();
        backButton = new TextButton(Texts.BACK.getText(), skin);
        backSaveTable.add(backButton).top().left().pad(40);

        stage.addActor(background);
        stage.addActor(backSaveTable);
        stage.addActor(table);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
    }
}
