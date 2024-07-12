package main.java.Smartphones;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BudgetSmartphone{
    int volumeMemory() ;
    String screenSize();
    String nameOfSmartphone();
}