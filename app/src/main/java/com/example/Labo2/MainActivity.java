package com.example.Labo2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    //NAVIGATION DRAWER
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    //MainActivity Context
    Context context = MainActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SETUP LA NAVIGATION DRAWER TOGGLE FEATURE
        setupNavigationToggle();
        //SETUP LA NAVIGATION DRAWER ITEMS OnCLICK LISTENER
        setupItemOnClick();


    }


    //**************\\
    //  FONCTIONS    \\
    //*******************************************************************************************************************************************


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
                    AlertDialog myForm_create = createFormDialog();
                    myForm_create.show();
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


    //*******\\
    // FORMS  \\
    //*******************************************************************************************************************************************
    private AlertDialog createFormDialog() {
        //form_them --> themes.xml value
        AlertDialog.Builder myForm = new AlertDialog.Builder(context, R.style.form_theme);
        //Define inflate into MainActivity = context
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inflate-Create the View of the Form-- (layout, ViewGroup can be null)
        //View myFormView = inflater.inflate(R.layout.form_enregistrer, findViewById(R.id.linearLayoutRoot));
        View myFormView = inflater.inflate(R.layout.form_enregistrer, null);


        //GET VALUES IN THE FORM
//        EditText code_input = myFormView.findViewById(R.id.input_code);
//        EditText depart_input = myFormView.findViewById(R.id.input_depart);
//        EditText destination_input = myFormView.findViewById(R.id.input_destination);
//        EditText transporteur_input = myFormView.findViewById(R.id.input_transporteur);


        myForm.setView(myFormView)
                .setPositiveButton("Creer <ITEM>", (dialog, id) -> {

                    //FORM DATA
//                    String code = code_input.getText().toString();
//                    String depart = depart_input.getText().toString();
//                    String destination = destination_input.getText().toString();
//                    String transporteur = transporteur_input.getText().toString();


                    //Appeler DATABASE pour INSERT
//                    MyDataBaseHelper.insertVoyage(code, depart, destination, transporteur, maDB);


                    String toastmsg = "Le <ITEM> a été bien enregistré!";

                    Toast.makeText(context, toastmsg, Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        return myForm.create();
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