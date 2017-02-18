package com.android.gifts.bottomnavigation;


import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.machin.test.dataHelper.DatabaseHandler;
import com.machine.test.model.CartAdapter;
import com.machine.test.model.CartItem;
import com.machine.test.model.Icommunication;
import com.machine.test.model.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand on 11/29/2016.
 */

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<CartItem> cartList;
    DatabaseHandler db;
    Icommunication icommunication;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View V = inflater.inflate(R.layout.cart_fragment_layout, container, false);
        cartList=new ArrayList<>();
        recyclerView = (RecyclerView)V.findViewById(R.id.recycler_view);
        icommunication=(Icommunication)getActivity();
        db=new DatabaseHandler(getActivity());
        prepareCartItemList();



        return V;
    }

    private void prepareCartItemList() {
        cartList.clear();
        cartList=db.getAllCartItem();
        if(cartList.size()!=0) {

            adapter = new CartAdapter(getActivity(), cartList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            DisplayPrices();
        }

    }

    private void DisplayPrices() {
        int totalprices=db.getTotalPrices();
        if(totalprices!=0) {
            icommunication.showSnackBarMesage("Total Price " + totalprices);
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
