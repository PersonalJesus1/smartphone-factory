import Smartphones.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;


public class Application {
    public final static BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

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

        //Add order to queue
        SmartphoneFactory.queueOfOrders.offer(new Order(dataTimeOfOrder, Status.CREATED,smartphone, quatityOfSmartphones));
        System.out.println("Currently your order is in the queue, please stand by.");


    }
    public static void main(String[] args) throws IOException {
        SmartphoneFactory smartphoneFactory = new SmartphoneFactory(Runtime.getRuntime().availableProcessors());
        Application application = new Application();
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
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("You have written a wrong information. Try again.");
                }
            } while (true);
        }
    }
}