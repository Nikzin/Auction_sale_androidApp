package com.example.amanda.thechosensevenauction;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



public class MainActivity extends AppCompatActivity {
    public static final String AUCTION = "AUCTION";
    public static final String SUPPLIER = "SUPPLIER";
    private ArrayList<Auction> auctionArrayList = new ArrayList<>();
    public ArrayList<Bid> bidList = new ArrayList<>();
    Auction auctionObject;
    public ArrayList<Supplier> supplierList = new ArrayList<>();
    Supplier supplierObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        final JsonArrayRequest supplierRequest = new JsonArrayRequest("http://nackademiska-api.azurewebsites.net/api/supplier",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                final JSONObject supplier = (JSONObject) response.get(i);
                                supplierObject = new Supplier(supplier.getString("id"), supplier.getString("companyName"), supplier.getString("email"), supplier.getString("phone"), supplier.getString("address"), supplier.getString("postalCode"), supplier.getString("city"));
                                supplierList.add(supplierObject);
                                System.out.println(supplierObject.getCompanyName());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });


        final JsonArrayRequest request = new JsonArrayRequest("http://nackademiska-api.azurewebsites.net/api/auction",
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                final JSONObject auction = (JSONObject) response.get(i);
                                auctionObject = new Auction(auction.getInt("id"),
                                        auction.getString("name"), auction.getString("description"),
                                        auction.getString("startTime"), auction.getString("endTime"),
                                        auction.getString("imageUrl"), auction.getInt("categoryId"),
                                        auction.getString("supplierId"), auction.getInt("buyNowPrice"));


                                Date todayDate = Calendar.getInstance().getTime();

                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                try {

                                    Date endDate = formatter.parse(auctionObject.getEndTime());
                                    Date startDate = formatter.parse(auctionObject.getStartTime());

                                    if (todayDate.before(endDate) && todayDate.after(startDate)) {

                                        auctionArrayList.add(auctionObject);
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                final JsonArrayRequest bidRequest = new JsonArrayRequest("http://nackademiska-api.azurewebsites.net/api/bid/" + auction.getInt("id"),
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
                                                try {

                                                    JSONObject highestBidObject = (JSONObject) response.get(response.length() - 1);
                                                    double highestBid = highestBidObject.getDouble("bidPrice");
                                                    int auctionId = highestBidObject.getInt("auctionId");
                                                    for (Auction a : auctionArrayList) {
                                                        if (a.getId() == auctionId) {
                                                            a.setHighestBid(highestBid);
                                                            break;
                                                        }
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                            }
                                        });
                                requestQueue.add(bidRequest);

                            }

                            setUpAuctionList();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        requestQueue.add(request);
        requestQueue.add(supplierRequest);
    }


    private void setUpAuctionList() {

        AuctionListAdapter auctionListAdapter = new AuctionListAdapter(this, R.layout.auction_list_item, auctionArrayList);

        ListView auctionListView = (ListView) findViewById(R.id.auctionListView);

        auctionListView.setAdapter(auctionListAdapter);


        auctionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Auction auction = auctionArrayList.get(position);
                Supplier supplier = null;
                for (Supplier s : supplierList) {
                    if (auction.getSupplierId().equals(s.getId())) {
                        supplier = s;
                        break;
                    }
                }
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(AUCTION, auction);
                intent.putExtra(SUPPLIER, supplier);
                startActivity(intent);

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
