package com.example.arpit.usbcom;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.usb.driver.uart.ReadLisener;

import java.text.DateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    Button btOpen, btClose,  btWrite;
int count=0;
    TextView tvRead,time,t1,t2,t3;
    Spinner spBaud;
    CheckBox cbAutoscroll;
String str1="",str2="";
    Physicaloid mPhysicaloid; // initialising library

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
time=(TextView)findViewById(R.id.textView6);
        btOpen  = (Button) findViewById(R.id.btOpen);
        btClose = (Button) findViewById(R.id.btClose);
        Typeface face2 = Typeface.createFromAsset(getAssets(),
                "fonts/Kid Kosmic.ttf");


        t1  = (TextView) findViewById(R.id.textView4);
        t2  = (TextView) findViewById(R.id.textView5);
        t3  = (TextView) findViewById(R.id.textView3);

        tvRead  = (TextView) findViewById(R.id.tvRead);

        mPhysicaloid = new Physicaloid(this);
        mPhysicaloid.setBaudrate(9600);
    }

    public void onClickOpen(View v) {
        //String baudtext = spBaud.getSelectedItem().toString();


        if(mPhysicaloid.open()) {



            mPhysicaloid.addReadListener(new ReadLisener() {
                @Override
                public void onRead(int size) {
                    byte[] buf = new byte[255];
                   int n= mPhysicaloid.read(buf);
                    String readMessage = new String(buf,0,n);
                    tvAppend(tvRead, readMessage);

                }
            });
        } else {
            Toast.makeText(this, "Cannot open", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickClose(View v) {
        if(mPhysicaloid.close()) {
            mPhysicaloid.clearReadListener();

        }
    }





    Handler mHandler = new Handler();
    private void tvAppend(TextView tv, String cs) {
        final TextView ftv = tv;
final String cs1=cs;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
try {
 str1=str1.concat(cs1);

if(cs1.endsWith("\n")) {

    String s3=str1;
 //   Toast.makeText(Main2Activity.this, s3+"", Toast.LENGTH_SHORT).show();

    s3 = cs1.substring(cs1.indexOf("(") + 1, cs1.indexOf(")"));
    //t2.setText(s1);
    tvRead.setText(s3);
    str1="";
}
}
catch(StringIndexOutOfBoundsException e){e.printStackTrace(); }

            }

        });

    }

}
