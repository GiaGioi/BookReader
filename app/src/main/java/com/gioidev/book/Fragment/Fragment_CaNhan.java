package com.gioidev.book.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.gioidev.book.Activities.DangkiActivity;
import com.gioidev.book.Activities.HomeActivity;
import com.gioidev.book.Activities.LoginActivity;
import com.gioidev.book.Adapter.AdapterHome.VerticalRecyclerViewAdapter;
import com.gioidev.book.Model.GridViewModel;
import com.gioidev.book.Model.HorizontalModel;
import com.gioidev.book.Model.VerticalModel;
import com.gioidev.book.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarteist.autoimageslider.SliderView;

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
    private TextView tvten;
    String TAG = "TAG";
    DrawerLayout drawer;
    private boolean isExit = false;
    private Timer timer = null;
    private TimerTask timeTask = null;

    private FrameLayout mainContainer;
    private RecyclerView rvVertical;
    ArrayList<VerticalModel> mArrayList = new ArrayList<>();
    private TextView tvLogOut;
    VerticalRecyclerViewAdapter mAdapter;

    private SliderView imageSlider;
    private SwipeRefreshLayout swipeRefreshLayout;

    HorizontalModel mHorizontalModel;
    GridViewModel gridViewModel;
    VerticalModel mVerticalModel;
    List<HorizontalModel> horizontalModels;
    ArrayList<GridViewModel> gridViewModels;

    String userId;
    HorizontalModel user;
    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    LineChartView lineChartView;
    String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
            "Oct", "Nov", "Dec"};
    int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18};

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);

        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        tvLogOut = view.findViewById(R.id.tvLogOUt);
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if (FirebaseAuth.getInstance().getAccessToken(true) != null){
            signOut();
        }
        else if(auth.getCurrentUser().getDisplayName() != null){
            signOutAuthenication();
        }
        else if (AccessToken.getCurrentAccessToken()!= null){
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(getContext(),LoginActivity.class);
            startActivity(intent);

        }
            }
        });
        tvten= view.findViewById(R.id.tvTen);
//        tvten.setText(user.getEmail());
        lineChartView = view.findViewById(R.id.chart);

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("Read per month");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);


        return view;

    }
        private void signOut() {
   final FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build();
    GoogleSignInClient signInClient = GoogleSignIn.getClient(getContext(),signInOptions);
    signInClient.revokeAccess().addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Toast.makeText(getContext(),auth.getCurrentUser().getDisplayName()+" Đăng xuất thành công" ,Toast.LENGTH_SHORT).show();
            auth.signOut();
            Intent intent = new Intent(getContext(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    });

    }
    private void signOutAuthenication(){
        auth.signOut();
        Intent intent = new Intent(getContext(),LoginActivity.class);
        startActivity(intent);

    }
    @Override
    public void onRefresh() {

    }
}
