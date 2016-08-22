package com.alburaq.simled;

import com.alburaq.simled.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePayment extends Activity {
	Button Jbtn_ok, Jbtn_update;
	EditText Jedt_id, Jedt_amt;
	TextView Jtv_data;
	String s,name;
	double pending,paid;
	final DBAdapter db = new DBAdapter(this);
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.updatepayment);
		
		Jbtn_ok = (Button)findViewById(R.id.btn_id_ok);
		Jbtn_update = (Button)findViewById(R.id.btn_amt_ok);
		
		Jedt_id = (EditText)findViewById(R.id.edt_id_inp);
		Jedt_amt = (EditText)findViewById(R.id.edt_amt_inp);
		
		Jtv_data = (TextView)findViewById(R.id.tv_disp_data);
		
		Jedt_amt.setEnabled(false);
		Jbtn_update.setEnabled(false);
		
		Jbtn_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				db.open();
				// TODO Auto-generated method stub
				if(Jedt_id.getText().toString().contentEquals(""))
				{
					Toast.makeText(UpdatePayment.this, "Enter the bill no.", Toast.LENGTH_LONG).show();
					Jedt_id.setFocusable(true);
				}
				else
				{
					db.open();
					Cursor c = db.getIdData(Integer.parseInt(Jedt_id.getText().toString()));
					if(c.moveToFirst())
					{
						s="Bill no.: " + c.getString(0) + "\n" +
								"Date: " + c.getString(1) + "\n" +
								"Name: " + c.getString(2) + "\n" +
								"Mob no.: " + c.getString(3) + "\n" +
								"Type: " + c.getString(4) + "\n" +
								"No of bags.: " + c.getString(5) + "\n" +
								"Total amt.: " + c.getString(6) + "\n" +
								"Paid amt.: " + c.getString(7) + "\n" +
								"Remaining amt.: " + c.getString(8) + "\n"  ;	
								Jtv_data.setText(s);
								if(Double.parseDouble(c.getString(8).toString())>0)
								{
									pending = Double.parseDouble(c.getString(8).toString());
									paid = Double.parseDouble(c.getString(7).toString());
									name = c.getString(2);
									Jedt_amt.setEnabled(true);
									Jbtn_update.setEnabled(true);
									Jedt_id.setEnabled(false);
									Jbtn_ok.setEnabled(false);
								}
								else
								{
									Toast.makeText(UpdatePayment.this,
                                            "No pending payment for this customer.",
                                            Toast.LENGTH_LONG).show();
									Jedt_id.setFocusable(true);
									Jedt_id.setText("");
								}
					}
					else
					{
						Toast.makeText(UpdatePayment.this,
                                "No Such bill Enter bill no properly",
                                Toast.LENGTH_LONG).show();
						Jedt_id.setFocusable(true);
						Jtv_data.setText("");
						Jedt_id.setText("");
					}
				}
				db.close();
			}
		});
		
		Jbtn_update.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.open();
				if(Double.parseDouble(Jedt_amt.getText().toString())>pending)
				{
					Toast.makeText(UpdatePayment.this,
                            "Your Balance amt is "+pending,
                            Toast.LENGTH_LONG).show();
					Jedt_amt.setFocusable(true);
					Jedt_amt.setText("");
				}
				else
				{
					double remain;
					String status = "";
					if(Double.parseDouble(Jedt_amt.getText().toString())<pending)
					{
						remain = pending - Double.parseDouble(Jedt_amt.getText().toString());
						status = "PENDING";
					}
					else
					{
						remain = 0;
						status = "PAID";
					}
					paid+=Double.parseDouble(Jedt_amt.getText().toString());
					db.updatePayment(name, Integer.parseInt(Jedt_id.getText().toString()),
                            paid, remain, status, Double.parseDouble(Jedt_amt.getText().toString()));
					db.close();
					Toast.makeText(UpdatePayment.this, "Updated", Toast.LENGTH_LONG).show();
					finish();
				}				
				
				
			}
		});
		
		
		
	}
	
}
