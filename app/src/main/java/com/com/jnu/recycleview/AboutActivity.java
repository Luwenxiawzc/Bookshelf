package com.com.jnu.recycleview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import java.util.HashMap;
import java.util.Map;

public class AboutActivity extends AppCompatActivity {
    private static final String TAG = "Aboutactivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Map<String, String> logEvents = new HashMap<>();
        logEvents.put("Activity", TAG);

        logEvents.clear();
        logEvents.put("Name", "onCreate");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.about_preference_category_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            AboutFragment aboutFragment = new AboutFragment();
            getFragmentManager().beginTransaction().add(R.id.activity_about_container, aboutFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://返回
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
