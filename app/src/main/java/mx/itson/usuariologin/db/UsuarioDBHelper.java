package mx.itson.usuariologin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuarioDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "usuarios.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRES = "nombres";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String COLUMN_CORREO = "correo";
    public static final String COLUMN_CONTRASENA = "contraseña";
    public static final String COLUMN_TELEFONO = "telefono";

    public UsuarioDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRES + " TEXT, " +
                COLUMN_APELLIDOS + " TEXT, " +
                COLUMN_CORREO + " TEXT UNIQUE, " +
                COLUMN_CONTRASENA + " TEXT, " +
                COLUMN_TELEFONO + " TEXT)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    public boolean correoExiste(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USUARIOS, null, COLUMN_CORREO + " = ?", new String[]{correo}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void insertarUsuario(String nombres, String apellidos, String correo, String contraseña, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NOMBRES, nombres);
        contentValues.put(COLUMN_APELLIDOS, apellidos);
        contentValues.put(COLUMN_CORREO, correo);
        contentValues.put(COLUMN_CONTRASENA, contraseña);
        contentValues.put(COLUMN_TELEFONO, telefono);

        long result = db.insert(TABLE_USUARIOS, null, contentValues);
        db.close();

        // Verificar si la inserción fue exitosa
        if (result == -1) {
            // Error al insertar
            throw new RuntimeException("Error al insertar el usuario");
        }
    }
}
