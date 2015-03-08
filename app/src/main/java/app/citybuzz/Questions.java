package app.citybuzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class Questions extends ActionBarActivity {

    private ListView list_questions;

    public HashMap<String, String> list_Accessibility = new HashMap<String, String>();
    public HashMap<String, String> list_community = new HashMap<String, String>();
    public HashMap<String, String> list_culture = new HashMap<String, String>();
    public HashMap<String, String> list_enviroment = new HashMap<String, String>();
    public HashMap<String, String> list_ammenities = new HashMap<String, String>();
    public HashMap<String, String> list_molestation = new HashMap<String, String>();
    public HashMap<String, String> list_bullying = new HashMap<String, String>();

    public ArrayList<String> adapter_Accessibility = new ArrayList<String>();
    public ArrayList<String> adapter_Community = new ArrayList<String>();

    public ArrayList<String> adapter_Culture = new ArrayList<String>();
    public ArrayList<String> adapter_Environment = new ArrayList<String>();
    public ArrayList<String> adapter_Molestation = new ArrayList<String>();
    public ArrayList<String> adapter_Amenities = new ArrayList<String>();
    public ArrayList<String> adapter_Bullying = new ArrayList<String>();

    final static String ADDRESS = "http://172.16.10.217:3000/";

    public HashMap<String, String> chosed = new HashMap<String, String>();

    public ArrayList<String> chosedAdapter = new ArrayList<String>();

    String category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questions);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent intent = getIntent();
        final String categorie = intent.getStringExtra("categorie");
        list_questions = (ListView) findViewById(R.id.list_questions);
        GetQuestions questions = new GetQuestions();

        try {
            JSONObject a = questions.execute(
                    ADDRESS + "getQuestions").get();

            if (a != null) {
                JSONArray array = a.getJSONArray("res");

                for (int i = 0; i < array.length(); i++) {
                    if (!array.getJSONObject(i).getString("category").isEmpty()) {
                        if (array.getJSONObject(i).getString("category")
                                .equals("Accessibility")) {
                            list_Accessibility.put(array.getJSONObject(i).getString("_id"),array.getJSONObject(i).getString("question"));
                            adapter_Accessibility.add(array.getJSONObject(i).getString("question"));

                        } else if (array.getJSONObject(i).getString("category")
                                .equals("Community Provision")) {
                            list_community.put(array.getJSONObject(i).getString("_id"),array.getJSONObject(i).getString("question"));
                            adapter_Community.add(array.getJSONObject(i).getString("question"));

                        } else if (array.getJSONObject(i).getString("category")
                                .equals("Culture")) {
                            list_culture.put(array.getJSONObject(i).getString("_id"),array.getJSONObject(i).getString("question"));
                            adapter_Culture.add(array.getJSONObject(i).getString("question"));
                        } else if (array.getJSONObject(i).getString("category")
                                .equals("Environment")) {
                            list_enviroment.put(array.getJSONObject(i).getString("_id"),array.getJSONObject(i).getString("question"));
                            adapter_Environment.add(array.getJSONObject(i).getString("question"));
                        } else if (array.getJSONObject(i).getString("category")
                                .equals("Local Amenities")) {
                            list_ammenities.put(array.getJSONObject(i).getString("_id"),array.getJSONObject(i).getString("question"));
                            adapter_Amenities.add(array.getJSONObject(i).getString("question"));
                        } else if (array.getJSONObject(i).getString("category")
                                .equals("Molestation")) {
                            list_molestation.put(array.getJSONObject(i).getString("_id"),array.getJSONObject(i).getString("question"));
                            adapter_Molestation.add(array.getJSONObject(i).getString("question"));
                        } else if (array.getJSONObject(i).getString("category")
                                .equals("Bulling")) {
                            list_bullying.put(array.getJSONObject(i).getString("_id"),array.getJSONObject(i).getString("question"));
                            adapter_Bullying.add(array.getJSONObject(i).getString("question"));
                        }
                    }

                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (categorie.equals("Accessibility")) {
            chosed = list_Accessibility;
            chosedAdapter = adapter_Accessibility;
        } else if (categorie.equals("Community Provision")) {
            chosed = list_community;
            chosedAdapter = adapter_Community;
        }else if (categorie.equals("Culture")) {
            chosed = list_culture;
            chosedAdapter = adapter_Culture;
        }else if (categorie .equals("Environment")) {
            chosed = list_enviroment;
            chosedAdapter = adapter_Environment;
        }else if (categorie .equals("Local Amenities")) {
            chosed = list_ammenities;
            chosedAdapter = adapter_Amenities;
        }else if (categorie .equals("Bullying")) {
            chosed = list_bullying;
            chosedAdapter = adapter_Bullying;
        } else if (categorie .equals("Molestation")) {
            chosed = list_molestation;
            chosedAdapter = adapter_Molestation;
        }

        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, chosedAdapter);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        list_questions.setAdapter(arrayAdapter);

        list_questions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Questions.this,Poll.class);
                intent.putExtra("question",list_questions.getItemAtPosition(position).toString());

                Iterator<String> keySetIterator = chosed.keySet().iterator();

                while(keySetIterator.hasNext()){
                    String key = keySetIterator.next();
                    if (chosed.get(key) == list_questions.getItemAtPosition(position).toString()) {
                        intent.putExtra("id",key);
                    }
                }
                intent.putExtra("category",categorie);
                intent.putExtra("question",list_questions.getItemAtPosition(position).toString());
                startActivity(intent);

            }
        });
	}



}
