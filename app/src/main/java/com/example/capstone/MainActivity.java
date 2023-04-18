package com.example.capstone;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;

import com.example.capstone.ui.gallery.GalleryFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.databinding.ActivityMainBinding;

/*
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d("&&&&&&&&&", "inside of main activity");

        BuildingDB buildingDB = new BuildingDB(this);
        SQLiteDatabase db = buildingDB.getWritableDatabase();

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.Abbot_Pennings_Hall_of_Fine_Arts, R.id.Francis_H_Boyle_Hall, R.id.Carol_and_Robert_Busch_Art_Center,
                R.id.Austin_E_Cofrin_Hall, R.id.Gehl_Mulva_Science_Center, R.id.Medical_College_of_Wisconsin, R.id.Miriam_B_and_James_J_Mulva_Library,
                R.id.Dudley_Birder_Hall, R.id.Cassandra_Voss_Center, R.id.Old_St_Joseph_Church, R.id. Rev_Ignatius_Francis_Van_Dyke_Alumni_House,
                R.id.Ray_Van_Dan_Heuvel_Family_Campus_Center, R.id. Michels_Commons, R.id.Mulva_Family_Fitness_and_Sports_Center, R.id.Pennings_Activity_Center,
                R.id.Dennis_M_Burke_Hall, R.id.Ralph_and_Catherine_Paulson_Carriage, R.id.Doksany_Hall, R.id.Gertrude_S_Bergstrom_Hall, R.id.Madelaine_and_Lorraine_Hall,
                R.id.Mary_Minahan_McCormick_Hall, R.id.Frank_J_Sensenbrenner_Hall, R.id.Fr_Eugene_E_Gries_OPraem_Hall, R.id.Hugh_Hall, R.id.Dale_and_Ruths_Michels_Hall,
                R.id.Premontre_Hall, R.id.Roggenburg_Hall, R.id.St_Joseph_Hall, R.id.Thomas_and_Maureen_Manion_Townhouse_Village, R.id.Victor_McCormick_Hall, R.id.Xanten_Hall,
                R.id.Ariens_Family_Welcome_Center, R.id.FK_Bemis_International_Center, R.id.Main_Hall, R.id.Todd_Wehr_Hall, R.id.Heating_Plant, R.id.George_F_Kress_Residence_for_Executive_Education,
                R.id.Grounds_Facility)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}*/

public class MainActivity extends AppCompatActivity  {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Set up the navigation view and drawer layout
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.Abbot_Pennings_Hall_of_Fine_Arts, R.id.Francis_H_Boyle_Hall, R.id.Carol_and_Robert_Busch_Art_Center,
                R.id.Austin_E_Cofrin_Hall, R.id.Gehl_Mulva_Science_Center, R.id.Medical_College_of_Wisconsin, R.id.Miriam_B_and_James_J_Mulva_Library,
                R.id.Dudley_Birder_Hall, R.id.Cassandra_Voss_Center, R.id.Old_St_Joseph_Church, R.id. Rev_Ignatius_Francis_Van_Dyke_Alumni_House,
                R.id.Ray_Van_Dan_Heuvel_Family_Campus_Center, R.id. Michels_Commons, R.id.Mulva_Family_Fitness_and_Sports_Center, R.id.Pennings_Activity_Center,
                R.id.Dennis_M_Burke_Hall, R.id.Ralph_and_Catherine_Paulson_Carriage, R.id.Doksany_Hall, R.id.Gertrude_S_Bergstrom_Hall, R.id.Madelaine_and_Lorraine_Hall,
                R.id.Mary_Minahan_McCormick_Hall, R.id.Frank_J_Sensenbrenner_Hall, R.id.Fr_Eugene_E_Gries_OPraem_Hall, R.id.Hugh_Hall, R.id.Dale_and_Ruths_Michels_Hall,
                R.id.Premontre_Hall, R.id.Roggenburg_Hall, R.id.St_Joseph_Hall, R.id.Thomas_and_Maureen_Manion_Townhouse_Village, R.id.Victor_McCormick_Hall, R.id.Xanten_Hall,
                R.id.Ariens_Family_Welcome_Center, R.id.FK_Bemis_International_Center, R.id.Main_Hall, R.id.Todd_Wehr_Hall, R.id.Heating_Plant, R.id.George_F_Kress_Residence_for_Executive_Education,
                R.id.Grounds_Facility
        ).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
