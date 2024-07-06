package Smartphones;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;


public class RealSmartphoneBuilder implements SmartphoneBuilder {

    private Smartphone smartphone;
    private int volumeMemory;
    private String screenSize;
    private String modelOfSmartphone;
    private String nameOfSmartphone;
    private BufferedReader reader;

    @Override
    public void buildSmartphone(BufferedReader reader) throws IOException {
        this.reader = reader;
        // choice of BudgetSmartphone, MidTierSmartphone, FlagshipSmartphone
        Class<? extends Annotation> categoryClass = chooseCategory();
        smartphone = chooseBudgetType(categoryClass);
    }

    @Override
    public Smartphone getSmartphone() {
        return smartphone;
    }

    // choice of BudgetSmartphone, MidTierSmartphone, FlagshipSmartphone
    public  Class<? extends Annotation> chooseCategory() throws IOException {
        int categoryChoice;
        boolean isRight = false;

        do {
            try {
                System.out.println("Choose category (number) by the price: 1. Budget (up to 250£), 2. MidTier (up to 450£), 3. Flagship (up to 900£).");
                categoryChoice = Integer.parseInt(reader.readLine());
                switch (categoryChoice) {
                    case 1:
                        return BudgetSmartphone.class;

                    case 2:
                        return MidTierSmartphone.class;

                    case 3:
                        return FlagshipSmartphone.class;

                    default:
                        System.out.println("Invalid category choice");
                }
                isRight = true;
            } catch (IllegalArgumentException e) {
                System.out.println("You have written wrong information. Try again.");
            }
        } while (!isRight);
        return MidTierSmartphone.class;
    }

    //choice of the type of phone according to budget
    public Smartphone chooseBudgetType(Class<? extends Annotation> categoryClass) {

        boolean isRight = false;
        if (categoryClass.equals(BudgetSmartphone.class)) {
            do {
                try {
                    System.out.println("Choose country-producer (number): 1. China, 2. India");
                    int choiceOfType = Integer.parseInt(reader.readLine());
                    switch (choiceOfType) {
                        //China
                        case 1:
                            return getChinaAnnotation();

                        //India
                        case 2:
                            return getIndiaAnnotation();

                        default:
                            throw new IllegalArgumentException("Invalid category choice");
                    }
                } catch (IllegalArgumentException | IOException e) {
                    System.out.println("You have written a wrong information. Try again.");
                }
            } while (!isRight);
        } else if (categoryClass.equals(MidTierSmartphone.class)) {
            return getTaiAnnotation();

        } else if (categoryClass.equals(FlagshipSmartphone.class)) {
            do {
                try {
                    System.out.println("Choose country-producer (number): 1. USA, 2. Korea");
                    int choiceOfType = Integer.parseInt(reader.readLine());
                    switch (choiceOfType) {
                        //USA
                        case 1:
                            return getUSAAnnotation();

                        //Korea
                        case 2:
                            return getKoreaAnnotation();

                        default:
                            throw new IllegalArgumentException("Invalid category choice");
                    }
                } catch (IllegalArgumentException | IOException e) {
                    System.out.println("You have written a wrong information. Try again.");
                }
            } while (!isRight);
        }
        return null;
    }

    //choice of the China phone
    public Smartphone getChinaAnnotation() throws IOException {
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
        } else {
            System.out.println("Something went wrong.");
        }
        return new Smartphone(nameOfSmartphone, modelOfSmartphone, volumeMemory, screenSize);
    }

    //choice of the India phone
    public Smartphone getIndiaAnnotation() {
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
        } else {
            System.out.println("Something went wrong.");
        }
        return new Smartphone(nameOfSmartphone, modelOfSmartphone, volumeMemory, screenSize);
    }

    //choice of the Tai phone
    public Smartphone getTaiAnnotation() {
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
        return new Smartphone(nameOfSmartphone, modelOfSmartphone, volumeMemory, screenSize);
    }

    //choice of the USA phone
    public Smartphone getUSAAnnotation() throws IOException {
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
        } else {
            System.out.println("Something went wrong.");
        }
        return new Smartphone(nameOfSmartphone, modelOfSmartphone, volumeMemory, screenSize);
    }

    //choice of the Korea phone
    public Smartphone getKoreaAnnotation() {
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
        } else {
            System.out.println("Something went wrong.");
        }
        return new Smartphone(nameOfSmartphone, modelOfSmartphone, volumeMemory, screenSize);
    }

    public  String choiceOfChinaModel() throws IOException {
        do {
            try {
                System.out.println("Choose the model of the phone: 1.XIAOMI or 2.HONOR?");
                int choiceOfModel = Integer.parseInt(reader.readLine());
                switch (choiceOfModel) {
                    case (1): {
                        do {
                            try {
                                System.out.println("Choose the model of XIAOMI: 1. XIAOMI Redmi 12 Smartphone (100£), 2. XIAOMI Redmi Note 13 (150£)");
                                choiceOfModel = Integer.parseInt(reader.readLine());
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
                                choiceOfModel = Integer.parseInt(reader.readLine());
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

    public  String choiceOfIndiaModel() {
        do {
            try {
                System.out.println("Choose the model of the phone: 1.Lava Blaze Pro (150£) or 2.Lava Yuva3 Pro (110£)?");
                int choiceOfModel = Integer.parseInt(reader.readLine());
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

    public  String choiceOfTaiModel() {
        do {
            try {
                System.out.println("Choose the model of HTC: 1. HTC U23 Pro 5G (280£), 2. HTC Desire 22 Pro 5G (180£)");
                int choiceOfModel = Integer.parseInt(reader.readLine());
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

    public  String choiceOfUSAModel() throws IOException {
        do {
            try {
                System.out.println("Choose the model of the phone: 1.IPHONE or 2.GOOGLE?");
                int choiceOfModel = Integer.parseInt(reader.readLine());
                switch (choiceOfModel) {
                    case (1): {
                        do {
                            try {
                                System.out.println("Choose the model of IPHONE: 1. IPHONE 14 (700£), 2. IPHONE 15 (800£)");
                                choiceOfModel = Integer.parseInt(reader.readLine());
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
                                choiceOfModel = Integer.parseInt(reader.readLine());
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

    public  String choiceOfKoreaModel() {
        do {
            try {
                System.out.println("Choose the model of SAMSUNG: 1.SAMSUNG Galaxy S23 Ultra (650£), 2.SAMSUNG Galaxy S24 Ultra (790£)");
                int choiceOfModel = Integer.parseInt(reader.readLine());
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
}