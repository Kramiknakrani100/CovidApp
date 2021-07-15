package com.example.covidtracker.apisecond;

import android.annotation.SuppressLint;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiSecondUtilites {

    private static Retrofit retrofit = null;
    public static ApiSecondUtilites apiSecondUtilites;
    static final String BASE_URL2 = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/";


    private ApiSecondUtilites(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL2)
                .client(getUnsafeOkHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiSecondUtilites getInstance(){

        if (apiSecondUtilites==null){
            apiSecondUtilites = new ApiSecondUtilites();
        }
        return apiSecondUtilites;
    }

    public  ApiSecondInterface getApi(){
        return  retrofit.create(ApiSecondInterface.class);
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient(){
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
