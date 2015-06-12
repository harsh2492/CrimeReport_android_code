    package com.google.android.gms.location.sample.locationaddress;

    import android.app.Activity;
    import android.content.Intent;
    import android.graphics.drawable.BitmapDrawable;
    import android.net.Uri;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.text.format.DateFormat;
    import android.util.Log;
    import android.view.ContextMenu;
    import android.view.LayoutInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.ImageView;
    import android.widget.TextView;

    import org.w3c.dom.Text;

    import java.util.UUID;


    public class CrimeFragment extends Fragment {

    //my code
        private TextView location_display;

        public static final String TITLE  = "TITLE";

        //added these two

        public static final String ADDRESS  = "ADDRESS";

        public static final String DATE  = "DATE";


        public Crime mCrime;

        private static final String DIALOG_IMAGE = "image";

        private static final String TAG = "CrimeFragment";

        private EditText mTitleField;

    private Button mDateButton;

        private Button mgetlocationButton;

        private ImageView mPhotoView;

        private Button mButton; //done by me

        private CheckBox mSolvedCheckBox;



    private ImageButton mImageButton;

    private static final int REQUEST_PHOTO = 1;

    public static final String EXTRA_CRIME_ID =
            "com.bignerdranch.android.criminalintent.crime_id";

        public static CrimeFragment newInstance(UUID crimeId) {
            Bundle args = new Bundle();
            args.putSerializable(EXTRA_CRIME_ID, crimeId);
            //Log.d("why u no here?",getCrime(crimeId).getTitle());
            CrimeFragment fragment = new CrimeFragment();
            fragment.setArguments(args);

            return fragment;
        }


        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
            mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);

       //ArrayList<Crime> hjl =  CrimeLab.get(getActivity()).getCrimes();

     /*
        for(int i=0;i<hjl.size();i++) {
            Log.d("Inside CrimeFragment OnCreate method", hjl.get(i).getTitle()+""+hjl.get(i).getId().toString());
        }
    */

    }
     //   @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

            mTitleField = (EditText)v.findViewById(R.id.crime_title);

        location_display = (TextView)v.findViewById(R.id.location);


        Log.d("Crime Fragment","inside CrimeFragment");
        mTitleField.setText(mCrime.getTitle());

        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                mCrime.setTitle(c.toString());
            }
            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
// This space intentionally left blank
            }
            public void afterTextChanged(Editable c) {
// This one too
            }
        });

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        mDateButton.setText(mCrime.getDate().toString());

        mDateButton.setEnabled(false);

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);

        mSolvedCheckBox.setChecked(mCrime.isSolved());

        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        mImageButton = (ImageButton)v.findViewById(R.id.crime_imageButton);
        mImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i =  new Intent(getActivity(),CrimeCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView)v.findViewById(R.id.crime_imageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Photo p = mCrime.getPhoto();
                if (p == null)
                    return;
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                String path = getActivity()
                        .getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.newInstance(path).show(fm, DIALOG_IMAGE);
            }
        });


        registerForContextMenu(mPhotoView);

        mButton = (Button)v.findViewById(R.id.send_button);
        mButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String dateFormat = "EEE, MMM dd";
                String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();

                /*
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("application/image");
                String send = "Name of the incident : "+ mCrime.getTitle()+"\nDate of incident : "+dateString+"\nLocation of Crime:"+location_display.getText().toString();
                i.putExtra(Intent.EXTRA_TEXT, send);
                i.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.crime_report_subject));
                Uri uri = Uri.parse(mCrime.getPhoto().getFilename());
                i.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(i);
                */

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String send = "Name of the incident : "+ mCrime.getTitle()+"\nDate of incident : "+dateString+"\nLocation of Crime:"+location_display.getText().toString();
                i.putExtra(Intent.EXTRA_TEXT, send);
                i.putExtra(Intent.EXTRA_SUBJECT,
                        getString(R.string.crime_report_subject));
                startActivity(i);
            }
        });

        //my code

        mgetlocationButton = (Button)v.findViewById(R.id.get_location);
        mgetlocationButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               // Intent i =  new Intent(getActivity(),MainActivity.class);
                //startActivityForResult(i, 2);
                Intent i =  new Intent(getActivity(),MainActivity.class);
                startActivityForResult(i, 2);


            }
        });

        //here changed
        Button mBackEnd = (Button)v.findViewById(R.id.backend);
        mBackEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Backend.class);

                String addressto = location_display.getText().toString();

                i.putExtra(TITLE,mCrime.getTitle()+"%"+addressto+"%"+mCrime.getDate().toString());
                   //added these two


               // i.putExtra(ADDRESS,location_display.getText());
               // i.putExtra(DATE,mCrime.getDate().toString());
                Log.d("Date is here",mCrime.getDate().toString());
                Log.d("Address is here",location_display.getText().toString());

                startActivity(i);
            }
        });


        return v;
    }

        @Override
        public void onPause() {
            super.onPause();
            CrimeLab.get(getActivity()).saveCrimes();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (resultCode != Activity.RESULT_OK) {Log.d("No result :((",requestCode+"");return;}

         if (requestCode == REQUEST_PHOTO) {


// Create a new Photo object and attach it to the crime
                String filename = data
                        .getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
            Log.d("here-->",filename);
                if (filename != null) {
                   // Log.d(TAG, "filename: " + filename);
                    Photo p = new Photo(filename);
                    mCrime.setPhoto(p);
                    showPhoto();
                    Log.i(TAG, "Crime: " + mCrime.getTitle() + " has a photo");
                }
            }


            if(requestCode == 2){


               // location_display = (TextView)v.findbyViewId(R.id.loca);

                String address = data.getStringExtra("key");
              //  Log.d("gg","err");
                if(address.length()!=0)
               location_display.setText(address);
                else
                location_display.setText("address");
            }


        }

        private void showPhoto() {
// (Re)set the image button's image based on our photo
            Photo p = mCrime.getPhoto();
            BitmapDrawable b = null;
            if (p != null) {
                String path = getActivity()
                        .getFileStreamPath(p.getFilename()).getAbsolutePath();
                b = PictureUtils.getScaledDrawable(getActivity(), path);
            }
            mPhotoView.setImageDrawable(b);
        }

        @Override
        public void onStart() {
            super.onStart();
            showPhoto();
        }

        @Override
        public void onStop() {
            super.onStop();
            PictureUtils.cleanImageView(mPhotoView);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            getActivity().getMenuInflater().inflate(R.menu.photo_delete, menu);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            Log.d("ppppp","deleting");
            switch (item.getItemId()){
                case R.id.delete_photo:
                    mCrime.setPhoto(null);
                    getActivity().recreate();

                return true;
            }
            return super.onContextItemSelected(item);
        }

}
