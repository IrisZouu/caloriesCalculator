package com.kun.food;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends Activity implements OnClickListener {
	private double foodAmount;
	private String foodName;
	
	private Button[] buttons = new Button[2];
	private EditText editText;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
