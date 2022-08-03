package com.example.dailynews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hsalf.smileyrating.SmileyRating;

public class FeedbackActivity extends AppCompatActivity {
    SmileyRating smiley;
    TextInputLayout feed;
    MaterialRippleLayout send;
    FirebaseUser firebaseUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        smiley = (SmileyRating) findViewById(R.id.smile_rating);
        feed = findViewById(R.id.feed);
        send = findViewById(R.id.send_feedback);
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid() + "";

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(haveNetwork()){
                    if(!feed.getEditText().getText().toString().equals("")){
                        SmileyRating.Type type = smiley.getSelectedSmiley();
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"dailynews20202020@gmail.com"});
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Feedback");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "User Id: "+id+"\n \n" +"feedback rating = "+type+"\n\n\n"+"user feedback : \n"+feed.getEditText().getText().toString());
                        try{
                            startActivity(Intent.createChooser(emailIntent,"Send mail..."));
                            finish();
                        }catch (Exception e){
                            Toast.makeText(FeedbackActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }else {
                    Toast.makeText(FeedbackActivity.this, "Check your internet connection", Toast.LENGTH_SHORT).show();

                }

        }
    });



}
    public boolean haveNetwork(){
        boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;
    }
}
