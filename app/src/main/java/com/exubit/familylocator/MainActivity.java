package com.exubit.familylocator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.MenuItem;

import com.exubit.familylocator.view.activity.MainActivity1;
import com.exubit.familylocator.view.activity.SingleFragmentActivity;
import com.exubit.familylocator.view.activity.StartSettingsActivity;
import com.exubit.familylocator.view.fragment.StartSettingsFragment;
import com.exubit.familylocator.view.fragment.YandexFragment;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

public class MainActivity extends SingleFragmentActivity {


    final int TYPE_PHOTO = 1;
    final int TYPE_VIDEO = 2;
    final int REQUEST_CODE_PHOTO = 1;
    final int REQUEST_CODE_VIDEO = 2;
    File directory;

    @Override
    protected Fragment createFragment() {
         return YandexFragment.newInstance();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.show_subtitle:
               /* File photoFile = getPhotoFile(this.getApplicationContext());
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri uri = FileProvider.getUriForFile(this, "com.exubit.familylocator.fileprovider", photoFile);
                takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                List<ResolveInfo> cameraActivites = getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo activity : cameraActivites) {
                    grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(takePictureIntent, 1);*/

             /*   Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 1);
                }*/

               
                Intent intent = new Intent(this, MainActivity1.class);
                startActivity(intent);


                /*CropImage.activity()
                        //.setOutputUri()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(100, 100)
                        .setMaxCropResultSize(1000, 1000)
                        .setCropShape(CropImageView.CropShape.RECTANGLE)
                        .setAspectRatio(1,1)
                        .start(this);*/

                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(TYPE_PHOTO));
                startActivityForResult(intent, REQUEST_CODE_PHOTO);*/

              /*  Intent intent = new Intent(this, Main2Activity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/

                /*MaterialDialog dialog = new MaterialDialog.Builder(this)
                        .title("Title")
                        .content("Content")
                        .positiveText("Да")
                        .iconRes(R.drawable.mark)
                        .checkBoxPrompt("Чек бокс", false, null)
                        .checkBoxPrompt("Чек бокс2", true, null)
                        .negativeText("Нет")
                        .neutralText("Отмена")
                        .build();

                dialog.setCancelable(false);

                dialog.show();*/

                /*new MaterialDialog.Builder(this)
                        .title("Test")
                        .items(R.array.testArray)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                *//**
             * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
             * returning false here won't allow the newly selected radio button to actually be selected.
             **//*
                                return true;
                            }
                        })
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                        .input("Введите код приглашения", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                // Do something
                            }
                        })
                        .positiveText("Выбрать")
                        .show();*/


               /* ProgressDialog pd=new ProgressDialog(MainActivity1.this);
                pd.setTitle("Please Wait..");
                pd.setMessage("Loading...");

                pd.setCancelable(false);
                pd.show();*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }








    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) {return;}

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        int i=1;
     }



    public File getPhotoFile(Context context) {
        File fileDir = context.getFilesDir();
        return new File(fileDir, getPhotoFilenme());
    }

    public String getPhotoFilenme() {
        return "IMG_" + "serg" + ".jpg";
    }
}
