package com.machine.test.model;

import android.app.Activity;
import android.content.Context;
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
 * Created by Anand on 11/29/2016.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> productList;
     Icommunication icommunication;
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Product product=productList.get(position);
        holder.mProductName.setText(product.getProductname());
        holder.mProductPrice.setText("Price: "+product.getPrice());
        holder.mVendorName.setText(product.getVendorname());
        holder.mVendorAddress.setText(product.getVendoraddress());
        // loading album cover using Glide library
        Glide.with(mContext).load(product.getProductImg()).into(holder.mProductImage);
       holder.mAddtocartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // adding product item into cart item table
                DatabaseHandler db = new DatabaseHandler(mContext);
                CartItem cartItem =new CartItem();
                cartItem.setProductname(product.getProductname());
                cartItem.setPrice(product.getPrice());
                cartItem.setVendorname(product.getVendorname());
                cartItem.setVendoraddress(product.getVendoraddress());
                cartItem.setPhoneNumber(product.getPhoneNumber());
                cartItem.setProductImg(product.getProductImg());
                db.addCart(cartItem);
                icommunication.showSnackBarMesage("Item added into cart");
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mProductName,mProductPrice,mVendorName,mVendorAddress;
        public ImageView mProductImage;
        Button mAddtocartButton;
        public MyViewHolder(View view) {
            super(view);
            mProductName= (TextView) view.findViewById(R.id.product_name);
            mProductPrice = (TextView) view.findViewById(R.id.product_price);
            mVendorName= (TextView) view.findViewById(R.id.vendor_name);
            mVendorAddress= (TextView) view.findViewById(R.id.vendor_address);
            mProductImage= (ImageView) view.findViewById(R.id.thumbnail);
            mAddtocartButton=(Button)view.findViewById(R.id.add_item_cart);

        }
    }

    public ProductAdapter(Activity activity, List<Product> productList) {
        mContext=activity;
        this.productList=productList;
        icommunication=(Icommunication)mContext;
    }
}
