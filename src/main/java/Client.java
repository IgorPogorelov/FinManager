import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();

        try (Socket clientSocket = new Socket("127.0.0.1", 8989);
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Введите название покупки и сумму через пробел:");
            String[] in = scanner.nextLine().split(" ");
            String jsonStr = "{\"title\": " + in[0] + ", \"date\": " + formatter.format(date) + ", \"sum\": " + in[1] + "}";

            writer.println(jsonStr);

            System.out.println(reader.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
