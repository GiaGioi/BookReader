package com.gioidev.book.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.Config;
import com.folioreader.Constants;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.AppUtil;
import com.folioreader.util.OnHighlightListener;
import com.folioreader.util.ReadPositionListener;
import com.gioidev.book.Activities.HighlightData;
import com.gioidev.book.Adapter.AdapterBook.FragmentKiNangAdapter;
import com.gioidev.book.Adapter.AdapterHome.AutoFitGridLayoutManager;
import com.gioidev.book.Adapter.AdapterHome.GridViewRecyclerViewAdapter;
import com.gioidev.book.Model.GridViewFragment;
import com.gioidev.book.Model.GridViewModel;
import com.gioidev.book.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Ki_Nang extends Fragment  implements OnHighlightListener, ReadPositionListener, FolioReader.OnClosedListener{
    View v;
    RecyclerView recyclerView;
    FragmentKiNangAdapter adapter;
    ArrayList<GridViewFragment> gridViewModels;
    DatabaseReference mDatabase;
    GridViewFragment gridViewModel;
    private ImageView imageFragmentKiNang;
    private TextView tvnamBookFragmentKiNang;
    private TextView tvAutherFragmentKiNang;
    private CardView cardview_ki_nang;
    FolioReader folioReader;


    public Fragment_Ki_Nang(){


//        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(getContext(), 600);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_ki_nang,container,false);
//        recyclerView = v.findViewById(R.id.rv_fragment_ki_nang);
//        adapter = new FragmentKiNangAdapter(getContext(),gridViewModels);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

//        recyclerView.setAdapter(adapter);

        imageFragmentKiNang = v.findViewById(R.id.image_fragment_ki_nang);
        tvnamBookFragmentKiNang = v.findViewById(R.id.tvnamBook_fragment_ki_nang);
        tvAutherFragmentKiNang = v.findViewById(R.id.tvAuther_fragment_ki_nang);
        cardview_ki_nang = v.findViewById(R.id.cardview_ki_nang);
        folioReader = FolioReader.get()
//                .setOnHighlightListener(this)
                .setOnClosedListener(this);

//        getHighlightsAndSave();
        cardview_ki_nang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Config config = AppUtil.getSavedConfig(getContext());
                if (config == null)
                    config = new Config();
                config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL);
                folioReader.setConfig(config, true)
                        .openBook(Constants.ASSET + "biethailong.epub");
            }
        });

//        getDataGridview();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        gridViewModel = new GridViewFragment();

    }

    public void getDataGridview(){
        mDatabase = FirebaseDatabase.getInstance().getReference("books").child("PDF/SkillBook");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> keys = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    keys.add(snapshot.getKey());

                    String nameBook = String.valueOf(snapshot.child("NameBook").getValue());
                    String nameAuthor = String.valueOf(snapshot.child("NameAuthor").getValue());
                    String Description = String.valueOf(snapshot.child("Description").getValue());
                    String Image = String.valueOf(snapshot.child("Image").getValue());
                    String Url = String.valueOf(snapshot.child("Url").getValue());
                    String Gs = String.valueOf(snapshot.child("Gs").getValue());
                    String Price = String.valueOf(snapshot.child("Price").getValue());
                    String Category = String.valueOf(snapshot.child("Category").getValue());

                    Glide.with(getContext()).load(Image).into(imageFragmentKiNang);
                    tvAutherFragmentKiNang.setText(nameAuthor);
                    tvnamBookFragmentKiNang.setText(nameBook);




                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onFolioReaderClosed() {
        Log.v("LOG_TAG", "-> onFolioReaderClosed");
    }
//    private ReadPosition getLastReadPosition() {
//
//        String jsonString = loadAssetTextAsString("read_positions/read_position.json");
//        return ReadPositionImpl.createInstance(jsonString);
//    }
//    private String loadAssetTextAsString(String name) {
//        BufferedReader in = null;
//        try {
//            StringBuilder buf = new StringBuilder();
//            InputStream is = getAssets().open(name);
//            in = new BufferedReader(new InputStreamReader(is));
//
//            String str;
//            boolean isFirst = true;
//            while ((str = in.readLine()) != null) {
//                if (isFirst)
//                    isFirst = false;
//                else
//                    buf.append('\n');
//                buf.append(str);
//            }
//            return buf.toString();
//        } catch (IOException e) {
//            Log.e("HomeActivity", "Error opening asset " + name);
//        } finally {
//            if (in != null) {
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    Log.e("HomeActivity", "Error closing asset " + name);
//                }
//            }
//        }
//        return null;
//    }
//    private void getHighlightsAndSave() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<HighlightData> highlightList = null;
//                ObjectMapper objectMapper = new ObjectMapper();
//                try {
//                    highlightList = objectMapper.readValue(
//                            loadAssetTextAsString("highlights/highlights_data.json"),
//                            new TypeReference<List<HighlightData>>() {
//                            });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (highlightList == null) {
//                    folioReader.saveReceivedHighLights(null, new OnSaveHighlight() {
//                        @Override
//                        public void onFinished() {
//                            //You can do anything on successful saving highlight list
//                        }
//                    });
//                }
//            }
//        }).start();
//    }
    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {

    }

    @Override
    public void saveReadPosition(ReadPosition readPosition) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }
}
