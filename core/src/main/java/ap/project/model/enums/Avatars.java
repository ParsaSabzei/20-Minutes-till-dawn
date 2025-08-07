package ap.project.model.enums;

import com.badlogic.gdx.utils.Array;

public enum Avatars {
    Default("Default", "avatars/avatar.png"),
    Jameson("Jameson", "avatars/Jameson.png"),
    Nolan("Nolan", "avatars/Nolan.png"),
    Sawyer("Sawyer", "avatars/Sawyer.png"),
    Custom("Custom", ""),
    ;

    private String name;
    private String path;

    Avatars(String name, String path){
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public static Array<String> getNamesList(){
        Array<String> list = new Array<>();
        for(Avatars bm : Avatars.values())
            list.add(bm.getName());
        return list;
    }

    public static Avatars fromName(String name){
        for(Avatars bm : Avatars.values())
            if(bm.getName().equals(name))
                return bm;
        return Default;
    }
}
