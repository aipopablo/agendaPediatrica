package util;

import android.widget.Toast;
import android.content.Context;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.net.Proxy.Type.HTTP;

public class HttpGestor {

    public static String getData(String uri){
        BufferedReader reader = null;
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lina;

            while((lina = reader.readLine()) != null){
                stringBuilder.append(lina + "\n");
            }
            return stringBuilder.toString();

        } catch (MalformedURLException ee){
            ee.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return "C";
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
