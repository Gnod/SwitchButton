package com.example.switchbutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.switchbutton.SwitchButton.OnSwitchListener;

public class SwitchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_switch);
		
		SwitchButton switchBtn = (SwitchButton) findViewById(R.id.btn_switch);
		switchBtn.setOnSwitchListener(new OnSwitchListener() {
			@Override
			public boolean onSwitch(SwitchButton v, boolean isRight) {
				Toast.makeText(getApplicationContext(), isRight ? "Right" : "Left",	
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_switch, menu);
        return true;
    }
}
