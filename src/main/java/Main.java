import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public final static Map<String, String> listProducts = fillMapFromFile(new File("categories.tsv"));

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8989);) {

            FinStatistic finStatistic;

            if (Files.exists(Path.of("data.bin"))) {
                finStatistic = FinStatistic.loadFromBin(new File("data.bin"));
            } else {
                finStatistic = new FinStatistic();
            }

            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    String tmp = in.readLine();
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    Purchase purchase = gson.fromJson(tmp, Purchase.class);

                    finStatistic.addPurchase(finStatistic.getCountCategory(), purchase);
                    finStatistic.countMaxCategory(finStatistic.getCountCategory());
                    finStatistic.saveBin(new File("data.bin"));
                    out.println(finStatistic.answerToClient(finStatistic.getMaxCategory(), finStatistic.getMaxCategoryValue()));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> fillMapFromFile(File file) {

        HashMap<String, String> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split("\\t");
                map.put(parts[0], parts[1]);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}