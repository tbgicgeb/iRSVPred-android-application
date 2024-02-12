package org.icgeb.basmati_seed_icgeb_3;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;


import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ServerConnectionActivity extends AppCompatActivity {


    Integer SELECT_FILE = 0;
    Uri imageuri;
    private Bitmap bitmap;
    private ImageView image_1;
    String picturePath, fileName;
    Button bt, bt2, bt3, bt4;
    TextView homeText2, progresText;



    private byte[] imgByte;


    private RecyclerView recyclerView;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;

    String title1 ,title2, title3, title4, title5;


    AdapterServer adapterResult, adapterResult2;
    ArrayList<ModelServer> models, models2;

    RecyclerView recycle, recycle2;

    public static final int STORAGE_PERMISSION_CODE = 1;
    public static final int PERMISSION_REQUEST_CODE_CAMERA = 200;
    public static final int CAMERA_REQUEST_CODE = 102;

    String currentPhotoPath, imageFileName;

    String ipAddress, subIp;

    String sv1, sv2;

    TextView model1, model2;





    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_connection);

        // Text View

        homeText2 = findViewById(R.id.test_text);
        progresText = findViewById(R.id.progressText);

        Toolbar toolbar = findViewById(R.id.toolbarServer);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ipNano();


        //Button Activity

        bt = (Button) findViewById(R.id.home_bt_1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(ServerConnectionActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectPictureFromStorage();
                }
                else {
                    requestStoragePermission();
                }
            }
        });


        bt2 = findViewById(R.id.home_bt_2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    dispatchTakePictureIntent();
                }
                else {
                    requestPermission();
                }
            }
        });


        bt3 = (Button) findViewById(R.id.home_bt_3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (picturePath == null) {
                    Toast.makeText(ServerConnectionActivity.this, "Please Select an Image", Toast.LENGTH_SHORT).show();
                }

                else if (isNetworkAvailable()){

                    //** Send image and offload image processing task  to server by starting async task **
                    ServerTask task = new ServerTask();
                    task.execute(picturePath);
                }

                else {
                    Toast.makeText(ServerConnectionActivity.this, "Ensure Internet Connectivity", Toast.LENGTH_SHORT).show();
                }



            }

        });

        title1 = "";
        title2 = "";
        title3 = "";
        title4 = "";
        title5 = "";

        model1 = findViewById(R.id.model502);
        model2 = findViewById(R.id.model251);






        bt4 = findViewById(R.id.test_button);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TextView txt4 = findViewById(R.id.test_text);
                if (fileName == null) {
                    Toast.makeText(ServerConnectionActivity.this, subIp, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ServerConnectionActivity.this, ipAddress, Toast.LENGTH_LONG).show();
                }
            }
        });



        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParseAdapter(parseItems, this);
        recyclerView.setAdapter(adapter);


        recycle = findViewById(R.id.recycleView);
        recycle2 = findViewById(R.id.recycleView2);


        models = new ArrayList<>();
        models2 = new  ArrayList<>();

        adapterResult = new AdapterServer(models, ServerConnectionActivity.this);
        adapterResult2 = new AdapterServer(models2, ServerConnectionActivity.this);

        recycle.setHasFixedSize(true);
        recycle2.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle2.setLayoutManager(new LinearLayoutManager(this));

        recycle.setAdapter(adapterResult);
        recycle2.setAdapter(adapterResult2);

    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed since image has to uploaded for prediction")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(ServerConnectionActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void ipNano() {
            // sv1 = server address for file upload;
            // sv2 = server address to fetch results;

    }



    //Camera Permission

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE_CAMERA);
    }


    //CAMERA PERMISSION


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String camera = "cameraCaptured";
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        imageFileName = "JPEG_" + camera;
        imageFileName = "CameraCaptured_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) ;
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }




    // select Picture from Storage

    public void selectPictureFromStorage(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i.createChooser(i, "Select File"), SELECT_FILE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "org.icgeb.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE_CAMERA:


                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    dispatchTakePictureIntent();

                    // main logic
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow camera permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermission();
//                                                Toast.makeText(getApplicationContext(), "Please Allow from Settings", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                                intent.setData(uri);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
            case STORAGE_PERMISSION_CODE:
                if (requestCode == STORAGE_PERMISSION_CODE) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                        selectPictureFromStorage();
                    } else {
//                        Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                showMessageOKCancel("You need to allow Storage permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermission();
//                                                Toast.makeText(getApplicationContext(), "Please Allow from Settings", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                                    intent.setData(uri);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }
                        }

                    }
                }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(ServerConnectionActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        image_1 = findViewById(R.id.home_image_1);


        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            imageuri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                image_1.setImageBitmap(bitmap);



                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                imgByte = byteArrayOutputStream.toByteArray();


                String[] projection = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imageuri, projection, null, null, null);
                cursor.moveToFirst();

                Log.d(TAG, DatabaseUtils.dumpCursorToString(cursor));

                int columnIndex = cursor.getColumnIndex(projection[0]);
                picturePath = cursor.getString(columnIndex); // returns null
                fileName = picturePath.substring(picturePath.lastIndexOf("/") + 1);

                cursor.close();

                Log.d(TAG, "onActivityResult: imageuri:::   " + imageuri);
                Log.d(TAG, "onActivityResult: picturepath:::   " + picturePath);
                Log.d(TAG, "onActivityResult: filename:::    " + fileName);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {


            File f = new File(currentPhotoPath);
            image_1.setImageURI(Uri.fromFile(f));



            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            imageuri = Uri.fromFile(f) ;
            mediaScanIntent.setData(imageuri);
            this.sendBroadcast(mediaScanIntent);


            picturePath = currentPhotoPath;
            fileName =  imageFileName + ".JPG";
            Log.d(TAG, "onActivityResult: " + imageuri);
            Log.d(TAG, "onActivityResult: " + picturePath);
            Log.d(TAG, "onActivityResult: " + fileName);

        }



    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    private static final String TAG = "ServerActivity";


    public class ServerTask extends AsyncTask<String, Integer, Void> {


        String words;
        String wordfinal;


        //upload photo to server
        HttpURLConnection uploadPhoto(FileInputStream fileInputStream) {




            final String lineEnd = "\r\n";
            final String twoHyphens = "--";
            final String boundary = "*****";

            try {
//                URL url = new URL(SERVERURL);
                URL url = new URL(sv1);
                // Open a HTTP connection to the URL
//                final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                // Allow Inputs
                conn.setDoInput(true);
                // Allow Outputs
                conn.setDoOutput(true);
                // Don't use a cached copy.
                conn.setUseCaches(false);

                // Use a post method.
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("enctype", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"fileToUpload\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);


                // create a buffer of maximum size
                int bytesAvailable = fileInputStream.available();
                int maxBufferSize = 102400000;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                // read file and write it into form...
                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//                publishProgress(SERVER_PROC_STATE);
                // close streams
                fileInputStream.close();
                dos.flush();



                return conn;
            } catch (MalformedURLException ex) {
                Log.e(TAG, "error: " + ex.getMessage(), ex);
                return null;
            } catch (IOException ioe) {
                Log.e(TAG, "error: " + ioe.getMessage(), ioe);
                return null;
            }


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            image_1.setVisibility(View.GONE);
            bt.setVisibility(View.GONE);
            bt2.setVisibility(View.GONE);
            bt3.setVisibility(View.GONE);
            bt4.setVisibility(View.GONE);
            homeText2.setVisibility(View.GONE);



            progresText.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(ServerConnectionActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (title1=="" || title2=="" || title3=="" || title4=="" || title5==""){
                recycle.setVisibility(View.GONE);
                recycle2.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                progressBar.startAnimation(AnimationUtils.loadAnimation(ServerConnectionActivity.this, android.R.anim.fade_out));
                progresText.setText("ERROR 403   " + "Check Internet Connectivity" + " and TRY AGAIN");


            }
            else {

                model1.setVisibility(View.VISIBLE);
                model2.setVisibility(View.VISIBLE);
                progresText.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                progressBar.startAnimation(AnimationUtils.loadAnimation(ServerConnectionActivity.this, android.R.anim.fade_out));
                adapter.notifyDataSetChanged();

                recycle.setVisibility(View.VISIBLE);
                recycle2.setVisibility(View.VISIBLE);

                models.add(new ModelServer(imageuri, "Rotated 60 Degrees", title1, 60));
                models.add(new ModelServer(imageuri, "Rotated 140 Degrees", title2, 140));
                models.add(new ModelServer(imageuri, "Rotated 160 Degrees", title3, 160));
                models2.add(new ModelServer(imageuri, "Rotated 45 Degrees", title4, 45));
                models2.add(new ModelServer(imageuri, "Rotated 120 Degrees", title5, 120));

                adapterResult.notifyDataSetChanged();
                adapterResult2.notifyDataSetChanged();
            }



        }


        @Override
        protected void onCancelled() {
            super.onCancelled();

            finishAffinity();

        }



        @Override
        protected Void doInBackground(String... strings) {


            //Main code for processing image algorithm on the server

            {
                File inputFile = new File(picturePath);
                try {

                    //create file stream for captured image file
                    FileInputStream fileInputStream = new FileInputStream(inputFile);


                    final HttpURLConnection conn = uploadPhoto(fileInputStream);

                    conn.getInputStream();



                } catch (IOException ex) {
                    Log.e(TAG, ex.toString());
                }


                try {

                    String url = sv2;



                    Document doc = Jsoup.connect(url).get();

                    // Fetch the results
                    // Title and prediction scores
                    // Filter as per your needs


                } catch (IOException e) {
                    e.printStackTrace();
                }



                return null;

            }
        }




    }



}