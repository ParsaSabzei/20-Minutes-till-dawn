package ap.project;

import ap.project.model.App;
import ap.project.model.FileChooserService;
import ap.project.model.GameAssetManager;
import ap.project.screens.ProfileMenuScreen;
import ap.project.screens.RegisterMenuScreen;
import ap.project.utils.UIUtils;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import org.w3c.dom.ls.LSOutput;

public class Main extends Game {
    public static Main instance;
    private Screen currentScreen;
    public FileChooserService fileChooserService;

    public Main(FileChooserService fileChooserService) {
        instance = this;
        this.fileChooserService = fileChooserService;

    }
    public ShaderProgram grayscaleShader;

    @Override
    public void create() {
        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/default.vert"),
            Gdx.files.internal("shaders/grayscale.frag")
        );

        if (!grayscaleShader.isCompiled()) {
            System.err.println("Shader error: " + grayscaleShader.getLog());
        }

        App.getInstance().loadFromFile();
        GameAssetManager.getInstance().load();
        currentScreen = new RegisterMenuScreen();
        setScreen(currentScreen);

        Pixmap pixmap = UIUtils.textureToPixmap(GameAssetManager.getInstance().cursor);
        Cursor cursor = Gdx.graphics.newCursor(pixmap, 0, 0);
        Gdx.graphics.setCursor(cursor);
        pixmap.dispose();
    }

    @Override
    public void dispose() {
        GameAssetManager.getInstance().dispose();
        super.dispose();
    }

    public void changeScreen(Screen screen){
        currentScreen.dispose();
        currentScreen = screen;
        App.getInstance().saveToFile();
        setScreen(currentScreen);
    }

    public void onFileDropped(FileHandle file) {
        if(screen instanceof ProfileMenuScreen profile) {
            profile.getController().fileReceived(file);
        }
    }
}
