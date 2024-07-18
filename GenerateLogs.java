import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenerateLogs {

    private static final int NUM_RECORDS = 1_000_000;
    private static final String FILE_PATH = "/home/username/data/website_logs.csv";
    private static final String[] IP_ADDRESSES = new String[256];
    private static final String[] PAGE_URLS = {"/home", "/about", "/contact", "/products", "/services", "/blog"};
    private static final String[] USER_AGENTS = {
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.1.1 Safari/605.1.15",
            "Mozilla/5.0 (iPhone; CPU iPhone OS 14_6 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/14.0 Mobile/15E148 Safari/604.1",
            "Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Mobile Safari/537.36"
    };

    public static void main(String[] args) {
        generateIpAddresses();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            Random random = new Random();
            LocalDateTime startTime = LocalDateTime.now().minusDays(1);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Write header
            writer.write("ip,page_url,timestamp,user_agent\n");

            for (int i = 0; i < NUM_RECORDS; i++) {
                String ip = IP_ADDRESSES[random.nextInt(IP_ADDRESSES.length)];
                String url = PAGE_URLS[random.nextInt(PAGE_URLS.length)];
                LocalDateTime timestamp = startTime.plusSeconds(random.nextInt(86400));
                String userAgent = USER_AGENTS[random.nextInt(USER_AGENTS.length)];

                String logEntry = String.format("%s,%s,%s,%s%n", ip, url, timestamp.format(formatter), userAgent);
                writer.write(logEntry);
            }

            System.out.printf("%d log entries have been generated and saved to %s%n", NUM_RECORDS, FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateIpAddresses() {
        for (int i = 0; i < IP_ADDRESSES.length; i++) {
            IP_ADDRESSES[i] = "192.168.1." + i;
        }
    }
}
