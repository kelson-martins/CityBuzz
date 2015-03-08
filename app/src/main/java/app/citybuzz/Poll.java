package app.citybuzz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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

                JSONObject obj = new JSONObject();
                try {
                    obj.put("appID", "1");
                    obj.put("id", id);
                    obj.put("answer", "1");
                    obj.put("category", category);
                    obj.put("coordX", latitude);
                    obj.put("coordY", longitude);


                } catch (JSONException e) {

                    e.printStackTrace();
                }

                GetJsonTask p = new GetJsonTask();
                p.execute(obj);

                dialogBox();



            }
		});
		
		button_No = (Button) findViewById(R.id.button_no);
		button_No.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

                JSONObject obj = new JSONObject();
                try {
                    obj.put("appID", "1");
                    obj.put("id", id);
                    obj.put("answer", "0");
                    obj.put("category", category);
                    obj.put("coordX", latitude);
                    obj.put("coordY", longitude);

                } catch (JSONException e) {

                    e.printStackTrace();
                }

                GetJsonTask p = new GetJsonTask();
                p.execute(obj);

                dialogBox();
            }
		});
	}

    @Override
    public void onLocationChanged(Location location) {

        try {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
        } catch (Exception e) {
            latitude = String.valueOf(53.3422227);
            longitude = String.valueOf(-6.2833865);
        }


        Log.v("MainActivity","OnLocationChanged");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        try {
            Location l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            // we are forcing data for the Emulator purposes since the Emulator does not have Network Provider
            latitude = String.valueOf(53.3422227);
            longitude = String.valueOf(-6.2833865);
        }

        Log.v("MainActivity", "OnProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        try {
            lm.requestLocationUpdates(provider, 400, 1, this);
        } catch (Exception e) {

            // we are forcing data for the Emulator purposes since the Emulator does not have Network Provider
            latitude = String.valueOf(53.3422227);
            longitude = String.valueOf(-6.2833865);
        }
    }

    public void dialogBox() {
        AlertDialog.Builder alertDialogBuilder = new        AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Thank You. Your Response has been recorded.");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent goBack = new Intent(Poll.this,MainActivity.class);
                        startActivity(goBack);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
