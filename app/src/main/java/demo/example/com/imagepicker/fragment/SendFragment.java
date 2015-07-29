package demo.example.com.imagepicker.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import demo.example.com.imagepicker.R;

/**
 * Created by siddharth on 27/7/15.
 */
public class SendFragment extends BaseFragment {
private ImageView sendimg;
private TextView sendtxt;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_send_screen, container, false);
        sendimg = (ImageView)view.findViewById(R.id.sndimg);
        sendtxt = (TextView)view.findViewById(R.id.addtxt);
        Intent intent = getActivity().getIntent();
        String imagepath = intent.getStringExtra("imagepath");
        String imageText = intent.getStringExtra("imagetext");
        if(imagepath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
            sendimg.setImageBitmap(bitmap);
        }
        if(imageText != null){
            sendtxt.setText(imageText);
        }
        return view;
    }


}