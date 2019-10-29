package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lab3.R;
import com.example.lab3.task.Task;

public class DetailsActivity extends AppCompatActivity {

    private boolean creating;
    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);

        Task task = getIntent().getParcelableExtra("task");
        if (task != null) {
            this.done = task.isDone();

            if (done) {
                Button saveButton = (Button)findViewById(R.id.saveButton);
                saveButton.setVisibility(View.INVISIBLE);
            }
        }

        this.creating = getIntent().getBooleanExtra("creating", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);

        if (done) {
            MenuItem editItem = (MenuItem)menu.findItem(R.id.editMI);
            editItem.setEnabled(false);
        }

        if (creating) {
            MenuItem deleteItem = (MenuItem)menu.findItem(R.id.deleteMI);
            MenuItem editItem = (MenuItem)menu.findItem(R.id.editMI);
            deleteItem.setEnabled(false);
            editItem.setEnabled(false);
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
