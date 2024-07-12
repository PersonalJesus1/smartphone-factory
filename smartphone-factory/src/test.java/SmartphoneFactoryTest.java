import main.java.*;
import main.java.Order;
import main.java.Smartphones.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SmartphoneFactoryTest {
    private static final String TEMP_DIR_PREFIX = "tempLogFile";
    private File tempFile;
    private SmartphoneFactory smartphoneFactory;
    private Application application;
    private FileWriter writer;
    private ExecutorService executorService;

    @BeforeEach
    public void setUp() {
        application = new Application();
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @AfterEach
    public void tearDown() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }

    @Test
    public void testEmptyQueue() throws IOException, InterruptedException {
        tempFile = Files.createTempFile(TEMP_DIR_PREFIX, ".txt").toFile();
        writer = new FileWriter(tempFile, true);
        application.setFileWriter(writer);
        application.setFileOfResults(tempFile);
        smartphoneFactory = new SmartphoneFactory("TestFactory", Runtime.getRuntime().availableProcessors(), application);
        application.attach(smartphoneFactory);
        smartphoneFactory.startProduction();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Ensure logs are flushed to the file
        writer.flush();
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        long logEntries = Files.lines(tempFile.toPath()).count();
        assertEquals(0, logEntries, "Log file should have 0 entries for an empty queue.");
        tempFile.delete();
    }

    @Test
    public void testNonEmptyQueue() throws IOException, InterruptedException {
        File tempFile = Files.createTempFile(TEMP_DIR_PREFIX, ".txt").toFile();
        writer = new FileWriter(tempFile, true);
        application.setFileWriter(writer);
        application.setFileOfResults(tempFile);
        smartphoneFactory = new SmartphoneFactory("TestFactory", Runtime.getRuntime().availableProcessors(), application);
        application.attach(smartphoneFactory);
        SmartphoneFactory.queueOfOrders.offer(new Order(LocalDateTime.now(), Status.CREATED, new Smartphone(), 5));
        smartphoneFactory.startProduction();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writer.flush();
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        long logEntries = Files.lines(tempFile.toPath()).count();
        assertEquals(3, logEntries, "Log file should have 3 entries for one order.");
    }
    @Test
    public void testLogFileCreationFailure() {
        File invalidFile = new File("/invalid/path/to/logfile.txt");
        try {
            FileWriter writer = new FileWriter(invalidFile, true);
        } catch (IOException e) {
            assertEquals(true, e instanceof IOException, "An IOException should be thrown when log file creation fails.");
        }
    }
}