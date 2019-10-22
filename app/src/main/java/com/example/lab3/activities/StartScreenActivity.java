package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lab3.R;

/**
 *  Start screen
 */
public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createMI:
                Intent intent = new Intent(StartScreenActivity.this, TaskActivity.class);
                intent.putExtra("creating", true);
                startActivity(intent);
                return true;
            case R.id.sortByDateMI:
                //sort
                return true;
            case R.id.deleteDone:
                //remove all done
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                return true;
            case R.id.exitMI:
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}