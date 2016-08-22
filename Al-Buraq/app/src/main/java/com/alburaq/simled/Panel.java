package com.alburaq.simled;

import com.alburaq.simled.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Panel extends Activity {

	Button Jbtn_stk,
            Jbtn_sls,
            Jbtn_stk_det,
            Jbtn_sls_det,
            Jbtn_go_det,
            Jbtn_pen_det,
            Jbtn_update_pay,
            Jbtn_show_pmt, Jb_admin;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.panel);


		
        Jb_admin = (Button) findViewById(R.id.btn_apnl);
		Jbtn_stk = (Button)findViewById(R.id.btn_stk_entry);
		Jbtn_stk_det = (Button)findViewById(R.id.btn_stk_details);
		Jbtn_sls = (Button)findViewById(R.id.btn_sls_entry);
		Jbtn_sls_det = (Button)findViewById(R.id.btn_sls_details);
		Jbtn_go_det = (Button)findViewById(R.id.btn_go_det);
		Jbtn_pen_det = (Button)findViewById(R.id.btn_pen_det);
		Jbtn_update_pay = (Button)findViewById(R.id.btn_update_pay);
		Jbtn_show_pmt = (Button)findViewById(R.id.btn_show_pmt);

        //Jbtn_up_rate.setVisibility(View.INVISIBLE);
        //Jbtn_pen_det.setVisibility(View.INVISIBLE);
        //Jbtn_update_pay.setVisibility(View.INVISIBLE);
        //Jbtn_show_pmt.setVisibility(View.INVISIBLE);
        //Jbtn_exit.setVisibility(View.INVISIBLE);


        /* Admin ctrl*/
		Jb_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Class c=Class.forName("com.alburaq.simled.Admin_ctrl");
                    Intent i=new Intent(Panel.this,c);
                    startActivity(i);
                    //finish();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        /* Show the Payment list */
        Jbtn_show_pmt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Class c=Class.forName("com.alburaq.simled.ShowPayList");
					Intent i=new Intent(Panel.this,c);
					startActivity(i);
					//finish();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Jbtn_stk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Class c=Class.forName("com.alburaq.simled.MainActivity");
					Intent i=new Intent(Panel.this,c);
					startActivity(i);
					//finish();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Jbtn_stk_det.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Class c=Class.forName("com.alburaq.simled.GoDownView");
					Intent i=new Intent(Panel.this,c);
					startActivity(i);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Jbtn_sls.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Class c=Class.forName("com.alburaq.simled.Party");
					Intent i=new Intent(Panel.this,c);
					startActivity(i);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Jbtn_sls_det.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Class c=Class.forName("com.alburaq.simled.SalesView");
					Intent i=new Intent(Panel.this,c);
					startActivity(i);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Jbtn_go_det.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String s="";
				final DBAdapter db = new DBAdapter(Panel.this);
				db.open();
				Cursor c = db.getAllInven();
				if(c.moveToFirst())
				{
					s = "GoDown Details" + "\n" + "No. of Chicken Manure Bags : " +
                            Integer.parseInt(c.getString(1)) + "\n" +  "No. of Sheep Manure Bags : "
                            + Integer.parseInt(c.getString(2)) +"\n";
					
				}
				 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Panel.this);
				 alertDialogBuilder.setMessage(s);
				 alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				 AlertDialog alertDialog = alertDialogBuilder.create();
			     alertDialog.show();
			}
		});
		Jbtn_pen_det.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Class c=Class.forName("com.alburaq.simled.PendingView");
					Intent i=new Intent(Panel.this,c);
					startActivity(i);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Jbtn_update_pay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Class c=Class.forName("com.alburaq.simled.UpdatePayment");
					Intent i=new Intent(Panel.this,c);
					startActivity(i);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}



}
