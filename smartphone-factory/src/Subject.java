import java.io.IOException;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers() throws IOException;

}
