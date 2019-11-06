package com.example.lab3.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3.R;
import com.example.lab3.data.Data;
import com.example.lab3.fragments.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {

    private int position;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);

        this.position = getIntent().getIntExtra("position", -1);

        // создание -> 'Новая задача'
        // редактирование -> название задачи
        setTitle(position < 0 ? "Новая задача" : Data.getAdapter().get(position).getName());

        DetailsFragment df = (DetailsFragment)
                getSupportFragmentManager().findFragmentById(R.id.detailsFragment);

        if (df != null) {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            df.setArguments(bundle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (position > -1) {
            // Отобразить меню, если режим редактирования
            getMenuInflater().inflate(R.menu.details_menu, menu);

            MenuItem editItem = (MenuItem) menu.findItem(R.id.editMI);
            MenuItem removeItem = (MenuItem)menu.findItem(R.id.removeMI);

            // переход к редактированию в режиме создания/редактирования запрещено
            editItem.setEnabled(false);
            // удаление в режиме создания запрещено
            if (position == -1) {
                removeItem.setEnabled(false);
                return true;
            }

            // удаление в режиме редактирования разрешено
            removeItem.setOnMenuItemClickListener(item -> {
                Data.remove(Data.getAdapter().get(position), true);
                finish();   // выходим из активити после удаления
                return true;
            });
        }

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
}
