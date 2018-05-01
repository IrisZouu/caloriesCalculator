package com.kun.food;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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


public class MainActivity extends Activity implements OnQueryTextListener, OnItemClickListener {
	private ArrayAdapter<String> adapter;
	private ArrayList<String> foodList = new ArrayList<String>();
	
	private SearchView search;
	private ListView list;

	private Exception exception;


	protected String doInBackground(Void... urls) {
		String email = getText(R.id.textView1).toString();

		try {
			URL url = new URL("https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/ingredients/9266/information?unit=gram&amount=100" + "email=" + email + "&apiKey=" + "xewLWQuRMcmshTSp4gVrM8l88FLPp1VHvcmjsnqym3ugMkeMqi");
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
