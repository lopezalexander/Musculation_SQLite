package com.example.Labo2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ListeExercice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView imageSelector;
    Spinner imageSpinner;

    ArrayList<Exercice> listeExerciceCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_exercice);

        //Get ImageView for the Form
        imageSelector = findViewById(R.id.drawableImage);

        //Get Spinner for the Form
        imageSpinner = findViewById(R.id.drawableSpinner);
        imageSpinner.setAdapter(new SpinnerAdapter(this, R.layout.spinner_content_layout, getResources().getStringArray(R.array.spinnerData)));
        imageSpinner.setOnItemSelectedListener(this);

        //GET ARRAYLIST OF Exercice PAR CATEGORIE
        Bundle extras = getIntent().getExtras();
        listeExerciceCategorie = extras.getParcelableArrayList("listeExerciceCategorie");
    }

    //**************\\
    //  FONCTIONS    \\
    //*******************************************************************************************************************************************

//    private AlertDialog formAjouterExercice() {
//        AlertDialog.Builder myForm = new AlertDialog.Builder(context, R.style.form_theme);
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View myFormView = inflater.inflate(R.layout.insert_produit_form, findViewById(R.id.linearLayoutRoot));
//
//
//        //SET FILTERS FOR PRICE EDITTEXT
//        EditText nomProduit = myFormView.findViewById(R.id.input_nom);
//        EditText categorieProduit = myFormView.findViewById(R.id.input_categorie);
//        EditText prixProduit = myFormView.findViewById(R.id.input_prix);
//        EditText qteProduit = myFormView.findViewById(R.id.input_qte);
//
//        prixProduit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(9, 2)});
//
//        myForm.setView(myFormView)
//                .setPositiveButton("Create", (dialog, id) -> {
//
//                    //FORM DATA
//                    String nom = nomProduit.getText().toString();
//                    String categorie = categorieProduit.getText().toString();
//                    String prix = prixProduit.getText().toString();
//                    String qte = qteProduit.getText().toString();
//
//                    Produit nouveauProduit = new Produit(nom, categorie, prix, qte);
//                    listeProduits.add(nouveauProduit);
//
//                    arrayAdapter.notifyDataSetChanged();
//
//                    Toast.makeText(context, "Produits sauvegarder correctement!", Toast.LENGTH_LONG).show();
//                })
//                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
//        return myForm.create();
//    }


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