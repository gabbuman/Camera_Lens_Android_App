package cs276.e.cameradof;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cs276.e.cameradof.module.Lens;
import cs276.e.cameradof.module.LensManager;

public class MainActivity extends AppCompatActivity {

    private LensManager manager;
    private List<Lens> myLens = new ArrayList<Lens>();
    private ArrayAdapter<Lens> adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = LensManager.getInstance();
        populateMyLensList();
        populateListView();
        registerClickCallback();

        //Calling AddLens Activity
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddLens.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onStart() {
        manager = LensManager.getInstance();
        list.setAdapter(adapter);
        super.onStart();
    }

    // Call CalculateDepthOfField Activity on selected lens
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listViewLens);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Intent intent = CalculateDepthOfFieldActivity.makeIntent(MainActivity.this, position);
                startActivity(intent);
            }
        });
    }

    private void populateMyLensList() {
        for (Lens l : manager) {
            myLens.add(l);
        }
    }

    // Display Lens List
    private void populateListView() {
        adapter = new MyListAdapter();
        list = (ListView) findViewById(R.id.listViewLens);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Lens> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_lens_layout, myLens);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_lens_layout, parent, false);
            }
            // Find the lens to work with
            Lens currentLens = myLens.get(position);

            //Icon:
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewLensIcon);
            imageView.setImageResource(currentLens.getIconID());

            // Make:
            TextView makeText = (TextView) itemView.findViewById(R.id.text_Make);
            makeText.setText(currentLens.getMake());

            // Focal length:
            TextView focalText = (TextView) itemView.findViewById(R.id.text_focal_length);
            focalText.setText("" + currentLens.getFocalLength() + "mm");

            // Aperture
            TextView apertureText = (TextView) itemView.findViewById(R.id.text_aperture);
            apertureText.setText("F" + currentLens.getMaximumAperture());

            return itemView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}