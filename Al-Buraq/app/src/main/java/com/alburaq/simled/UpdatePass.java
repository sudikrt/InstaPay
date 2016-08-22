package com.alburaq.simled;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 6/4/16.
 */
public class UpdatePass extends Activity {
    Button btn;
    EditText Jet1, Jet2;
    DBAdapter dbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepass);

        dbAdapter = new DBAdapter(this);

        Jet1 = (EditText) findViewById(R.id.edt_pass1);
        Jet2 = (EditText) findViewById(R.id.edt_pass2);

        btn = (Button) findViewById(R.id.btn_up_pass);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Jet1.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Enter the password properly",Toast.LENGTH_LONG).show();
                    Jet1.requestFocus();
                }
                else if (Jet2.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Re-Enter the password properly",Toast.LENGTH_LONG).show();
                    Jet2.requestFocus();
                }
                else {
                    if (Jet1.getText().toString().equals(Jet2.getText().toString())) {

                        dbAdapter.open();
                        dbAdapter.updatePassword(Jet1.getText().toString());
                        dbAdapter.close();
                        Toast.makeText(getApplicationContext(),"Password Updated", Toast.LENGTH_LONG).show();
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), "Password Mismatch Re-enter", Toast.LENGTH_SHORT).show();
                        Jet1.setText("");
                        Jet2.setText("");
                        Jet1.requestFocus();
                    }
                }
            }
        });
    }
}
