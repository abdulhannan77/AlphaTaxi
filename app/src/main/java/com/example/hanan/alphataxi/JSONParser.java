package com.example.hanan.alphataxi;

/**
 * Created by hanan on 11/27/2015.
 */
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Astapor on 11/18/2015.
 */
public class JSONParser {
    static InputStream is = null;
    static double dist = 0;
    static String json,time = "";

    // constructor
    public JSONParser() {
    }
    public String getJSONFromUrl(String url2) {

        // Making HTTP request
        try {
            URL url = new URL(url2);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //   conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //       writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                //       InputStream in = new InputStream(conn.getInputStream());
                is = conn.getInputStream();


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            json = sb.toString();

            is.close();
            JSONObject jsonObject = new JSONObject();
            jsonObject = new JSONObject(json);

            JSONArray array = jsonObject.getJSONArray("routes");

            JSONObject routes = array.getJSONObject(0);

            JSONArray legs = routes.getJSONArray("legs");

            JSONObject steps = legs.getJSONObject(1);

            JSONObject distance = steps.getJSONObject("distance");
            JSONObject duration = steps.getJSONObject("duration");

            Log.i("Distance", distance.toString());
            time = duration.getString("text");
            String[] separated = (distance.getString("text")).split(" ");

            String unit = separated[1];

            dist = Double.parseDouble(distance.getString("text").replaceAll("[^\\.0123456789]",""));
            if(!unit.equals("mi")){
                dist = dist * 0.000189394;
            }

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return json;

    }
}
