public class Smartphone {
    private String nameOfSmartphone;
    private String modelOfSmartphone;
    private int volumeMemory;
    private String screenSize;


    public String getNameOfSmartphone() {
        return nameOfSmartphone;
    }

    public String getModelOfSmartphonel() {
        return modelOfSmartphone;
    }

    public void setNameOfSmartphone(String nameOfSmartphone) {
        this.nameOfSmartphone = nameOfSmartphone;
    }

    public void setModel(String modelOfSmartphone) {
        this.modelOfSmartphone = modelOfSmartphone;
    }

    public int getVolumeMemory() {
        return volumeMemory;
    }

    public String getScreenSize() {
        return screenSize;
    }


    public void setVolumeMemory(int volumeMemory) {
        this.volumeMemory = volumeMemory;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public Smartphone(String nameOfSmartphone, String modelOfSmartphone, int volumeMemory, String screenSize) {
        this.nameOfSmartphone = nameOfSmartphone;
        this.modelOfSmartphone = modelOfSmartphone;
        this.volumeMemory = volumeMemory;
        this.screenSize = screenSize;
    }
}
