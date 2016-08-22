/**
 * 
 */
package com.alburaq.simled;

import com.alburaq.simled.R;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Sudarshan
 *
 */
public class ShowPayList extends Activity {

	final DBAdapter db = new DBAdapter(this);
	Button Jbtn_ok;
	EditText Jedt_inp;
	TextView Jtv_bill;
	String s = "";
	
	TableLayout t1;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpaylist);
		
		Jbtn_ok = (Button)findViewById(R.id.btn_show_pay);
		Jedt_inp = (EditText)findViewById(R.id.edt_show_pay_inp);
		Jtv_bill = (TextView)findViewById(R.id.tv_show_pay);
		t1 = (TableLayout)findViewById(R.id.tbl_show_pay);
		
		Jbtn_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.open();
				if(Jedt_inp.getText().toString().contentEquals(""))
				{
					Toast.makeText(ShowPayList.this, "Enter the id/bill no. properly",
                                                                        Toast.LENGTH_LONG).show();
					Jedt_inp.setFocusable(true);
					t1.removeAllViewsInLayout();
					Jtv_bill.setText("Bill Details");
				}
				else
				{
					t1.removeAllViewsInLayout();
					Cursor c=db.getIdData(Integer.parseInt(Jedt_inp.getText().toString()));
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
								"Remaining amt.: " + c.getString(8) + "\n" + 
								"Status : " + c.getString(9) + "\n";	
								Jtv_bill.setText("Bill Details" + "\n" + "\n" + s);	
								
								int count=0;
								Cursor cr = db.getBalanceDetails
                                        (Integer.parseInt(Jedt_inp.getText().toString()));
								if(cr.moveToFirst())
								{
									TableRow tr_head = new TableRow(ShowPayList.this);
									tr_head.setId(65535);
									tr_head.setBackgroundColor(Color.CYAN);
									tr_head.setLayoutParams(new LayoutParams
                                            (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
									
									TextView id = new TextView(ShowPayList.this);
							        id.setId(65536);
							        id.setText("ID");
							        id.setTextColor(Color.RED);
							        id.setPadding(5, 5, 5, 5);
							        tr_head.addView(id);
							        
							        TextView date = new TextView(ShowPayList.this);
							        date.setId(65537);
							        date.setText("Date");
							        date.setTextColor(Color.RED);
							        date.setPadding(10, 5, 10, 5);
							        tr_head.addView(date);
							        
							        TextView name = new TextView(ShowPayList.this);
							        name.setId(65538);
							        name.setText("Name");
							        name.setTextColor(Color.RED);
							        name.setPadding(15, 5, 15, 5);
							        tr_head.addView(name);
							        
							        
							        
							        TextView paid = new TextView(ShowPayList.this);
							        paid.setId(65540);
							        paid.setText("Amt.");
							        paid.setTextColor(Color.RED);
							        paid.setPadding(10, 5, 10, 5);
							        tr_head.addView(paid);
							        
							        TextView bap = new TextView(ShowPayList.this);
							        bap.setId(65541);
							        bap.setText("Tot paid amt.");
							        bap.setTextColor(Color.RED);
							        bap.setPadding(10, 5, 10, 5);
							        tr_head.addView(bap);
							        
							        TextView bbp = new TextView(ShowPayList.this);
							        bbp.setId(65539);
							        bbp.setText("Bal. Amt.");
							        bbp.setTextColor(Color.RED);
							        bbp.setPadding(10, 5, 10, 5);
							        tr_head.addView(bbp);
							        
							        t1.addView(tr_head, new TableLayout.LayoutParams
                                            (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
									do{
										
										
										TableRow tr = new TableRow(ShowPayList.this);
							             
							             tr.setId(65547+count);
							             tr.setLayoutParams(new LayoutParams
                                                 (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
										
							             int id_str = Integer.parseInt((cr.getString(0).toString()));
										String date_str = cr.getString(1).toString();
										String name_str = cr.getString(2).toString();
										double bbp_str = cr.getDouble(3);
										double paid_str = cr.getDouble(4);
										double bap_str = cr.getDouble(5);
										
										
										  TextView id_t = new TextView(ShowPayList.this);
								             id_t.setId(65548+count++);
								             id_t.setText(String.valueOf(id_str));
								             id_t.setTextColor(Color.RED);
								             id_t.setPadding(5, 5, 5, 5);
								             tr.addView(id_t);
								             
								             TextView date_t = new TextView(ShowPayList.this);
								             date_t.setId(65549+count++);
								             date_t.setText(date_str);
								             date_t.setTextColor(Color.RED);
								             date_t.setPadding(10, 5, 10, 5);
								             tr.addView(date_t);
								             
								             TextView name_t = new TextView(ShowPayList.this);
								             name_t.setId(65550+count++);
								             name_t.setText(name_str);
								             name_t.setTextColor(Color.RED);
								             name_t.setPadding(15, 5, 15, 5);
								             tr.addView(name_t);
								             
								             
								             
								             TextView type_t = new TextView(ShowPayList.this);
								             type_t.setId(65552+count++);
								             type_t.setText(String.valueOf(paid_str));
								             type_t.setTextColor(Color.RED);
								             type_t.setPadding(10, 5, 10, 5);
								             tr.addView(type_t);
								             
								             TextView bag_t = new TextView(ShowPayList.this);
								             bag_t.setId(65553+count++);
								             bag_t.setText(String.valueOf(bap_str));
								             bag_t.setTextColor(Color.RED);
								             bag_t.setPadding(10, 5, 10, 5);
								             tr.addView(bag_t);
								             
								             
								             TextView mob_t = new TextView(ShowPayList.this);
								             mob_t.setId(65551+count++);
								             mob_t.setText(String.valueOf(bbp_str));
								             mob_t.setTextColor(Color.RED);
								             mob_t.setPadding(10, 5, 10, 5);
								             tr.addView(mob_t);
								             
								             t1.addView(tr, new TableLayout.LayoutParams
                                                     (LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
								             count++;
										
									} while(cr.moveToNext());
								}
								else
								{
									if(c.getString(9).contentEquals("PENDING"))
									{
										Toast.makeText(getApplicationContext(),
                                                "This person had not yet paid the balance amount",
                                                Toast.LENGTH_LONG).show();
									}
									else
									{
										Toast.makeText(getApplicationContext(),
                                                "This person had made a single full payment",
                                                Toast.LENGTH_LONG).show();
									}
									t1.removeAllViewsInLayout();
									Jedt_inp.setFocusable(true);
									
								}
								Jedt_inp.setText("");	
								InputMethodManager inputManager = (InputMethodManager)
						                getSystemService(Context.INPUT_METHOD_SERVICE); 

								inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						                   InputMethodManager.HIDE_NOT_ALWAYS);
					}
					else
					{
						Toast.makeText(ShowPayList.this, "No Such bill Enter bill no properly",
                                                            Toast.LENGTH_LONG).show();
						Jedt_inp.setText("");
						t1.removeAllViewsInLayout();
						Jtv_bill.setText("Bill Details");
						Jedt_inp.setFocusable(true);	
						InputMethodManager inputManager = (InputMethodManager)
				                getSystemService(Context.INPUT_METHOD_SERVICE); 

						inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				                   InputMethodManager.HIDE_NOT_ALWAYS);
					}
				}
				db.close();
			}
		});
	}

}
