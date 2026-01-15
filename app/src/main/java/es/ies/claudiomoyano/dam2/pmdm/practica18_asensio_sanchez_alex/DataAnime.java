package es.ies.claudiomoyano.dam2.pmdm.practica18_asensio_sanchez_alex;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataAnime {
    @SerializedName("data")
    private List<Anime> data;

    public List<Anime> getData() {
        return data;
    }

    public static class Anime {
        @SerializedName("title")
        private String titulo;

        @SerializedName("episodes")
        private int episodios;

        @SerializedName("score")
        private double puntuacion;

        @SerializedName("images")
        private Images images;

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public int getEpisodios() {
            return episodios;
        }

        public void setEpisodios(int episodios) {
            this.episodios = episodios;
        }

        public double getPuntuacion() {
            return puntuacion;
        }

        public void setPuntuacion(double puntuacion) {
            this.puntuacion = puntuacion;
        }

        public static class Images {
            @SerializedName("jpg")
            private Jpg jpg;
        }

        public static class Jpg {
            @SerializedName("image_url")
            private String imageUrl;
        }

        public String getImageUrl() {
            return (images != null && images.jpg != null) ? images.jpg.imageUrl : null;
        }
    }
}
