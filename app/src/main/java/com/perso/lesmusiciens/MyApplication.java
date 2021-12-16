package com.perso.lesmusiciens;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
    // Déclaration d'un objet membre de type MYAPPLICATION !
    // Cette objet sera utilisé en tant que Singleton !
    private static MyApplication m_singleton_myApplication;

    // Déclaration du database helper réutilisable
    DataBaseHelper m_dataBaseHelper = null;

    // Renvoie de l'instance de l'application
    public static MyApplication getInstance() {
        return m_singleton_myApplication;
    }

    @Override public final void onCreate() {
        super.onCreate();
        m_singleton_myApplication = this;

        Log.d("TRIGRAMME", "Je passe dans le on create de la classe Application");

        // On demande l'initialisation du database helper
        initialiserDatabaseHelper();
    }

    // Getter pour l'objet DatabaseHelper centralisé
    public DataBaseHelper getDatabaseHelper() {
        return m_dataBaseHelper;
    }

    // Methode qui permet d'initialiser le Database Helper
    private void initialiserDatabaseHelper() {
        // Je déclare et j'instancie un nouveau database Helper
        m_dataBaseHelper = new DataBaseHelper(this);
    }
}
