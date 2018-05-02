package com.kun.food;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.JsonToken;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Object;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.*;


public class MainActivity extends Activity implements OnQueryTextListener, OnItemClickListener {

	private ArrayAdapter<String> adapter;
	private ArrayList<String> foodList = new ArrayList<String>();

	private SearchView search;
	private ListView list;
	/*

	private Exception exception;
	//enterFood = (TextView)
	View enterFood;
	View responseView;

	@Override
	public View findViewById(int id) {
		return super.findViewById(id);
	}

	public void setEnterFood(View enterFood) {
		this.enterFood = findViewById(R.id.searchView1);
	}
    public void setResponseView(View responseView) {
		this.responseView = findViewById(R.id.textView1);
	}

	static final String API_KEY = "xewLWQuRMcmshTSp4gVrM8l88FLPp1VHvcmjsnqeMqi";
	static final String API_URL = "ym3ugMkhttps://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/ingredients/9266/information?unit=gram&amount=100";

	protected void onPreExcute() {
		setProgressBarIndeterminateVisibility(true);
		//setResponseView(responseView);
		setContentView(responseView);
	}

	protected String doInBackground(Void... urls) {
		setEnterFood(enterFood);
		String email = enterFood.toString();

		try {
			URL url = new URL(API_URL + "email=" + email + "&apiKey=" + API_KEY);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			try {
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
				StringBuilder stringBuilder = new StringBuilder();
				//String line = stringBuilder.toString();


				String line;
				while ((line = bufferedReader.readLine()) != null) {
					stringBuilder.append(line).append("\n");
				}
				bufferedReader.close();
				return stringBuilder.toString();
			}
			finally{
				urlConnection.disconnect();
			}
		}
		catch(Exception e) {
			Log.e("ERROR", e.getMessage(), e);
			return null;
		}
	}
protected void onPostExcuse(String response) {
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

		} catch (JSONException e) {
			e.printStackTrace();
		}
}


*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		search = (SearchView)findViewById(R.id.searchView1);
		search.setOnQueryTextListener(this);
		deleteSearchIcon();
		
		String[] str = {"croissant", "starbucks chocolate frappuccion", "poke bowl", "orange",
				"apple", "meatball with tomate source", "steak", "cream cheese", "cheese cake",
				"vanilla ice cream", "iced latte", "americano"};
		foodList.addAll(Arrays.asList(str));
		
		list = (ListView)findViewById(R.id.listView1);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		adapter.addAll(foodList);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(this);
	}

	private void deleteSearchIcon() {
		Resources res = getResources();
		int id = res.getIdentifier("android:id/search_mag_icon", null, null);
		ImageView img = (ImageView)search.findViewById(id);
		LayoutParams params = new LayoutParams(0, 0);
		img.setLayoutParams(params);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	public boolean onQueryTextChange(String value) {
		adapter.clear();
		for (String food : foodList) {
			if (food.contains(value)) {
				adapter.add(food);
			}
		}
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String value) {
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		String name = (String)adapter.getItemAtPosition(position);
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra("name", name);
		startActivity(intent);
	}
}
