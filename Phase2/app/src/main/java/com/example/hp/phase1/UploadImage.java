package com.example.hp.phase1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class UploadImage extends ActionBarActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    private static final int MENU_REPORT_911 = Menu.FIRST;
    private static final int MENU_TWITTER = Menu.FIRST+1;
    private static final int MENU_HELP = Menu.FIRST+2;
    private Bitmap imageBitmap;
    public static String final_path;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        Button cameraBtn = (Button) findViewById(R.id.cameraBtn);
        imageView = (ImageView)findViewById(R.id.imageView1);

        cameraBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageView.setImageDrawable(null);
            imageView.destroyDrawingCache();
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(Menu.NONE, MENU_REPORT_911, Menu.NONE, "Report to 911");
        menu.add(Menu.NONE, MENU_TWITTER, Menu.NONE, "Share on Twitter");
        menu.add(Menu.NONE, MENU_HELP, Menu.NONE, "Help");
        setTitle("Crime Capture");

//        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId())
        {
            case MENU_REPORT_911:
                openReporting();
                return true;

            case MENU_TWITTER:
                openTwitter();
                return true;

            case MENU_HELP:
                openHelp();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openReporting(){
        Intent intent = new Intent(context, ReportActivity.class);
        startActivity(intent);

    }


    private void openHelp(){

    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void openTwitter(){
        getActualImagePath();
        Intent intent = new Intent(context, TwitterActivity.class);
        startActivity(intent);
    }

    private void getActualImagePath(){
        String[] proj={MediaStore.MediaColumns.DATA};
        Cursor cursor=managedQuery(getImageUri(context, imageBitmap),proj,null,null,null);
        int column_index=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        final_path = cursor.getString(column_index);

    }
}
