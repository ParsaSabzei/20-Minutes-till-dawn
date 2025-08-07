package ap.project.view;

public interface View {
    void render(float delta);
    void resize(int width, int height);
    void dispose();
}
