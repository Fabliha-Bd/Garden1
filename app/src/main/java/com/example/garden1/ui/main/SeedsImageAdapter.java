package com.example.garden1.ui.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.garden1.R;
import com.example.garden1.ui.main.Model.CartItem;
import com.example.garden1.ui.main.Model.Upload;

import java.util.HashMap;
import java.util.List;

public class SeedsImageAdapter extends RecyclerView.Adapter<SeedsImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private HashMap<String, CartItem> cartMap;
    public SeedsImageAdapter(Context context, List<Upload> uploads, HashMap<String, CartItem>cartMap) {
        mContext = context;
        mUploads = uploads;
        this.cartMap=cartMap;

    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.shop_cardview, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        CartItem cartCurrent= new CartItem();
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewPrice.setText(uploadCurrent.getmPrice());
       /* Picasso.with(mContext)
               // .load("http://via.placeholder.com/300.png")
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);*/
        Glide.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .centerCrop()
                .into(holder.imageView);
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key=uploadCurrent.getName();
                Log.v("CartKey",key);

                if(cartMap.containsKey(key))
                {
                    cartMap.put(key,
                            new CartItem(uploadCurrent.getName(),Integer.parseInt(uploadCurrent.getmPrice()),uploadCurrent.getmType()
                                    ,cartMap.get(key).getQuantity()+1));
                }else{
                    cartMap.put(key,
                            new CartItem(uploadCurrent.getName(),Integer.parseInt(uploadCurrent.getmPrice()),uploadCurrent.getmType()
                                    ,1));
                }


             Log.v("Cart",cartMap.get(key).toString());
            }
        });

    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;
        public TextView textViewPrice;
        public Button btnAddToCart;
        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewPrice = itemView.findViewById(R.id.text_view_price);
            imageView = itemView.findViewById(R.id.image_view_upload);
            btnAddToCart= itemView.findViewById(R.id.btnAddToCart);

        }
    }
}