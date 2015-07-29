package demo.example.com.imagepicker;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;

import demo.example.com.imagepicker.fragment.BaseFragment;
import demo.example.com.imagepicker.models.Media;
import demo.example.com.imagepicker.utilies.FileUtils;
import demo.example.com.imagepicker.utilies.Utility;

/**
 * Created by siddharth on 22/7/15.
 */
public class BaseActivity extends AppCompatActivity {

    private int containerId = R.id.fragment_container;
    private Stack<String> fragmentStack;
    public static final int GALLERY_REQUEST = 1002;
    public static final int CAMERA_REQUEST = 1005;
    public static final int GALLERY_KITKAT_INTENT_CALLED = 1003;
    public static final int CROP_REQUEST = 1009;
    private final int choose_picture = 1;
    private ArrayList<String> imagesPathList;
    private LinearLayout lnrImages;
    private Bitmap resized;
    private Uri u1;
    private Uri mCapturedImageURI;


    final CharSequence[] items = {"Take Photo", "Choose from Library",
            "Cancel"};
    public File image_asset;
    Bitmap bitmap;
    private String path;
    private String imageName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main_activity);
        fragmentStack = new Stack<>();
    }

    public void openGallery() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_KITKAT_INTENT_CALLED);
//            startActivityForResult(intent, PICK_IMAGE_MULTIPLE);
            //startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST);
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getData() != null && resultCode == RESULT_OK) {
            try {

                if (data != null) {
                    mCapturedImageURI = data.getData();
                }

                if ((requestCode == GALLERY_REQUEST || requestCode == GALLERY_KITKAT_INTENT_CALLED) && resultCode == Activity.RESULT_OK) {
                    if (requestCode == GALLERY_REQUEST) {
                        mCapturedImageURI = data.getData();
                        String[] column = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(mCapturedImageURI, column, null, null, null);
                        c.moveToFirst();
                        path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                        path = FileUtils.getPath(this, mCapturedImageURI);
                        c.close();
                        Log.d("Uri Got Path", "" + path);
                        bitmap = BitmapFactory.decodeFile(path);
                        saveImage(bitmap, path);

                    } else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {

                        path = FileUtils.getPath(this, mCapturedImageURI);
                        mCapturedImageURI = Uri.fromFile(new File(path));
                        bitmap = BitmapFactory.decodeFile(path);
                        saveImage(bitmap, path);


                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
//            saveImage(bitmap,path);
        } else if (resultCode == RESULT_OK && data != null && data.getClipData() != null) {
//                int flags = data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    if ((requestCode == GALLERY_REQUEST || requestCode == GALLERY_KITKAT_INTENT_CALLED) && resultCode == Activity.RESULT_OK) {
                        if (requestCode == GALLERY_REQUEST) {
                            for (int i = 0; i < clipData.getItemCount(); i++) {
                                ClipData.Item item = clipData.getItemAt(i);
                                mCapturedImageURI = item.getUri();
                                String[] column = {MediaStore.Images.Media.DATA};
                                Cursor c = getContentResolver().query(mCapturedImageURI, column, null, null, null);
                                c.moveToFirst();
                                path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                                path = FileUtils.getPath(this, mCapturedImageURI);
                                c.close();
                                Log.d("Uri Got Path", "" + path);
                                bitmap = BitmapFactory.decodeFile(path);
                                saveImage(bitmap, path);
                            }

                        } else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {

                            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                {
                                    for (int i = 0; i < clipData.getItemCount(); i++) {
                                        ClipData.Item item = clipData.getItemAt(i);
                                        mCapturedImageURI = item.getUri();
                                        String[] column = {MediaStore.Images.Media.DATA};
                                        Cursor c = getContentResolver().query(mCapturedImageURI, column, null, null, null);
                                        c.moveToFirst();

                                            path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                                            path = FileUtils.getPath(this, mCapturedImageURI);

                                        bitmap = BitmapFactory.decodeFile(path);
                                        saveImage(bitmap, path);
                                    }
                                }
                            }
                        }
                    }

                }


            }

            super.

                    onActivityResult(requestCode, resultCode, data);

        }



    private void saveImage(Bitmap bitmap, String imagePath) {
        if (getCurrentFragment() != null) {
            getCurrentFragment().saveImage(bitmap, imagePath);
        }
    }

    /**
     * Method to be used when sub class has set his own layout and wants to add the given fragment to the passed container
     *
     * @param containerId
     * @param fragment
     */
    public void addFragment(int containerId, BaseFragment fragment) {
        if (fragment == null || containerId <= 0)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.popup_enter, R.anim.popup_exit);
        transaction.replace(containerId, fragment);
        transaction.addToBackStack(fragment.getClass().getName());
        fragmentStack.add(fragment.getClass().getName());
        transaction.commit();
    }

    /**
     * Method to be used when you want to add fragment in the base layout container.
     * use when your screen just have a single fragment
     *
     * @param fragment
     */
    public void addFragment(BaseFragment fragment) {
        addFragment(R.id.fragment_container, fragment);
    }

    public BaseFragment getCurrentFragment() {
        BaseFragment currentFragment = null;
        if (containerId > 0) {
            currentFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(containerId);
        }
        return currentFragment;
    }


}

