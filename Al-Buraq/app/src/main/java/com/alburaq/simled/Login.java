package com.alburaq.simled;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by root on 9/3/16.
 */
public class Login extends Activity {

    Button Jbtn;
    DBAdapter dbAdapter;
    EditText JUname, JPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Jbtn = (Button) findViewById(R.id.logbtn);
        JUname = (EditText) findViewById(R.id.logUname);
        JPass = (EditText) findViewById(R.id.logPass);

        dbAdapter = new DBAdapter(Login.this);

        Jbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = JUname.getText().toString();
                String uPass = JPass.getText().toString();

                if (uName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter UserName",
                                                                        Toast.LENGTH_LONG).show();
                    JUname.setFocusable(true);
                    return;
                }
                if (uPass.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Password",
                                                                        Toast.LENGTH_LONG).show();
                    JPass.setFocusable(true);
                    return;
                }

                dbAdapter.open();
                if (dbAdapter.checkLogin(uName, uPass)) {
                    startActivity(new Intent(Login.this,Panel.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "UserName or password mismatch",
                                                                        Toast.LENGTH_LONG).show();
                }
                dbAdapter.close();
            }
        });
    }
}
