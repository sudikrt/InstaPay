package com.alburaq.simled;

import java.util.Calendar;

import com.alburaq.simled.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Party extends Activity{

	Button Jbtn_save;
	EditText Jedt_date, Jedt_name, Jedt_mob, Jedt_no_of_bags, Jedt_rate_per_bags, Jedt_tot_amt,
	Jedt_paid_amt, Jedt_rem_amt;
	RadioGroup grp;
	String selectedItem="";
	final DBAdapter db = new DBAdapter(this);
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.party);
		
		
		
		InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE); 

		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                   InputMethodManager.HIDE_NOT_ALWAYS);
		
		Jbtn_save = (Button)findViewById(R.id.btn_save_party);
		Jedt_date = (EditText)findViewById(R.id.ed_date);
		Jedt_name = (EditText)findViewById(R.id.ed_name);
		Jedt_mob = (EditText)findViewById(R.id.ed_mob);
		Jedt_no_of_bags = (EditText)findViewById(R.id.et_no_of_bags);
		Jedt_rate_per_bags = (EditText)findViewById(R.id.et_rate_per_bags);
		Jedt_tot_amt = (EditText)findViewById(R.id.et_tot_amt);
		Jedt_paid_amt = (EditText)findViewById(R.id.et_paid_amt);
		Jedt_rem_amt = (EditText)findViewById(R.id.et_rem_amt);
		grp = (RadioGroup)findViewById(R.id.rgrp_party);
		
		
		
		Jedt_tot_amt.setKeyListener(null);
		Jedt_rem_amt.setKeyListener(null);
		
		grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				db.open();
				switch(checkedId)
				{
				case R.id.radio_chicken_party:
						selectedItem="Chicken Manure";
						Jedt_rate_per_bags.setText(String.valueOf(db.getRate(selectedItem)));
						break;
				case R.id.radio_sheep_party:
						selectedItem="Sheep Manure";
						Jedt_rate_per_bags.setText(String.valueOf(db.getRate(selectedItem)));
						break;
				}
				db.close();
			}
		});
		Jedt_tot_amt.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Jedt_tot_amt.setKeyListener(null);
				InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE); 

				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
				if(!((Jedt_no_of_bags.getText().toString().contentEquals(""))||
                                    (Jedt_rate_per_bags.getText().toString().contentEquals("")))){
				double d=Integer.parseInt(Jedt_no_of_bags.getText().toString()) *
                                        Double.parseDouble(Jedt_rate_per_bags.getText().toString());
				Jedt_tot_amt.setText(String.valueOf(d));
				}
				return false;
			}
		});
		
		Jedt_rem_amt.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Jedt_rem_amt.setKeyListener(null);
				InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE); 

				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
				if(!((Jedt_paid_amt.getText().toString().contentEquals("")) ||
                                            (Jedt_tot_amt.getText().toString().contentEquals("")))){
					double d=Double.parseDouble(Jedt_tot_amt.getText().toString()) -
                                            Double.parseDouble(Jedt_paid_amt.getText().toString());
					Jedt_rem_amt.setText(String.valueOf(d));
				}return false;
			}
		});
		
		Jedt_date.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE); 

				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
				class  DatePickerFragment extends DialogFragment implements
                                                            DatePickerDialog.OnDateSetListener{

					/* (non-Javadoc)
					 * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
					 */
					@Override
					
					public Dialog onCreateDialog(Bundle savedInstanceState) {
						// TODO Auto-generated method stub
						final Calendar c = Calendar.getInstance();
				        int year = c.get(Calendar.YEAR);
				        int month = c.get(Calendar.MONTH);
				        int day = c.get(Calendar.DAY_OF_MONTH);

				        // Create a new instance of DatePickerDialog and return it
				        return new DatePickerDialog(getActivity(), this, year, month, day);
					}

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						Jedt_date.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
					}					
				}
				DialogFragment n=new DatePickerFragment();
				n.show(getFragmentManager(), "datePicker");
			}
		});
		
		Jbtn_save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.open();
				if((Jedt_date.getText().toString()).contentEquals(""))
				{
					Toast.makeText(Party.this, "Enter the date", Toast.LENGTH_LONG).show();
					Jedt_date.setFocusable(true);
				}
				else if((Jedt_name.getText().toString()).contentEquals(""))
				{
					Toast.makeText(Party.this, "Enter the name", Toast.LENGTH_LONG).show();
					Jedt_name.setFocusable(true);
				}
				else if((Jedt_mob.getText().toString()).contentEquals(""))
				{
					Toast.makeText(Party.this, "Enter the mobile no.", Toast.LENGTH_LONG).show();
					Jedt_mob.setFocusable(true);
				}
				else if(grp.getCheckedRadioButtonId() == -1)
				{
					Toast.makeText(Party.this, "Select the manure.", Toast.LENGTH_LONG).show();
					grp.setFocusable(true);
				}
				else if((Jedt_no_of_bags.getText().toString()).contentEquals(""))
				{
					Toast.makeText(Party.this, "Enter the no of bags purchased",
                                                                    Toast.LENGTH_LONG).show();
					Jedt_no_of_bags.setFocusable(true);
				}
				else if((Jedt_paid_amt.getText().toString()).contentEquals(""))
				{
					Toast.makeText(Party.this, "Enter the amount paid", Toast.LENGTH_LONG).show();
					Jedt_paid_amt.setFocusable(true);
				}
				else if((Jedt_tot_amt.getText().toString()).contentEquals("") ||
                                        (Jedt_rem_amt.getText().toString()).contentEquals(""))
				{
					Toast.makeText(Party.this, "Fill all the fields", Toast.LENGTH_LONG).show();
				}
				else if(db.getCount(selectedItem)<Integer.parseInt(Jedt_no_of_bags.getText().toString())){
					Jedt_paid_amt.setText("");
					Jedt_tot_amt.setText("");
					Jedt_rem_amt.setText("");
					Jedt_no_of_bags.setText("");
					Jedt_no_of_bags.setFocusable(true);
					Toast.makeText(Party.this, "You can purchase only "+
                            db.getCount(selectedItem) + " "+ selectedItem + " bags",
                                                                Toast.LENGTH_LONG).show();
				}
				else if((Double.parseDouble(Jedt_paid_amt.getText().toString())) >
                                            (Double.parseDouble(Jedt_tot_amt.getText().toString()))){
					Jedt_paid_amt.setFocusable(true);
					Jedt_paid_amt.setText("");
					Jedt_rem_amt.setText("");
					Toast.makeText(Party.this, "Amount paid must not exceed total amount",
                                                                    Toast.LENGTH_LONG).show();
				}
				else{				
			    long id;
			    id=db.insertParty(Jedt_date.getText().toString(), 
			    			Jedt_name.getText().toString(), Jedt_mob.getText().toString(), 
			    			selectedItem, Integer.parseInt(Jedt_no_of_bags.getText().toString()), 
			    			Double.parseDouble(Jedt_rate_per_bags.getText().toString()), 
			    			Double.parseDouble(Jedt_tot_amt.getText().toString()), 
			    			Double.parseDouble(Jedt_paid_amt.getText().toString()), 
			    			Double.parseDouble(Jedt_rem_amt.getText().toString()));
			    db.close();	
			    Toast.makeText(Party.this, "Saved",Toast.LENGTH_LONG).show();
			    try {
					Class c=Class.forName("com.alburaq.simled.Bill");
					Intent i=new Intent(Party.this,c);
					startActivity(i);
					finish();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
				}
		}
		});
	
		
	}
}
