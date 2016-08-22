package com.alburaq.simled;

import com.alburaq.simled.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UpdateRate extends Activity{

	Button Jbtn_ok;
	EditText Jedt_rate;
	RadioGroup rg;
	String selectedItem="";
	final DBAdapter db = new DBAdapter(this);
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updaterate);
		
		Jbtn_ok = (Button)findViewById(R.id.btn_update_rate);
		Jedt_rate = (EditText)findViewById(R.id.edt_update_rate);
		rg = (RadioGroup)findViewById(R.id.r_update_rate);
		
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId)
				{
				case R.id.radio_chicken_rate:
						selectedItem="Chicken Manure";
						break;
				case R.id.radio_sheep_rate:
						selectedItem="Sheep Manure";
						break;
				}
			}
		});
		Jbtn_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(rg.getCheckedRadioButtonId() == -1)
				{
					Toast.makeText(getApplicationContext(), "Select the manure type",
                                                                        Toast.LENGTH_LONG).show();
				}
				else if(Jedt_rate.getText().toString().contentEquals(""))
				{
					Toast.makeText(getApplicationContext(), "Enter the new price ",
                                                                        Toast.LENGTH_LONG).show();
					Jedt_rate.setFocusable(true);
				}
				else
				{
					double d = Double.parseDouble(Jedt_rate.getText().toString());
					db.open();
					db.updateRate(d, selectedItem);
					Toast.makeText(getApplicationContext(), "Updated Successfully",
                                                                        Toast.LENGTH_LONG).show();
					finish();
				}
				
			}
		});
		
	}

}
