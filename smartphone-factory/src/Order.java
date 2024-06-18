import Smartphones.Smartphone;
import java.time.LocalDateTime;

public class Order {

    private LocalDateTime dataTimeOfOrder;
    private Status statusOfOrder;
    private Smartphone smartphone;
    private int quatityOfSmartphones;

    public void setDataTimeOfOrder(LocalDateTime dataTimeOfOrder) {
        this.dataTimeOfOrder = dataTimeOfOrder;
    }

    public void setStatusOfOrder(Status statusOfOrder) {
        this.statusOfOrder = statusOfOrder;
    }

    public void setSmartphone(Smartphone smartphone) {
        this.smartphone = smartphone;
    }

    public void setQuatityOfSmartphones(int quatityOfSmartphones) {
        this.quatityOfSmartphones = quatityOfSmartphones;
    }

    public LocalDateTime getDataTimeOfOrder() {
        return dataTimeOfOrder;
    }

    public Status getStatusOfOrder() {
        return statusOfOrder;
    }

    public Smartphone getSmartphone() {
        return smartphone;
    }

    public int getQuatityOfSmartphones() {
        return quatityOfSmartphones;
    }


    public Order(LocalDateTime dataTimeOfOrder, Status statusOfOrder, Smartphone smartphone, int quatityOfSmartphones) {
        this.dataTimeOfOrder = dataTimeOfOrder;
        this.statusOfOrder = statusOfOrder;
        this.smartphone = smartphone;
        this.quatityOfSmartphones = quatityOfSmartphones;
    }

}


