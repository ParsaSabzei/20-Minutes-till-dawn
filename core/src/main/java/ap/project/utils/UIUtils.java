package ap.project.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class UIUtils {
    public static Color backgroundColor = Color.valueOf("#08122e");

    public static Drawable coloredDrawable(Color color){
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }
    public static TextureRegionDrawable createTransparentBackground() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.5f);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    public static Label.LabelStyle getLabelStyle(int fontSize){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ChevyRay - Express.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        return labelStyle;
    }
    public static Label.LabelStyle getLabelStyle(int fontSize, Color color){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ChevyRay - Express.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.color = color;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        return labelStyle;
    }
    public static BitmapFont getBitmapFont(int fontSize){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ChevyRay - Express.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSize;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;
    }
    public static Texture createCircularTexture(Texture originalTexture) {
        Pixmap originalPixmap = textureToPixmap(originalTexture);
        int width = originalPixmap.getWidth();
        int height = originalPixmap.getHeight();

        Pixmap maskedPixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        int centerX = width / 2;
        int centerY = height / 2;
        int radius = Math.min(centerX, centerY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = originalPixmap.getPixel(x, y);

                int dx = x - centerX;
                int dy = y - centerY;

                if (dx*dx + dy*dy <= radius*radius) {
                    maskedPixmap.drawPixel(x, y, pixel);
                } else {
                    maskedPixmap.drawPixel(x, y, 0);
                }
            }
        }
        Texture maskedTexture = new Texture(maskedPixmap);
        originalPixmap.dispose();
        maskedPixmap.dispose();
        return maskedTexture;
    }

    public static Pixmap textureToPixmap(Texture texture) {
        texture.getTextureData().prepare();
        return texture.getTextureData().consumePixmap();
    }

}
