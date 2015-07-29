package demo.example.com.imagepicker;

import android.os.Bundle;

import demo.example.com.imagepicker.fragment.ImagePickFragment;
import demo.example.com.imagepicker.fragment.SendFragment;

/**
 * Created by siddharth on 27/7/15.
 */
public class SendActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(R.id.fragment_container, new SendFragment());
    }
}
