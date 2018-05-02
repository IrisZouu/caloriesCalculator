package com.kun.food;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.*;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Object;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;




public class DetailActivity extends Activity implements OnClickListener {
	private double foodAmount;
	private String foodName;
	
	private Button[] buttons = new Button[2];

	private EditText editText;
	private TextView textView;
	static final String API_KEY = "xewLWQuRMcmshTSp4gVrM8l88FLPp1VHvcmjsnqeMqi";
	static final String API_URL = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/ingredients/{9266}/information";

	class RetrieveFeedBack extends AsyncTask<Void, Void, String> {
		private Exception exception;
		//enterFood = (TextView)
		private SearchView enterFood;
		private TextView responseView;


		protected void onPreExecute() {
			setProgressBarIndeterminateVisibility(true);
			responseView.setText("");
		}

			String email;
		protected String doInBackground(Void... urls) {
			enterFood = (SearchView) findViewById(R.id.searchView1);
			responseView = (TextView) findViewById(R.id.textView);
			email = enterFood.toString();

			try {
				URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
				/*HttpURLConnection urlConnection = Unirest.get("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/ingredients/9266/information?amount=100&unit=gram")
						.header(API_KEY, "<required>")
						.header("Accept", "application/json")
						.asJson();*/
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				try {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
					StringBuilder stringBuilder = new StringBuilder();
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						stringBuilder.append(line).append("\n");
					}
					bufferedReader.close();
					return stringBuilder.toString();
				} finally {
					urlConnection.disconnect();
				}
			} catch (Exception e) {
				Log.e("ERROR", e.getMessage(), e);
				return null;
			}
		}

		protected void onPostExecute(String response) {
			if (response == null) {
				response = "there was an error";
			}
			setProgressBarIndeterminateVisibility(true);
			Log.i("INFO", response);
			responseView.setText(response);

			try {
				JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
				JSONObject locObj = object.getJSONObject("nutrition");
				JSONArray currentRoot = locObj.getJSONArray("nutrients");
				Double calories = currentRoot.getDouble(1);
				String setCalories = String.valueOf(calories);
				responseView.setText(setCalories);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {

	    //wire up the botton to do stuff
        Button SearchBTN = (Button) findViewById(R.id.searchButton);
        SearchBTN.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("my app", "this is the magic log message");
                new RetrieveFeedBack().execute();
            }
        });



		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		Intent intent = getIntent();
		foodName = intent.getStringExtra("name");
		setTitle(foodName);
		foodAmount = 369.1;
		
		buttons[0] = (Button)findViewById(R.id.button1);
		buttons[0].setOnClickListener(this);
		buttons[1] = (Button)findViewById(R.id.button2);
		buttons[1].setOnClickListener(this);
		
		editText = (EditText)findViewById(R.id.editText1);
		textView = (TextView)findViewById(R.id.textView1);
		textView.setText("Food Name: " + foodName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View view) {
		Double num;
		try {
			Editable editable = editText.getText();
			String str = editable.toString();
			num = Double.valueOf(str);
		} catch (NumberFormatException e) {
			num = 0.0;
		}
		
		if (num <= 0.0) {
			alert("Fault", "The number has to be bigger than 1");
			return;
		}
		
		TotalActivity.count += num * foodAmount;
		if (view == buttons[1]) {
			Intent intent = new Intent(this, TotalActivity.class);
			startActivity(intent);
		}
		finish();
	}

	private void alert(String title, String msg) {
		alert(this, title, msg);
	}

	public static void alert(Context context, String title, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton("False", null);
		
		AlertDialog dlg = builder.create();
		dlg.show();
	}
}
