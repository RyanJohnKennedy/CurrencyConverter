import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) throws IOException {

        HashMap<Integer, String> currencyCodes = new HashMap<>();

        // Add currency codes
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "ZAR");
        currencyCodes.put(3, "GBP");
        currencyCodes.put(4, "EUR");
        currencyCodes.put(5, "AUD");
        currencyCodes.put(6, "CAD");
        currencyCodes.put(7, "HKD");
        currencyCodes.put(8, "CNY");

        String fromCode, toCode;
        double amount;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the currency converter!");

        // Convert From
        System.out.println("Currency converting FROM?");
        System.out.println("1:" + currencyCodes.get(1) +
                "\t 2:" + currencyCodes.get(2) +
                "\t 3:" + currencyCodes.get(3) +
                "\t 3:" + currencyCodes.get(3) +
                "\t 4:" + currencyCodes.get(4) +
                "\t 5:" + currencyCodes.get(5) +
                "\t 6:" + currencyCodes.get(6) +
                "\t 7:" + currencyCodes.get(7) +
                "\t 8:" + currencyCodes.get(8));
        fromCode = currencyCodes.get(sc.nextInt());

        // Convert To
        System.out.println("Currency converting TO?");
        System.out.println("1:" + currencyCodes.get(1) +
                "\t 2:" + currencyCodes.get(2) +
                "\t 3:" + currencyCodes.get(3) +
                "\t 3:" + currencyCodes.get(3) +
                "\t 4:" + currencyCodes.get(4) +
                "\t 5:" + currencyCodes.get(5) +
                "\t 6:" + currencyCodes.get(6) +
                "\t 7:" + currencyCodes.get(7) +
                "\t 8:" + currencyCodes.get(8));
        toCode = currencyCodes.get(sc.nextInt());

        System.out.println("Amount you wish to Convert?");
        amount = sc.nextFloat();

        sendHttpGETRequest(fromCode, toCode, amount);
        //dccc9430f393aef03aeccd641eab17c3
    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
        String GET_URL = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_2zkY54hDXVa8TXAVQgpTbjLWfCSAKHJJFLIg1VSp&currencies=" + fromCode + "&base_currency=" + toCode;

        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK){ // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }in.close();

            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("data").getDouble(fromCode);
            System.out.println(obj.getJSONObject("data"));
            System.out.println(exchangeRate);
            System.out.println();
            System.out.println(amount + " " + fromCode + " = " + String.format("%.2f",amount/exchangeRate) + " " + toCode);
        }
        else{
            System.out.println("GET request failed!");
        }
    }
}
