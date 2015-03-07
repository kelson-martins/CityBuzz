package app.citybuzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity {

    GetQuestions asyncTask = new GetQuestions();

	private Button button_poll;
	private Button button_report;
    public JSONObject questions = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_poll = (Button) findViewById(R.id.button_poll);
        button_poll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//Intent nextStep = new Intent(MainActivity.this, Poll.class);
				//startActivity(nextStep);
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

        //asyncTask.execute("http://citybuzz.mybluemix.net/getQuestions");

        try {
            questions = asyncTask.execute("http://citybuzz.mybluemix.net/getQuestions").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
