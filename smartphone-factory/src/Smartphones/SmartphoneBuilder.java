package Smartphones;

import java.io.BufferedReader;
import java.io.IOException;

public interface SmartphoneBuilder {
    void buildSmartphone(BufferedReader reader) throws IOException;
      Smartphone getSmartphone();
}