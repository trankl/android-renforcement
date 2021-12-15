package com.perso.lesmusiciens;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

// Notre DataBaseHelper réalise un extends de SQLiteOpenHelper
// On devra redéfinir un ensemble de méthodes hérités de SQLiteOpenHelper
public class DataBaseHelper extends SQLiteOpenHelper {

    // Déclaration des variables réutilisables pour la base de donnée
    public static final String T_MUSICIENS = "T_MUSICIENS";
    public static final String F_MUSICIEN_ID = "MUSICIEN_ID";
    public static final String F_MUSICIEN_NOM = "MUSICIEN_NOM";
    public static final String F_MUSICIEN_NB_ETOILES = "MUSICIEN_NB_ETOILES";
    public static final String F_MUSICIEN_ACTIF = "MUSICIEN_ACTIF";



    public DataBaseHelper(@Nullable Context context) {
        // On fourni le nom de la base de donnée à créer
        super(context, "musicien.db", null, 1);
    }

    // Cette méthode sera executée la première fois que l'on tente d'accéder à l'objet DataBase.
    // Il convient de l'utiliser pour créer la nouvelle base de donnée
    @Override
    public void onCreate(SQLiteDatabase db) {
        // On va créer un Statement Sql Lite pour créer la base de donnée

        // REQUETE A TRANSFORMER EN VARIABLE
        //          CREATE TABLE T_MUSICIENS (
        //              ID INTEGER PRIMARY KEY AUTOINCREMENT,
        //              MUSICIEN_NOM TEXT,
        //              MUSICIEN_NB_ETOILES INT,
        //              MUSICIEN_ACTIF BOOL
        //          );

        String l_str_createTableStatement = "CREATE TABLE " + T_MUSICIENS + " ("
                + F_MUSICIEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + F_MUSICIEN_NOM + " TEXT, "
                + F_MUSICIEN_NB_ETOILES + " INT, "
                + F_MUSICIEN_ACTIF + " BOOL"
                + ")";

        // On va executer le statement
        db.execSQL(l_str_createTableStatement);
    }


    // Cette méthode sera executée lorsque le numéro de version de la base de donnée est modifié
    // Il convient d'utiliser cette méthode pour mettre à jour la base de donnée automatiquement
    // Exemple : Le client demande une évolution de l'application. Vous mettez à jour le code
    // et une nouvelle table est nécessaire. Cette méthode va permettre de mettre à jour la base
    // de donnée ancienne version vers la nouvelle version incluant la nouvelle table. En effet,
    // pendant la mise à jour de l'application, la base de donnée ne contient pas encore la nouvelle
    // table. Cette méthode va permettre de mettre à jour la base de donnée SQLite
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOneMusicien(MusicienModel i_ref_musicienAAjouterDansLaBase) {
        // Déclaration de la variable en sortie
        boolean o_bool_resultatInsertion = false;

        // On récupère une référence vers la base de donnée
        SQLiteDatabase l_ref_db = this.getWritableDatabase();

        // On crée un content values pour gérer les données du musicien à ajouter
        ContentValues l_ContentValues_donneesDuMusicienAAjouter = new ContentValues();

        // Je prépare les données du musicien dans le ContentValues
        l_ContentValues_donneesDuMusicienAAjouter.put(F_MUSICIEN_NOM, i_ref_musicienAAjouterDansLaBase.getNomMusicien());
        l_ContentValues_donneesDuMusicienAAjouter.put(F_MUSICIEN_NB_ETOILES, i_ref_musicienAAjouterDansLaBase.getNbEtoiles());
        l_ContentValues_donneesDuMusicienAAjouter.put(F_MUSICIEN_ACTIF, i_ref_musicienAAjouterDansLaBase.isActive());

        // On peut maintenant réaliser le INSERT en base
        long l_long_insertedId = l_ref_db.insert(T_MUSICIENS, null, l_ContentValues_donneesDuMusicienAAjouter);

        // CAS 1 : CAS ECHEC, Le inserted id vaut -1 cela veut dire que l'insertion n'a pas eu lieu
        if (l_long_insertedId==-1) {o_bool_resultatInsertion = false;}
        // CAS 2 : CAS SUCCES, on positionne le flag en sortie à true
        else {o_bool_resultatInsertion = true;}

        return true;
    }

    public List<MusicienModel> getAllMusiciens() {
        // On déclare et on initialise la ArrayList en sortie
        List<MusicienModel> o_ALIST_tousLesMusiciens = new ArrayList<MusicienModel>();

        // On déclare la requete à utiliser
        // Requete à utiliser : SELECT * FROM T_MUSICIENS
        String l_str_requete = "SELECT * FROM " + T_MUSICIENS;

        // On récupère la référence vers la base de donnée
        SQLiteDatabase l_ref_db = this.getWritableDatabase();

        // On execute la requete avece un Raw Query (celle ci retourne un cursor)
        Cursor l_Cursor_resultatALaRequete = l_ref_db.rawQuery(l_str_requete, null);

        // On vérifie que l'on parvient à se positionner sur le premier élément
        if (l_Cursor_resultatALaRequete.moveToFirst()==true) {
            do {
                // On récupère les données à partir du Cursor
                int musicienId = l_Cursor_resultatALaRequete.getInt(0);
                String musicienNom = l_Cursor_resultatALaRequete.getString(1);
                int musicienNbEtoiles = l_Cursor_resultatALaRequete.getInt(2);
                boolean musicienActif = l_Cursor_resultatALaRequete.getInt(3) == 1 ? true : false;

                // On va reconstituer un musicien à partir des données du Cursor
                MusicienModel t_musicienAAjouterDansLaListeResultats = new MusicienModel(musicienId, musicienNom, musicienNbEtoiles, musicienActif);

                // On pense bien à ajouter dans la liste en sortie l'instance de Musicien que nous venons de reconstituer
                o_ALIST_tousLesMusiciens.add(t_musicienAAjouterDansLaListeResultats);
            }
            while (l_Cursor_resultatALaRequete.moveToNext());
        }
        // CAS 2 : On n'a pas pu se positionner sur le premier élément (0 résultats)
        else {

        }

        // Attention, on doit bien penser réaliser le close sur le cursor
        l_Cursor_resultatALaRequete.close();

        // Attention, on doit bien penser réaliser le close sur la base de donnée
        l_ref_db.close();

        // On retourne la liste de résultats
        return o_ALIST_tousLesMusiciens;
    }
}
