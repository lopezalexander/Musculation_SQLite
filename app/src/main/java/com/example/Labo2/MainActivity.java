package com.example.Labo2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //NAVIGATION DRAWER
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //MainActivity Context
    Context context = MainActivity.this;

    protected MyDataBaseHelper maDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SETUP LA NAVIGATION DRAWER TOGGLE FEATURE
        setupNavigationToggle();
        //SETUP LA NAVIGATION DRAWER ITEMS OnCLICK LISTENER
        setupItemOnClick();

        maDB = new MyDataBaseHelper(this);
        maDB.getWritableDatabase();


        //SETUP BUTTON MAIN MENU
        ArrayList<ImageView> buttonList = new ArrayList<>();

        buttonList.add(findViewById(R.id.main_btn_abdominaux));
        buttonList.add(findViewById(R.id.main_btn_biceps));

        buttonList.add(findViewById(R.id.main_btn_fessiers));
        buttonList.add(findViewById(R.id.main_btn_dos));

        buttonList.add(findViewById(R.id.main_btn_epaules));
        buttonList.add(findViewById(R.id.main_btn_mollets));

        buttonList.add(findViewById(R.id.main_btn_pectoraux));
        buttonList.add(findViewById(R.id.main_btn_triceps));

        buttonList.add(findViewById(R.id.main_btn_avantbras));

        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setOnClickListener(this);
        }


        //************TEST*******************
        //TEST ALL AND CATEGORIE

        //ArrayList<Exercice> allEntries = maDB.obtenirToutLesExercices();
        ArrayList<Exercice> allEntries = maDB.obtenirExerciceParCategorie("Abdominaux");

        for (Exercice unique : allEntries) {
            Log.d("row", "///////////////////////////////////////////////////////////////////////////////////////////////");
            Log.d("row", unique.get_id().toString());
            Log.d("row", unique.getTitle());
        }

        //TEST INSERT
        //maDB.insertExercice("test", "test", "test", "test", "test", "test", "test", "test", "test");

        //TEST DELETE
//        maDB.effacerExercice(37);

    }


    //**************\\
    //  FONCTIONS    \\
    //*******************************************************************************************************************************************

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {

        //GET CATEGORY
        String categorieChoisie = "";

        switch (v.getId()) {
            case R.id.main_btn_abdominaux:
                categorieChoisie = "ABDOMINAUX";
                break;
            case R.id.main_btn_biceps:
                categorieChoisie = "BICEPS";
                break;
            case R.id.main_btn_fessiers:
                categorieChoisie = "FESSIERS";
                break;
            case R.id.main_btn_dos:
                categorieChoisie = "DOS";
                break;
            case R.id.main_btn_epaules:
                categorieChoisie = "EPAULES";
                break;
            case R.id.main_btn_mollets:
                categorieChoisie = "MOLLETS";
                break;
            case R.id.main_btn_pectoraux:
                categorieChoisie = "PECTORAUX";
                break;
            case R.id.main_btn_triceps:
                categorieChoisie = "TRICEPS";
                break;
            case R.id.main_btn_avantbras:
                categorieChoisie = "AVANTBRAS";
                break;
        }

        //Database Fetch -> categorie choisie -->object parcelable

        ArrayList<Exercice> listeExerciceCategorie = new ArrayList<>();

        listeExerciceCategorie = maDB.obtenirExerciceParCategorie(categorieChoisie);

        Intent intentToCategorie = new Intent(context, ListeExercice.class);
        intentToCategorie.putParcelableArrayListExtra("listeExerciceCategorie", listeExerciceCategorie);
        startActivity(intentToCategorie);


        //TEST-----------------------------------------------------------------------------
        String toastmsg = "ca marche";
        Toast.makeText(MainActivity.this, toastmsg, Toast.LENGTH_SHORT).show();
        //TEST-----------------------------------------------------------------------------


    }

    //***********************************\\
    //  MENU DRAWER CLICK ITEM SELECTION  \\
    //*******************************************************************************************************************************************
    @SuppressLint("NonConstantResourceId")
    private void setupItemOnClick() {
        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.questionA:
                    drawerLayout.closeDrawers();
                    break;
                case R.id.questionB:
                    drawerLayout.closeDrawers();
                    break;
//                case R.id.questionC:
//                    drawerLayout.closeDrawers();
//                    break;
//                case R.id.questionD:
//                    drawerLayout.closeDrawers();
//                    break;
//                case R.id.questionE:
//                    drawerLayout.closeDrawers();
//                    break;
            }
            return true;
        });
    }


    //*********************\\
    // UTILITIES FUNCTIONS  \\  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    //*******************************************************************************************************************************************
    //SETUP :: MENU DRAWER TOGGLE
    private void setupNavigationToggle() {
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    //SETUP :: MENU DRAWER NECESSARY FUNCTION
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}