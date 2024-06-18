package Smartphones;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FlagshipSmartphone {
    int volumeMemory();
    String screenSize();
    String nameOfSmartphone();
}