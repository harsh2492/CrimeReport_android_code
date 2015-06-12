package com.google.android.gms.location.sample.locationaddress;

/**
 * Created by Dell on 22-05-2015.
 */


import android.app.ListActivity;
import android.app.ProgressDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CrimeStatus extends ListActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> productsList;

    // url to get all products list

    /*
    WHEN USING WITH HOME WIFI SETTINGS
      private static String url_all_crimes = "http://192.168.1.3/Backend/get_crimes.php";
*/
    /*
    WHEN USING WITH MOBILE HOTSPOT SETTINGS
*/
    // private static String url_all_crimes = "http://192.168.43.185/Backend/get_crimes.php";

    //  private static String url_all_crimes = "http://192.168.1.5/Backend/get_crimes.php";

    /*USED EARLIER
  private static String url_all_crimes = "http://192.168.1.5/Backend/get_crimes.php";
    */

    //Now i am using this
  //  private static String url_all_crimes = "http://192.168.1.5/Backend/crime_status.php";
    private static String url_all_crimes = "http://192.168.1.4/Backend/crime_status.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    //earlier
    // private static final String TAG_PRODUCTS = "crimes";

    //i am using this
    private static final String TAG_PRODUCTS = "crimelist";


    private static final String TAG_CID = "crime_id";
    private static final String TAG_TITLE = "title";

    //added these
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_DATE = "date";
    private static final String TAG_STATUS = "status";


    // products JSONArray
    JSONArray products = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_crimes);

        // Hashmap for ListView
        productsList = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadAllProducts().execute();

        // Get listview
        ListView lv = getListView();
    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CrimeStatus.this);
            pDialog.setMessage("Loading crimes. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(url_all_crimes, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_PRODUCTS);

                    // looping through All Products
                    for (int i = 0; i < products.length();i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        //String id = c.getString(TAG_CID);
                        String name = c.getString(TAG_TITLE);

                        //did this
                        Log.d("ggg",(c.getString(TAG_STATUS)));
                       // if((c.getString(TAG_STATUS).equals("null")))
                        //name = "Name : " +name + "\n" + "Address : " + c.getString(TAG_ADDRESS) + "\nDate : " + c.getString(TAG_DATE)+"\nStatus : "+"In Progress";
                        //else
                           name = "Name : " +name + "\n" + "Address : " + c.getString(TAG_ADDRESS) + "\nDate : " + c.getString(TAG_DATE)+"\nStatus : "+(c.getString(TAG_STATUS));


                        ArrayList<Crime> cc =  CrimeLab.get(CrimeStatus.this).getCrimes();


                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        //map.put(TAG_CID, id);
                        for(int i1=0;i1<cc.size();i1++) {
                            Log.d("here",cc.get(i1).getTitle());
                        }

                            for(int i1=0;i1<cc.size();i1++) {
                            if (cc.get(i1).getTitle().equals(c.getString(TAG_TITLE)))
                            { map.put(TAG_TITLE, name);
                                productsList.add(map);

                                break;
                            }
                        }

                        // adding HashList to ArrayList
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                        /*Intent i = new Intent(getApplicationContext(),
                                NewProductActivity.class);
                        // Closing all previous activities
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        */
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
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            CrimeStatus.this, productsList,
                            R.layout.list_item, new String[] {
                            TAG_TITLE},
                            new int[] {
                                    R.id.title });
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }

    }
}

