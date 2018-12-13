package tk.lorddarthart.yandexweathertestapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpServiceHelper {
    JSONObject jsonObject;

    public JSONObject getWeather(City city, String apiKey) throws IOException, JSONException {
        String url = "https://api.weather.yandex.ru/v1/forecast?lat="+city.getLatitude()+"&lon="+city.getLongitude()+"&lang=ru_RU";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("connection", "keep-alive");
        con.setRequestProperty("content-type", "application/json");
        con.setRequestProperty("X-Yandex-API-Key", apiKey);
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);

        int responseCode = con.getResponseCode();

        if (responseCode==200) {
            InputStream inputStream = con.getInputStream();
            String stringResponse = inputStreamToString(inputStream);

            jsonObject = new JSONObject(stringResponse);
        } else {
            jsonObject = new JSONObject("error");
        }
        return jsonObject;
    }

    public int checkConnection(String apiKey) throws IOException {
        String url = "https://api.weather.yandex.ru/v1/forecast?lat=0.0&lon=0.0&lang=ru_RU";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("connection", "keep-alive");
        con.setRequestProperty("content-type", "application/json");
        con.setRequestProperty("X-Yandex-API-Key", apiKey);
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);

        int responseCode = con.getResponseCode();

        return responseCode;
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString("UTF-8");
        }
    }
}
