package com.gioidev.wakabook.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.gioidev.wakabook.Adapter.AdapterHome.VerticalRecyclerViewAdapter;
import com.gioidev.wakabook.Fragment.BookcaseFragment;
import com.gioidev.wakabook.Fragment.FragmentComic;
import com.gioidev.wakabook.Fragment.Fragment_Book_Vip;
import com.gioidev.wakabook.Fragment.Fragment_CaNhan;
import com.gioidev.wakabook.Fragment.Fragment_Home;
import com.gioidev.wakabook.Fragment.Fragment_Khach_Hang;
import com.gioidev.wakabook.Fragment.Fragment_Tam_ly;
import com.gioidev.wakabook.Fragment.Fragment_TruyenNgan;
import com.gioidev.wakabook.Model.TimerUser;
import com.gioidev.wakabook.Model.VerticalModel;
import com.gioidev.book.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.gioidev.wakabook.Utils.Functions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    //    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    private TextView txtName, txtEmail;

    private CallbackManager callbackManager;
    String TAG = "TAG";
    DrawerLayout drawer;
    private boolean isExit = false;
    private Timer timer = null;
    private TimerTask timeTask = null;
    private TextView tvVip;
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
    private TextView tvViewNewBook;

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        View view = getLayoutInflater().inflate(R.layout.nav_header_main, null);

        toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        textViewnameemail = view.findViewById(R.id.textViewnameemail);
        setSupportActionBar(toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu", "").equals("0")) {
            textViewnameemail.setText(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
        } else if (this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu", "").equals("1")) {
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                textViewnameemail.setText(acct.getDisplayName());
            }
        } else if (AccessToken.getCurrentAccessToken() != null) {
            Profile profile = Profile.getCurrentProfile();
            textViewnameemail.setText(profile.getName() + "");
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

        tvVip = view.findViewById(R.id.textViewVIP);
        Fragment_Home homeFragment = new Fragment_Home();
        Functions.changeMainFragment(HomeActivity.this, homeFragment);

        if (AccessToken.getCurrentAccessToken() != null) {
            // here is facebook

        } else if (HomeActivity.this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu", "").equals("0")) {
            //firebase
            tvEmail.setText("" + Objects.requireNonNull(user).getEmail());
            Log.e(TAG, "User: " + user.getEmail());
        } else if (HomeActivity.this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu", "").equals("1")) {

            //google
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(HomeActivity.this);
            if (acct != null) {
                tvEmail.setText("" + acct.getEmail());
            }
        }

        View headerview = navigationView.getHeaderView(0);
        FirebaseUser emailfirebase = auth.getCurrentUser();
        String email1 = null;
        if (emailfirebase != null) {
            email1 = emailfirebase.getEmail();
        }
        Log.e("Email: ", Objects.requireNonNull(email1));
        textViewnameemail = headerview.findViewById(R.id.textViewnameemail);
        textViewnameemail.setText(email1);
        startRepeating();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_vip) {
            Toasty.success(this, "Vip", Toast.LENGTH_SHORT).show();
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
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                    fragment = new BookcaseFragment();
                    loadFragment(fragment);
                    toolbar.setVisibility(View.GONE);
                    return true;

                case R.id.navigation_image:
                    overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
                    Fragment fragmentcanhan;
                    fragmentcanhan = new Fragment_CaNhan();
                    loadFragment(fragmentcanhan);
                    toolbar.setVisibility(View.GONE);
                    return true;

            }
            return false;
        }
    };

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
            fragment = new Fragment_Book_Vip();
            loadFragment(fragment);
        } else if (id == R.id.nav_skillbook) {
            fragment = new Fragment_Tam_ly();
            loadFragment(fragment);

        } else if (id == R.id.nav_short_story) {
            fragment = new Fragment_TruyenNgan();
            loadFragment(fragment);
        } else if (id == R.id.nav_comic) {
            fragment = new FragmentComic();
            loadFragment(fragment);

        } else if (id == R.id.nav_audio_book) {
            Intent intent = new Intent(HomeActivity.this, AudioBookActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_link) {
//

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else if (getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        } else {
            getFragmentManager().popBackStack();
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

    public void startRepeating() {

        mToastRunnable.run();
    }

    public void stopRepeating() {
        mHandler.removeCallbacks(mToastRunnable);
    }

    private Runnable mToastRunnable = new Runnable() {

        @Override
        public void run() {
            if (AccessToken.getCurrentAccessToken() != null) {
                loadUserProfile(AccessToken.getCurrentAccessToken());
            } else if (HomeActivity.this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu", "").equals("0")) {
                //ffirebase
                //Toast.makeText(HomeActivity.this, "bO DANG LAP LAI", Toast.LENGTH_SHORT).show();

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
                        if (i >= 3600)
                            vip = true;

                        if (vip == true) {
                            tvVip.setText("VIP");
                        }
                        i++;
                        final String user = auth.getUid();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("usertimer");
                        TimerUser timerUser = new TimerUser(user, i, vip);
                        if (user != null) {
                            reference.child(user).setValue(timerUser);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                mHandler.postDelayed(mToastRunnable, 1000);
            }
            //lỗi phần đếm thời gian của google
            else if (HomeActivity.this.getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu", "").equals("1")) {
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(HomeActivity.this);
                if (acct != null) {
                    database = FirebaseDatabase.getInstance().getReference("usertimer").child(acct.getId());
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
                            if (i >= 3600)
                                vip = true;

                            if (vip == true) {
                                tvVip.setText("VIP");
                            }
                            i++;
                            final String user = acct.getId();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("usertimer");
                            TimerUser timerUser = new TimerUser(user, i, vip);
                            if (user != null) {
                                reference.child(user).setValue(timerUser);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


                mHandler.postDelayed(mToastRunnable, 1000);
            }

        }


    };

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void loadUserProfile(AccessToken newAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {

            @Override

            public void onCompleted(JSONObject object, GraphResponse response) {
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


                            int i;

                            if (dataSnapshot.child("timer").getValue() == null) {

                                i = 0;

                            } else {

                                i = Integer.valueOf(String.valueOf(dataSnapshot.child("timer").getValue()));
                            }

                            i++;

                            boolean vip = false;

                            if (i >= 3600) {

                                vip = true;

                            }

                            final String user = id;

                            FirebaseDatabase database = FirebaseDatabase.getInstance();

                            DatabaseReference reference = database.getReference("usertimer");

                            TimerUser timerUser = new TimerUser(user, i, vip);

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

        parameters.putString("fields", "first_name,last_name,email,id");

        request.setParameters(parameters);

        request.executeAsync();

    }


}
