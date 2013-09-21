package com.example.switchbutton;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
		
		SwitchButton switchBtnB = (SwitchButton)findViewById(R.id.btn_switch_b);
		switchBtnB.setSwitch(true, 0);
		switchBtnB.setOnSwitchListener(new OnSwitchListener() {
			
			@Override
			public boolean onSwitch(final SwitchButton v, boolean isRight) {
				if(isRight) {
					AlertDialog.Builder builder = new AlertDialog.Builder(SwitchActivity.this);
					builder.setMessage("Confirm to Switch Right?");
					builder.setPositiveButton("Confirm", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							v.setSwitch(true);
							
						}
					});
					builder.setNegativeButton("Cancel", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.show();
				}else {
					v.setSwitch(isRight);
				}
				return true;
			}
		});
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_switch, menu);
        return true;
    }
}
