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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.gioidev.book.Adapter.AdapterHome.VerticalRecyclerViewAdapter;
import com.gioidev.book.Fragment.BookcaseFragment;
import com.gioidev.book.Fragment.FragmentComic;
import com.gioidev.book.Fragment.Fragment_CaNhan;
import com.gioidev.book.Fragment.Fragment_Home;
import com.gioidev.book.Fragment.Fragment_Khach_Hang;
import com.gioidev.book.Fragment.Fragment_Ki_Nang;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.gioidev.book.Utils.Functions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth auth;
    //    private GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;

    String TAG = "TAG";
    DrawerLayout drawer;
    private boolean isExit = false;
    private Timer timer = null;
    private TimerTask timeTask = null;

    private FrameLayout mainContainer;
    private RecyclerView rvVertical;
    ArrayList<VerticalModel> mArrayList = new ArrayList<>();

    VerticalRecyclerViewAdapter mAdapter;

    private SliderView imageSlider;
    private SwipeRefreshLayout swipeRefreshLayout;

    Toolbar toolbar;
    TextView textViewnameemail;
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
        textView = findViewById(R.id.textView);
        textViewnameemail = findViewById(R.id.textViewnameemail);
        setSupportActionBar(toolbar);

        String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
//        textViewnameemail.setText(email);
        Log.e(TAG, "onCreate: " + email);

//        if (AccessToken.getCurrentAccessToken() != null) {
//            Profile profile = Profile.getCurrentProfile();
//            textViewnameemail.setText(profile.getName() + "");
//        }
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
//            fragment = new Fragment_Khach_Hang();
//            loadFragment(fragment);
        } else if (id == R.id.nav_horoscope) {

        } else if (id == R.id.nav_skillbook) {
//            fragment = new Fragment_Ki_Nang();
//            loadFragment(fragment);

        } else if (id == R.id.nav_short_story) {

        } else if (id == R.id.nav_comic) {
                fragment  = new FragmentComic();
                loadFragment(fragment);
        } else if (id == R.id.nav_audio_book) {
            Intent intent = new Intent(HomeActivity.this, AudioBookActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_link) {

        }

//        if (FirebaseAuth.getInstance().getAccessToken(true) != null){
//            signOut();
//        }
//        else if(auth.getCurrentUser().getDisplayName() != null){
//            signOutAuthenication();
//        }
//        else if (AccessToken.getCurrentAccessToken()!= null){
//            LoginManager.getInstance().logOut();
//            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //    private void signOut() {
//   final FirebaseAuth auth = FirebaseAuth.getInstance();
//    GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail().build();
//    GoogleSignInClient signInClient = GoogleSignIn.getClient(HomeActivity.this,signInOptions);
//    signInClient.revokeAccess().addOnSuccessListener(new OnSuccessListener<Void>() {
//        @Override
//        public void onSuccess(Void aVoid) {
//            Toast.makeText(HomeActivity.this,auth.getCurrentUser().getDisplayName()+" Đăng xuất thành công" ,Toast.LENGTH_SHORT).show();
//            auth.signOut();
//            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }
//    }).addOnFailureListener(new OnFailureListener() {
//        @Override
//        public void onFailure(@NonNull Exception e) {
//
//        }
//    });
//
//    }
//    private void signOutAuthenication(){
//        auth.signOut();
//        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
