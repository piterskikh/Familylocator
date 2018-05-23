package com.exubit.familylocator.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.exubit.familylocator.R;

import java.io.File;
import java.util.List;

public class MainActivity1 extends AppCompatActivity {

    private Button button;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        button = findViewById(R.id.button);

        button.setOnClickListener(this::photoClick);
        photoFile = getPhotoFile(this.getApplicationContext());

    }

    private void photoClick(View view) {
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = FileProvider.getUriForFile(this, "com.exubit.familylocator.fileprovider", photoFile);
        captureImage.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
        List<ResolveInfo> cameraActivites = getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : cameraActivites) {
           grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        startActivityForResult(captureImage, 1);

        /*File directory = getApplicationContext().getFilesDir();
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            System.out.println(files[i].getName());
            if(files[i].getName().startsWith("IMG")){
                files[i].delete();
            }

        }*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //data.getData();
        Uri uri = FileProvider.getUriForFile(this, "com.exubit.familylocator.fileprovider", photoFile);
       // getContentResolver().delete(data.getData(), null, null);

      /*  Bitmap photo = (Bitmap) data.getExtras().get("data");
        Uri tempUri = Utils.getUri(this, photo);
        File finalFile = new File(getRealPathFromURI(tempUri));
        finalFile.delete();*/


        revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }

    public File getPhotoFile(Context context) {
        File fileDir = context.getFilesDir();
        return new File(fileDir, getPhotoFilenme());
    }

    public String getPhotoFilenme() {
        return "IMG_" + "serg" + ".jpg";
    }
}
