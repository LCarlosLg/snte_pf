package pantallaCliente;


public class ProductoModel {
    public int id;
    public String nombre;
    public double precio;
    public int stock;
    public String categoria;

    public ProductoModel(int id, String nombre, double precio, int stock, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }
}
