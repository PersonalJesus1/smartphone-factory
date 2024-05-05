import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;


public class Application {
    public final static BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

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
                quatityOfSmartphones = Integer.parseInt(READER.readLine());
                isRight = true;
            } catch (NumberFormatException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        } while (!isRight);

        //name of brand
        System.out.println("Write the name of the brand (for example: APPLE, SAMSUNG, GOOGLE, NOKIA, HONOR, ALCATEL or REALME)");
        nameOfSmartphone = READER.readLine().toUpperCase();
        //model
        isRight = false;
        System.out.println("Write the model");
        modelOfSmartphone = READER.readLine();

        //volume of memory
        do {
            try {
                System.out.println("Write the volume of memory (for example: 64, 128 or 256GB)");
                volumeMemory = Integer.parseInt(READER.readLine());
                isRight = true;
            } catch (NumberFormatException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        } while (!isRight);

        //size of screen
        System.out.println("Write the screenSize (for example:480×800, 640×1136, 720×1280, 750×1334, 1080×1920 or 1440×2560)");
        String screenSize = READER.readLine();

        //Add order to queue
        SmartphoneFactory.makeOrder(new Order(dataTimeOfOrder, "Order has been created",
                new Smartphone(nameOfSmartphone, modelOfSmartphone, volumeMemory, screenSize), quatityOfSmartphones));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SmartphoneFactory smartphoneFactory = new SmartphoneFactory(Runtime.getRuntime().availableProcessors());
        Thread smartphoneFactoryThread = new Thread(smartphoneFactory, "smartphoneFactory");


        try (READER) {
            int choice;
            do {
                System.out.println("1. Make your order. 0. Exit.");
                choice = Integer.parseInt(READER.readLine());
                if (choice == 1) {
                    choiceOfOrder();
                    smartphoneFactoryThread.start();
                    smartphoneFactoryThread.join();
                } else if (choice == 0) break;
            } while (choice != 0);

        }

    }


}

