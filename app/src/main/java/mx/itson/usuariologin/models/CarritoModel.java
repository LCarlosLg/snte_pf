package mx.itson.usuariologin.models;

public class CarritoModel {

    private int id;
    private int usuario_id;
    private int producto_id;
    private int cantidad;
    private String fecha_agregado;

    public int getId() {
        return id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getFecha_agregado() {
        return fecha_agregado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setFecha_agregado(String fecha_agregado) {
        this.fecha_agregado = fecha_agregado;
    }
}
