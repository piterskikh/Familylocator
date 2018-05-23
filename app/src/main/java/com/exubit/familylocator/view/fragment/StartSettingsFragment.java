package com.exubit.familylocator.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.exubit.familylocator.R;
import com.exubit.familylocator.databinding.StartSettingsBinding;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

public class StartSettingsFragment extends BaseFragment {

    private Button button;
    private ImageView imageView;
    private ConstraintLayout progressView;
    private TextView progresstext;
    private File photoFile;
    private Uri uri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        StartSettingsBinding binding = DataBindingUtil.inflate(inflater, R.layout.start_settings, container, false);
        button = binding.button;
        imageView = binding.imageView;
        button.setOnClickListener(this::makePhoto);
        photoFile = getPhotoFile(getActivity().getApplicationContext());
        progressView = binding.ProgressView;
        progresstext = binding.ProgressViewText;
        uri = FileProvider.getUriForFile(getActivity(), "com.exubit.familylocator.fileprovider", photoFile);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Settings");
        updatePhotoView();
        activity.getSupportActionBar().hide();
        progresstext.setText("Loading...");
        progressView.setVisibility(View.VISIBLE);

        button.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);

        //  getActivity().setTheme(R.style.AppThemeNoBar);


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference rootRef = storage.getReference();

        StorageReference myRef = rootRef.child("familylocator/123.png");

        Glide.with(this)
                .load(myRef)
                .apply(new RequestOptions()
                                .placeholder(R.drawable.mark)
                                .fitCenter()
                                .circleCrop()
                        //.transform(new CircleCrop().)
                )
                .into(imageView);


        return binding.getRoot();
    }


    private void checkStatus() {


    }

    private void makePhoto(View view) {


    /*    Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

//folder stuff
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
        imagesFolder.mkdirs();

        File image = new File(imagesFolder, "QR_" + timeStamp + ".png");
        Uri uriSavedImage = Uri.fromFile(image);

        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(imageIntent, 3);*/

        Uri uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), "com.exubit.familylocator.fileprovider", photoFile);

        //Uri uri = FileProvider.getUriForFile(getActivity(), "com.piterskikh.criminalintent.fileprovider", mPhotoFile);
       /* Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        List<ResolveInfo> cameraActivites = getActivity().getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : cameraActivites) {
            getActivity().grantUriPermission(activity.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        startActivityForResult(captureImage, 3);*/

        //CropImage.getActivityResult(intent)

       /* Intent  intent1 = CropImage.activity().getIntent(getContext());
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent1,2);*/

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(100, 100)
                .setMaxCropResultSize(1000, 1000)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(1, 1)
                .setOutputUri(uri)
                .start(getActivity().getApplicationContext(), this);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");

        CropImage.ActivityResult result = CropImage.getActivityResult(data);

        try {


            System.out.println("Attempt");
            Uri resultUri = result.getUri();
            System.out.println(resultUri.getPath());


        } catch (Exception e) {

            System.out.println("none");
        }

        if (data != null) {

            System.out.println("null");
        }

        //data.getData()
        System.out.println("requestCode " + requestCode);
        System.out.println("resultCode " + resultCode);

        //  System.out.println(data.getData().getPath());
        if (requestCode == 3) {
   /* CropImage.activity(uri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .setFixAspectRatio(true)
            .start(getContext(), this);*/
        }

       /* if (requestCode == 3 && null != data) {
            System.out.println("прошел");
            CropImage.activity(data.getData())
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setFixAspectRatio(true)
                    .start(getContext(), this);

        }*/
    }


   /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                Uri resultUri = result.getUri();
                //Uri uri = FileProvider.getUriForFile(getActivity(), "com.piterskikh.criminalintent.fileprovider", mPhotoFile);
                getActivity().revokeUriPermission(resultUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                updatePhotoView();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }*/


    private void updatePhotoView() {
        if (photoFile == null || !photoFile.exists())
            imageView.setImageDrawable(null);
        else {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
            imageView.setImageBitmap(bitmap);
        }
    }


    public File getPhotoFile(Context context) {
        File fileDir = context.getFilesDir();
        return new File(fileDir, getPhotoFilenme());
    }

    public String getPhotoFilenme() {
        return "IMG_" + "serg" + ".jpg";
    }
}
