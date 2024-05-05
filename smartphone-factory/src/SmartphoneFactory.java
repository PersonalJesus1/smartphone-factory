import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.lang.Thread.sleep;

public class SmartphoneFactory implements Runnable {
    public int availableProcessors;

    public SmartphoneFactory(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public static List<Order> queueOfOrders = new LinkedList<>();

    public static Smartphone produce() {
        return new Smartphone(queueOfOrders.get(0).getSmartphone().getNameOfSmartphone(),
                queueOfOrders.get(0).getSmartphone().getModelOfSmartphonel(),
                queueOfOrders.get(0).getSmartphone().getVolumeMemory(),
                queueOfOrders.get(0).getSmartphone().getScreenSize());
    }

    public static void makeOrder(Order order) {
        queueOfOrders.add(order);
    }

    @Override
    public void run() {
        int quatity = queueOfOrders.get(0).getQuatityOfSmartphones();

        while (quatity > 0) {
            try {
                Smartphone smartphone = produce();
                sleep(1000);
                quatity--;
            } catch (InterruptedException e) {
                System.out.println("Something went wrong");
            }
        }
        queueOfOrders.remove(0);
        LocalDateTime dateTmeOfProduced = LocalDateTime.now();
        System.out.println("Order of " + Order.getDataTimeOfOrder().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")) + " with quantity " + Order.getQuatityOfSmartphones()
                + " has been done on " + dateTmeOfProduced.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")));


    }
}




