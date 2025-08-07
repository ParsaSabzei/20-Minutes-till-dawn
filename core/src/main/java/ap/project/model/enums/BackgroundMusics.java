package ap.project.model.enums;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

public enum BackgroundMusics {
    Default("Default", "musics/Default Music.wav", true),
    Music2("Music2", "musics/Music 2.mp3", true),
    Western("Western", "musics/wasteland.wav", false),
    ;

    private String name;
    private String path;
    private boolean show;

    BackgroundMusics(String name, String path, boolean show){
        this.name = name;
        this.path = path;
        this.show = show;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public static Array<String> getNamesList(){
        Array<String> list = new Array<>();
        for(BackgroundMusics bm : BackgroundMusics.values())
            if(bm.show)
                list.add(bm.getName());
        return list;
    }

    public static BackgroundMusics fromName(String name){
        for(BackgroundMusics bm : BackgroundMusics.values())
            if(bm.getName().equals(name))
                return bm;
        return Default;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
