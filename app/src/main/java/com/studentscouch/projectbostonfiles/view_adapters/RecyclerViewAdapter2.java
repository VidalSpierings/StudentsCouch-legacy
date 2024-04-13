package com.studentscouch.projectbostonfiles.view_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.ui.StartupActivity;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.ui.CityActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.RecyclerViewViewHolder2> {

    private Context context;
    private ArrayList<StartupActivityInformation> information;

    public RecyclerViewAdapter2(Context context, ArrayList<StartupActivityInformation> information) {

        this.context = context;
        this.information = information;

    }

    @NonNull
    @Override
    public RecyclerViewViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    LayoutInflater inflater = LayoutInflater.from(context);
    @SuppressLint("InflateParams")
    View view = inflater.inflate(R.layout.city_item, null);

    return new RecyclerViewViewHolder2(view, context, information);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewViewHolder2 holder, int position) {

        StartupActivityInformation city = information.get(position);

        holder.textView.setText(city.getName());

        holder.imageView.setImageDrawable(context.getResources().getDrawable(city.getImage()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.imageView.setTransitionName("item_imageView_shared");
                }

                //  options = ActivityOptionsCompat.makeSceneTransitionAnimation(StartupActivity.class, holder.imageView, "item_imageView_shared");

                Intent i = new Intent(context, CityActivity.class);

                Drawable drawable = holder.imageView.getDrawable();

                // get image drawable from holder object

                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();

                // convert drawable object into byte array object

              //  i.putExtra("img_id", holder.imageView);
                i.putExtra("cityName", holder.textView.getText().toString());
                i.putExtra("img_id", b);

                // get city name from holder object, pass byte array and city name to intent

                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    class RecyclerViewViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;
        Context context;
        ArrayList<StartupActivityInformation> information;

        RecyclerViewViewHolder2(View itemView, Context context, ArrayList<StartupActivityInformation> information) {
            super(itemView);

            this.information = information;
            this.context = context;

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);

            // link xml layout elements to java variables

            itemView.setOnClickListener(this);

            // set list item onClickListener

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setTransitionName("item_imageView_shared");
            }

            // if device OS version is equal to or higher than Lollipop, set transition name

            Typeface tp = Typeface.createFromAsset(context.getAssets(),"project_boston_typeface.ttf");

            textView.setTypeface(tp);

            // set listItem textView typeface

        }

        @Override
        public void onClick(View view) {

            if (StartupMethods.isOnline(context)) {

                int pos = getAdapterPosition();

                StartupActivityInformation information = this.information.get(pos);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    Intent i2 = new Intent(this.context, StartupActivity.class);

                    i2.putExtra("img_id", information.getImage());
                    i2.putExtra("cityName", information.getName());

                    //  this.context.startActivity(i2);

                } else {

                    Intent i = new Intent(this.context, CityActivity.class);

                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    i.putExtra("img_id", information.getImage());
                    i.putExtra("cityName", information.getName());

                    // this.context.startActivity(i);

                }

            } else {

                Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

                // if internet connection wasn't found, inform user by showing a toast

            }

        }
    }

    /*

    public void filterList(ArrayList<StartupActivityInformation> filteredList){

        information = filteredList;
        notifyDataSetChanged();

    }

    */



}
