package com.example.Labo2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class CategorieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //INTERFACE UI
    ListView listViewExercicesCategorie;
    ExerciceArrayAdapter arrayAdapter;

    ArrayList<Exercice> listeExercices;

    ImageView imageSelector;
    Spinner imageSpinner;

    Context context = CategorieActivity.this;

    protected MyDataBaseHelper maDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        maDB = new MyDataBaseHelper(this);


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

    //**********************\\
    // FORM AJOUTER EXERCICE   \\
    //*******************************************************************************************************************************************
    private void formAjouterExercice() {
        AlertDialog.Builder myForm = new AlertDialog.Builder(context, R.style.form_theme);


        LayoutInflater inflater = LayoutInflater.from(context);
        View myFormView = inflater.inflate(R.layout.form_ajouter_exercice, findViewById(R.id.linearLayoutRoot));

        //Get ImageView for the Form
        imageSelector = myFormView.findViewById(R.id.drawableImage);

        //Get Spinner for the Form
        imageSpinner = myFormView.findViewById(R.id.drawableSpinner);
        imageSpinner.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerImage)));
        imageSpinner.setOnItemSelectedListener(this);

        //Set Spinner Layout
        Spinner spinnerCategorie = myFormView.findViewById(R.id.spinnerCategorie);
        Spinner spinnerSets = myFormView.findViewById(R.id.spinnerSets);
        Spinner spinnerPause = myFormView.findViewById(R.id.spinnerPause);
        Spinner spinnerRepeat = myFormView.findViewById(R.id.spinnerRepeat);
        Spinner spinnerDuree = myFormView.findViewById(R.id.spinnerDuree);

        spinnerCategorie.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerCategorie)));

        spinnerSets.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerCategorie)));
        spinnerPause.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerCategorie)));
        spinnerRepeat.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerCategorie)));
        spinnerDuree.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerCategorie)));

        myForm.setView(myFormView)
                .setPositiveButton("Create", (dialog, id) -> {


                    arrayAdapter.notifyDataSetChanged();

                    Toast.makeText(context, "Produits sauvegarder correctement!", Toast.LENGTH_LONG).show();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        myForm.create();

        myForm.show();
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