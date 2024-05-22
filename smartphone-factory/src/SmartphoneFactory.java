import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class SmartphoneFactory extends Thread {

    public static PriorityQueue<Order> queueOfOrders = new PriorityQueue<>(16, new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return 1;
        }
    });

    private int availableProcessors;

    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public SmartphoneFactory(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public static void makeOrder() {
        if (queueOfOrders.size() == 1) {
            produce();
        }
    }

    public static Smartphone produce() {
        queueOfOrders.peek().setStatusOfOrder(Status.CREATING);
        Smartphone producedSmartphone = queueOfOrders.peek().getSmartphone();
        int quatity = queueOfOrders.peek().getQuatityOfSmartphones();
        System.out.println("Your order is producing.");
        while (quatity > 0) {
            try {
                sleep(1000);
                quatity--;
            } catch (InterruptedException e) {
                System.out.println("Something went wrong");
            }
        }
        queueOfOrders.peek().setStatusOfOrder(Status.TERMINATED);
        LocalDateTime dateTmeOfProduced = LocalDateTime.now();
        System.out.println("Order of " + queueOfOrders.peek().getDataTimeOfOrder().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")) +
                " with quantity " + queueOfOrders.peek().getQuatityOfSmartphones()
                + " has been done on " + dateTmeOfProduced.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")));
        queueOfOrders.poll();
        return producedSmartphone;
    }

}