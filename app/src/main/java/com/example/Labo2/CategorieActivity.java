package com.example.Labo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;

public class CategorieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //INTERFACE UI
    ListView listViewExercicesCategorie;
    ExerciceArrayAdapter arrayAdapter;

    ArrayList<Exercice> listeExercices;

    ImageView imageSelector;
    Spinner imageSpinner;


    protected MyDataBaseHelper maDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        maDB = new MyDataBaseHelper(this);

        //Get ImageView for the Form
        // imageSelector = findViewById(R.id.drawableImage);

        //Get Spinner for the Form
//        imageSpinner = findViewById(R.id.drawableSpinner);
//        imageSpinner.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerData)));
//        imageSpinner.setOnItemSelectedListener(this);

        //GET ARRAYLIST OF EXERCICE PAR CATEGORIE
        Bundle extras = getIntent().getExtras();
        String categorieChoisie = extras.getString("categorieChoisie");

        //LISTER LE ARRAYLIST
        listerExerciceSelonCategorie(categorieChoisie);
    }

    private void listerExerciceSelonCategorie(String categorieChoisie) {
        listeExercices = new ArrayList<>();
        listViewExercicesCategorie = findViewById(R.id.liste_Exercices_Categorie);


        listeExercices = maDB.obtenirExerciceParCategorie(categorieChoisie);

        arrayAdapter = new ExerciceArrayAdapter(this, R.layout.exercices_list_layout, listeExercices);

        listViewExercicesCategorie.setAdapter(arrayAdapter);
    }


    //**************************\\
    //  SPINNER AND IMAGEVIEW    \\
    //*******************************************************************************************************************************************

    @Override
    public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
        String drawableName = parentView.getItemAtPosition(position).toString();

        Context context = imageSelector.getContext();
        int drawableID = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
        imageSelector.setImageResource(drawableID);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}