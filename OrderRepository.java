import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.*;
import java.io.IOException;

public class OrderRepository {
    private static final String DATA_FILE = "data.txt";

   
    public static void saveOrder(FoodItem[] items, int count, double totalCost) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, true));

        for (int i = 0; i < count; i++) {
            bw.write(items[i].toString());
            bw.newLine();
        }
        bw.write("Total: " + totalCost + " tk");
        bw.newLine();
        bw.write("----");
        bw.newLine();
        bw.close();
    }

   
    public static String loadOrderHistory() {
        String history = "";
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            return "No order history found.";
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            boolean anyLine = false;

            while ((line = br.readLine()) != null) {
                history = history + line + "\n";
                anyLine = true;
            }
            br.close();

            if (!anyLine) {
                return "No order history found.";
            }
            return history;
        } catch (IOException ex) {
            return "Failed to read order history.";
        }
    }

    

    public static void deleteOrderHistory() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, false));
        
        bw.write("");
        bw.close();
    }
}
