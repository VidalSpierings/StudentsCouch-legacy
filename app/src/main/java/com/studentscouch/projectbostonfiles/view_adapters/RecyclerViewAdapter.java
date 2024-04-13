package com.studentscouch.projectbostonfiles.view_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.studentscouch.projectbostonfiles.R;
import com.studentscouch.projectbostonfiles.StartupMethods;
import com.studentscouch.projectbostonfiles.observables.CityActivityObservables;
import com.studentscouch.projectbostonfiles.ui.AccountAndApartementActivity;
import com.studentscouch.projectbostonfiles.view.viewImplementers.CityActivityViewImplementer;
import com.studentscouch.projectbostonfiles.view.views.CityActivityView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewViewHolder> {

    private Context context;
    private ArrayList<CityActivityInformation> information;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    public static Intent i;

    private CityActivityObservables observables;

    private CityActivityView cityView;

    private AppCompatActivity appCompatActivity;

    public RecyclerViewAdapter(Context context, AppCompatActivity appCompatActivity, ArrayList<CityActivityInformation> information, CityActivityObservables observables, CityActivityViewImplementer view) {
        this.context = context;
        this.information = information;
        this.observables = observables;
        this.appCompatActivity = appCompatActivity;
        cityView = view;
    }

    @NonNull
    @Override
    public RecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER){

            // if listItem is a header item, do the following

            LayoutInflater inflater = LayoutInflater.from(context);
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.top_item, null);

            // inflate item with 'top item (header item)' layout

            // create new viewHolder object

            return new RecyclerViewViewHolder(view, appCompatActivity, viewType, context, information);

        }else if (viewType == TYPE_ITEM){

            // if listItem is a normal item, do the following

            LayoutInflater inflater = LayoutInflater.from(context);
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.list_item, null);

            // inflate item with 'list item (normal item)' layout

            return new RecyclerViewViewHolder(view, appCompatActivity, viewType, context, information);

        }

            return null;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder holder, int position) {

        if (holder.view_type1 == TYPE_HEADER){

            // if listItem is a header item, do the following

            // Intent intent = Intent.parseUri(,0);

           String cityName = observables.getCityName();
           String countryName = observables.getCountryName();
           // backgroundImage.setImageResource(getIntent().getIntExtra("img_id", 0));

            // retrieve city name and country name of selected city by user

            holder.cityNameTextView.setText(cityName);
            holder.countryNameTextView.setText(countryName);
            holder.header_image.setImageBitmap(observables.getImage2());

            // set city name, country name and header image of city

        }else if (holder.view_type1 == TYPE_ITEM){

            // if listItem is a normal item, do the following

            CityActivityInformation cityInformation = information.get(position-1);

            // get RecyclerView Information object at current position item -1 (subtracts Header item for all other items)

            holder.profileImageView.setImageBitmap(cityInformation.getProfileId());
            holder.apartementImageView.setImageBitmap(cityInformation.getIconId());

            // set profile image and header apartment image for created item

            if (cityInformation.getRating() > 0 && cityInformation.getRating() < 1.25){

                holder.ratingImageView.setImageResource(R.drawable.one_star);

            } else if (cityInformation.getRating() >= 1.25 && cityInformation.getRating() < 1.75){

                holder.ratingImageView.setImageResource(R.drawable.one_and_a_half_stars);

            }else if (cityInformation.getRating() >= 1.75 && cityInformation.getRating() < 2.25){

                holder.ratingImageView.setImageResource(R.drawable.two_stars);

            }else if (cityInformation.getRating() >= 2.25 && cityInformation.getRating() < 2.75){

                holder.ratingImageView.setImageResource(R.drawable.two_and_a_half_stars);

            }else if (cityInformation.getRating() >= 2.75 && cityInformation.getRating() < 3.25){

                holder.ratingImageView.setImageResource(R.drawable.three_stars);

            }else if (cityInformation.getRating() >= 3.25 && cityInformation.getRating() < 3.75){

                holder.ratingImageView.setImageResource(R.drawable.three_and_a_half_stars);

            }else if (cityInformation.getRating() >= 3.75 && cityInformation.getRating() < 4.25){

                holder.ratingImageView.setImageResource(R.drawable.four_stars);

            }else if (cityInformation.getRating() >= 4.25 && cityInformation.getRating() < 4.75){

                holder.ratingImageView.setImageResource(R.drawable.four_and_a_half_stars);

            }else if (cityInformation.getRating() >= 4.75){

                holder.ratingImageView.setImageResource(R.drawable.five_stars);

            }else if (cityInformation.getRating() == 0){

                holder.ratingImageView.setImageResource(0);

            }

            // set rounded average rating for apartment

            holder.nameTextView.setText(cityInformation.getName());
            holder.addressTextView.setText(cityInformation.getAddress());
            holder.price_textView.setText("$ " + cityInformation.getPrice() + ",-");

            // set host name, apartment address and price per night

        }

    }

    @Override
    public int getItemCount() {
        return information.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0)

            return TYPE_HEADER;
            return TYPE_ITEM;
    }

        class RecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Context context;
            ArrayList<CityActivityInformation> information;

            int view_type1;

                ImageView
                        profileImageView,
                        apartementImageView,
                        ratingImageView;
                TextView
                        price_textView,
                        addressTextView,
                        nameTextView;

                //variables for header

            ImageView
                    header_image;
            TextView
                cityNameTextView,
                countryNameTextView;

            private AppCompatActivity appCompatActivity;

            // Variables for listitem


            RecyclerViewViewHolder(View itemView, AppCompatActivity appCompatActivity, int view_type, Context context, ArrayList<CityActivityInformation> information) {
                super(itemView);

                Typeface tp = Typeface.createFromAsset(context.getAssets(),"project_boston_typeface.ttf");

                this.context = context;
                this.information = information;
                this.appCompatActivity = appCompatActivity;

                itemView.setOnClickListener(this);

                // initialisation listItems

                if (view_type == TYPE_ITEM){

                    // if listItem is a normal item, do the following

                    profileImageView = itemView.findViewById(R.id.profile_imageView);
                    apartementImageView = itemView.findViewById(R.id.list_item_imageView);
                    ratingImageView = itemView.findViewById(R.id.rating_imageView);

                    price_textView = itemView.findViewById(R.id.top_textView);
                    addressTextView = itemView.findViewById(R.id.middle_textView);
                    nameTextView = itemView.findViewById(R.id.textView3);

                    // link xml layout elements to java variables

                    price_textView.setTypeface(tp);
                    addressTextView.setTypeface(tp);
                    nameTextView.setTypeface(tp);

                    // set typeface to all textViews

                    view_type1 = 1;

                    // initialisation header

                } else if (view_type == TYPE_HEADER){

                    // if listItem is a header item, do the following

                    header_image = itemView.findViewById(R.id.apartement_images_imageView);

                    cityNameTextView = itemView.findViewById(R.id.city_name_textView);
                    countryNameTextView = itemView.findViewById(R.id.subtitle_textView);

                    // link xml layout elements to java variables

                    cityNameTextView.setTypeface(tp);
                    countryNameTextView.setTypeface(tp);

                    // set city name and country name text typefaces

                    view_type1 = 0;

                }

            }

            @Override
            public void onClick(View view) {

                Firebase.setAndroidContext(context.getApplicationContext());

                if (view_type1 == TYPE_ITEM){

                    // if listItem is a normal item, do the following

                    if (StartupMethods.isOnline(context)) {

                        Drawable drawable = cityView.getBackGroundDrawable();

                        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] b = baos.toByteArray();

                        // convert bitmap object to byte array object

                        int pos = getAdapterPosition();

                        CityActivityInformation information1 = this.information.get(pos-1);

                        // instantiate new CityActivityInformation object at current position -1 (subtracting header item)

                        i = new Intent(this.context, AccountAndApartementActivity.class);

                        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        // prevent activity transition animation from animating

                        i.putExtra("cityBackground", b);
                        i.putExtra("fromActivity", "2");
                        i.putExtra("UID", information1.getUID());
                        i.putExtra("locationId", observables.getCityName());
                        i.putExtra("AID", information1.getAID());



                        cityView.animateActivityExitTransition(context, appCompatActivity, i);

                    } else {

                        Toast.makeText(context, context.getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();

                    }

                }

            }

        }

    }


