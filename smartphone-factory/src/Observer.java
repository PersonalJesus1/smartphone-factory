import java.io.IOException;

public interface Observer {
    void update(String state) throws IOException;
}
