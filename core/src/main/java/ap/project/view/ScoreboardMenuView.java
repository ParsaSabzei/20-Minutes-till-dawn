package ap.project.view;

import ap.project.model.App;
import ap.project.model.GameAssetManager;
import ap.project.model.User;
import ap.project.model.enums.Texts;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardMenuView extends Menu implements View {
    public TextButton backButton;
    public TextButton sortByUsername;
    public TextButton sortByKills;
    public TextButton sortByScore;
    public TextButton sortBySurvival;

    public List<User> topPlayers = new ArrayList<>();

    public ScoreboardMenuView() {
        super();
        backButton = new TextButton(Texts.Back.getText(), skin);
        sortByUsername = new TextButton(Texts.ByUsername.getText(), skin);
        sortByKills = new TextButton(Texts.ByKills.getText(), skin);
        sortByScore = new TextButton(Texts.ByScore.getText(), skin);
        sortBySurvival = new TextButton(Texts.BySurvival.getText(), skin);
    }

    public void drawUI() {
        Table scoreboardTable = new Table();
        scoreboardTable.setFillParent(true);
        scoreboardTable.top().padTop(20);

        Label title = new Label(Texts.TopPlayers.getText(), skin, "title");
        scoreboardTable.add(title).colspan(4).center().padBottom(20).row();

        scoreboardTable.add(new Label(Texts.Username.getText()+":", skin, "default")).pad(10);
        scoreboardTable.add(new Label(Texts.ByScore.getText(), skin, "default")).pad(10);
        scoreboardTable.add(new Label(Texts.ByKills.getText(), skin, "default")).pad(10);
        scoreboardTable.add(new Label(Texts.BySurvival.getText(), skin, "default")).pad(10);
        scoreboardTable.row();

        for (int i = 0; i < topPlayers.size(); i++) {
            User p = topPlayers.get(i);

            boolean isTop3 = (i < 3);
            boolean isLoggedInUser = p.getUsername().equals(App.getInstance().getLoggedInUser().getUsername());

            Color rowColor = Color.WHITE;
            if (isTop3) rowColor = new Color(1f, 0.9f, 0.6f, 1f);
            if (isLoggedInUser) rowColor = new Color(0.6f, 0.8f, 1f, 1f);

            Label nameLabel = new Label(p.getUsername(), skin);
            nameLabel.setColor(rowColor);
            Label scoreLabel = new Label(String.valueOf(p.getScore()), skin);
            scoreLabel.setColor(rowColor);
            Label killsLabel = new Label(String.valueOf(p.getTotalKillCount()), skin);
            killsLabel.setColor(rowColor);
            Label survivalLabel = new Label(String.format("%.1f s", p.getTotalTimeSurvived()), skin);
            survivalLabel.setColor(rowColor);

            scoreboardTable.add(nameLabel).pad(5);
            scoreboardTable.add(scoreLabel).pad(5);
            scoreboardTable.add(killsLabel).pad(5);
            scoreboardTable.add(survivalLabel).pad(5);
            scoreboardTable.row();
        }

        Table sortButtons = new Table();
        sortButtons.padTop(30);
        sortButtons.defaults().pad(10).width(stage.getViewport().getWorldWidth()/8f);

        scoreboardTable.add(new Label(Texts.Sort.getText(), UIUtils.getLabelStyle(60))).colspan(4).center().padTop(stage.getViewport().getWorldHeight()/5f);
        scoreboardTable.row();

        sortButtons.add(sortByUsername).pad(10);
        sortButtons.add(sortByKills).pad(10);
        sortButtons.add(sortByScore).pad(10);
        sortButtons.add(sortBySurvival).pad(10);

        scoreboardTable.add(sortButtons).colspan(4).center().padTop(30).row();

        Image background = new Image(GameAssetManager.getInstance().simpleBackground);
        background.setFillParent(true);
        background.setScaling(Scaling.stretch);

        Table backSaveTable = new Table();
        backSaveTable.setFillParent(true);
        backSaveTable.top().left();

        backSaveTable.add(backButton).top().left().pad(40);

        stage.addActor(background);
        stage.addActor(backSaveTable);
        stage.addActor(scoreboardTable);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void dispose() {}
}
