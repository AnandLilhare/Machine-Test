package com.machine.test.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.gifts.bottomnavigation.R;
import com.bumptech.glide.Glide;
import com.machin.test.dataHelper.DatabaseHandler;

import java.util.List;

/**
 * Created by anand on 30/11/16.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private Context mContext;
    private List<CartItem> cartList;
    Icommunication icommunication;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        CartItem cartItem=cartList.get(position);
        holder.mProductName.setText(cartItem.getProductname());
        holder.mProductPrice.setText(cartItem.getPrice());
        holder.mVendorName.setText(cartItem.getVendorname());
        holder.mVendorAddress.setText(cartItem.getVendoraddress());
        // loading album cover using Glide library
        Glide.with(mContext).load(cartItem.getProductImg()).into(holder.mProductImage);
        holder.mRemovecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CartItem cartItem=cartList.get(position);
                DatabaseHandler db = new DatabaseHandler(mContext);
                db.deleteContact(cartItem);
                cartList.remove(position);
                 notifyDataSetChanged();
                icommunication.showSnackBarMesage("Item Removed from cart");
                int totalprices=db.getTotalPrices();
                if(totalprices!=0) {
                    icommunication.showSnackBarMesage("Total Price " + totalprices);
                }
            }
        });

        holder. mCallTOVendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartItem cartItem = cartList.get(position);
                String mphonenumber = cartItem.getPhoneNumber();
                icommunication.phoneCall(mphonenumber);

            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mProductName,mProductPrice,mVendorName,mVendorAddress;
        public ImageView mProductImage;
        Button mCallTOVendor,mRemovecart;
        public MyViewHolder(View view) {
            super(view);
            mProductName= (TextView) view.findViewById(R.id.product_name);
            mProductPrice = (TextView) view.findViewById(R.id.product_price);
            mVendorName= (TextView) view.findViewById(R.id.vendor_name);
            mVendorAddress= (TextView) view.findViewById(R.id.vendor_address);
            mProductImage= (ImageView) view.findViewById(R.id.thumbnail);
            mCallTOVendor=(Button)view.findViewById(R.id.call_vendor);
            mRemovecart=(Button)view.findViewById(R.id.remove_list);

        }
    }

    public CartAdapter(Activity activity, List<CartItem> productList) {
        mContext=activity;
        this.cartList=productList;
        icommunication=(Icommunication)mContext;

    }
}
