package com.example.Labo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
    ImageView imageSelector;
    ArrayList<Exercice> listeExercices;
    Context context = CategorieActivity.this;

    protected MyDataBaseHelper maDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //DEFAULT
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);

        //SETUP BACK BUTTON
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //SET DATABASE
        maDB = new MyDataBaseHelper(this);


        //GET CategorieChoisie
        Bundle extras = getIntent().getExtras();
        String categorieChoisie = extras.getString("categorieChoisie");

        //LISTER LE ARRAYLIST
        listerExerciceSelonCategorie(categorieChoisie);
    }


    private void listerExerciceSelonCategorie(String categorieChoisie) {
        //New Exercice List
        listeExercices = new ArrayList<>();

        //Get Exercices par Categorie ou Favoris
        if (categorieChoisie.equals("Favoris")) {
            listeExercices = maDB.obtenirFavoris();
        } else {
            listeExercices = maDB.obtenirExerciceParCategorie(categorieChoisie);
        }

        //Get ListView to show Exercices
        listViewExercicesCategorie = findViewById(R.id.liste_Exercices_Categorie);
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
        Spinner imageSpinner = myFormView.findViewById(R.id.spinnerImage);
        imageSpinner.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerImage)));
        imageSpinner.setOnItemSelectedListener(this);

        //Set Spinner Layout and Strings
        Spinner spinnerCategorie = myFormView.findViewById(R.id.spinnerCategorie);
        Spinner spinnerSets = myFormView.findViewById(R.id.spinnerSets);
        Spinner spinnerPause = myFormView.findViewById(R.id.spinnerPause);
        Spinner spinnerRepeat = myFormView.findViewById(R.id.spinnerRepeat);
        Spinner spinnerDuree = myFormView.findViewById(R.id.spinnerDuree);


        spinnerCategorie.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerCategorie)));
        spinnerSets.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerSets)));
        spinnerPause.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerPause)));
        spinnerRepeat.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerRepeat)));
        spinnerDuree.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerDuree)));


        myForm.setView(myFormView)
                .setPositiveButton("Create", (dialog, id) -> {
                    //Get Form Values
                    EditText nomInput = myFormView.findViewById(R.id.input_title);
                    String nomForm = nomInput.getText().toString();

                    EditText descriptionInput = myFormView.findViewById(R.id.input_description);
                    String descriptionForm = descriptionInput.getText().toString();

                    String categorieForm = spinnerCategorie.getSelectedItem().toString();
                    String setsForm = spinnerSets.getSelectedItem().toString();
                    String pauseForm = spinnerPause.getSelectedItem().toString();
                    String repeatForm = spinnerRepeat.getSelectedItem().toString();
                    String dureeForm = spinnerDuree.getSelectedItem().toString();
                    String imageForm = imageSpinner.getSelectedItem().toString();
                    String favoriteForm = "0";

                    //Insert into Database
                    maDB.insertExercice(nomForm, imageForm, repeatForm, categorieForm, setsForm, dureeForm, descriptionForm, pauseForm, favoriteForm);


                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "Exercice " + nomForm + " sauvegarder correctement!", Toast.LENGTH_LONG).show();
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

    //****************\\
    //  BACK BUTTON    \\
    //*******************************************************************************************************************************************

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}