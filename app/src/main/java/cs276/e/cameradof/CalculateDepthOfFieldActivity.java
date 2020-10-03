package cs276.e.cameradof;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import cs276.e.cameradof.module.DepthOfFieldCalculator;
import cs276.e.cameradof.module.Lens;
import cs276.e.cameradof.module.LensManager;

public class CalculateDepthOfFieldActivity extends AppCompatActivity {

    private static final String POSITION_OF_LENS = "Position of Lens";
    public static final String COC_DEFAULT = "0.029";
    public static final String INVALID_COC = "Invalid COC";
    public static final String INVALID_APERTURE = "Invalid aperture";
    public static final String INVALID_DISTANCE = "Invalid Distance";
    public static final String ENTER_ALL_VALUES = "Enter all values";
    private int position;
    private DepthOfFieldCalculator dof;
    private LensManager manager;
    private EditText userTextEntryCOC;
    private EditText userTextEntryDistance;
    private EditText userTextEntryAperture;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(calculateDOFValues()){
                displayResult();
            }
        }
    };


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
        userTextEntryCOC = (EditText) findViewById(R.id.textInputCOC);
        userTextEntryDistance = (EditText) findViewById(R.id.textInputDistance);
        userTextEntryAperture = (EditText) findViewById(R.id.textInputAperture);

        setDefaultCOC();
        extractPositionFromIntent();
        printLensDetails();
        getUserInput();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    private void setDefaultCOC() {
        EditText userTextEntryCOC = (EditText) findViewById(R.id.textInputCOC);
        userTextEntryCOC.setText(COC_DEFAULT, TextView.BufferType.EDITABLE);
    }

    private void extractPositionFromIntent() {
        Intent intent = getIntent();
        position = intent.getIntExtra(POSITION_OF_LENS, 0);
    }

    @SuppressLint("SetTextI18n")
    private void printLensDetails() {
        Lens l = manager.getListAtThisIterator(position);

        TextView textViewLensDetails = (TextView) findViewById(R.id.textViewLensDetails);
        textViewLensDetails.setText(l.getMake() + " " + formatM(l.getFocalLength()) + "mm F"
                + formatM(l.getMaximumAperture()));
    }

    private void getUserInput() {
        userTextEntryCOC.addTextChangedListener(textWatcher);
        userTextEntryAperture.addTextChangedListener(textWatcher);
        userTextEntryDistance.addTextChangedListener(textWatcher);
    }


    private boolean calculateDOFValues(){

        String userCOCString = userTextEntryCOC.getText().toString();
        String userDistanceString = userTextEntryDistance.getText().toString();
        String userApertureString = userTextEntryAperture.getText().toString();

        if(userCOCString.isEmpty() || userDistanceString.isEmpty() || userApertureString.isEmpty()){
            displayError(ENTER_ALL_VALUES);
            return false;
        }
        double COC = Double.parseDouble(userCOCString);
        double distanceInM = Double.parseDouble(userDistanceString);
        double aperture = Double.parseDouble(userApertureString);

        if(COC == 0){
            Toast.makeText(this, "Circle of Confusion must not be 0", Toast.LENGTH_SHORT).show();
            displayError(INVALID_COC);
            return false;
        }
        if(aperture < manager.getListAtThisIterator(position).getMaximumAperture() || aperture > 22){
            Toast.makeText(this, "Aperture must be in range [1.8 22]", Toast.LENGTH_SHORT).show();
            displayError(INVALID_APERTURE);
            return false;
        }
        if(distanceInM == 0){
            Toast.makeText(this, "Distance must not be 0", Toast.LENGTH_LONG).show();
            displayError(INVALID_DISTANCE);
            return false;
        }

        dof = new DepthOfFieldCalculator(manager.getListAtThisIterator(position),
                distanceInM*1000, aperture, COC);
        return true;
    }

    @SuppressLint("SetTextI18n")
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


    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}
