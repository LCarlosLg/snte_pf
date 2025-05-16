package mx.itson.usuariologin.models;

public class PedidoModel {

    private int id;
    private int usuario_id;
    private String direccion;
    private String fecha_pedido;
    private String estado;

    public int getId() {
        return id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getFecha_pedido() {
        return fecha_pedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setFecha_pedido(String fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
