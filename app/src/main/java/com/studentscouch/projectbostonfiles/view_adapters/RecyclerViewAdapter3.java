package com.studentscouch.projectbostonfiles.view_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.studentscouch.projectbostonfiles.R;

import java.util.ArrayList;

public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.RecyclerViewViewHolder3> {

    private Context context;
    private ArrayList<MessageActivityInformation> information;

    //redo this recyclerview with tutorials to make it work!!!!!

    public RecyclerViewAdapter3(Context context, ArrayList<MessageActivityInformation> information) {

        this.context = context;
        this.information = information;

    }

    @NonNull
    @Override
    public RecyclerViewViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.message_item_view, null);

        return new RecyclerViewViewHolder3(view, context, information);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewViewHolder3 holder, int position) {

        MessageActivityInformation city = information.get(position);

        holder.profileImageView.setImageDrawable(context.getResources().getDrawable(city.getProfileImage()));
        holder.notificationImageView.setImageDrawable(context.getResources().getDrawable(city.getNotificationImage()));

    }

    @Override
    public int getItemCount() {
        return information.size();
    }

    class RecyclerViewViewHolder3 extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView
                profileImageView,
                notificationImageView;
        Context context;
        ArrayList<MessageActivityInformation> information;

        RecyclerViewViewHolder3(View itemView, Context context, ArrayList<MessageActivityInformation> information) {
            super(itemView);

            this.information = information;
            this.context = context;

            itemView.setOnClickListener(this);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            notificationImageView = itemView.findViewById(R.id.notificationImageView);

        }

        @Override
        public void onClick(View view) {

            //int pos = getAdapterPosition();

            //MessageActivityInformation information = this.information.get(pos);

            /*

            Intent i = new Intent(this.context, PersonMessagingActivity.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            i.putExtra("profile_img_id", information.getProfileImage());

            this.context.startActivity(i);

            */

        }
    }

}
