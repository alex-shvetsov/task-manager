package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lab3.R;
import com.example.lab3.fragments.DetailsFragment;
import com.example.lab3.json.Json;
import com.example.lab3.task.Task;

public class DetailsActivity extends AppCompatActivity {

    private boolean creating;
    private boolean done;
    private int taskPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);

        taskPosition = getIntent().getIntExtra("taskPosition", 0);
        Task task = Json.get()[taskPosition];
        if (task != null) {
            if (done = task.isDone()) {
                Button saveButton = (Button)findViewById(R.id.saveButton);
                saveButton.setVisibility(View.INVISIBLE);
            }
        }

        creating = getIntent().getBooleanExtra("creating", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);

        MenuItem editItem = (MenuItem) menu.findItem(R.id.editMI);
        MenuItem deleteItem = (MenuItem)menu.findItem(R.id.deleteMI);

        if (creating) {
            editItem.setEnabled(false);
            deleteItem.setEnabled(false);
        } else {
            editItem.setOnMenuItemClickListener(item -> {
                DetailsFragment detailsFragment = (DetailsFragment)
                        getSupportFragmentManager().findFragmentById(R.id.detailsFragment);
                detailsFragment.setMode(DetailsFragment.Mode.CREATE);
                return true;
            });

            deleteItem.setOnMenuItemClickListener(item -> {
                Json.remove(Json.get()[taskPosition]);
                Json.update();
                finish();
                return true;
            });
        }

        return true;
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {

    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        //
        super.onSaveInstanceState(state);
    }
}
