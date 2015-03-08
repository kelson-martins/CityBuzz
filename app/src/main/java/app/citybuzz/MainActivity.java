package app.citybuzz;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {

    GetQuestions asyncTask = new GetQuestions();

	private Button button_poll;
	private Button button_report;
    private Button button_map;

    public JSONObject questions = new JSONObject();
    public String question = "";
    final static String ADDRESS = "http://172.16.10.217:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        button_poll = (Button) findViewById(R.id.button_poll);
        button_poll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent nextStep = new Intent(MainActivity.this, Poll.class);
                GetQuestions questions = new GetQuestions();

                try {
                    JSONObject a = questions.execute(
                            ADDRESS + "getLastQuestion?category=Accessibility").get();

                    JSONArray array = a.getJSONArray("res");

                    question = array.getJSONObject(0).getString("question");

                    nextStep.putExtra("id",array.getJSONObject(0).getString("_id"));
                    nextStep.putExtra("question",question);
                    nextStep.putExtra("category","polling");

                } catch (Exception e) {

                }

                if (!question.isEmpty()) {
                    startActivity(nextStep);
                } else {
                    Toast.makeText(getApplicationContext(),"Server not Available",Toast.LENGTH_LONG).show();
                }


			}
		});

        button_report = (Button) findViewById(R.id.button_report);
        button_report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent nextStep = new Intent(MainActivity.this, Audience.class);
				startActivity(nextStep);
			}
		});


        try {
            questions = asyncTask.execute(ADDRESS + "getQuestions").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        button_map = (Button) findViewById(R.id.button_map);
        button_map.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ADDRESS + "mobile_map";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
