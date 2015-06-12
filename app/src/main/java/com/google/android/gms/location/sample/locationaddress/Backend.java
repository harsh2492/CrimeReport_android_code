package com.google.android.gms.location.sample.locationaddress;

/**
 * Created by Dell on 22-05-2015.
 */

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.PersistableBundle;
        import android.support.v7.app.ActionBarActivity;
        import android.view.View;
        import android.widget.Button;

public class Backend extends Activity {

    private Button mReportButton;
    private Button mGetAllReportsButton;
    private String title;
//added this
private Button mcrimestatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backend);

        title = getIntent().getStringExtra(CrimeFragment.TITLE);

        mReportButton = (Button)findViewById(R.id.crime_sendDatabase);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Backend.this, NewCrimeActivity.class);
                i.putExtra(CrimeFragment.TITLE,title);
                startActivity(i);
            }
        });

        mGetAllReportsButton = (Button)findViewById(R.id.getAllCrimes);
        mGetAllReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launching All products Activity
                Intent i = new Intent(Backend.this, AreaCrime.class);
                i.putExtra(CrimeFragment.TITLE,title);
                startActivity(i);

            }
        });

        //newly added
        mcrimestatus = (Button)findViewById(R.id.status);
        mcrimestatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launching All products Activity
                Intent i = new Intent(Backend.this, CrimeStatus.class);
               // i.putExtra(CrimeFragment.TITLE,title);
                startActivity(i);

            }
        });

    }
}

