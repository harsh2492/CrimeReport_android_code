package com.google.android.gms.location.sample.locationaddress;

/**
 * Created by Dell on 22-05-2015.
 */

import org.json.JSONException;


        import java.util.ArrayList;
        import java.util.List;

        import org.apache.http.NameValuePair;
        import org.apache.http.message.BasicNameValuePair;
        import org.json.JSONException;
        import org.json.JSONObject;

        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

public class NewCrimeActivity extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    // url to create new product

    /*
    WHEN USING WITH HOME WIFI SETTINGS
      private static String url_create_crime = "http://192.168.1.3/Backend/add_crime.php";

    /*
    WHEN USING WITH MOBILE HOTSPOT SETTINGS
    */

   // private static String url_create_crime = "http://192.168.1.5/Backend/add_crime.php";


    /* earlier using this
    private static String url_create_crime = "http://192.168.1.5/Backend/add_crime.php";
*/

    //now this
    private static String url_create_crime = "http://192.168.1.4/Backend/add_crimelist.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private String title;

    //added these two
    private String address;
    private String date;
    private String status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

       // title= getIntent().getStringExtra("TITLE");

        String nnn  = getIntent().getStringExtra("TITLE");

        String aa[] = nnn.split("%");

        title = aa[0];
        address = aa[1];
        date = aa[2];
        status = new String("In Progress");
        //added these two
        //address = getIntent().getStringExtra("ADDRESS");
        //date = getIntent().getStringExtra("DATE");

        Log.d("Check me",title+","+address+","+date);

        new CreateNewCrime().execute();


    }



    /**
     * Background Async Task to Create new product
     * */

    class CreateNewCrime extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewCrimeActivity.this);
            pDialog.setMessage("Creating Crime..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("title", title));

            //i added this
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("date", date));
            //did this today
            params.add(new BasicNameValuePair("status", status));



            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_crime,
                    "POST", params);
            //Toast.makeText(NewCrimeActivity.this,"MadeConnection",Toast.LENGTH_SHORT).show();
            // check log cat fro response
            //Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    //Toast.makeText(NewCrimeActivity.this,"InsideSuccess",Toast.LENGTH_SHORT).show();
                        /*Intent i = new Intent(getApplicationContext(), GetAllCrimes.class);
                        startActivity(i);
                        */
                    //Toast.makeText(NewCrimeActivity.this,"BeforeClose",Toast.LENGTH_SHORT).show();
                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }

    }

}
