package com.example.lab3.activities;

import com.example.lab3.OnTaskDone;
import com.example.lab3.R;
import com.example.lab3.data.Data;
import com.example.lab3.fragments.ViewDetailsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class ViewDetailsActivity extends AppCompatActivity implements OnTaskDone {

    private int position;
    private Menu menu;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_details);

        this.position = getIntent().getIntExtra("position", -1);

        setTitle(position < 0 ? "lol" : Data.getAdapter().get(position).getName());

        ViewDetailsFragment vf = (ViewDetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.viewDetailsFragment);

        if (vf != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            vf.setArguments(bundle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        this.menu = menu;

        MenuItem editItem = (MenuItem) menu.findItem(R.id.editMI);
        MenuItem removeItem = (MenuItem)menu.findItem(R.id.removeMI);

        if (!Data.getAdapter().get(position).isDone()) {
            editItem.setOnMenuItemClickListener(item -> {
                // переходим к редактированию
                Intent intent = new Intent(this, DetailsActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                return true;
            });
        } else {
            editItem.setEnabled(false);
        }

        removeItem.setOnMenuItemClickListener(item -> {
            Data.remove(Data.getAdapter().get(position), true);
            finish();   // выходим из активити после удаления
            return true;
        });

        return true;
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        position = state.getInt("position");
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("position", position);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void blockEdit(boolean isDone) {
        if (menu != null) {
            // блокируем кнопку редактирования, если задача выполнена
            MenuItem editItem = (MenuItem) menu.findItem(R.id.editMI);
            editItem.setEnabled(!isDone);
        }
    }
}
