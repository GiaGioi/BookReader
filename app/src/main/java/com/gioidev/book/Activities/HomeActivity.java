package com.gioidev.book.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.jean.jcplayer.model.JcAudio;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.gioidev.book.Adapter.AdapterHome.VerticalRecyclerViewAdapter;
import com.gioidev.book.Fragment.BookcaseFragment;
import com.gioidev.book.Fragment.Fragment_Audio_Book;
import com.gioidev.book.Fragment.Fragment_CaNhan;
import com.gioidev.book.Fragment.Fragment_Home;
import com.gioidev.book.Fragment.Fragment_Khach_Hang;
import com.gioidev.book.Fragment.Fragment_Ki_Nang;
import com.gioidev.book.Model.DownSong;
import com.gioidev.book.Model.TimerUser;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.gioidev.book.Utils.Functions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static java.security.AccessController.getContext;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private FirebaseAuth auth;
//    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    private TextView txtName,txtEmail;

    private CallbackManager callbackManager;
    String TAG = "TAG";
    DrawerLayout drawer;
    private boolean isExit = false;
    private Timer timer = null;
    private TimerTask timeTask = null;
    DatabaseReference database;
    private FrameLayout mainContainer;
    private RecyclerView rvVertical;
    ArrayList<VerticalModel> mArrayList = new ArrayList<>();

    VerticalRecyclerViewAdapter mAdapter;
TextView textViewnameemail;
    private SliderView imageSlider;
    private SwipeRefreshLayout swipeRefreshLayout;

    Toolbar toolbar;

    private ImageView imageView;
    private TextView textView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.imageView);


        setSupportActionBar(toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
//            textViewnameemail.setText(email);
        }
        else if (AccessToken.getCurrentAccessToken()!= null){
            Profile profile = Profile.getCurrentProfile();
//            textViewnameemail.setText(profile.getName() + "");
        }

        LayoutInflater inflater = getLayoutInflater();
        View myView = inflater.inflate(R.layout.nav_header_main, null);
        TextView tvEmail = (TextView) myView.findViewById(R.id.textViewnameemail);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(android.R.color.background_dark);
        toolbar.setNavigationIcon(R.drawable.toggle);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        Fragment_Home homeFragment = new Fragment_Home();
        Functions.changeMainFragment(HomeActivity.this, homeFragment);

        if (AccessToken.getCurrentAccessToken() != null) {
            // here is facebook

        }
        else if (HomeActivity.this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("0")){

            //ffirebase
            tvEmail.setText(""+user.getEmail());
        }
        else if (HomeActivity.this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("1")){

            //google
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(HomeActivity.this);
            if (acct != null) {
                tvEmail.setText(""+acct.getEmail());
            }
        }

            startRepeating();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_vip) {
            Toasty.success(this,"Vip",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("WrongConstant")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {

                case R.id.navigation_home:
                    fragment = new Fragment_Home();
                    loadFragment(fragment);
                    toolbar.setVisibility(View.VISIBLE);
                    toolbar.setTitle(R.string.home);
                    return true;
                case R.id.navigation_category:

                    overridePendingTransition(R.anim.right_to_left,R.anim.left_to_right);
                    fragment = new BookcaseFragment();
                    loadFragment(fragment);
                    toolbar.setVisibility(View.GONE);

                    return true;
                case R.id.navigation_image:
                    overridePendingTransition(R.anim.right_to_left,R.anim.left_to_right);
                    Fragment fragmentcanhan;
                    fragmentcanhan = new Fragment_CaNhan();
                    loadFragment(fragmentcanhan);

                    return true;

            }
            return false;
        }
    };
    public void hide_show(){
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }



    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        int id = menuItem.getItemId();

        if (id == R.id.nav_marketing) {
            fragment = new Fragment_Khach_Hang();
            loadFragment(fragment);
        } else if (id == R.id.nav_horoscope) {

        } else if (id == R.id.nav_skillbook) {
            fragment = new Fragment_Ki_Nang();
            loadFragment(fragment);

        } else if (id == R.id.nav_short_story) {

        }else if (id == R.id.nav_comic) {

        }else if (id == R.id.nav_audio_book) {
            fragment = new Fragment_Audio_Book();
            loadFragment(fragment);

        }else
            if (id == R.id.nav_link) {
//

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            doubleExit();
        }
    }
    private void doubleExit() {
        if (isExit) {
            finish();
        } else {
            isExit = true;
            Toast.makeText(this, "Nhấn lần nữa để trở lại", Toast.LENGTH_SHORT).show();
            timeTask = new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            };
            timer.schedule(timeTask, 2000);
        }
    }


    private Handler mHandler = new Handler();

    public void  startRepeating() {
        //mHandler.postDelayed(mToastRunnable, 5000);

        mToastRunnable.run();
    }

    public void stopRepeating() {
        mHandler.removeCallbacks(mToastRunnable);
    }



    private Runnable mToastRunnable = new Runnable() {

        @Override
        public void run() {
            if (AccessToken.getCurrentAccessToken() != null) {
                // here is facebook
            loadUserProfile(AccessToken.getCurrentAccessToken());
            }
            else if (HomeActivity.this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("0")){
                //ffirebase
                if (auth.getUid()!=null) {
                    database = FirebaseDatabase.getInstance().getReference("usertimer").child(auth.getUid() + "");

                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            int i;
                            if (dataSnapshot.child("timer").getValue() == null) {
                                i = 0;

                            } else {
                                i = Integer.valueOf(String.valueOf(dataSnapshot.child("timer").getValue()));
                            }
                            boolean vip = false;
                            if (i >= 3600) vip = true;
                            i++;
                            final String user = auth.getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("usertimer");
                            TimerUser timerUser = new TimerUser(user, i, vip);
                            if (timerUser != null) {
                                reference.child(user).setValue(timerUser);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }
            else if (HomeActivity.this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("1")) {
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(HomeActivity.this);
                database = FirebaseDatabase.getInstance().getReference("usertimer").child(acct.getId());
                Toast.makeText(HomeActivity.this, ""+acct.getId(), Toast.LENGTH_SHORT).show();
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int i ;
                        if (dataSnapshot.child("timer").getValue() == null){
                            i = 0;

                        }
                        else {
                            i=Integer.valueOf(String.valueOf(dataSnapshot.child("timer").getValue()));
                        }

                        i++;
                        final String user = auth.getUid();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("usertimer");
                        TimerUser timerUser = new TimerUser(user, i);
                        reference.child(user).setValue(timerUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }


            mHandler.postDelayed(this, 1000);


        }
    };
    @Override
    protected void onDestroy() {
        stopRepeating();
        super.onDestroy();
    }
    private void loadUserProfile(AccessToken newAccessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();


                    database = FirebaseDatabase.getInstance().getReference("usertimer").child(id);

                    database.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            int i ;
                            if (dataSnapshot.child("timer").getValue() == null){
                                i = 0;

                            }
                            else {
                                i=Integer.valueOf(String.valueOf(dataSnapshot.child("timer").getValue()));
                            }

                            i++;
                            boolean vip = false;
                            if (i >= 3600){
                                 vip = true;
                            }

                            final String user = id;
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("usertimer");
                            TimerUser timerUser = new TimerUser(user, i,vip);
                            reference.child(user).setValue(timerUser);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }


}
