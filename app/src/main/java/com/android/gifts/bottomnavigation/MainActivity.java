package com.android.gifts.bottomnavigation;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.machine.test.model.Icommunication;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class MainActivity extends AppCompatActivity implements Icommunication{

    BottomBar bottomBar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    CoordinatorLayout coordinatorLayout;
    private static final int RECORD_REQUEST_CODE = 101;
    //changing code for showing new changes for branch
    int branchcode=0;
    // thired branch code changes
    // checking merging confilog during the ther merging a barach.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_buttons);
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        final TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.shop_string);
        setSupportActionBar(toolbarTop);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottomBar = BottomBar.attach(this, savedInstanceState);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        bottomBar.setItemsFromMenu(R.menu.three_buttons_menu, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int itemId) {
               fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager
                       .beginTransaction();
                switch (itemId) {
                    case R.id.prodouct_item:
                        mTitle.setText(R.string.shop_string);
                        ProductFragment productFragment=new ProductFragment();
                        fragmentTransaction.replace(R.id.fragmentContainer, productFragment, "product_flag");
                        fragmentTransaction.commit();
                        break;
                    case R.id.cart_item:
                      mTitle.setText(R.string.item_cart);
                       CartFragment cardFragment=new CartFragment();
                       fragmentTransaction.replace(R.id.fragmentContainer,cardFragment, "card_flag");
                        fragmentTransaction.commit();
                        break;
                }
            }
        });

        bottomBar.setActiveTabColor("#C2185B");
        int itemId=bottomBar.getCurrentTabPosition();
        if(itemId==0){
            mTitle.setText(R.string.shop_string);
            ProductFragment productFragment=new ProductFragment();
            fragmentTransaction.replace(R.id.fragmentContainer, productFragment, "product_flag");
            fragmentTransaction.commit();
        }else if(itemId==1){
            mTitle.setText(R.string.item_cart);
            CartFragment cardFragment=new CartFragment();
            fragmentTransaction.replace(R.id.fragmentContainer,cardFragment, "card_flag");
            fragmentTransaction.commit();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        bottomBar.onSaveInstanceState(outState);
    }

    @Override
    public void showSnackBarMesage(String message) {
        Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_LONG).show();;
    }

    @Override
    public void phoneCall(String number) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }else {
            startActivity(callIntent);
        }
    }
    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE},
                RECORD_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[]
                                                   grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE: {
                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {


                        Toast.makeText(this,
                                "Record permission required",
                                Toast.LENGTH_LONG).show();
                    }
                    return;
                }

            }
        }

}
