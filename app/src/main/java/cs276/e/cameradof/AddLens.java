package cs276.e.cameradof;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cs276.e.cameradof.module.Lens;
import cs276.e.cameradof.module.LensManager;

public class AddLens extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, AddLens.class);
    }
    private LensManager manager;
    private int iconID = R.drawable.icon7;              // Set a default icon if no options selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = LensManager.getInstance();
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        configureIconButton();
    }

    private void configureIconButton() {
        ImageButton btn1 = (ImageButton) findViewById(R.id.iconButton1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconID = R.drawable.icon1;
            }
        });

        ImageButton btn2 = (ImageButton) findViewById(R.id.iconButton2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconID = R.drawable.icon2;
            }
        });

        ImageButton btn3 = (ImageButton) findViewById(R.id.iconButton3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconID = R.drawable.icon3;
            }
        });

        ImageButton btn4 = (ImageButton) findViewById(R.id.iconButton4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconID = R.drawable.icon4;
            }
        });

        ImageButton btn5 = (ImageButton) findViewById(R.id.iconButton5);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconID = R.drawable.icon5;
            }
        });

        ImageButton btn6 = (ImageButton) findViewById(R.id.iconButton6);
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconID = R.drawable.icon6;
            }
        });

        ImageButton btn7 = (ImageButton) findViewById(R.id.iconButton7);
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconID = R.drawable.icon7;
            }
        });

        ImageButton btn8 = (ImageButton) findViewById(R.id.iconButton8);
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconID = R.drawable.icon8;
            }
        });

        ImageButton btn9 = (ImageButton) findViewById(R.id.iconButton9);
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconID = R.drawable.icon9;
            }
        });

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

        EditText userTextEntryAperture = (EditText) findViewById(R.id.textInputAperture);
        String userApertureString = userTextEntryAperture.getText().toString();

        if(userMake.isEmpty() ){
            Toast.makeText(this, "Lens name must not be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if(userApertureString.isEmpty()){
            Toast.makeText(this, "Aperture value cannot be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if(userFocalString.isEmpty()){
            Toast.makeText(this, "Please enter Focal Length", Toast.LENGTH_LONG).show();
            return false;
        }

        double userAperture = Double.parseDouble(userApertureString);
        int userFocal = Integer.parseInt(userFocalString);

        if(userAperture >= 1 && userAperture <=22 && userFocal >= 0) {
            manager.add(new Lens(userMake, userAperture, (double)userFocal, iconID));
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(userAperture < 1 || userAperture > 22){
            Toast.makeText(this, "Aperture must be in [1 22] range",
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