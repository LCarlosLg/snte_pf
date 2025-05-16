package mx.itson.usuariologin.pantallaEmpleado;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.List;

import mx.itson.usuariologin.R;
import mx.itson.usuariologin.models.ProductoModel;
import mx.itson.usuariologin.models.ResponseModel;
import mx.itson.usuariologin.network.ApiService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmpleadoActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private ApiService apiService;
    private Button btnGenerarReporte, btnInventario;
    private List<ProductoModel> productosParaReporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);

        btnGenerarReporte = findViewById(R.id.btnGenerarReporte);
        btnInventario = findViewById(R.id.btnInventario);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://snte.store/") // Cambia por tu URL base
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        btnGenerarReporte.setOnClickListener(v -> {
            apiService.obtenerProductos().enqueue(new Callback<List<ProductoModel>>() {
                @Override
                public void onResponse(Call<List<ProductoModel>> call, Response<List<ProductoModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        productosParaReporte = response.body();
                        checkPermissionsAndGenerateReport();
                    } else {
                        Toast.makeText(EmpleadoActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ProductoModel>> call, Throwable t) {
                    Toast.makeText(EmpleadoActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnInventario.setOnClickListener(v -> {
            Intent intent = new Intent(EmpleadoActivity.this, InventarioActivity.class);
            startActivity(intent);
        });
    }

    private void checkPermissionsAndGenerateReport() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                generarYSubirReporteExcel();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        } else {
            // Para Android Q (10) y superior no se necesita permiso WRITE_EXTERNAL_STORAGE para subir
            generarYSubirReporteExcel();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                generarYSubirReporteExcel();
            } else {
                Toast.makeText(this, "Permiso denegado para almacenamiento", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void generarYSubirReporteExcel() {
        if (productosParaReporte == null || productosParaReporte.isEmpty()) {
            Toast.makeText(this, "No hay productos para generar reporte", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Productos");

            // Encabezados
            Row headerRow = sheet.createRow(0);
            String[] headers = {"ID", "Nombre", "Precio", "Stock", "Categoria"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }

            // Datos
            int rowNum = 1;
            for (ProductoModel p : productosParaReporte) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getNombre());
                row.createCell(2).setCellValue(p.getPrecio());
                row.createCell(3).setCellValue(p.getStock());
                row.createCell(4).setCellValue(p.getCategoria());
            }

            // Ajustar columnas con ancho fijo (evitar uso de autoSizeColumn para evitar error en Android)
            for (int i = 0; i < headers.length; i++) {
                sheet.setColumnWidth(i, 5000); // ancho fijo, ajusta según prefieras
            }

            // Convertir workbook a bytes para subir
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            byte[] excelBytes = bos.toByteArray();
            workbook.close();
            bos.close();

            // Preparar RequestBody y MultipartBody.Part para subir
            RequestBody requestFile = RequestBody.create(
                    MediaType.parse("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
                    excelBytes
            );

            MultipartBody.Part body = MultipartBody.Part.createFormData("file", "reporte_productos.xlsx", requestFile);

            // Subir reporte
            apiService.subirReporte(body).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        Toast.makeText(EmpleadoActivity.this, "Reporte subido correctamente", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(EmpleadoActivity.this, "Error al subir reporte", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(EmpleadoActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al generar o subir reporte Excel", Toast.LENGTH_SHORT).show();
        }
    }
}
