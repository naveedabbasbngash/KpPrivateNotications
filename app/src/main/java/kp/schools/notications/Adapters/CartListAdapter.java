package kp.schools.notications.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kp.schools.notications.Activities.NotificationDetails;
import kp.schools.notications.Model.NotificationModel;
import kp.schools.notications.R;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {
    private Context context;
    private List<NotificationModel> cartList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            thumbnail = view.findViewById(R.id.thumbnail);

            viewForeground = view.findViewById(R.id.view_foreground);

        }
    }


    public CartListAdapter(Context context, List<NotificationModel> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final NotificationModel item = cartList.get(position);
        holder.name.setText(item.getTitle());

        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Bundle bundle = new Bundle();
                bundle.putString("title", item.getTitle());
                bundle.putString("description", item.getDescription());
                bundle.putString("pdf_url", item.getDate());
                bundle.putString("image_url", item.getImageurl());
                context.startActivity(new Intent(context, NotificationDetails.class)
                        .putExtras(bundle));


            }
        });


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public void removeItem(int position) {
        cartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(NotificationModel item, int position) {
        cartList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}