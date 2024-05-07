import java.io.IOException;
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

    public static void makeOrder(Order order) {
        queueOfOrders.offer(order);
    }

    public static void choiceOfOrder() throws IOException {

        LocalDateTime dataTimeOfOrder = LocalDateTime.now();
        int quatityOfSmartphones = 0;
        String nameOfSmartphone = null;
        String modelOfSmartphone;
        int volumeMemory = 0;
        boolean isRight = false;

        //quantity of smartphones
        do {
            try {
                System.out.println("Write the quantity of smartphones");
                quatityOfSmartphones = Integer.parseInt(Application.READER.readLine());
                isRight = true;
            } catch (NumberFormatException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        } while (!isRight);

        //name of brand
        System.out.println("Write the name of the brand (for example: APPLE, SAMSUNG, GOOGLE, NOKIA, HONOR, ALCATEL or REALME)");
        nameOfSmartphone = Application.READER.readLine().toUpperCase();

        //model
        isRight = false;
        System.out.println("Write the model");
        modelOfSmartphone = Application.READER.readLine();

        //volume of memory
        do {
            try {
                System.out.println("Write the volume of memory (for example: 64, 128 or 256GB)");
                volumeMemory = Integer.parseInt(Application.READER.readLine());
                isRight = true;
            } catch (NumberFormatException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        } while (!isRight);

        //size of screen
        System.out.println("Write the screenSize (for example:480×800, 640×1136, 720×1280, 750×1334, 1080×1920 or 1440×2560)");
        String screenSize = Application.READER.readLine();

        //Add order to queue
        SmartphoneFactory.makeOrder(new Order(dataTimeOfOrder, Status.CREATED,
                new Smartphone(nameOfSmartphone, modelOfSmartphone, volumeMemory, screenSize), quatityOfSmartphones));
    }

    public static Smartphone produce() {
        queueOfOrders.peek().setStatusOfOrder(Status.CREATING);
        Smartphone producedSmartphone = queueOfOrders.peek().getSmartphone();
        int quatity = queueOfOrders.peek().getQuatityOfSmartphones();

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