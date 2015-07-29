package demo.example.com.imagepicker.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import demo.example.com.imagepicker.BaseActivity;

/**
 * Created by siddharth on 22/7/15.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void addFragment(BaseFragment fragment){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).addFragment(fragment);
        }
    }
    public void addFragment(int containerId, BaseFragment fragment){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).addFragment(containerId, fragment);
        }
    }

    public void openGallery(){
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).openGallery();
        }
    }

    public void saveImage(Bitmap bitmap , String imagePath){}



}
