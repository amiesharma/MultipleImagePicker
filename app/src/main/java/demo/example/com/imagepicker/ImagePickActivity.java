package demo.example.com.imagepicker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import demo.example.com.imagepicker.fragment.ImagePickFragment;

public class ImagePickActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_base);
        addFragment(R.id.fragment_container,new ImagePickFragment());
//        Button cancel = (Button)findViewById(R.id.cancel);
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ImagePickActivity.this.finish();
//            }
//        });

    }

}
