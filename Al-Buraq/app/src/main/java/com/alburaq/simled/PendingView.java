package com.alburaq.simled;

import com.alburaq.simled.R;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class PendingView extends Activity {

	TableLayout t1;
	TextView Jtv_bal_tot, Jtv_paid_tot,Jtv_bill_tot;
	final DBAdapter db = new DBAdapter(this);
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pendingview);
		
		Jtv_bal_tot = (TextView)findViewById(R.id.tv_bal_tot);
		Jtv_paid_tot = (TextView)findViewById(R.id.tv_paid_tot);
		Jtv_bill_tot = (TextView)findViewById(R.id.tv_bill_tot);
		
		t1 = (TableLayout) findViewById(R.id.tbl_pending);
		
		TableRow tr_head = new TableRow(this);
		tr_head.setId(65535);
		tr_head.setBackgroundColor(Color.CYAN);
		tr_head.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		
		
		TextView id = new TextView(this);
        id.setId(64536);
        id.setText("ID");
        id.setTextColor(Color.RED);
        id.setPadding(5, 5, 5, 5);
        tr_head.addView(id);
        
        TextView date = new TextView(this);
        date.setId(64537);
        date.setText("Date");
        date.setTextColor(Color.RED);
        date.setPadding(10, 5, 10, 5);
        tr_head.addView(date);
        
        TextView name = new TextView(this);
        name.setId(64538);
        name.setText("Name");
        name.setTextColor(Color.RED);
        name.setPadding(15, 5, 15, 5);
        tr_head.addView(name);
        
        TextView mob = new TextView(this);
        mob.setId(64539);
        mob.setText("Mob");
        mob.setTextColor(Color.RED);
        mob.setPadding(10, 5, 10, 5);
        tr_head.addView(mob);
        
        TextView type = new TextView(this);
        type.setId(64540);
        type.setText("Type");
        type.setTextColor(Color.RED);
        type.setPadding(10, 5, 10, 5);
        tr_head.addView(type);
        
        
        TextView bag = new TextView(this);
        bag.setId(64541);
        bag.setText("No.");
        bag.setTextColor(Color.RED);
        bag.setPadding(10, 5, 10, 5);
        tr_head.addView(bag);
        
        TextView tamt = new TextView(this);
        tamt.setId(65543);
        tamt.setText("TAMT");
        tamt.setTextColor(Color.RED);
        tamt.setPadding(10, 5, 10, 5);
        tr_head.addView(tamt);
        
        TextView pamt = new TextView(this);
        pamt.setId(65544);
        pamt.setText("PAMT");
        pamt.setTextColor(Color.RED);
        pamt.setPadding(10, 5, 10, 5);
        tr_head.addView(pamt);
        
        TextView ramt = new TextView(this);
        ramt.setId(65545);
        ramt.setText("Balance");
        ramt.setTextColor(Color.RED);
        ramt.setPadding(10, 5, 10, 5);
        tr_head.addView(ramt);
        
        t1.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        
        db.open();
        
        double bal = 0;
        double pd = 0;
        double tot = 0;
        Cursor cPen = db.getPendingAmt();
        if(cPen.moveToFirst())
        {
        	do{
        		bal += cPen.getDouble(2);
        		pd += cPen.getDouble(1);
        		tot += cPen.getDouble(0);
        	}while(cPen.moveToNext());
        	
        	Jtv_bal_tot.setText(Jtv_bal_tot.getText().toString() + " : " +String.valueOf(bal));
        	Jtv_paid_tot.setText(Jtv_paid_tot.getText().toString() + " : " +String.valueOf(pd));
        	Jtv_bill_tot.setText(Jtv_bill_tot.getText().toString() + " : " +String.valueOf(tot));
        }
        else
        {
        	Toast.makeText(PendingView.this, "No pending Payments", Toast.LENGTH_LONG).show();
        	Jtv_bal_tot.setText(Jtv_bal_tot.getText().toString() + " : " +String.valueOf(bal));
        	Jtv_paid_tot.setText(Jtv_paid_tot.getText().toString() + " : " +String.valueOf(pd));
        	Jtv_bill_tot.setText(Jtv_bill_tot.getText().toString() + " : " +String.valueOf(tot));
        }
        
        
        
        Cursor cursor=db.getAllPending();
        Integer count=1;
        if(cursor.moveToLast()){
           do{
        	  int id_str = Integer.parseInt((cursor.getString(0).toString()));
          	  String date_str = cursor.getString(1).toString();
          	  String name_str = cursor.getString(2).toString();
          	  String mob_str = cursor.getString(3).toString();
          	  String type_str = cursor.getString(4).toString();
          	  int bag_str = Integer.parseInt(cursor.getString(5).toString());
          	  
          	  double tamt_str = Double.parseDouble(cursor.getString(6).toString());
        	  double pamt_str = Double.parseDouble(cursor.getString(7).toString());
        	  double ramt_str = Double.parseDouble(cursor.getString(8).toString());
        	  
        	  TableRow tr = new TableRow(this);
              
              tr.setId(65547+count);
              tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
              
              
              TextView id_t = new TextView(this);
              id_t.setId(65548+count++);
              id_t.setText(String.valueOf(id_str));
              id_t.setTextColor(Color.RED);
              id_t.setPadding(5, 5, 5, 5);
              tr.addView(id_t);
              
              TextView date_t = new TextView(this);
              date_t.setId(65549+count++);
              date_t.setText(date_str);
              date_t.setTextColor(Color.RED);
              date_t.setPadding(10, 5, 10, 5);
              tr.addView(date_t);
              
              TextView name_t = new TextView(this);
              name_t.setId(65550+count++);
              name_t.setText(name_str);
              name_t.setTextColor(Color.RED);
              name_t.setPadding(15, 5, 15, 5);
              tr.addView(name_t);
              
              TextView mob_t = new TextView(this);
              mob_t.setId(65551+count++);
              mob_t.setText(mob_str);
              mob_t.setTextColor(Color.RED);
              mob_t.setPadding(10, 5, 10, 5);
              tr.addView(mob_t);
              
              TextView type_t = new TextView(this);
              type_t.setId(65552+count++);
              type_t.setText(type_str);
              type_t.setTextColor(Color.RED);
              type_t.setPadding(10, 5, 10, 5);
              tr.addView(type_t);
              
              TextView bag_t = new TextView(this);
              bag_t.setId(65553+count++);
              bag_t.setText(String.valueOf(bag_str));
              bag_t.setTextColor(Color.RED);
              bag_t.setPadding(10, 5, 10, 5);
              tr.addView(bag_t);
              
              TextView tamt_t = new TextView(this);
              tamt_t.setId(65555+count++);
              tamt_t.setText(String.valueOf(tamt_str));
              tamt_t.setTextColor(Color.RED);
              tamt_t.setPadding(10, 5, 10, 5);
              tr.addView(tamt_t);
              
              TextView pamt_t = new TextView(this);
              pamt_t.setId(65556+count++);
              pamt_t.setText(String.valueOf(pamt_str));
              pamt_t.setTextColor(Color.RED);
              pamt_t.setPadding(10, 5, 10, 5);
              tr.addView(pamt_t);
              
              TextView ramt_t = new TextView(this);
              ramt_t.setId(65557+count++);
              ramt_t.setText(String.valueOf(ramt_str));
              ramt_t.setTextColor(Color.RED);
              ramt_t.setPadding(10, 5, 10, 5);
              tr.addView(ramt_t);
              
              t1.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
              count++;
        	   
           }while(cursor.moveToPrevious());
        }
        db.close();
	}

}
