package app.citybuzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Audience extends ActionBarActivity {
	
	private ListView list_audience;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audience);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

		list_audience = (ListView) findViewById(R.id.list_audience);

        // to an array adapter such that it can be used with the spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.audience,
                android.R.layout.simple_spinner_item);
        // specify the layout that will be used on the list of choices when the spinner
        // is clicked

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // set the adapter on the spinner
        list_audience.setAdapter(adapter);


        list_audience.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent categories = new Intent(Audience.this,Categories.class);
                categories.putExtra("audience",list_audience.getItemAtPosition(position).toString());
                startActivity(categories);
            }
        });


	}

}
