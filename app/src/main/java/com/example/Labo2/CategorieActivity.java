package com.example.Labo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class CategorieActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    //INTERFACE UI
    ArrayList<Exercice> listeExercices;
    ListView listViewExercicesCategorie;
    ExerciceArrayAdapter arrayAdapter;
    Context context = CategorieActivity.this;

    //FORM UI
    ImageView imageSelector;

    //DATABASEHELPER FOR DATABASE
    protected MyDataBaseHelper maDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //DEFAULT
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);

        //SETUP BACK BUTTON
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //GET DATABASEHELPER TO ACCESS DATABASE
        maDB = new MyDataBaseHelper(this);

        //GET CategorieChoisie
        Bundle extras = getIntent().getExtras();
        String categorieChoisie = extras.getString("categorieChoisie");

        //LISTER LE ARRAYLIST
        listerExerciceSelonCategorie(categorieChoisie);


        //TEST****************************************
        Button btn_form = findViewById(R.id.btn_form);
        btn_form.setOnClickListener(this);
        //TEST****************************************
    }


    //************************************\\
    // GET LIST OF EXERCICE BY CATEGORIE   \\
    //*******************************************************************************************************************************************************
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
    //*******************************************************************************************************************************************************
    private void formAjouterExercice() {
        //INSTANTIATE THE FORM AND INFLATE IT________________________________________________________________________________________________________________
        AlertDialog.Builder myForm = new AlertDialog.Builder(context, R.style.form_theme);
        LayoutInflater inflater = LayoutInflater.from(context);
        View myFormView = inflater.inflate(R.layout.form_ajouter_exercice, findViewById(R.id.linearLayoutRoot));

        //SET UNFOCUS FOR EDITTEXT, THIS WILL ENABLE TO CLICK OUTSIDE THE KEYBOARD TO CLOSE IT_______________________________________________________________
        EditText nomInput = myFormView.findViewById(R.id.input_title);
        EditText descriptionInput = myFormView.findViewById(R.id.input_description);

        ArrayList<EditText> editTextList = new ArrayList<>();
        editTextList.add(nomInput);
        editTextList.add(descriptionInput);

        for (int i = 0; i < editTextList.size(); i++) {
            editTextList.get(i).setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            });
        }


        //Get ImageView for the Form__________________________________________________________________________________________________________________________
        imageSelector = myFormView.findViewById(R.id.drawableImage);

        //Get Spinner for the Form____________________________________________________________________________________________________________________________
        Spinner imageSpinner = myFormView.findViewById(R.id.spinnerImage);
        imageSpinner.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerImage)));
        imageSpinner.setOnItemSelectedListener(this);

        //Set Spinner Layout and Strings______________________________________________________________________________________________________________________
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

        //FORM ACTION ====CREATE / CANCEL======_______________________________________________________________________________________________________________
        myForm.setView(myFormView)
                .setPositiveButton("Create", (dialog, id) -> {
                    //Get Form Values

                    String nomForm = nomInput.getText().toString();
                    String descriptionForm = descriptionInput.getText().toString();
                    String categorieForm = spinnerCategorie.getSelectedItem().toString();
                    String setsForm = spinnerSets.getSelectedItem().toString();
                    String pauseForm = spinnerPause.getSelectedItem().toString();
                    String repeatForm = spinnerRepeat.getSelectedItem().toString();
                    String dureeForm = spinnerDuree.getSelectedItem().toString();
                    String imageForm = imageSpinner.getSelectedItem().toString();
                    String favoriteForm = "0";

                    //Insert into Database_______________________________________________________________________________________________________________________
                    maDB.insertExercice(nomForm, imageForm, repeatForm, categorieForm, setsForm, dureeForm, descriptionForm, pauseForm, favoriteForm);

                    //TEST_______________________________________________________________________________________________________________________________________
                    arrayAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "Exercice " + nomForm + " sauvegarder correctement!", Toast.LENGTH_LONG).show();
                })

                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        //CREATE FORM
        myForm.create();

        //DISPLAY THE FORM
        myForm.show();
    }


    //*************************************************\\
    //  OnSELECTION FOR SPINNER AND IMAGEVIEW IN FORM   \\
    //*******************************************************************************************************************************************
    @Override
    public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
        //GET IMAGE NAME FROM SPINNER SELECTION
        String drawableName = parentView.getItemAtPosition(position).toString();

        //GET CONTEXT OF THE IMAGE VIEW
        Context context = imageSelector.getContext();

        //GET IMAGE RESOURCE
        int drawableID = context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());

        //SET IMAGE TO IMAGEVIEW
        imageSelector.setImageResource(drawableID);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //NOT NEEDED, JUST NEED TO IMPLEMENT FUNCTION
    }

    //**********************\\
    //  BACK BUTTON ACTION   \\
    //*******************************************************************************************************************************************
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //BACK BUTTON --> GO ONE ACTIVITY UP, IT GETS THE PARENT DECLARED IN THE ANDROIDMANIFEST.XML
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //****************\\
    //  HIDE KEYBOARD   \\
    //*******************************************************************************************************************************************
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //**************************************\\
    //  TEST THE FORM FOR ADDING EXERCICE    \\
    //*******************************************************************************************************************************************
    @Override
    public void onClick(View view) {
        formAjouterExercice();
    }

}