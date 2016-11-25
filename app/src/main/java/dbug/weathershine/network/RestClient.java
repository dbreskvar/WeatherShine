package dbug.weathershine.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static Retrofit retrofit;
    private static final String API_BASE_URL = "http://api.openweathermap.org/data/2.5/";


    private static class MyDeserializer implements JsonDeserializer<Object> {
        @Override
        public Object deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
            // Get the "content" element from the parsed JSON
            JsonElement content = je.getAsJsonObject().get("data");

            // Deserialize it. You use a new instance of Gson to avoid infinite recursion
            // to this deserializer
            return new Gson().fromJson(content, Object.class);
        }
    }

    @SuppressWarnings("unchecked")
    public static <S> S setupRestClient() throws ClassCastException {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Object.class, new MyDeserializer())
                .create();
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                //If additional API KEY is requested, add it here under headers
                Request.Builder builder = request.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(request.method(), request.body());

                Request main = builder.build();

                return chain.proceed(main);
            }
        });

        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        setRetrofit(retrofit);

        return retrofit.create((Class<S>) APIs.class);
    }

    private static void setRetrofit(Retrofit mRetro) {
        retrofit = mRetro;
    }

    public static Retrofit retrofit() {
        if (retrofit != null) return retrofit;
        else return null;
    }

    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }
}
