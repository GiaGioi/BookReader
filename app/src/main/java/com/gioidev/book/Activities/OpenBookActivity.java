package com.gioidev.book.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.folioreader.Config;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.model.ReadPosition;
import com.folioreader.model.ReadPositionImpl;
import com.folioreader.ui.base.OnSaveHighlight;
import com.folioreader.util.AppUtil;
import com.folioreader.util.OnHighlightListener;
import com.folioreader.util.ReadPositionListener;
import com.gioidev.book.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class OpenBookActivity extends AppCompatActivity implements
        OnHighlightListener, ReadPositionListener, FolioReader.OnClosedListener{


    FolioReader folioReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_book);

        Intent intent = getIntent();
        String filename = intent.getStringExtra("FileName");

        Log.e("TAG", "onCreate: " + filename );

        folioReader = FolioReader.get()
                .setOnHighlightListener(this)
                .setOnClosedListener(this);

        Config config = AppUtil.getSavedConfig(getApplicationContext());
        if (config == null)
            config = new Config();
        config.setAllowedDirection(Config.AllowedDirection.VERTICAL_AND_HORIZONTAL);
        folioReader.setConfig(config,true)
                .openBook(filename);

        getHighlightsAndSave();


    }
    @Override
    public void onFolioReaderClosed() {
        Log.v("LOG_TAG", "-> onFolioReaderClosed");
    }

    private ReadPosition getLastReadPosition() {

        String jsonString = loadAssetTextAsString("read_positions/read_position.json");
        return ReadPositionImpl.createInstance(jsonString);
    }

    private String loadAssetTextAsString(String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("HomeActivity", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("HomeActivity", "Error closing asset " + name);
                }
            }
        }
        return null;
    }

    private void getHighlightsAndSave() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HighlightData> highlightList = null;
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    highlightList = objectMapper.readValue(
                            loadAssetTextAsString("highlights/highlights_data.json"),
                            new TypeReference<List<HighlightData>>() {
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (highlightList == null) {
                    folioReader.saveReceivedHighLights(null, new OnSaveHighlight() {
                        @Override
                        public void onFinished() {
                            //You can do anything on successful saving highlight list
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {
    }

    @Override
    public void saveReadPosition(ReadPosition readPosition) {

        Log.i("LOG_TAG", "-> ReadPosition = " + readPosition.toJson());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }
}
