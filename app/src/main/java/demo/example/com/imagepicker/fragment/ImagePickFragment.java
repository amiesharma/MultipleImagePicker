package demo.example.com.imagepicker.fragment;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothA2dp;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import demo.example.com.imagepicker.ImagePickActivity;
import demo.example.com.imagepicker.R;
import demo.example.com.imagepicker.SendActivity;
import demo.example.com.imagepicker.models.Media;
import demo.example.com.imagepicker.views.FlowLayout;

/**
 * Created by siddharth on 22/7/15.
 */
public class ImagePickFragment extends BaseFragment {


    private ImageView fullImageView;
    private TextView removetxt;
    private FlowLayout flowLayout;
    private EditText imagetext;
    private RelativeLayout containerLayout;
    private ArrayList<Media> list = new ArrayList<Media>();
    private FlowLayout.LayoutParams layoutParams;
    String msg;
    private int dragitemtag = -1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_picker_screen, container, false);

        fullImageView = (ImageView) view.findViewById(R.id.img1);
        removetxt = (TextView) view.findViewById(R.id.removeTV);
        flowLayout = (FlowLayout) view.findViewById(R.id.fl1);
        imagetext = (EditText)view.findViewById(R.id.tv);
        containerLayout = (RelativeLayout) view.findViewById(R.id.rl1);
        Button cancel = (Button)view.findViewById(R.id.cancel);
        Button send = (Button)view.findViewById(R.id.send);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SendActivity.class);

                if(list != null) {
                    Media m = list.get(0);
                    intent.putExtra("imagepath", m.getImgPath());
                }
                if(imagetext.getText() != null) {
                    intent.putExtra("imagetext", imagetext.getText().toString());
                }
                startActivity(intent);

            }
        });


        addLayout();

        // Set the drag event listener for the View
        removetxt.setOnDragListener(new View.OnDragListener() {
                                        @Override
                                        public boolean onDrag(View v, DragEvent event) {
                                            switch (event.getAction()) {
                                                case DragEvent.ACTION_DRAG_STARTED:

                                                    return true;
                                                case DragEvent.ACTION_DRAG_ENTERED:
                                                    ((TextView) removetxt).setTextColor(Color.GREEN);
                                                    removetxt.setTextSize(50f);
                                                    return true;
                                                case DragEvent.ACTION_DRAG_EXITED:
                                                    removetxt.setVisibility(View.GONE);
                                                    removetxt.setTextSize(20f);
                                                    return true;
                                                case DragEvent.ACTION_DRAG_LOCATION:
                                                    ((TextView) removetxt).setTextColor(Color.GREEN);
                                                    removetxt.setTextSize(50f);
                                                    return true;
                                                case DragEvent.ACTION_DRAG_ENDED:
                                                    removetxt.setVisibility(View.GONE);
                                                    removetxt.setTextSize(20f);
                                                    Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
                                                    return true;
                                                case DragEvent.ACTION_DROP:
                                                    ClipData data = event.getClipData();
                                                    ClipData.Item item = data.getItemAt(0);
                                                    int start = Integer.valueOf((String) item.getText());
                                                    list.remove(start);
                                                    if (start == list.size()) {
                                                        start -= 1;
                                                    }
                                                    addImageView(true, start);
                                                    removetxt.setVisibility(View.GONE);
                                                    setDragViewVisibilityVisible();
                                                    return true;
                                                default:
                                                    return false;
                                            }
                                        }
                                    }

        );
        containerLayout.setOnDragListener(new View.OnDragListener() {
                                              @Override
                                              public boolean onDrag(View v, DragEvent event) {
                                                  switch (event.getAction()) {

                                                      case DragEvent.ACTION_DRAG_STARTED:
                                                          return true;
                                                      case DragEvent.ACTION_DRAG_ENTERED:
                                                          return true;
                                                      case DragEvent.ACTION_DRAG_EXITED:
                                                          removetxt.setVisibility(View.GONE);
                                                          removetxt.setTextSize(20f);
                                                          return true;
                                                      case DragEvent.ACTION_DRAG_LOCATION:
                                                          return true;
                                                      case DragEvent.ACTION_DRAG_ENDED:
                                                          removetxt.setVisibility(View.GONE);
                                                          removetxt.setTextSize(20f);
                                                          return true;
                                                      case DragEvent.ACTION_DROP:
                                                          removetxt.setVisibility(View.GONE);
                                                          removetxt.setTextSize(20f);
                                                          setDragViewVisibilityVisible();
                                                          return true;
                                                      default:
                                                          return false;
                                                  }
                                              }
                                          }

        );


        return view;

    }

    @Override
    public void saveImage(Bitmap bitmap, String imagePath) {
        Media media = new Media();
        media.setImgPath(imagePath);
        media.setBitmap(bitmap);
        list.add(media);
        addImageView(false, 0);

    }

    public void addImageView(boolean isShowing, final int position) {
        try {
            flowLayout.removeAllViews();
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {

                    LayoutInflater li = LayoutInflater.from(getActivity());
                    final View child = li.inflate(R.layout.imageadd, null, false);
                    final ImageView image = (ImageView) child.findViewById(R.id.img);
                    image.setImageBitmap(list.get(i).getBitmap());
                    image.setBackgroundResource(R.drawable.normal);
                    image.setTag(i);
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int tag = (int) v.getTag();
                            v.setBackgroundResource(R.drawable.imagebackground);
                            fullImageView.setImageDrawable(((ImageView) v).getDrawable());
                            fullImageView.setTag(tag);
                            setBackgroundNormalToViews(tag);
                        }

                    });

                    image.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int i = (int) v.getTag();
                            v.setBackgroundResource(R.drawable.normal);
                            ClipData data = ClipData.newPlainText((String) "" + i, String.valueOf(i));
                            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                            v.startDrag(data, myShadow, null, 0);
                            removetxt.setVisibility(View.VISIBLE);
                            v.setVisibility(View.INVISIBLE);
                            removetxt.setTag(i);
                            ((TextView) removetxt).setTextColor(Color.RED);
                            return true;
                        }
                    });


                    flowLayout.addView(child);
                }

                if (isShowing) {
                    fullImageView.setImageBitmap(list.get(position).getBitmap());
                    fullImageView.setTag(position);
                } else if (fullImageView.getDrawable() == null) {
                    fullImageView.setImageBitmap(list.get(0).getBitmap());
                    fullImageView.setTag(0);
                }
            } else {
                fullImageView.setImageDrawable(null);
            }
            setBackgroundRedToViews();
            addLayout();


        } catch (Exception e) {
        }
    }


    private void setBackgroundNormalToViews(int tag) {
        for (int i = 0; i < list.size(); i++) {
            if (i != tag) {
                View child = flowLayout.getChildAt(i);
                ImageView image = (ImageView) child.findViewById(R.id.img);
                image.setBackgroundResource(R.drawable.normal);
            }
        }
    }

    private void setBackgroundRedToViews() {
        int position = (int) fullImageView.getTag();
        if (position > -1) {
            View child = flowLayout.getChildAt(position);
            ImageView image = (ImageView) child.findViewById(R.id.img);
            image.setBackgroundResource(R.drawable.imagebackground);
        }
    }

    private void setDragViewVisibilityVisible(){
        int position = (int) removetxt.getTag();
        if (position > -1) {
            removetxt.setTag(null);
            View child = flowLayout.getChildAt(position);
            ImageView image = (ImageView) child.findViewById(R.id.img);
            image.setVisibility(View.VISIBLE);
        }
    }


    private void addLayout() {

        try {
            LayoutInflater li = LayoutInflater.from(getActivity());
            final View child = li.inflate(R.layout.imageadd, flowLayout, false);
            ImageView image = (ImageView) child.findViewById(R.id.img);
            image.setVisibility(View.GONE);
            ImageButton mCrossButton = (ImageButton) child.findViewById(R.id.ivb1);
            mCrossButton.setImageResource(R.drawable.plus_icon);
            mCrossButton.setVisibility(View.VISIBLE);
            mCrossButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            });
            flowLayout.addView(child);
        } catch (Exception e) {
        }
    }



}
