package cs276.e.cameradof;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import cs276.e.cameradof.module.DepthOfFieldCalculator;
import cs276.e.cameradof.module.Lens;
import cs276.e.cameradof.module.LensManager;

public class CalculateDepthOfFieldActivity extends AppCompatActivity {
    private static final String POSITION_OF_LENS = "Position of Lens";
    private int position;
    private DepthOfFieldCalculator dof;
    private LensManager manager;


    public static Intent makeIntent(Context context, int position) {
        Intent intent = new Intent(context, CalculateDepthOfFieldActivity.class);
        intent.putExtra(POSITION_OF_LENS, position);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_depth_of_field);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = LensManager.getInstance();
        setDefaultCOC();
        extractPositionFromIntent();
        printLensDetails();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getUserInput()) {
                    displayResult();
                }
            }
        });
    }

    private void printLensDetails() {
        Lens l = manager.getListAtThisIterator(position);
        TextView textViewLensDetails = (TextView) findViewById(R.id.textViewLensDetails);
        textViewLensDetails.setText(l.getMake() + " " + formatM(l.getFocalLength()) + "mm F"
                + formatM(l.getMaximumAperture()));
    }

    private void displayResult() {

        TextView textView = (TextView) findViewById(R.id.displayNearFocalDistance);
        textView.setText(formatM(dof.nearFocalPointInM()) + "m");

        TextView textView1 = (TextView) findViewById(R.id.displayFarFocalDistance);
        textView1.setText(formatM(dof.farFocalPointInM()) + "m");

        TextView textView2 = (TextView) findViewById(R.id.displayHyperfocalDistance);
        textView2.setText(formatM(dof.hyperFocalDistanceInM()) + "m");

        TextView textView3 = (TextView) findViewById(R.id.displayDepthOfField);
        textView3.setText(formatM(dof.depthOfFieldInM()) + "m");
    }

    private void setDefaultCOC() {
        EditText userTextEntryCOC = (EditText) findViewById(R.id.textInputCOC);
        userTextEntryCOC.setText("0.029", TextView.BufferType.EDITABLE);
    }

    private boolean getUserInput(){
        EditText userTextEntryCOC = (EditText) findViewById(R.id.textInputCOC);
        String userCOCString = userTextEntryCOC.getText().toString();
        double COC = Double.parseDouble(userCOCString);

        EditText userTextEntryDistance = (EditText) findViewById(R.id.textInputDistance);
        String userDistanceString = userTextEntryDistance.getText().toString();
        double distanceInM = Double.parseDouble(userDistanceString);

        EditText userTextEntryAperture = (EditText) findViewById(R.id.textInputAperture);
        String userApertureString = userTextEntryAperture.getText().toString();
        double aperture = Double.parseDouble(userApertureString);

        if(COC == 0){
            displayError("Invalid COC");
            return false;
        }
        if(aperture < manager.getListAtThisIterator(position).getMaximumAperture()){
            displayError("Invalid aperture");
            return false;
        }

        dof = new DepthOfFieldCalculator(manager.getListAtThisIterator(position),
                distanceInM*1000, aperture, COC);
        return true;
    }

    private void displayError(String Error){
        TextView textView = (TextView) findViewById(R.id.displayNearFocalDistance);
        textView.setText(Error);

        TextView textView1 = (TextView) findViewById(R.id.displayFarFocalDistance);
        textView1.setText(Error);

        TextView textView2 = (TextView) findViewById(R.id.displayHyperfocalDistance);
        textView2.setText(Error);

        TextView textView3 = (TextView) findViewById(R.id.displayDepthOfField);
        textView3.setText(Error);
    }

    private void extractPositionFromIntent() {
        Intent intent = getIntent();
        position = intent.getIntExtra(POSITION_OF_LENS, 0);
    }
    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}