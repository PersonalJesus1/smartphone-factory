import Smartphones.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;


public class Application {
    public final static BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static void choiceOfOrder() throws IOException {
        LocalDateTime dataTimeOfOrder = LocalDateTime.now();
        int quatityOfSmartphones = 0;
        int categoryChoice = 0;
        boolean isRight = false;
        String modelOfSmartphone = "";
        int volumeMemory = 0;
        String screenSize = "";
        String nameOfSmartphone = null;

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

        // choice of BudgetSmartphone, MidTierSmartphone, FlagshipSmartphone
        isRight = false;
        Class<? extends Annotation> categoryClass = null;
        do {
            try {
                System.out.println("Choose category (number) by the price: 1. Budget (up to 250£), 2. MidTier (up to 450£), 3. Flagship (up to 900£).");
                categoryChoice = Integer.parseInt(READER.readLine());
                switch (categoryChoice) {
                    case 1:
                        categoryClass = BudgetSmartphone.class;
                        break;
                    case 2:
                        categoryClass = MidTierSmartphone.class;
                        break;
                    case 3:
                        categoryClass = FlagshipSmartphone.class;
                        break;
                    default:
                        System.out.println("Invalid category choice");
                }
                isRight = true;
            } catch (IllegalArgumentException e) {
                System.out.println("You have written wrong information. Try again.");
            }
        } while (!isRight);

        //choice of the type of phone according to budget
        isRight = false;
        if (categoryClass.equals(BudgetSmartphone.class)) {
            do {
                try {
                    System.out.println("Choose country-producer (number): 1. China, 2. India");
                    int choiceOfType = Integer.parseInt(READER.readLine());
                    switch (choiceOfType) {
                        //China
                        case 1:
                            NoNameChinaSmartphone objectChina = new NoNameChinaSmartphone();
                            Class<?> classChina = objectChina.getClass();
                            // Getting annotation
                            if (classChina.isAnnotationPresent(BudgetSmartphone.class)) {
                                BudgetSmartphone annotation = classChina.getAnnotation(BudgetSmartphone.class);
                                //Reading values from the annotation
                                volumeMemory = annotation.volumeMemory();
                                screenSize = annotation.screenSize();
                                nameOfSmartphone = annotation.nameOfSmartphone();
                                modelOfSmartphone = choiceOfChinaModel();
                                isRight = true;
                                break;
                            } else {
                                System.out.println("Something went wrong.");
                            }

                            //India
                        case 2:
                            NoNameIndiaSmartphone objectIndia = new NoNameIndiaSmartphone();
                            Class<?> classIndia = objectIndia.getClass();
                            // Getting annotation
                            if (classIndia.isAnnotationPresent(BudgetSmartphone.class)) {
                                BudgetSmartphone annotation = classIndia.getAnnotation(BudgetSmartphone.class);
                                //Reading values from the annotation
                                volumeMemory = annotation.volumeMemory();
                                screenSize = annotation.screenSize();
                                nameOfSmartphone = annotation.nameOfSmartphone();
                                modelOfSmartphone = choiceOfIndiaModel();
                                isRight = true;
                                break;
                            } else {
                                System.out.println("Something went wrong.");
                            }
                        default:
                            throw new IllegalArgumentException("Invalid category choice");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("You have written a wrong information. Try again.");
                }
            } while (!isRight);
        } else if (categoryClass.equals(MidTierSmartphone.class)) {
            TaiwanSmartphone objectTaiPhone = new TaiwanSmartphone();
            Class<?> classTai = objectTaiPhone.getClass();
            // Getting annotation
            if (classTai.isAnnotationPresent(MidTierSmartphone.class)) {
                MidTierSmartphone annotation = classTai.getAnnotation(MidTierSmartphone.class);
                //Reading values from the annotation
                volumeMemory = annotation.volumeMemory();
                screenSize = annotation.screenSize();
                nameOfSmartphone = annotation.nameOfSmartphone();
                modelOfSmartphone = choiceOfTaiModel();
            } else {
                System.out.println("Something went wrong.");
            }
        } else if (categoryClass.equals(FlagshipSmartphone.class)) {
            do {
                try {
                    System.out.println("Choose country-producer (number): 1. USA, 2. Korea");
                    int choiceOfType = Integer.parseInt(READER.readLine());
                    switch (choiceOfType) {
                        //USA
                        case 1:
                            TopUsaSmartphone objectUsaPhone = new TopUsaSmartphone();
                            Class<?> classUsaPhone = objectUsaPhone.getClass();
                            // Getting annotation
                            if (classUsaPhone.isAnnotationPresent(FlagshipSmartphone.class)) {
                                FlagshipSmartphone annotation = classUsaPhone.getAnnotation(FlagshipSmartphone.class);
                                //Reading values from the annotation
                                volumeMemory = annotation.volumeMemory();
                                screenSize = annotation.screenSize();
                                nameOfSmartphone = annotation.nameOfSmartphone();
                                modelOfSmartphone = choiceOfUSAModel();
                                isRight = true;
                                break;
                            } else {
                                System.out.println("Something went wrong.");
                            }

                            //Korea
                        case 2:
                            TopKoreaSmartphone objectKorea = new TopKoreaSmartphone();
                            Class<?> classKoreaPhone = objectKorea.getClass();
                            // Getting annotation
                            if (classKoreaPhone.isAnnotationPresent(FlagshipSmartphone.class)) {
                                FlagshipSmartphone annotation = classKoreaPhone.getAnnotation(FlagshipSmartphone.class);
                                //Reading values from the annotation
                                volumeMemory = annotation.volumeMemory();
                                screenSize = annotation.screenSize();
                                nameOfSmartphone = annotation.nameOfSmartphone();
                                modelOfSmartphone = choiceOfKoreaModel();
                                isRight = true;
                                break;
                            } else {
                                System.out.println("Something went wrong.");
                            }
                        default:
                            throw new IllegalArgumentException("Invalid category choice");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("You have written a wrong information. Try again.");
                }
            } while (!isRight);
        }

        //Add order to queue
        SmartphoneFactory.queueOfOrders.offer(new Order(dataTimeOfOrder, Status.CREATED,
                new Smartphone(nameOfSmartphone, modelOfSmartphone, volumeMemory, screenSize), quatityOfSmartphones));
        System.out.println("Currently your order is in the queue, please stand by.");
    }

    public static String choiceOfChinaModel() throws IOException {
        do {
            try {
                System.out.println("Choose the model of the phone: 1.XIAOMI or 2.HONOR?");
                int choiceOfModel = Integer.parseInt(READER.readLine());
                switch (choiceOfModel) {
                    case (1): {
                        do {
                            try {
                                System.out.println("Choose the model of XIAOMI: 1. XIAOMI Redmi 12 Smartphone (100£), 2. XIAOMI Redmi Note 13 (150£)");
                                choiceOfModel = Integer.parseInt(READER.readLine());
                                if (choiceOfModel == 1) {
                                    return "XIAOMI Redmi 12 Smartphone";
                                } else if (choiceOfModel == 2) {
                                    return "XIAOMI Redmi Note 13";
                                }
                            } catch (NumberFormatException | IOException e) {
                                System.out.println("You have written a wrong information. Try again.");
                            }
                        } while (true);
                    }
                    case (2): {
                        do {
                            try {
                                System.out.println("Choose the model of HONOR: 1. HONOR X6a (100£), 2. HONOR 90 LITE (170£)");
                                choiceOfModel = Integer.parseInt(READER.readLine());
                                if (choiceOfModel == 1) {
                                    return "HONOR X6a";
                                } else if (choiceOfModel == 2) {
                                    return "HONOR 90 LITE";
                                }
                            } catch (NumberFormatException | IOException e) {
                                System.out.println("You have written a wrong information. Try again.");
                            }
                        } while (true);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        }
        while (true);
    }

    public static String choiceOfIndiaModel() {
        do {
            try {
                System.out.println("Choose the model of the phone: 1.Lava Blaze Pro (150£) or 2.Lava Yuva3 Pro (110£)?");
                int choiceOfModel = Integer.parseInt(READER.readLine());
                switch (choiceOfModel) {
                    case (1):
                        return "Lava Blaze Pro";
                    case (2):
                        return "Lava Yuva3 Pro";
                    default:
                        throw new IllegalArgumentException("Invalid category choice");

                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        } while (true);
    }

    public static String choiceOfTaiModel() {
        do {
            try {
                System.out.println("Choose the model of HTC: 1. HTC U23 Pro 5G (280£), 2. HTC Desire 22 Pro 5G (180£)");
                int choiceOfModel = Integer.parseInt(READER.readLine());
                if (choiceOfModel == 1) {
                    return "HTC U23 Pro 5G";
                } else if (choiceOfModel == 2) {
                    return "HTC Desire 22 Pro 5G";
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        } while (true);
    }

    public static String choiceOfUSAModel() throws IOException {
        do {
            try {
                System.out.println("Choose the model of the phone: 1.IPHONE or 2.GOOGLE?");
                int choiceOfModel = Integer.parseInt(READER.readLine());
                switch (choiceOfModel) {
                    case (1): {
                        do {
                            try {
                                System.out.println("Choose the model of IPHONE: 1. IPHONE 14 (700£), 2. IPHONE 15 (800£)");
                                choiceOfModel = Integer.parseInt(READER.readLine());
                                if (choiceOfModel == 1) {
                                    return "IPHONE 14";
                                } else if (choiceOfModel == 2) {
                                    return "IPHONE 15";
                                }
                            } catch (NumberFormatException | IOException e) {
                                System.out.println("You have written a wrong information. Try again.");
                            }
                        } while (true);
                    }
                    case (2): {
                        do {
                            try {
                                System.out.println("Choose the model of GOOGLE: 1. GOOGLE Pixel 7 Pro(430£), 2. GOOGLE Pixel 8 Pro (500£)");
                                choiceOfModel = Integer.parseInt(READER.readLine());
                                if (choiceOfModel == 1) {
                                    return "GOOGLE Pixel 7 Pro";
                                } else if (choiceOfModel == 2) {
                                    return "GOOGLE Pixel 8 Pro ";
                                }
                            } catch (NumberFormatException | IOException e) {
                                System.out.println("You have written a wrong information. Try again.");
                            }
                        } while (true);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        } while (true);
    }

    public static String choiceOfKoreaModel() {
        do {
            try {
                System.out.println("Choose the model of SAMSUNG: 1.SAMSUNG Galaxy S23 Ultra (650£), 2.SAMSUNG Galaxy S24 Ultra (790£)");
                int choiceOfModel = Integer.parseInt(READER.readLine());
                if (choiceOfModel == 1) {
                    return "SAMSUNG Galaxy S23 Ultra";
                } else if (choiceOfModel == 2) {
                    return "SAMSUNG Galaxy S24 Ultra";
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("You have written a wrong information. Try again.");
            }
        } while (true);
    }

    public static void main(String[] args) throws IOException {
        SmartphoneFactory smartphoneFactory = new SmartphoneFactory(Runtime.getRuntime().availableProcessors());

        try (READER) {
            int choice;
            do {
                try {

                    System.out.println("1. Make your order. 0. Exit.");
                    choice = Integer.parseInt(READER.readLine());
                    if (choice == 1) {
                        choiceOfOrder(); //get information,add to queue
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