package Smartphones;

import java.io.BufferedReader;
import java.io.IOException;

public class SmartphoneDirector {
    private SmartphoneBuilder smartphoneBuilder;

    public SmartphoneDirector(SmartphoneBuilder smartphoneBuilder) {
        this.smartphoneBuilder = smartphoneBuilder;
    }

    public void constructSmartphone(BufferedReader reader) throws IOException {
        smartphoneBuilder.buildSmartphone(reader);
    }

    public Smartphone getSmartphone() {
        return this.smartphoneBuilder.getSmartphone();
    }
}