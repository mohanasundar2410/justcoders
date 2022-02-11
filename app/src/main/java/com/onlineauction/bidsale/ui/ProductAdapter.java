package com.onlineauction.bidsale.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onlineauction.bidsale.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private static final String TAG = "ProductAdapter";
    Context context;
    ArrayList<Uri> imageList;
    private RemoveListener listener;


    public ProductAdapter(Context context, ArrayList<Uri> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.single_image_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context)
                .asBitmap()
                .load(imageList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productImage);

        Log.d(TAG, "onBindViewHolder: "+holder.productImage.getDrawable());


        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAtPosition(position);
            }
        });

    }


    private void removeAtPosition(int position) {
        imageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, imageList.size());

        if (listener != null) {
            listener.onRemoveClick(position);
        }
    }



    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface RemoveListener {
        void onRemoveClick(int position);
    }

    public void setRemoveClickListener(RemoveListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage, cancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.productImage);
            cancel = itemView.findViewById(R.id.delete);
        }
    }
}
