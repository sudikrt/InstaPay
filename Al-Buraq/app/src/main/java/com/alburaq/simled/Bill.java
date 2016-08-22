package com.alburaq.simled;

import com.alburaq.simled.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class Bill extends Activity {

	String s="";
	TextView tv;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bill);
		
		final DBAdapter db = new DBAdapter(this);
		
		tv = (TextView)findViewById(R.id.tv_b);
		db.open();
		Cursor c=db.getAllParty();
		if(c.moveToLast())
		{
			s="Bill no.: " + c.getString(0) + "\n" +
					"Date: " + c.getString(1) + "\n" +
					"Name: " + c.getString(2) + "\n" +
					"Mob no.: " + c.getString(3) + "\n" +
					"Type: " + c.getString(4) + "\n" +
					"No of bags.: " + c.getString(5) + "\n" +
					"Rate per bag.: " + c.getString(6) + "\n" +
					"Total amt.: " + c.getString(7) + "\n" +
					"Paid amt.: " + c.getString(8) + "\n" +
					"Remaining amt.: " + c.getString(9) + "\n" +
					"Status.: " + c.getString(10) + "\n" ;					
		}
		tv.setText(s);
		db.close();
	}

}
