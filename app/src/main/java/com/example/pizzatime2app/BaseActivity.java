package com.example.pizzatime2app;

import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    /**
     * Inflate the main menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Adds functionality to the buttons on the menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuWikiPizza:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://es.wikipedia.org/wiki/Historia_de_la_pizza"));
                if (intent.resolveActivity(getPackageManager()) != null) startActivity(intent);
                break;
            case R.id.menuVersion: Toast.makeText(this, (getString(R.string.version) + ": " + MainActivity.VERSION), Toast.LENGTH_LONG).show(); break;
            case R.id.menuAuthor: Toast.makeText(this, getString(R.string.name_author), Toast.LENGTH_LONG).show(); break;
        }
        return super.onOptionsItemSelected(item);
    }
}
