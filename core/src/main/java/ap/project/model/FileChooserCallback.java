package ap.project.model;

import com.badlogic.gdx.files.FileHandle;

public interface FileChooserCallback {
    void onFileChosen(FileHandle file);
    void onCancel();
}
