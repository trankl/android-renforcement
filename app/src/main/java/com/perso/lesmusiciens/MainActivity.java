package com.perso.lesmusiciens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Déclaration des variables membres
    Button m_btn_add;
    Button m_btn_viewAll;
    EditText m_editText_nomMusicien;
    EditText m_editText_nombreEtoiles;
    Switch m_switch_musicienActif;
    ListView m_listView_listeDesMusiciens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On execute la méthode d'association XML / Java
        associerElementsXMLauJava();

        // On execute la méthode qui met en place les listeners
        mettreEnPlaceLesListeners();
    }

    // Méthode qui permet de centraliser la connection de nos éléments XML à notre Activity Java
    private void associerElementsXMLauJava() {
        m_btn_add = findViewById(R.id.xml_mainActivity_btn_ajouter);
        m_btn_viewAll = findViewById(R.id.xml_mainActivity_btn_voirTout);
        m_editText_nomMusicien = findViewById(R.id.xml_editText_mainActivity_nomMusicien);
        m_editText_nombreEtoiles = findViewById(R.id.xml_number_mainActivity_nombreEtoiles);
        m_switch_musicienActif = findViewById(R.id.xml_switch_mainActivity_selecteurMusiciens);
        m_listView_listeDesMusiciens = findViewById(R.id.xml_mainActivity_listView_listeMusiciens);
    }

    // Méthode qui permet de centraliser la mise en place des listeners sur les boutons
    private void mettreEnPlaceLesListeners() {
        // Création du listener sur le bouton ajouter musicien
        m_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "CLICK SUR BOUTON AJOUTER", Toast.LENGTH_LONG).show();
            }
        });

        // Création du listener sur le bouton ajouter musicien
        m_btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "CLICK SUR BOUTON VIEW ALL", Toast.LENGTH_LONG).show();
            }
        });
    }

}