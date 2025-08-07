package ap.project.lwjgl3;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import ap.project.model.FileChooserCallback;
import ap.project.model.FileChooserService;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.awt.*;
import java.io.File;

public class DesktopFileChoose implements FileChooserService {

    @Override
    public void chooseImageFileAsync(FileChooserCallback callback) {
        new Thread(() -> {
            try {
                FileDialog dialog = new FileDialog((Frame) null, "Choose an avatar", FileDialog.LOAD);
                dialog.setFilenameFilter((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg"));
                dialog.setVisible(true);

                if (dialog.getFile() != null) {
                    String path = dialog.getDirectory() + dialog.getFile();
                    File file = new File(path);
                    Gdx.app.postRunnable(() ->
                        callback.onFileChosen(Gdx.files.absolute(file.getAbsolutePath()))
                    );
                } else {
                    Gdx.app.postRunnable(callback::onCancel);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Gdx.app.postRunnable(callback::onCancel);
            }
        }).start();
    }
}
