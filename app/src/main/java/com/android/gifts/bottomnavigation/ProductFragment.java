package com.android.gifts.bottomnavigation;


import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.machine.test.model.Icommunication;
import com.machine.test.model.InternetDetector;
import com.machine.test.model.Product;
import com.machine.test.model.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand on 11/29/2016.
 */

public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private InternetDetector NetDector;
    Icommunication icommunication;
    ProgressBar progressbar;
    private static final  String PRODUCT_URL="https://mobiletest-hackathon.herokuapp.com/getdata/";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View V = inflater.inflate(R.layout.product_fragment_layout, container, false);
        icommunication=(Icommunication)getActivity();
        NetDector= new InternetDetector(getActivity());
        progressbar = (ProgressBar) V.findViewById(R.id.Progressyoutubsearch);
        productList=new ArrayList<>();
        recyclerView = (RecyclerView)V.findViewById(R.id.recycler_view);
        adapter = new ProductAdapter(getActivity(), productList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
       boolean isInternetOn = NetDector.isConnectingToInternet();
        if (isInternetOn) {
                      new GetPrdocutList().execute("");
        }else{
          icommunication.showSnackBarMesage("Please check internet connection");
        }
        return V;
    }

    private class GetPrdocutList extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressbar.setVisibility(View.VISIBLE);

        }
        @Override
        protected String doInBackground(String... strings) {
            String result = "Done";
            try {
                productList.clear();

                URL url = new URL(PRODUCT_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONArray jsonArray=jsonObject.getJSONArray("products");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject productJsonObject = jsonArray.getJSONObject(i);
                     String productname=productJsonObject.getString("productname");
                     String price=productJsonObject.getString("price");
                     String vendorname=productJsonObject.getString("vendorname");
                     String vendoraddress=productJsonObject.getString("vendoraddress");
                     String productImg=productJsonObject.getString("productImg");
                     String phoneNumber=productJsonObject.getString("phoneNumber");
                    Product product=new Product(productname,price,vendorname,vendoraddress, productImg,phoneNumber);
                    productList.add(product);
                }

                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                result="fail";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String temp) {
            progressbar.setVisibility(View.INVISIBLE);
            if(temp.equals("Done")){
               adapter.notifyDataSetChanged();
            }
        }
    }
    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
