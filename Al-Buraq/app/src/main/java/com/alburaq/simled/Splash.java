package com.alburaq.simled;

//import com.example.simledb.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splah);
		Thread t=new Thread(){
			public void run()
			{
				try{
					sleep(4000);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					Class c;
					try {
						c = Class.forName("com.alburaq.simled.Panel");
						Intent i = new Intent(Splash.this,c);
						startActivity(i);
						finish();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		};
		t.start();
	}

}
