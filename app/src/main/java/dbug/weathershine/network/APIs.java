package dbug.weathershine.network;

import dbug.weathershine.models.Forecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIs {

    @GET("weather/")
    Call<Object> getWeatherByCity(@Query("q") String city, @Query("APPID") String api_key, @Query("units") String units);

    @GET("weather/")
    Call<Object> getWeatherByCityId(@Query("id") String id, @Query("APPID") String api_key, @Query("units") String units);

    @GET("weather/")
    Call<Object> getWeatherByCoords(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String api_key, @Query("units") String units);

    @GET("weather/")
    Call<Object> getWeatherByZip(@Query("zip") String zip, @Query("APPID") String api_key, @Query("units") String units);

    @GET("forecast/")
    Call<Forecast> getWeatherForecastByCity(@Query("q") String city, @Query("APPID") String api_key, @Query("units") String units);

    @GET("forecast/")
    Call<Forecast> getWeatherForecastByCityId(@Query("id") String id, @Query("APPID") String api_key, @Query("units") String units);

    @GET("forecast/")
    Call<Forecast> getWeatherForecastByCoords(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String api_key, @Query("units") String units);

    @GET("forecast/")
    Call<Forecast> getWeatherForecastByZip(@Query("zip") String zip, @Query("APPID") String api_key, @Query("units") String units);
}
