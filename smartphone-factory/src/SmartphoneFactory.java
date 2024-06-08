import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;


public class SmartphoneFactory extends Thread {

    public static PriorityQueue<Order> queueOfOrders = new PriorityQueue<>(16, new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return 1;
        }
    });

    private static int availableProcessors = Runtime.getRuntime().availableProcessors();

    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public static int getAvailableProcessors() {
        return availableProcessors;
    }

    public SmartphoneFactory(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public static void makeOrder() {
        if (SmartphoneFactory.queueOfOrders.size() == 1) {
            produce();
        }
    }

    public static void produce() {
        queueOfOrders.peek().setStatusOfOrder(Status.CREATING);
        int quatity = queueOfOrders.peek().getQuatityOfSmartphones();
        ArrayList<Smartphone> listSmartphones = new ArrayList<>();
        System.out.println("Your order is producing.");

        // Creating the list of the tasks
        List<Callable<Smartphone>> taskList = new ArrayList<>();
        for (int i = 1; i <= quatity; i++) {
            taskList.add(() -> {
                // process of producing
                Smartphone producedSmartphone = SmartphoneFactory.queueOfOrders.peek().getSmartphone();
                sleep(2000);
                return producedSmartphone;
            });
        }

        try {
            // Sending all tasks to the executorService with threads and getting back list of Future objects
            List<Future<Smartphone>> futures = Application.executorService.invokeAll(taskList);

            // Reforming Future objects to Smartphone
            for (Future<Smartphone> future : futures) {

                try {
                    Smartphone newSmartphone = future.get();
                    listSmartphones.add(newSmartphone);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queueOfOrders.peek().setStatusOfOrder(Status.TERMINATED);
        LocalDateTime dateTmeOfProduced = LocalDateTime.now();
        System.out.println("Order of " + queueOfOrders.peek().getDataTimeOfOrder().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")) +
                " with quantity " + queueOfOrders.peek().getQuatityOfSmartphones()
                + " has been done on " + dateTmeOfProduced.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")));
        queueOfOrders.poll();
    }

}