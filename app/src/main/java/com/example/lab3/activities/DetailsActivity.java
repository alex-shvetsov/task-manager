package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.lab3.R;
import com.example.lab3.Task;

public class DetailsActivity extends AppCompatActivity {

    private boolean creating;
    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Task task = getIntent().getParcelableExtra("task");
        this.done = task.isDone();

        this.creating = getIntent().getBooleanExtra("creating", false);

        if (done) {
            Button saveButton = (Button)findViewById(R.id.saveButton);
            saveButton.setVisibility(View.INVISIBLE);
        }
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
            deleteItem.setEnabled(false);
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