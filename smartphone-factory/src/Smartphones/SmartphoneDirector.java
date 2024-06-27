package Smartphones;

import java.io.BufferedReader;
import java.io.IOException;

public class SmartphoneDirector {
    private SmartphoneBuilder smartphoneBuilder;

    public SmartphoneDirector(SmartphoneBuilder smartphoneBuilder) {
        this.smartphoneBuilder = smartphoneBuilder;
    }

    public void constructSmartphone(BufferedReader READER) throws IOException {
        smartphoneBuilder.buildSmartphone(READER);
    }

    public Smartphone getSmartphone() {
        return this.smartphoneBuilder.getSmartphone();
    }
}