package app.citybuzz;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetQuestions extends AsyncTask<String, String, JSONObject> {


    @Override
    protected JSONObject doInBackground(String... params) {
        URL url;
        HttpURLConnection urlConnection = null;
        JSONArray response = new JSONArray();
        String json = "";
        JSONObject jobj = null;

        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpStatus.SC_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"iso-8859-1"),8);

                StringBuilder sb = new StringBuilder();
                String line = null;

                try {
                    while((line = reader.readLine())!=null){
                        sb.append(line+"\n");

                    }

                    json = sb.toString();
                    try {
                        jobj = new JSONObject(json);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }else{
                Log.v("CatalogClient", "Response code:"+ responseCode);

            }

        } catch (Exception e) {
            e.printStackTrace();
            String s = e.toString();
            Log.e("Main", e.toString());
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

        return jobj;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

    }
}

