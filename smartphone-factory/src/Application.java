import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    public final static BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, InterruptedException {
        SmartphoneFactory smartphoneFactory = new SmartphoneFactory(Runtime.getRuntime().availableProcessors());

        try (READER) {
            int choice;
            do {
                System.out.println("1. Make your order. 0. Exit.");
                choice = Integer.parseInt(READER.readLine());
                if (choice == 1) {
                    boolean isEmpty = SmartphoneFactory.queueOfOrders.isEmpty();
                    smartphoneFactory.choiceOfOrder();
                    if (isEmpty) {
                        smartphoneFactory.produce();
                        smartphoneFactory.join();
                    }

                } else if (choice == 0) break;
            } while (choice != 0);
        }
    }
}

