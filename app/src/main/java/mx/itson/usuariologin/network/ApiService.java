package mx.itson.usuariologin.network;

import java.util.List;

import mx.itson.usuariologin.models.*;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;

public interface ApiService {

    // Obtener datos
    @GET("ApiMovile/get_productos.php")
    Call<List<ProductoModel>> obtenerProductos();

    @GET("ApiMovile/get_usuarios.php")
    Call<List<UsuarioModel>> obtenerUsuarios();

    @GET("ApiMovile/get_carrito.php")
    Call<List<CarritoModel>> obtenerCarrito();

    @GET("ApiMovile/get_pedidos.php")
    Call<List<PedidoModel>> obtenerPedidos();

    @GET("ApiMovile/get_detalle_pedidos.php")
    Call<List<DetallePedidoModel>> obtenerDetallePedidos();


    // Registrar usuario
    @FormUrlEncoded
    @POST("ApiMovile/insert_usuario.php")
    Call<ResponseModel> registrarUsuario(
            @Field("nombres") String nombres,
            @Field("apellidos") String apellidos,
            @Field("correo") String correo,
            @Field("contrasena") String contrasena,
            @Field("telefono") String telefono,
            @Field("role") String role
    );

    // AGREGAR producto
    @FormUrlEncoded
    @POST("ApiMovile/insert_producto.php")
    Call<ResponseModel> insertarProducto(
            @Field("nombre") String nombre,
            @Field("precio") double precio,
            @Field("stock") int stock,
            @Field("categoria") String categoria
    );


    // ACTUALIZAR producto
    @FormUrlEncoded
    @POST("ApiMovile/update_producto.php")
    Call<ResponseModel> actualizarProducto(
            @Field("id") int id,
            @Field("nombre") String nombre,
            @Field("precio") double precio,
            @Field("stock") int stock,
            @Field("categoria") String categoria
    );

    // ELIMINAR producto
    @FormUrlEncoded
    @POST("ApiMovile/delete_producto.php")
    Call<ResponseModel> eliminarProducto(
            @Field("id") int id
    );

    // SUBIR archivo Excel (reporte)
    @Multipart
    @POST("ApiMovile/upload_reporte.php")
    Call<ResponseModel> subirReporte(
            @Part MultipartBody.Part file
    );
}
