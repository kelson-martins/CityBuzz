package app.citybuzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Categories extends ActionBarActivity {

    private ListView list_categories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();
        String audience = intent.getStringExtra("audience");
        list_categories = (ListView) findViewById(R.id.list_categories);

        int layout;

        int array = 0;

        if (audience.equals("Disabled")) {
            array = R.array.disabilities;
        } else if (audience.equals("General Citizen")) {
            array = R.array.citizen;
        }else if (audience.equals("Youngsters")) {
            array = R.array.young;
        }else if (audience.equals("Women")) {
            array = R.array.women;
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                array,
                android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        list_categories.setAdapter(adapter);


        list_categories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent categories = new Intent(Categories.this,Questions.class);
                categories.putExtra("categorie",list_categories.getItemAtPosition(position).toString());
                startActivity(categories);



            }
        });


	}

	
}
