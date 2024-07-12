package main.java;

public enum Status {


    CREATED("main.java.Order has been created") {},
    CREATING("main.java.Order is in work") {},
    TERMINATED("main.java.Order has been done") {},
    ;

    String s;
    Status(String s) {
        this.s=s;
    }
    public String getInfo(){
        return s;
    }
}

