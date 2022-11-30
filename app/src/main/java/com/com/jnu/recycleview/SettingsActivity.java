package com.com.jnu.recycleview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Map<String, String> logEvents = new HashMap<>();
        logEvents.put("Activity", TAG);

        logEvents.clear();
        logEvents.put("Name", "onCreate");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.settings_toolbar);//toorbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.settings_settings);//设置title
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            SettingsFragment settingsFragment = new SettingsFragment();//fragment
            getFragmentManager().beginTransaction().replace(R.id.activity_settings_container, settingsFragment).commit();
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
