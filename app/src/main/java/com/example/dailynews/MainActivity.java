package com.example.dailynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.example.dailynews.fragments.CountriesFragment;
import com.example.dailynews.fragments.Favorites;
import com.example.dailynews.fragments.HomeFragment;
import com.example.dailynews.fragments.SearchFragment;
import com.example.dailynews.fragments.UserFragment;
import com.example.dailynews.fragments.ViewNews;
import com.example.dailynews.interfaces.FavoriteFragmentListener;
import com.example.dailynews.interfaces.HomeFragmentListener;
import com.example.dailynews.interfaces.SearchFragmentListener;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mancj.materialsearchbar.MaterialSearchBar;


import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.Gravity.START;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , SearchFragmentListener, HomeFragmentListener, FavoriteFragmentListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public static BottomNavigationView bottomNavigationView;
    MaterialSearchBar materialSearchBar;
    View view_nave;
    CircleImageView circleImageView;
    TextView txt_name,txt_email;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount acct=null;
    private long backPressedTime;
    private Toast back;
    String frag_check="";
    ProgressBar progressBar;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        setId();
        progressBar.setVisibility(View.VISIBLE);
        setDrawerLayout();
        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        materialSearchBar=findViewById(R.id.searchBar);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        acct = GoogleSignIn.getLastSignedInAccount(this);

        SharedPreferences sharedPreferences1=getSharedPreferences("email_login",MODE_PRIVATE);
         editor=sharedPreferences1.edit();

        //Search view
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(frag_check.equals("fav") && haveNetwork()){
                    Favorites.filter(s.toString());
                }
                else if(!haveNetwork()){
                    if (s.toString().length()>0) {
                        HomeFragment.filterOff(s.toString());
                    }
                }
                else {
                    HomeFragment.filter(s.toString());
                }
            }
        });


        //To get information and display it in navigation
        navigationView.setNavigationItemSelectedListener(this);
        view_nave=navigationView.getHeaderView(0);
        mAuth = FirebaseAuth.getInstance();
        circleImageView=view_nave.findViewById(R.id.imageView_nav);
        txt_email=view_nave.findViewById(R.id.txt_email_nave);
        txt_name=view_nave.findViewById(R.id.txt_user_nav);
        if(mAuth.getCurrentUser()!=null){
            String name=mAuth.getCurrentUser().getDisplayName();
            String email=mAuth.getCurrentUser().getEmail();
            Uri uri=mAuth.getCurrentUser().getPhotoUrl();
            txt_name.setText(name);
            txt_email.setText(email);
            if(uri==null){
                circleImageView.setImageResource(R.drawable.user_defult);
            }
            else {
                Glide.with(this).load(uri).into(circleImageView);
            }
        }

        //To check connect internet
       if (!haveNetwork()) {
            Snackbar.make(drawerLayout,"No Internet Connection",Snackbar.LENGTH_LONG).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                bottomNavigationView.setSelectedItemId(R.id.navigationHome);

            }
        },3000);


    }



    private  void setId(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.navigation_bottom);
        progressBar = findViewById(R.id.main_bar);
    }
    private void setDrawerLayout(){
        ActionBarDrawerToggle toggle =new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(navigationView.getCheckedItem()!=null){
            navigationView.getCheckedItem().setChecked(false);
        }
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
            bottomNavigationView.setSelectedItemId(R.id.navigationHome);
        }else if(frag_check.equals("view")){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
            materialSearchBar.setVisibility(View.VISIBLE);
            bottomNavigationView.setSelectedItemId(R.id.navigationHome);
            frag_check="";
        }else if(frag_check.equals("favView")){
            getSupportFragmentManager().beginTransaction().replace(R.id.container,new Favorites()).commit();
            materialSearchBar.setVisibility(View.VISIBLE);
            frag_check="";
        }
        else if(frag_check.equals("fav")){
            frag_check="";
        }
        else {
            if (backPressedTime + 2000>System.currentTimeMillis()){
                back.cancel();
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                super.onBackPressed();
                return;
            }else {
                back= Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_LONG);
                back.show();
            }
            backPressedTime=System.currentTimeMillis();
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        switch (id){
            case R.id.nav_logout:
                if(haveNetwork()){
                signOut();}
                else{
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_about:
                Intent intent2 = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_contact_us:
               if (haveNetwork()){
                   AlertDialog.Builder builder = new AlertDialog.Builder(this);
                   View view = getLayoutInflater().inflate(R.layout.contact_us_layout, null);
                   MaterialRippleLayout face = view.findViewById(R.id.facebook);
                   MaterialRippleLayout gog = view.findViewById(R.id.tele);
                   builder.setView(view).setTitle("Contact").setIcon(R.drawable.ic_phone_dialog);
                   final AlertDialog dialog = builder.create();
                   dialog.show();
                   face.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {

                           Intent browse=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Daily-News-109948707528539/"));
                           startActivity(browse);
                           dialog.dismiss();

                       }
                   });

                   gog.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent emailIntent = new Intent(Intent.ACTION_SENDTO , Uri.parse("mailto:" + "dailynews20202020@gmail.com"));
                           emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Contact Owner");
                           startActivity(emailIntent);
                           dialog.dismiss();
                       }
                   });
               }else{
                   Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
               }

                break;
            case R.id.nav_favorites:
                if(haveNetwork()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new Favorites()).commit();
                    frag_check = "fav";
                    materialSearchBar.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
                bottomNavigationView.setSelectedItemId(R.id.navigationHome);
                materialSearchBar.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_countries:
                if(haveNetwork()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new CountriesFragment()).commit();
                    materialSearchBar.setVisibility(View.GONE);
                }else {
                    Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.nav_settings:
                Intent settingIntent=new Intent();
                settingIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri=Uri.fromParts("package",getPackageName(),null);
                settingIntent.setData(uri);
                startActivity(settingIntent);

                break;
            case R.id.nav_feedback:
                Intent intent = new Intent(MainActivity.this,FeedbackActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(START);
        return true;
    }

    private void signOut() {
        if (acct != null) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent=new Intent(MainActivity.this,Login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        }
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
        editor.putBoolean("login",false);
        editor.apply();
        Intent intent=new Intent(getApplicationContext(),Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        Toast.makeText(getApplicationContext(),"User account sign out.",Toast.LENGTH_SHORT).show();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.navigationHome:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).addToBackStack(null).commit();
                    materialSearchBar.setVisibility(View.VISIBLE);
                    frag_check="";
                    if(navigationView.getCheckedItem()!=null){
                        navigationView.getCheckedItem().setChecked(false);
                    }
                    break;
                case R.id.navigationSearch:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new SearchFragment()).commit();
                    materialSearchBar.setVisibility(View.GONE);
                    if(navigationView.getCheckedItem()!=null){
                        navigationView.getCheckedItem().setChecked(false);
                    }
                    break;
                case R.id.navigationMenu:
                    drawerLayout.openDrawer(GravityCompat.START);
                    break;
                case R.id.navigationMyProfile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new UserFragment()).commit();
                    if(navigationView.getCheckedItem()!=null){
                        navigationView.getCheckedItem().setChecked(false);
                    }
                    materialSearchBar.setVisibility(View.GONE);
                    break;
            }
            return true;
        }
    };


    @Override
    public void onDataSent(String data) {
        HomeFragment.upadteData(data);
    }

    @Override
    public void sendUrl(String url) {
        ViewNews.instance(url);
        materialSearchBar.setVisibility(View.GONE);
        frag_check="view";
    }

    @Override
    public void sendDescription(String description) {
        ViewNews.getDescription(description);
        materialSearchBar.setVisibility(View.GONE);
        frag_check="view";
    }

    @Override
    public void onClick(News news) {
        ViewNews.instance(news.getNewsUrl());
        materialSearchBar.setVisibility(View.GONE);
        frag_check="favView";
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MainActivity.this,NewsService.class));
    }
}
