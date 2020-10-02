package cs276.e.cameradof;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cs276.e.cameradof.module.Lens;
import cs276.e.cameradof.module.LensManager;

public class AddLens extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddLens.class);
    }
    private LensManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = LensManager.getInstance();
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_lens, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_save_button) {
            return getUserInput();
        }
        return super.onOptionsItemSelected(item);

    }

    private boolean getUserInput(){
        EditText userTextEntryMake = (EditText) findViewById(R.id.textInputMake);
        String userMake = userTextEntryMake.getText().toString();

        EditText userTextEntryFocal = (EditText) findViewById(R.id.textInputFocal);
        String userFocalString = userTextEntryFocal.getText().toString();
        int userFocal = Integer.parseInt(userFocalString);

        EditText userTextEntryAperture = (EditText) findViewById(R.id.textInputAperture);
        String userApertureString = userTextEntryAperture.getText().toString();
        double userAperture = Double.parseDouble(userApertureString);

        if(userAperture >= 0 && userFocal >= 0) {
            manager.add(new Lens(userMake, userAperture, (double)userFocal));
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(userAperture <= 0){
            Toast.makeText(this, "Aperture must be non-negative",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if(userFocal <= 0){
            Toast.makeText(this, "Focal Length must be non-negative",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}