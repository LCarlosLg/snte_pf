package mx.itson.usuariologin.models;

public class UsuarioModel {

    private int id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String password;
    private String create_at;
    private String role;

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCreate_at() { return create_at; }
    public void setCreate_at(String create_at) { this.create_at = create_at; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
