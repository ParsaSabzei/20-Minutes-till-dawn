package ap.project.view;

import ap.project.model.Ability;
import ap.project.model.Game;
import ap.project.model.GameAssetManager;
import ap.project.model.enums.Texts;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public class PauseView {
    public Stage pauseStage;

    public PauseView() {
        this.pauseStage = new Stage();
    }

    Skin skin = GameAssetManager.getInstance().uiSkin;

    public TextButton resumeButton = new TextButton(Texts.Resume.getText(), skin);
    public TextButton giveUpButton = new TextButton(Texts.GiveUp.getText(), skin);
    public TextButton saveButton = new TextButton(Texts.SaveAndQuit.getText(), skin);
    public TextButton blackButton = new TextButton(Texts.BlackAndWhite.getText(), skin);

    private Array<TextureRegion> abilityTextures;
    private List<String[]> cheatCodes; // [name, description]

    public void buildUI() {
        abilityTextures = new Array<>();
        for(Ability abb : Game.getInstance().getPlayer().getTotalAbilities()) {
            abilityTextures.add(abb.getType().textureRegion);
        }
        cheatCodes = new ArrayList<>();
        cheatCodes.add(new String[]{Texts.Time.getText(), Texts.PressMReduceTime.getText()});
        cheatCodes.add(new String[]{Texts.Level.getText(), Texts.PressNLevelUp.getText()});
        cheatCodes.add(new String[]{Texts.Heart.getText(), Texts.PressLIncreaseHP.getText()});
        cheatCodes.add(new String[]{Texts.Elder.getText(), Texts.PressPSummonElder.getText()});
        cheatCodes.add(new String[]{Texts.FastShot.getText(), Texts.PressOIncreaseFireRate.getText()});

        Image background = new Image(UIUtils.createTransparentBackground());
        background.setFillParent(true);
        pauseStage.addActor(background);

        Table abilityTable = new Table();

        int abilitiesPerColumn = 4;
        int index = 0;
        while (index < abilityTextures.size) {
            Table column = new Table();
            for (int i = 0; i < abilitiesPerColumn && index < abilityTextures.size; i++, index++) {
                Image abilityImage = new Image(new TextureRegionDrawable(new TextureRegion(abilityTextures.get(index))));
                column.add(abilityImage).size(abilityTextures.get(index).getRegionWidth()*2,
                    abilityTextures.get(index).getRegionHeight()*2).pad(5).row();
            }
            abilityTable.add(column).pad(10);
        }
        Table rightSideTable = new Table();
        rightSideTable.setFillParent(true);

        Table cheatCodeTable = new Table();
        cheatCodeTable.top().left().pad(20);

        Label cheatTitle = new Label(Texts.CheatCodes.getText(), skin, "title");
        cheatCodeTable.add(cheatTitle).padBottom(10).row();

        for (String[] cheat : cheatCodes) {
            Label name = new Label(cheat[0],  UIUtils.getLabelStyle(30));
            Label desc = new Label(cheat[1], UIUtils.getLabelStyle(30));
            cheatCodeTable.add(name).center().row();
            cheatCodeTable.add(desc).center().padBottom(10).row();
        }

        Table buttonTable = new Table();
        buttonTable.top().center();
        buttonTable.defaults().pad(10).width(Gdx.graphics.getWidth()/7f);
        buttonTable.add(resumeButton).row();
        buttonTable.add(giveUpButton).row();
        buttonTable.add(saveButton).row();
        buttonTable.add(blackButton).row();

        rightSideTable.add(buttonTable).expand().center();
        rightSideTable.add(cheatCodeTable).expand().top().left().pad(20).row();

        Table root = new Table();
        root.setFillParent(true);
        root.add(abilityTable).expand().left().padLeft(20);
        root.add(rightSideTable).expand().fill();
        pauseStage.addActor(root);
    }
}
