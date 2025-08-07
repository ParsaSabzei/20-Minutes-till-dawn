package ap.project.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class PointAdapter extends TypeAdapter<Point> {
    @Override
    public void write(JsonWriter out, Point point) throws IOException {
        out.beginObject();
        out.name("x").value(point.x);
        out.name("y").value(point.y);
        out.endObject();
    }

    @Override
    public Point read(JsonReader in) throws IOException {
        int x = 0, y = 0;
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("x")) x = in.nextInt();
            else if (name.equals("y")) y = in.nextInt();
        }
        in.endObject();
        return new Point(x, y);
    }
}
