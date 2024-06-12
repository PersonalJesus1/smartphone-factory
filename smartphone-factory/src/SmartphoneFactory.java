import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

public class SmartphoneFactory extends Thread {
    ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);

    public void startProduction() {
        executorService.submit(() -> makeOrder());
    }

    public void stopProduction() {
        executorService.shutdown();
    }

    public static PriorityQueue<Order> queueOfOrders = new PriorityQueue<>(16, new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return 1;
        }
    });

    private static int availableProcessors = Runtime.getRuntime().availableProcessors();

    public SmartphoneFactory(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public static int getAvailableProcessors() {
        return availableProcessors;
    }

    public void makeOrder() {
        if (queueOfOrders.size() == 1) {
            produce();
        }
    }

    public void produce() {
        Order orderToMake = queueOfOrders.peek();
        orderToMake.setStatusOfOrder(Status.CREATING);
        int quatity = orderToMake.getQuatityOfSmartphones();
        ArrayList<Smartphone> listSmartphones = new ArrayList<>();
        System.out.println("Your order is producing.");

        // Creating the list of the tasks
        List<Callable<Smartphone>> taskList = new ArrayList<>();
        for (int i = 1; i <= quatity; i++) {
            taskList.add(() -> {
                // process of producing
                Smartphone producedSmartphone = orderToMake.getSmartphone();
                sleep(2000);
                return producedSmartphone;
            });
        }

        try {
            // Sending all tasks to the executorService with threads and getting back list of Future objects
            List<Future<Smartphone>> futures = executorService.invokeAll(taskList);

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
        orderToMake.setStatusOfOrder(Status.TERMINATED);
        LocalDateTime dateTmeOfProduced = LocalDateTime.now();
        System.out.println("Order of " + orderToMake.getDataTimeOfOrder().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")) +
                " with quantity " + orderToMake.getQuatityOfSmartphones()
                + " has been done on " + dateTmeOfProduced.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")));
        queueOfOrders.poll();
    }
}