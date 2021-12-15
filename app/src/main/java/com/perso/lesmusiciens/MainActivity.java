package com.perso.lesmusiciens;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Déclaration des variables membres
    Button m_btn_add;
    Button m_btn_viewAll;
    EditText m_editText_nomMusicien;
    EditText m_editText_nombreEtoiles;
    Switch m_switch_musicienActif;
    ListView m_listView_listeDesMusiciens;
    ArrayAdapter m_ArrayAdapter_pourlistViewMusicien;
    DataBaseHelper m_dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On execute la méthode d'association XML / Java
        associerElementsXMLauJava();

        // On execute la méthode qui permet d'initialiser le Database helper
        initialiserDatabaseHelper();

        // On execute la méthode qui met en place les listeners
        mettreEnPlaceLesListeners();

        // On execute la méthode qui permet d'initialiser l'adapter de la ListView
        initialiserLadapterDeLaListView();
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

    // Methode qui permet d'initialiser le Database Helper
    private void initialiserDatabaseHelper() {
        // Je déclare et j'instancie un nouveau database Helper
        m_dataBaseHelper = new DataBaseHelper(MainActivity.this);
    }

    // Attention, il est nécessaire que le Database Helper soit bien initialisé
    private void initialiserLadapterDeLaListView() {
        // On demande au helper de nous retourner la liste de tous les musiciens
        List<MusicienModel> l_List_tousLesMusiciens = m_dataBaseHelper.getAllMusiciens();

        // On a besoin d'un adapteur pour gérer la ListView des musiciens
        // On va interconnecter l'adapter avec l'element XML ListView
        m_ArrayAdapter_pourlistViewMusicien = new ArrayAdapter<MusicienModel>(
                /* CONTEXT                      */ MainActivity.this,
                /* STYLE DE LISTE ADAPTER       */ android.R.layout.simple_list_item_1,
                /* LISTE CONNECTEE A L'ADAPTER  */ l_List_tousLesMusiciens
        );

        // On va définir l'adapter à utiliser pour notre objet Java ListView (lui même connecté à la listView XML)
        m_listView_listeDesMusiciens.setAdapter(m_ArrayAdapter_pourlistViewMusicien);
    }

    // Méthode qui permet de centraliser la mise en place des listeners sur les boutons
    private void mettreEnPlaceLesListeners() {
        // Création du listener sur le bouton ajouter musicien
        m_btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicienModel l_obj_musicienModel = null;

                try {
                    String l_str_nomMusicien = m_editText_nomMusicien.getText().toString();
                    int l_int_nombreEtoiles = Integer.parseInt(m_editText_nombreEtoiles.getText().toString());
                    boolean l_bool_musicienActif = m_switch_musicienActif.isChecked();

                    // On va créer une nouvelle instance de MusicienModel
                    l_obj_musicienModel = new MusicienModel(-1, l_str_nomMusicien, l_int_nombreEtoiles, l_bool_musicienActif);

                    Toast.makeText(MainActivity.this, "CLICK SUR BOUTON AJOUTER NOM ["+l_str_nomMusicien+"] / ETOILES ["+l_int_nombreEtoiles+"] / ACTIF ["+l_bool_musicienActif+"]", Toast.LENGTH_LONG).show();

                    // Je déclare et j'instancie un nouveau database Helper
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                    // On réalise l'insertion en base
                    boolean l_bool_success = dataBaseHelper.addOneMusicien(l_obj_musicienModel);

                    // On réinitialise l'adapter
                    initialiserLadapterDeLaListView();

                    Toast.makeText(MainActivity.this, "RESULTAT INSERTION EN BASE SQL LITE ["+l_bool_success+"]", Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this, "PROBLEME LORS DE LA CREATION DU MUSICIEN ["+e.toString()+"]", Toast.LENGTH_LONG).show();

                    l_obj_musicienModel = new MusicienModel(-1, "error", 0, false);
                }

            }
        });

        // Création du listener sur le bouton ajouter musicien
        m_btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialiserLadapterDeLaListView();
                // Toast.makeText(MainActivity.this, "CLICK SUR BOUTON VIEW ALL LISTE ["+l_List_tousLesMusiciens.toString()+"]", Toast.LENGTH_LONG).show();
            }
        });

        // Création du listener permettant de détecter le clic sur un ITEM de la LISTVIEW !!!
        m_listView_listeDesMusiciens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // On récupère l'element sur lequel on a cliqué à l'aide de l'automatisme Android
                // qui permet de détecter le numéro d'élément cliqué dans la liste (position)
                MusicienModel l_MusicienModel_clickedItemInList = (MusicienModel) parent.getItemAtPosition(position);

                // On utilise le database helper pour demander la suppression d'un musicien
                m_dataBaseHelper.removeOneMusicien(l_MusicienModel_clickedItemInList);

                // On execute la méthode pour réinitialiser l'adapter de la listview
                initialiserLadapterDeLaListView();

                String l_str_preparationMessageToast = "MUSICIEN SUPPRIME"
                        + " ID ["+l_MusicienModel_clickedItemInList.getId()+"] "
                        + " NOM ["+l_MusicienModel_clickedItemInList.getNomMusicien()+"] "
                        + " ACTIF ["+l_MusicienModel_clickedItemInList.isActive()+"]";

                Toast.makeText(MainActivity.this, l_str_preparationMessageToast , Toast.LENGTH_LONG).show();
            }
        });
    }

}