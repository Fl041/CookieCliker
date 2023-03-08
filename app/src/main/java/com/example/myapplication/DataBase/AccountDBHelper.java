package com.example.myapplication.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDBHelper extends SQLiteOpenHelper {

    /* Création de la BDD accounts */
    public static final int BASE_VERSION = 1;
    public static final String BASE_NOM = "accounts.db";

    /* Création de la table "table_accounts" et des colonnes */
    private static final String TABLE_ACCOUNTS = "table_accounts";

    public static final String COLONNE_ID = "id";
    public static final int COLONNE_ID_ID = 0;

    public static final String COLONNE_USERNAME = "username";
    public static final int COLONNE_USERNAME_ID = 1;

    public static final String COLONNE_EMAIL = "email";
    public static final int COLONNE_EMAIL_ID = 2;

    public static final String COLONNE_PASSWORD = "password";
    public static final int COLONNE_PASSWORD_ID = 3;

    public static final String COLONNE_AVATAR = "avatar";
    public static final int COLONNE_AVATAR_ID = 4;

    public static final String COLONNE_ISCONNECTED = "isconnected";
    public static final int COLONNE_ISCONNECTED_ID = 5;

    public static final String COLONNE_Cookies = "cookies";
    public static final int COLONNE_Cookies_ID = 6;

    public static final String COLONNE_UPGRADE1 = "upgrade1";
    public static final int COLONNE_UPGRADE1_ID = 7;

    /* Requête SQL de la création de la table "table_accounts" */
    private static final String REQUETE_CREATION_BD = "create table "
            + TABLE_ACCOUNTS + " (" + COLONNE_ID
            + " integer primary key autoincrement, " + COLONNE_USERNAME
            + " text not null, " + COLONNE_EMAIL
            + " text not null, " + COLONNE_PASSWORD
            + " text not null, " + COLONNE_AVATAR
            + " text not null, " + COLONNE_ISCONNECTED+
            " boolean, " +COLONNE_Cookies + " integer, " +
            COLONNE_UPGRADE1 + " boolean);";

    /**
     * L'instance de la base qui sera manipulée au travers de cette classe
     */
    private SQLiteDatabase maBaseDonnees;


    public AccountDBHelper(Context context, String nom,
                           SQLiteDatabase.CursorFactory cursorfactory, int version) {
        super(context, nom, cursorfactory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(REQUETE_CREATION_BD);
    }
    // insere les données lors de la création
    public void insertData(String username, String email, String password,String avatar) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLONNE_USERNAME, String.valueOf(username));
        values.put(COLONNE_EMAIL, String.valueOf(email));
        values.put(COLONNE_PASSWORD, String.valueOf(password));
        values.put(COLONNE_AVATAR, String.valueOf(avatar));
        values.put(COLONNE_ISCONNECTED,false);
        values.put(COLONNE_Cookies, 0);
        values.put(COLONNE_UPGRADE1,false);
        db.insert(TABLE_ACCOUNTS, null, values);
        db.close();
    }
    // renvoie toute les informations du compte connecté
    public Cursor showconnectedaccount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_ACCOUNTS+" WHERE "
                +COLONNE_ISCONNECTED + "= true", null);
        return cursor;
    }
    // permet de savoir si un compte est connecté ou non
    public boolean isconnected(){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean result = false;
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_ACCOUNTS+" WHERE "
                +COLONNE_ISCONNECTED + "= true", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                return result = true;
            }
        }
        return result ;
    }
    // savoir lors du login que les identifiants entrés correspondent à un utilisateur
    public boolean existe(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        boolean result = false;
        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_ACCOUNTS+" WHERE "
                +COLONNE_EMAIL +" = '" +email +"' and " + COLONNE_PASSWORD +
                " = '" + password +"'", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                return result = true;
            }
        }
        return result ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS + ";");
        // Création de la nouvelle structure.
        onCreate(db);
    }
    // connecte un compte via l'email et le password
    public void Connected(String email , String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLONNE_ISCONNECTED,true);
        db.update(TABLE_ACCOUNTS,values , COLONNE_EMAIL +" = '" +email +"' and " + COLONNE_PASSWORD +
                " = '" + password +"'" , null  );
    }
    // déconnecte le compte connecté
    public void Disconnect(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLONNE_ISCONNECTED,false);
        db.update(TABLE_ACCOUNTS,values ,COLONNE_ISCONNECTED + "= " +"true"  , null );
    }
    // modifie les informations du compte lors de la modification du compte
    public void updateAccount(String username, String email , String password, String avatar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLONNE_USERNAME, String.valueOf(username));
        values.put(COLONNE_EMAIL, String.valueOf(email));
        values.put(COLONNE_PASSWORD, String.valueOf(password));
        values.put(COLONNE_AVATAR, String.valueOf(avatar));
        db.update(TABLE_ACCOUNTS,values , COLONNE_ISCONNECTED + "= " +"true" , null  );
    }
    // update le nombre de cookie pour l'utilisateur connecté dans la bd
    public void updatenbcookies(int nbcookies){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLONNE_Cookies, nbcookies);
        db.update(TABLE_ACCOUNTS,values , COLONNE_ISCONNECTED + "= " +"true" , null  );
    }
    // permet d'activé cookie booster pour le compte connecté
    public void updateupgrade1true(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLONNE_UPGRADE1, true);
        db.update(TABLE_ACCOUNTS,values , COLONNE_ISCONNECTED + "= " +"true" , null  );
    }
    // permet désactivé cookie booster pour le compte connecté
    public void updateupgrade1false(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLONNE_UPGRADE1, false);
        db.update(TABLE_ACCOUNTS,values , COLONNE_ISCONNECTED + "= " +"true" , null  );
    }
}
