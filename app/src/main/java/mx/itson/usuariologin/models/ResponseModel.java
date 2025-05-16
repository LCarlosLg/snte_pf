package mx.itson.usuariologin.models;

public class ResponseModel {
    private boolean success;
    private String message;
    private int pedido_id; // Campo opcional para respuestas de creación de pedido

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }
}
