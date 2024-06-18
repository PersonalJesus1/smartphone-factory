package Smartphones;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MidTierSmartphone {
    int volumeMemory();
    String screenSize();
    String nameOfSmartphone();
}