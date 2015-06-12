package com.google.android.gms.location.sample.locationaddress;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainScreenActivity extends ActionBarActivity {

    private Button mHelpButton;

    private Button  mStatsButton;

    private Button mCrimeButton;

    private Button mQuickCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mHelpButton = (Button)findViewById(R.id.help_button);

        mStatsButton = (Button)findViewById(R.id.get_stat);

        mCrimeButton = (Button)findViewById(R.id.to_crime_list);

        mQuickCall = (Button)findViewById(R.id.quick_calll);

        mHelpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainScreenActivity.this,HelpActivity.class);
                startActivity(i);

            }
        });

        mStatsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i =new Intent(MainScreenActivity.this,GetAllCrimes.class);
                startActivity(i);
            }
        });

        mCrimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent i = new Intent(MainScreenActivity.this,CrimeListActivity.class);
                startActivity(i);
            }
        });


        mQuickCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:09910973263"));
                startActivity(callIntent);
            }
        });


    }


}
