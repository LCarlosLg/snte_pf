package mx.itson.usuariologin.models;

public class usuarioModel {
    public int id;
    public String nombres;
    public String apellidos;
    public String correo;
    public String contraseña;
    public String telefono;

    public usuarioModel(String nombres, String apellidos, String correo, String contraseña, String telefono) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
    }
}

