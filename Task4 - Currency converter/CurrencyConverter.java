import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class CurrencyConverter {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Welcome to the Currency Converter!");

        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = scn.nextLine().trim().toUpperCase(Locale.ROOT);

        System.out.print("Enter target currency (e.g., INR): ");
        String targetCurrency = scn.nextLine().trim().toUpperCase(Locale.ROOT);

        System.out.print("Enter amount to convert: ");
        double amount = scn.nextDouble();

        try {
            String url = "https://api.frankfurter.app/latest?from="
                    + URLEncoder.encode(baseCurrency, StandardCharsets.UTF_8)
                    + "&to="
                    + URLEncoder.encode(targetCurrency, StandardCharsets.UTF_8);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            int keyIndex = body.indexOf("\"" + targetCurrency + "\":");
            if (keyIndex == -1) {
                System.out.println("Invalid currency code. Please use valid codes like USD, INR, EUR.");
                scn.close();
                return;
            }

            int start = keyIndex + targetCurrency.length() + 3;
            int endComma = body.indexOf(",", start);
            int endBrace = body.indexOf("}", start);
            int end = (endComma != -1 && endComma < endBrace) ? endComma : endBrace;
            double rate = Double.parseDouble(body.substring(start, end).trim());

            double convertedAmount = amount * rate;

            System.out.printf(Locale.US, "%.2f %s = %.2f %s%n",
                    amount, baseCurrency, convertedAmount, targetCurrency);
        } catch (IOException | InterruptedException e) {
            System.out.println("Could not fetch exchange rates right now. Please try again later.");
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter correct currency codes and amount.");
        }

        scn.close();
    }
}
