package ap.project.model;

public class Result<T> {
    private boolean success;
    private T data;

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
    public boolean isSuccess() {
        return success;
    }
    @Override
    public String toString() {
        return data.toString();
    }

    public T getData() {
        return data;
    }
}
