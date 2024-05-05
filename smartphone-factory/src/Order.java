import java.time.LocalDateTime;

public class Order {
    private static LocalDateTime dataTimeOfOrder;
    private String statusOfOrder; //Reference for the future: Order has been created, in work, has been done
    private Smartphone smartphone;
    private static int quatityOfSmartphones;
    public  void setDataTimeOfOrder(LocalDateTime dataTimeOfOrder) {
        this.dataTimeOfOrder = dataTimeOfOrder;
    }
    public void setStatusOfOrder(String statusOfOrder) {
        this.statusOfOrder = statusOfOrder;
    }

    public void setSmartphone(Smartphone smartphone) {
        this.smartphone = smartphone;
    }

    public  void setQuatityOfSmartphones(int quatityOfSmartphones) {
        this.quatityOfSmartphones = quatityOfSmartphones;
    }

    public static LocalDateTime getDataTimeOfOrder() {
        return dataTimeOfOrder;
    }

    public String getStatusOfOrder() {
        return statusOfOrder;
    }

    public Smartphone getSmartphone() {
        return smartphone;
    }

    public static int getQuatityOfSmartphones() {
        return quatityOfSmartphones;
    }


    public Order(LocalDateTime dataTimeOfOrder, String statusOfOrder, Smartphone smartphone, int quatityOfSmartphones) {
        this.dataTimeOfOrder = dataTimeOfOrder;
        this.statusOfOrder = statusOfOrder;
        this.smartphone = smartphone;
        this.quatityOfSmartphones = quatityOfSmartphones;
    }


    }


