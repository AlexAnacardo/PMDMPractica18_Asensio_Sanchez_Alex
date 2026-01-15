package es.ies.claudiomoyano.dam2.pmdm.practica18_asensio_sanchez_alex;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        ImageView ivImagen = findViewById(R.id.imagenAnime);
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        TextView tvEpisodios = findViewById(R.id.tvEpisodios);
        TextView tvPuntuacion = findViewById(R.id.tvPuntuacion);

        EditText etBusqueda = findViewById(R.id.etBuscar);
        Button botonBuscar = findViewById(R.id.botonBuscar);

        botonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkInfo != null && networkInfo.isConnected()){

                    ApiService apiService = RetrofitClient.obtenerInstancia().create(ApiService.class);

                    Call<DataAnime> call = apiService.buscarAnime(etBusqueda.getText().toString());

                    call.enqueue(new Callback<DataAnime>() {
                        @Override
                        public void onResponse(Call<DataAnime> call, Response<DataAnime> response) {
                            List<DataAnime.Anime> lista = response.body().getData();
                            cargarDatos(lista, ivImagen, tvTitulo, tvPuntuacion, tvEpisodios);
                        }
                        @Override
                        public void onFailure(Call<DataAnime> call, Throwable throwable) {
                            Log.e("Error", throwable.getMessage());
                        }
                    });
                }
                else{
                    //No hay internet
                }
            }
        });

        //CARGA INICIAL DE LA APP
        if(networkInfo != null && networkInfo.isConnected()){

            ApiService apiService = RetrofitClient.obtenerInstancia().create(ApiService.class);

            Call<DataAnime> call = apiService.buscarAnime("Neon Genesis Evangelion");
            call.enqueue(new Callback<DataAnime>() {
                @Override
                public void onResponse(Call<DataAnime> call, Response<DataAnime> response) {
                    List<DataAnime.Anime> lista = response.body().getData();
                    cargarDatos(lista, ivImagen, tvTitulo, tvPuntuacion, tvEpisodios);
                }
                @Override
                public void onFailure(Call<DataAnime> call, Throwable throwable) {
                    Log.e("Error", throwable.getMessage());

                }
            });
        }
        else{
            //No hay internet
        }
    }
    public void cargarDatos(List<DataAnime.Anime> lista, ImageView ivImagen, TextView tvTitulo, TextView tvPuntuacion, TextView tvEpisodios){
        if (!lista.isEmpty()) {
            DataAnime.Anime animeInfo = lista.get(0);

            Glide.with(MainActivity.this).load(animeInfo.getImageUrl()).into(ivImagen);

            tvTitulo.setText(animeInfo.getTitulo());
            tvPuntuacion.setText("Puntuacion: "+animeInfo.getPuntuacion());
            tvEpisodios.setText("Episodios: "+animeInfo.getEpisodios());
        }
    }
}