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


public class GoDownView extends Activity {
	TableLayout t1;
	final DBAdapter db = new DBAdapter(this);

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.godownview);
		t1 = (TableLayout) findViewById(R.id.tbl);
		
		TableRow tr_head = new TableRow(this);
		tr_head.setId(10);
		tr_head.setBackgroundColor(Color.CYAN);
		tr_head.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                                                                        LayoutParams.WRAP_CONTENT));
	
		TextView id = new TextView(this);
        id.setId(20);
        id.setText("ID");
        id.setTextColor(Color.RED);
        id.setPadding(5, 5, 5, 5);
        tr_head.addView(id);// add the column to the table row here

        TextView date = new TextView(this);
        date.setId(21);// define id that must be unique
        date.setText("DATE"); // set the text for the header 
        date.setTextColor(Color.RED); // set the color
        date.setPadding(10, 5, 10, 5); // set the padding (if required)
        tr_head.addView(date); // add the column to the table row here
        
        TextView type = new TextView(this);
        type.setId(22);
        type.setText("Type");
        type.setTextColor(Color.RED);
        type.setPadding(10, 5, 10, 5);
        tr_head.addView(type);// add the column to the table row here
        
        TextView bag = new TextView(this);
        bag.setId(23);
        bag.setText("Bag");
        bag.setTextColor(Color.RED);
        bag.setPadding(10, 5, 10, 5);
        tr_head.addView(bag);// add the column to the table row here
        
        TextView inout = new TextView(this);
        inout.setId(25);
        inout.setText("In/Out");
        inout.setTextColor(Color.RED);
        inout.setPadding(10, 5, 10, 5);
        tr_head.addView(inout);
        
        t1.addView(tr_head, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                                                        LayoutParams.WRAP_CONTENT));
	
       db.open();
       Cursor cursor=db.getAllGoDown();
       
       Integer count=0;
       if(cursor.moveToLast()){
       do{
    	   int id_str = Integer.parseInt((cursor.getString(0).toString()));
    	   String date_str = cursor.getString(1).toString();
    	   String type_str = cursor.getString(2).toString();
    	   int bag_str = Integer.parseInt(cursor.getString(3).toString());
    	   String inout_str = cursor.getString(4).toString();
       // Create the table row
       TableRow tr = new TableRow(this);
      
       tr.setId(100+count);
       tr.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	
       TextView id_t = new TextView(this);
       id_t.setId(200+count+1); 
       id_t.setText(String.valueOf(id_str));
       id_t.setPadding(5, 0, 5, 0);
       id_t.setTextColor(Color.RED);
       tr.addView(id_t);
       
       TextView labelDATE = new TextView(this);
       labelDATE.setId(200+count++); 
       labelDATE.setText(date_str);
       labelDATE.setPadding(10, 0, 10, 0);
       labelDATE.setTextColor(Color.RED);
       tr.addView(labelDATE);
       
       TextView type_t = new TextView(this);
       type_t.setId(200+count++);
       type_t.setText(type_str);
       type_t.setPadding(10, 0, 10, 10);
       type_t.setTextColor(Color.RED);
       tr.addView(type_t);
       
       TextView bag_t = new TextView(this);
       bag_t.setId(200+count++);
       bag_t.setText(String.valueOf(bag_str));
       bag_t.setTextColor(Color.RED);
       bag_t.setPadding(10, 0, 10, 10);
       tr.addView(bag_t);
       
       TextView inout_t = new TextView(this);
       inout_t.setId(200+count++);
       inout_t.setText(String.valueOf(inout_str));
       inout_t.setTextColor(Color.RED);
       inout_t.setPadding(10, 0, 10, 10);
       tr.addView(inout_t);
       
       t1.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                                                                        LayoutParams.WRAP_CONTENT));
      count++;
       }while (cursor.moveToPrevious());
       }
       db.close();
	}
}