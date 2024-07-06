import Smartphones.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Application implements Subject {
    public final static BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private List<Observer> observers = new ArrayList<>();
    private String state;

    static File fileOfResults = new File("fileOfResults.txt");
    private static FileWriter fileWriter;

    static {
        try {
            fileWriter = new FileWriter(fileOfResults, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileWriter getFileWriter() {
        return fileWriter;
    }

    public void choiceOfOrder() throws IOException {
        LocalDateTime dataTimeOfOrder = LocalDateTime.now();
        int quatityOfSmartphones = 0;
        boolean isRight = false;

        //quantity of smartphones
        do {
            try {
                System.out.println("Write the quantity of smartphones:");
                quatityOfSmartphones = Integer.parseInt(READER.readLine());
                isRight = true;
            } catch (NumberFormatException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        } while (!isRight);

        //Building Smartphones
        SmartphoneBuilder builder = new RealSmartphoneBuilder();
        SmartphoneDirector director = new SmartphoneDirector(builder);
        director.constructSmartphone(READER);
        Smartphone smartphone = director.getSmartphone();
        Order MadeOrder=new Order(dataTimeOfOrder, Status.CREATED, smartphone, quatityOfSmartphones);

        //Add order to queue
        SmartphoneFactory.queueOfOrders.offer(MadeOrder);
        String stateOfOrder = "Order of " + MadeOrder.getDataTimeOfOrder().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm")) +
                " with quantity " + MadeOrder.getQuatityOfSmartphones()
                + " has been added to the queue " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"))+ " Status is: "+MadeOrder.getStatusOfOrder().getInfo();
        sendFinalInfoOfOrder(stateOfOrder);
        System.out.println("Currently your order is in the queue, please stand by.");
    }

    //methods for subject
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        notifyObservers();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        try {
            for (Observer observer : observers) {
                observer.update(state);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFinalInfoOfOrder(String FinalInfoOfOrder) {
        setState(FinalInfoOfOrder);
    }

    public static void main(String[] args) throws IOException {
        Application application = new Application(); // subject
        SmartphoneFactory smartphoneFactory = new SmartphoneFactory("SmartphoneFactory1", Runtime.getRuntime().availableProcessors(),
                application); //observer
        application.attach(smartphoneFactory);
        try (READER) {
            int choice;
            do {
                try {
                    System.out.println("1. Make your order. 0. Exit.");
                    choice = Integer.parseInt(READER.readLine());
                    if (choice == 1) {
                        application.choiceOfOrder(); //get information,add to queue
                        smartphoneFactory.startProduction(); //check the queue, produce
                    } else if (choice == 0) {
                        smartphoneFactory.stopProduction();
                        fileWriter.close();
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("You have written a wrong information. Try again.");
                }
            } while (true);
        }
    }
}