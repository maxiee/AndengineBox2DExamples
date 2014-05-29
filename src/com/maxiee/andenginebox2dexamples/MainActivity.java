package com.maxiee.andenginebox2dexamples;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btnBox2DInitActivity;
	private Button btnWheelActivityButton;
	private Button btnCarActivityButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnBox2DInitActivity = (Button)findViewById(R.id.btnBox2DInitActivity);
		btnBox2DInitActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Box2DInitActivity.class);
				startActivity(intent);
			}
		});
		
		btnWheelActivityButton = (Button)findViewById(R.id.btnWheelActivity);
		btnWheelActivityButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, WheelActivity.class);
				startActivity(intent);
			}
		});
		
		btnCarActivityButton = (Button)findViewById(R.id.btnCarActivity);
		btnCarActivityButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CarActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
