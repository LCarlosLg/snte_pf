package mx.itson.usuariologin.models;
import java.io.Serializable;

public class ProductoModel implements Serializable {

    private int id;
    private String nombre;
    private int stock;
    private double precio;
    private String categoria;
    private String imagen;
    private String create_at;
    private String delete_at;
    private String update_at;

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public String getCreate_at() {
        return create_at;
    }

    public String getDelete_at() {
        return delete_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public void setDelete_at(String delete_at) {
        this.delete_at = delete_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}
