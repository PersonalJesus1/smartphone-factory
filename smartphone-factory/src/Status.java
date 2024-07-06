
public enum Status {


    CREATED("Order has been created") {},
    CREATING("Order is in work") {},
    TERMINATED("Order has been done") {},
    ;

    String s;
    Status(String s) {
        this.s=s;
    }
    public String getInfo(){
        return s;
    }
}

