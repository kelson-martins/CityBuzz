package app.citybuzz;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class GetJsonTask extends AsyncTask<JSONObject, String, JSONObject> {


    final static String ADDRESS = "http://172.16.10.217:3000/";

    @Override
    protected JSONObject doInBackground(JSONObject... params) {


        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppostreq = new HttpPost(ADDRESS + "putAnswer");
        StringEntity se = null;
        try {
            se = new StringEntity(params[0].toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        se.setContentType("application/json;charset=UTF-8");
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
        httppostreq.setEntity(se);
        try {
            HttpResponse httpresponse = httpclient.execute(httppostreq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

