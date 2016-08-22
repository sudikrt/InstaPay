package com.alburaq.simled;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

/**
 * Created by root on 6/4/16.
 */
public class Admin_ctrl extends Activity {
    Button Jbtn_up_rate,
            Jbtn_exit, Jbtn_upass;
    DBAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_ctrl);

        Jbtn_up_rate = (Button)findViewById(R.id.btn_up_rate);
        Jbtn_exit = (Button)findViewById(R.id.btn_exit);
        Jbtn_upass = (Button) findViewById(R.id.btn_upass);


        dbAdapter = new DBAdapter(this);



        /* To generate the report */
        Jbtn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dbAdapter.open();
                exportToExcel(dbAdapter.getAllParty());
                dbAdapter.close();
                Toast.makeText(getApplicationContext(),"Generated Successfully Check in Device Storage for the report", Toast.LENGTH_LONG).show();
            }
        });

        /* To Update the Price*/
        Jbtn_up_rate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    Class c=Class.forName("com.alburaq.simled.UpdateRate");
                    Intent i=new Intent(Admin_ctrl.this,c);
                    startActivity(i);
                    //finish();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        /* To Update the pasword */
        Jbtn_upass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    Class c=Class.forName("com.alburaq.simled.UpdatePass");
                    Intent i=new Intent(Admin_ctrl.this,c);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }



    private void exportToExcel(Cursor cursor) {
        final String fileName = "Report.xlsx";

        //Saving file in external storage
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/Insta Pay");

        //create directory if not exist
        if(!directory.isDirectory()){
            directory.mkdirs();
        }

        //file path
        File file = new File(   directory, fileName);

        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook;

        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet("MyShoppingList", 0);

            try {
                sheet.addCell(new Label(0, 0, "ID")); // column and row
                sheet.addCell(new Label(1,0, "Date"));
                sheet.addCell(new Label(2, 0, "Name"));
                sheet.addCell(new Label(3, 0, "Mobile"));
                sheet.addCell(new Label(4, 0, "Type"));
                sheet.addCell(new Label(5, 0, "No of Bags"));
                sheet.addCell(new Label(6, 0, "Rate pr Bag"));
                sheet.addCell(new Label(7, 0, "Tot Amt"));
                sheet.addCell(new Label(8, 0, "Paid Amt"));
                sheet.addCell(new Label(9, 0, "Balance"));
                sheet.addCell(new Label(10, 0, "Status"));

                if (cursor.moveToFirst()) {
                    do {
                        String _id = cursor.getString(cursor.getColumnIndex("_id"));
                        String date_t = cursor.getString(cursor.getColumnIndex("date"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String mobile = cursor.getString(cursor.getColumnIndex("mob"));
                        String type = cursor.getString(cursor.getColumnIndex("type"));
                        String no_bag = String.valueOf(cursor.getInt(cursor.getColumnIndex("bag")));
                        String rpb = String.valueOf(cursor.getDouble(cursor.getColumnIndex("rpb")));
                        String tamt = String.valueOf(cursor.getDouble(cursor.getColumnIndex("tamt")));
                        String pamt = String.valueOf(cursor.getDouble(cursor.getColumnIndex("pamt")));
                        String balance = String.valueOf(cursor.getDouble(cursor.getColumnIndex("ramt")));
                        String status = cursor.getString(cursor.getColumnIndex("status"));



                        int i = cursor.getPosition() + 1;
                        sheet.addCell(new Label(0, i, _id));
                        sheet.addCell(new Label(1, i, date_t));
                        sheet.addCell(new Label(3, i, name));
                        sheet.addCell(new Label(4, i, mobile));
                        sheet.addCell(new Label(5, i, type));
                        sheet.addCell(new Label(6, i, no_bag));
                        sheet.addCell(new Label(7, i, tamt));
                        sheet.addCell(new Label(8, i, pamt));
                        sheet.addCell(new Label(9, i, balance));
                        sheet.addCell(new Label(10, i, status));

                    } while (cursor.moveToNext());
                }
                //closing cursor
                cursor.close();
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            workbook.write();
            try {
                workbook.close();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
