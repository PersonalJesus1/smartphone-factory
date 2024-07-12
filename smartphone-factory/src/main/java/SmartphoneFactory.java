package main.java;

import main.java.Smartphones.Smartphone;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

public class SmartphoneFactory extends Thread implements Observer {
    public static PriorityQueue<Order> queueOfOrders = new PriorityQueue<>(16, new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return 1;
        }
    });

    private static int availableProcessors = Runtime.getRuntime().availableProcessors();
    private String name;
    private String observerState;
    private Application application;
    ExecutorService executorService = Executors.newFixedThreadPool(availableProcessors);
    FileWriter fileWriter=Application.getFileWriter();
    @Override
    public void update(String state) throws IOException {
        this.observerState = state;
        fileWriter.write("main.java.Observer " + name + " updated with new state: " + observerState+"\n");
        fileWriter.flush();
    }

    public void startProduction() {
        executorService.submit(() -> {
            try {
                makeOrder();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void stopProduction() {
        executorService.shutdown();
    }

    public SmartphoneFactory(String name,int availableProcessors,Application application) {
        this.name=name;
        this.availableProcessors = availableProcessors;
        this.application=application;
    }

    public void setAvailableProcessors(int availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public static int getAvailableProcessors() {
        return availableProcessors;
    }

    public void makeOrder() throws IOException {
        if (queueOfOrders.size() == 1) {
            produce();
        }
    }

    public void produce()  {
        Order orderToMake = queueOfOrders.peek();
        if (orderToMake == null) {
            return;
        }
        try {
            String stateOfOrderBeforeProduce = "Order of " + orderToMake.getDataTimeOfOrder().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                    " with quantity " + orderToMake.getQuatityOfSmartphones()
                    + " has been added to the queue " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + " Status is: " + orderToMake.getStatusOfOrder().getInfo();
            application.sendFinalInfoOfOrder(stateOfOrderBeforeProduce);
            fileWriter.flush();

            orderToMake.setStatusOfOrder(Status.CREATING);
            int quatity = orderToMake.getQuatityOfSmartphones();
            String stateOfOrder = "Order of " + orderToMake.getDataTimeOfOrder().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                    " with quantity " + quatity
                    + " is producing " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + " Status is: " + orderToMake.getStatusOfOrder().getInfo();
            application.sendFinalInfoOfOrder(stateOfOrder);
            fileWriter.flush();

            List<Callable<Smartphone>> taskList = new ArrayList<>();
            for (int i = 1; i <= quatity; i++) {
                taskList.add(() -> {
                    Smartphone producedSmartphone = orderToMake.getSmartphone();
                    Thread.sleep(2000);
                    return producedSmartphone;
                });
            }

            List<Future<Smartphone>> futures = executorService.invokeAll(taskList);
            for (Future<Smartphone> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            orderToMake.setStatusOfOrder(Status.TERMINATED);
            String state = "Order of " + orderToMake.getDataTimeOfOrder().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                    " with quantity " + quatity
                    + " has been done on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                    " Status is: " + orderToMake.getStatusOfOrder().getInfo();
            application.sendFinalInfoOfOrder(state);
            fileWriter.flush();

            queueOfOrders.poll();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeFileWriter() throws IOException {
        fileWriter.close();
    }
}