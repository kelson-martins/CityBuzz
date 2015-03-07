package app.citybuzz;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Poll extends ActionBarActivity implements LocationListener {
	
	private TextView text_quesiton;
	private Button button_Yes;
	private Button button_No;

    public static String latitude;
    public static String longitude;
    private static LocationManager lm;
    private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = LocationManager.NETWORK_PROVIDER;
        Location location = lm.getLastKnownLocation(provider);

        setContentView(R.layout.activity_poll);

		text_quesiton = (TextView) findViewById(R.id.text_question);

        Intent intent = getIntent();
        final String categorie = intent.getStringExtra("question");
        final String id = intent.getStringExtra("id");
        final String category = intent.getStringExtra("category");
		text_quesiton.setText(categorie);

		button_Yes = (Button) findViewById(R.id.button_yes);
		button_Yes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Latitute: " + latitude + " / Longitude: " + longitude,Toast.LENGTH_LONG).show();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("id", id);
                    obj.put("appID", "0000");
                    obj.put("question", categorie);
                    obj.put("answer", "YES");
                    obj.put("category", category);
                    obj.put("coordX", latitude);
                    obj.put("coordY", longitude);
                    obj.put("timestamp", System.currentTimeMillis());


                } catch (JSONException e) {

                    e.printStackTrace();
                }

                GetJsonTask p = new GetJsonTask();
                p.execute(obj);
                Intent goBack = new Intent(Poll.this,MainActivity.class);
                startActivity(goBack);
            }
		});
		
		button_No = (Button) findViewById(R.id.button_no);
		button_No.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

                JSONObject obj = new JSONObject();
                try {
                    obj.put("id", id);
                    obj.put("appID", "0000");
                    obj.put("question", categorie);
                    obj.put("answer", "NO");
                    obj.put("category", category);
                    obj.put("coordX", latitude);
                    obj.put("coordY", longitude);
                    obj.put("timestamp", System.currentTimeMillis());

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                GetJsonTask p = new GetJsonTask();
                p.execute(obj);

                Intent goBack = new Intent(Poll.this,MainActivity.class);
                startActivity(goBack);
            }
		});
	}

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());

        Log.v("MainActivity","OnLocationChanged");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Log.v("MainActivity", "OnProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        lm.requestLocationUpdates(provider, 400, 1, this);
    }
}
