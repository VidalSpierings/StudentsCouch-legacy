package com.studentscouch.projectbostonfiles.viewpagers_and_corresponding_files;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.studentscouch.projectbostonfiles.R;

import java.util.Arrays;
import java.util.List;

public class CustomSwipeLayout extends PagerAdapter{

    private List<Bitmap> image_resources;
    @SuppressLint("StaticFieldLeak")
    static private Context context;
    @SuppressLint("StaticFieldLeak")
    private static LinearLayout imageViewLinearLayout;

    public CustomSwipeLayout (Context context, Bitmap[] image_resources) {

        CustomSwipeLayout.context = context;
        this.image_resources = Arrays.asList(image_resources);

    }

    @Override
    public int getCount() {
        return image_resources.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = inflater.inflate(R.layout.fragment_apartement_swipe_layout, container, false);

        ImageView imageView = item_view.findViewById(R.id.swipe_layout_imageView);
        imageViewLinearLayout = item_view.findViewById(R.id.imageViewLinearLayout);
        imageView.setImageBitmap(image_resources.get(position));

        // set imageView bitmap according to index

        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((LinearLayout)object);

    }

    public static LinearLayout getLayout(){

        return  imageViewLinearLayout;

    }

}
