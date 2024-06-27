package Smartphones;

import java.io.BufferedReader;
import java.io.IOException;

public interface SmartphoneBuilder {
    void buildSmartphone(BufferedReader READER) throws IOException;
      Smartphone getSmartphone();
}