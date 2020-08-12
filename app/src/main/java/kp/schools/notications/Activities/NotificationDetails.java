package kp.schools.notications.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import kp.schools.notications.R;

import static com.android.volley.Request.Method.GET;

public class NotificationDetails extends AppCompatActivity {

    //initialize root directory
    File rootDir = Environment.getExternalStorageDirectory();
    public String fileName = "codeofaninja.jpg";
    public String fileURL = "https://lh4.googleusercontent.com/-HiJOyupc-tQ/TgnDx1_HDzI/AAAAAAAAAWo/DEeOtnRimak/s800/DSC04158.JPG";


    public static final String LOG_TAG = "Android Downloader by The Code Of A Ninja";

    //initialize our progress dialog/bar
    private ProgressDialog mProgressDialog;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;

    //initialize root directory

    private String file_url;
    private String description;
    private String title;
    private String imageurl;

    TextView title_txt,description_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);


        title_txt=findViewById(R.id.title);
        description_txt=findViewById(R.id.description);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d(key ,": " + value + "\n\n");
            }
            title_txt.setText(bundle.getString("title"));
            description_txt.setText(bundle.getString("description"));
            imageurl=bundle.getString("image_url");
            file_url=bundle.getString("pdf_url");
        }



// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(NotificationDetails.this);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    public void image(View view) {
        if (imageurl==null || imageurl.length()<0){
            Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
        }
        else{

            //making sure the download directory exists
            if (isStoragePermissionGranted()) {


                //executing the asynctask
                checkAndCreateDirectory("/psra");

                String[] images=imageurl.split(",");
                Log.d("imagelenght",images.length+"");

                if (!imageurl.matches("")){
                    for (int i=0;i<images.length;i++){
                        if (!images[i].matches("")){
                            new DownloadFileAsync().execute("https://psra.gkp.pk/schoolReg/assets/images/"+images[i],images[i]);
                        }


                    }


                }
                else {
                    Toast.makeText(this, "No Documnets Here", Toast.LENGTH_SHORT).show();
                }            }else{

                Toast.makeText(this, "permisson not granted", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void pdf(View view) { //making sure the download directory exists
            if (isStoragePermissionGranted()) {


        //executing the asynctask
        checkAndCreateDirectory("/psra");


                String[] coments=file_url.split(",");
                Log.d("commentslenght",coments.length+"");

                if (!file_url.matches("")){
                    for (int i=0;i<coments.length;i++){
                        if (!coments[i].matches("")){
                            new DownloadFileAsync().execute("https://psra.gkp.pk/schoolReg/assets/images/"+coments[i],coments[i]);

                        }


                    }


                }
                else {
                    Toast.makeText(this, "No Documnets Here", Toast.LENGTH_SHORT).show();
                }
    }else{

        Toast.makeText(this, "permisson not granted", Toast.LENGTH_SHORT).show();
    }
}

    @Override
    public void setIntent(Intent newIntent) {
        Bundle extras = getIntent().getExtras();


        if(extras != null) {


            title = getIntent().getStringExtra("title");
            file_url = extras.getString("file_url");

            description = getIntent().getStringExtra("description");


            Log.d("TEMP", "Video description: " + description);
            Log.d("TEMP", "file_url: " + file_url);
            Log.d("TEMP", "title: " + title);


        } else {
            Log.d("TEMP", "Extras are NULL");

        }    }



    //this is our download file asynctask
    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @SuppressLint("LongLogTag")
        @Override
        protected String doInBackground(String... strings) {
            try {
                //connecting to url
                String url = strings[0];
                String filename = strings[1];
                String lastThreeChars = filename.substring(filename.length() - 4);
                URL u=new URL(url);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                //lenghtOfFile is used for calculating download progress
                int lenghtOfFile = c.getContentLength();

                String uniqeName= String.valueOf(System.currentTimeMillis());
                //this is where the file will be seen after the download
                FileOutputStream f = new FileOutputStream(new File(rootDir + "/psra/", uniqeName+lastThreeChars));
                //file input is from the url
                InputStream in = c.getInputStream();

                //here’s the download code
                byte[] buffer = new byte[1024];
                int len1 = 0;
                long total = 0;

                while ((len1 = in.read(buffer)) > 0) {
                    total += len1; //total = total + len1
                    publishProgress("" + (int)((total*100)/lenghtOfFile));
                    f.write(buffer, 0, len1);
                }
                f.close();

                MediaScannerConnection.scanFile(getApplicationContext(), new String[] { rootDir + "/psra/"+uniqeName+lastThreeChars }, null, null);


            } catch (Exception e) {
                Log.d(LOG_TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }



        @SuppressLint("LongLogTag")
        protected void onProgressUpdate(String... progress) {
            Log.d(LOG_TAG,progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            //dismiss the dialog after the file was downloaded
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
    }

    //function to verify if directory exists
    public void checkAndCreateDirectory(String dirName){
        File new_dir = new File( rootDir + dirName );
        if( !new_dir.exists() ){
            new_dir.mkdirs();
        }
    }

    //our progress bar settings
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS: //we set this to 0
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file…");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }


    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("permission 23","Permission is granted");
                return true;
            } else {

                Log.v("permission revoke 23","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("granted already 23","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("creating directory","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //Create your Directory here
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NotificationDetails.this,NotificationActivity.class));
        finish();
    }
}