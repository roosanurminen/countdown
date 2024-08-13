package com.example.countdowntimer;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    private static final String JSON_FILE = "countdown.json";
    public static void saveDataToJson(Context context, String title, String date) {
        List<JSONObject> dataList = (List<JSONObject>) readDataFromJson(context);
        if (dataList.isEmpty()) {
            dataList = new ArrayList<>();
        }
        JSONObject newData = new JSONObject();

        try {
            newData.put("title", title);
            newData.put("date", date);
            dataList.add(newData);
        } catch (JSONException e) {
            Log.e("JsonHandler", "Error while creating JSON object", e);
        }

        JSONArray jsonArray = new JSONArray(dataList);
        String jsonString = jsonArray.toString();

        try {
            FileOutputStream fos = context.openFileOutput(JSON_FILE, Context.MODE_PRIVATE);
            fos.write(jsonString.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            Log.e("JsonHandler", "Error while saving JSON to file", e);
        }
    }

    public static List<JSONObject> readDataFromJson(Context context) {
        String jsonString = "";
        List<JSONObject> dataList = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(JSON_FILE);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();
            jsonString = new String(data, StandardCharsets.UTF_8);

            if (!jsonString.isEmpty()) {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); ++i) {
                    dataList.add(jsonArray.getJSONObject(i));
                }
            }
        } catch (IOException e) {
            Log.e("JsonHandler", "Error while reading JSON from file", e);
        } catch (JSONException e) {
            Log.e("JsonHandler", "Error while parsing JSON data", e);
        }
        return dataList;
    }

    public static void deleteDataFromJson(Context context, String title, String date) {
        List<JSONObject> dataList = readDataFromJson(context);

        List<JSONObject> newDataList = new ArrayList<>();
        for (JSONObject data : dataList) {
            try {
                if (!data.getString("title").equals(title) || !data.getString("date").equals(date)) {
                    newDataList.add(data);
                }
            } catch (JSONException e) {
                Log.e("JsonHandler", "Error while comparing JSON data", e);
            }
        }
        JSONArray jsonArray = new JSONArray(newDataList);
        String jsonString = jsonArray.toString();

        try {
            FileOutputStream fos = context.openFileOutput(JSON_FILE, Context.MODE_PRIVATE);
            fos.write(jsonString.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (IOException e) {
            Log.e("JsonHandler", "Error while writing updated JSON to file", e);
        }
    }

    public static List<CountdownData> getCountdownDataFromJson(Context context) {
        List<JSONObject> jsonObjects = readDataFromJson(context);
        List<CountdownData> dataList = new ArrayList<>();

        if (jsonObjects.isEmpty()) {
            return dataList;
        }

        try {
            for (JSONObject object : jsonObjects) {
                String title = object.getString("title");
                String date = object.getString("date");
                dataList.add(new CountdownData(title, date));
            }
        } catch (JSONException e) {
            Log.e("JsonHandler", "Error while converting JSON to CountdownData", e);
        }
        return dataList;
    }
}