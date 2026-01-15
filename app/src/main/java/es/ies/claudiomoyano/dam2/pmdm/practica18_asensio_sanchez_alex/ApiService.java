package es.ies.claudiomoyano.dam2.pmdm.practica18_asensio_sanchez_alex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("anime")
    Call<DataAnime> buscarAnime(@Query("q") String nombre);
}
