package com.gioidev.book.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.gioidev.book.Activities.DangkiActivity;
import com.gioidev.book.Activities.HomeActivity;
import com.gioidev.book.Activities.LoginActivity;
import com.gioidev.book.Activities.Manhinhchao.In4Activity;
import com.gioidev.book.Adapter.AdapterHome.VerticalRecyclerViewAdapter;
import com.gioidev.book.Model.GridViewModel;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.Model.TimerUser;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class Fragment_CaNhan extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private TextView tvten,tvTime,tvVIP,tvIn4,tvEmailSend;
    String TAG = "TAG";
    DrawerLayout drawer;
    private boolean isExit = false;
    private Timer timer = null;
    private TimerTask timeTask = null;
    private FrameLayout mainContainer;
    private RecyclerView rvVertical;
    ArrayList<VerticalModel> mArrayList = new ArrayList<>();
    private Button tvLogOut;
    VerticalRecyclerViewAdapter mAdapter;
    private SliderView imageSlider;
    private SwipeRefreshLayout swipeRefreshLayout;
    HorizontalModel mHorizontalModel;
    GridViewModel gridViewModel;
    VerticalModel mVerticalModel;
    List<HorizontalModel> horizontalModels;
    ArrayList<GridViewModel> gridViewModels;
    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    String userId;
    HorizontalModel user;
    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference mDatabase;

    private CallbackManager callbackManager;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);

        auth = FirebaseAuth.getInstance();

        callbackManager = CallbackManager.Factory.create();
        tvTime = view.findViewById(R.id.tvTimeused);
        tvVIP = view.findViewById(R.id.tvVIP);
        tvLogOut = view.findViewById(R.id.tvLogOUt);
        tvIn4 = view.findViewById(R.id.tvIn4);
        tvIn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), In4Activity.class);
                startActivity(intent);

            }
        });
        tvEmailSend = view.findViewById(R.id.tvEmailSend);
        tvEmailSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"duc99iter@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Báo lỗi BookReader");
                i.putExtra(Intent.EXTRA_TEXT   , "Nội dung lỗi");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                  if (AccessToken.getCurrentAccessToken() != null) {
                      // here is facebook
                      stopRepeating();
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                 else if (getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("0")){

                    signOutAuthenication();
                    //ffirebase
                }
                 else if (getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("1")){

                    signOut();
                    //google
                }

            }
        });
        tvten = view.findViewById(R.id.tvTen);
        if (AccessToken.getCurrentAccessToken() != null) {
            // here is facebook
        }
        else if (getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("0")){

            //ffirebase
            tvten.setText(auth.getCurrentUser().getEmail());
        }
        else if (getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("1")){

            //google
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
            if (acct != null) {
                tvten.setText(acct.getDisplayName());
            }
        }

        startRepeating();

        return view;

    }

    private void signOut() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        GoogleSignInClient signInClient = GoogleSignIn.getClient(getContext(), signInOptions);

        signInClient.revokeAccess().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), auth.getCurrentUser().getDisplayName() + " Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                auth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                stopRepeating();
                startActivity(intent);
                getActivity().finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void signOutAuthenication() {

        auth.signOut();

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onRefresh() {

    }
    private Handler mHandler = new Handler();

    public void startRepeating() {
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

            }
            else if (getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("0")){
                //ffirebase
                if (auth.getUid()!=null){
                    mDatabase = FirebaseDatabase.getInstance().getReference("usertimer").child(auth.getUid());

                }

                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            int s;
                            if (dataSnapshot.child("timer").getValue() == null) {
                                s = 0;

                            } else {
                                s = Integer.valueOf(String.valueOf(dataSnapshot.child("timer").getValue()));
                            }
                            int p1, p2, p3;
                            p1 = s % 60;
                            p2 = s / 60;
                            p3 = p2 % 60;
                            p2 = p2 / 60;
                            boolean vip = false;
                            if (s>3600) {
                                 vip = true;

                            }
                            if(vip == true){
                                tvVIP.setText("VIP");
                            }
                            tvTime.setText("\t" + p2 + "\t" + "Phút" );
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            }
            else if (getContext().getSharedPreferences("DATA", Context.MODE_PRIVATE).getString("hieungu","").equals("1")){
                //google
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
                mDatabase = FirebaseDatabase.getInstance().getReference("usertimer").child(acct.getId());

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        int s ;
                        if (dataSnapshot.child("timer").getValue() == null){
                            s = 0;

                        }
                        else {
                            s=Integer.valueOf(String.valueOf(dataSnapshot.child("timer").getValue()));
                        }
                        int p1,p2,p3;
                        p1 = s %60;
                        p2 = s/60;
                        p3 = p2%60;
                        p2 = p2/60;
                        boolean vip = false;
                        if (s>3600) {
                            vip = true;

                        }
                        if(vip == true){
                            tvVIP.setText("VIP");
                        }
                        tvTime.setText("\t" + p2 + "\t" + "Phút" );
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
    public void onDestroy() {
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
                    tvten.setText(email);
                    mDatabase = FirebaseDatabase.getInstance().getReference("usertimer").child(id);

                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            int s ;
                            if (dataSnapshot.child("timer").getValue() == null){
                                s = 0;

                            }
                            else {
                                s=Integer.valueOf(String.valueOf(dataSnapshot.child("timer").getValue()));
                            }
                            int p1,p2,p3;
                            p1 = s %60;
                            p2 = s/60;
                            p3 = p2%60;
                            p2 = p2/60;



                            tvTime.setText(p2 + ":" + p3 + ":" + p1);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();


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
