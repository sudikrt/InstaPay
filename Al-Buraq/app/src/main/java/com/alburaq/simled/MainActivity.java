package com.alburaq.simled;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import com.alburaq.simled.R;



import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText Jet_date;
	RadioGroup J_group;
	Button Jbtn_save;
	EditText Jet_no_of_bags;
	String selectedItem="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.godownentry);
		final DBAdapter db = new DBAdapter(this);
		
		Jet_date = (EditText)findViewById(R.id.et_date);
		Jbtn_save = (Button)findViewById(R.id.btn_save);
		J_group = (RadioGroup)findViewById(R.id.rgrp);
		Jet_no_of_bags = (EditText)findViewById(R.id.et_no_of_bags);		
		
	
		Jet_date.setOnKeyListener(null);
		Jbtn_save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				InputMethodManager inputManager = (InputMethodManager)
		                getSystemService(Context.INPUT_METHOD_SERVICE); 

				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
		                   InputMethodManager.HIDE_NOT_ALWAYS);
				if(Jet_date.getText().toString().contentEquals(""))
				{
					Jet_date.setFocusable(true);
					Toast.makeText(MainActivity.this, "Enter the proper date",
                                                                        Toast.LENGTH_LONG).show();
				}
				else if(J_group.getCheckedRadioButtonId() == -1)
				{
					J_group.setFocusable(true);
					Toast.makeText(MainActivity.this, "Select Manure type",
                                                                        Toast.LENGTH_LONG).show();
				}
				else if(Jet_no_of_bags.getText().toString().contentEquals(""))
				{
					Jet_no_of_bags.setFocusable(true);
					Toast.makeText(MainActivity.this, "Enter the number of bags",
                                                                        Toast.LENGTH_LONG).show();
				}
				else{
					db.open();
				    long id = db.insertContact(Jet_date.getText().toString(), selectedItem ,
                                    Integer.parseInt(Jet_no_of_bags.getText().toString()), "IN");
				    db.close();	
				    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG).show();
				    finish();
				}
					
			}
		});
				
		J_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId)
				{
				case R.id.radio_chicken:
						selectedItem="Chicken Manure";
						break;
				case R.id.radio_sheep:
						selectedItem="Sheep Manure";
						break;
				}
			}
		});
		Jet_date.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
						Jet_date.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
						
					}
					
				}
				DialogFragment n=new DatePickerFragment();
				n.show(getFragmentManager(), "datePicker");
			}
		});
		
	}
}
