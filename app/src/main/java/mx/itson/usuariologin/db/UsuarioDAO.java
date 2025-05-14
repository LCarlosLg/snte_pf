package mx.itson.usuariologin.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {

    private SQLiteDatabase db;
    private UsuarioDBHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new UsuarioDBHelper(context);
        this.db = dbHelper.getReadableDatabase();
    }

    // Método para obtener todos los usuarios
    public Cursor obtenerUsuarios() {
        // Definir las columnas que deseas obtener
        String[] columnas = {
                "id",
                "nombres",
                "apellidos",
                "correo",
                "telefono"
        };

        // Realizar la consulta para obtener todos los usuarios
        return db.query("usuarios", columnas, null, null, null, null, null);
    }

    // Método para cerrar la base de datos
    public void cerrar() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
