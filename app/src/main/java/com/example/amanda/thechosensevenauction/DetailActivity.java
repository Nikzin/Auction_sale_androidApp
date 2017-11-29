package com.example.amanda.thechosensevenauction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        final Auction auction = (Auction) intent.getSerializableExtra(MainActivity.AUCTION);
        Supplier supplier = (Supplier) intent.getSerializableExtra(MainActivity.SUPPLIER);

        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        TextView textViewStartTime = (TextView) findViewById(R.id.textViewStartTime);
        TextView textViewEndTime = (TextView) findViewById(R.id.textViewEndTime);
        TextView textViewBuyNowPrice = (TextView) findViewById(R.id.textViewBuyNowPrice);
        ImageView detailImage = (ImageView) findViewById(R.id.imageViewAuction);

        Picasso.with(this).load(auction.getImageUrl()).into(detailImage);

        textViewName.setText(auction.getName());
        textViewDescription.setText(auction.getDescription());
        textViewStartTime.setText(auction.getStartTime());
        textViewEndTime.setText(auction.getEndTime());
        textViewBuyNowPrice.setText(String.valueOf(auction.getBuyNowPrice()));

        TextView supplierName = (TextView) findViewById(R.id.textViewSupplierName);
        TextView supplierEmail = (TextView) findViewById(R.id.textViewEmail);
        TextView supplierPhone = (TextView) findViewById(R.id.textViewPhone);
        TextView supplierAddress = (TextView) findViewById(R.id.textViewAddress);
        TextView supplierPostalcode = (TextView) findViewById(R.id.textViewPostalCode);
        TextView supplierCity = (TextView) findViewById(R.id.textViewCity);


        supplierName.setText(supplier.getCompanyName());
        supplierEmail.setText(supplier.getEmail());
        supplierPhone.setText(supplier.getPhone());
        supplierAddress.setText(supplier.getAddresss());
        supplierPostalcode.setText(supplier.getPostalCode());
        supplierCity.setText(supplier.getCity());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent email = new Intent(Intent.ACTION_SEND);

                email.setType("html/text");
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"perez.lencinas@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Auction!");
                email.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(new StringBuilder()
                        .append("<h1>" + auction.getName() + "</h1>" )
                        .append("<p>" + auction.getDescription() + "</p>" )
                        .append("<h4> Acceptpris: " + auction.getBuyNowPrice() + "</h4>" )
                        .append("<p>"+ auction.getImageUrl() +"/p>").toString()));

                try {
                    startActivity(Intent.createChooser(email, "Choose an Email client:"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
