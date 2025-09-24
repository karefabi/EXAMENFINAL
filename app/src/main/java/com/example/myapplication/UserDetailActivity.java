package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private User user;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        // Obtener datos del usuario desde el Intent
        user = (User) getIntent().getSerializableExtra("USER");

        if (user == null) {
            Toast.makeText(this, "Error al cargar usuario", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad si no hay datos válidos
            return;
        }

        // Configurar vistas
        setupViews();

        // Obtener la bandera del país
        fetchCountryFlag();

        // Configurar el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void setupViews() {
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        TextView addressTextView = findViewById(R.id.addressTextView);
        TextView ageTextView = findViewById(R.id.ageTextView);
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        TextView cellTextView = findViewById(R.id.cellTextView);
        TextView nationalityTextView = findViewById(R.id.nationalityTextView);
        TextView idTextView = findViewById(R.id.idTextView);
        ImageView userImageView = findViewById(R.id.userImageView);

        nameTextView.setText(user.getName().getFirst() + " " + user.getName().getLast());
        emailTextView.setText("Correo electrónico: " + user.getEmail());

        String address = "Dirección: " + user.getLocation().getStreet().toString() + ", "
                + user.getLocation().getCity() + ", " + user.getLocation().getCountry();
        addressTextView.setText(address);

        ageTextView.setText("Edad: " + user.getAge() + " años");
        phoneTextView.setText("Teléfono: " + user.getPhone());
        cellTextView.setText("Celular: " + user.getCell());
        nationalityTextView.setText("Nacionalidad: " + user.getLocation().getCountry());
        idTextView.setText("Identificación (AVS): " + user.getId().getValue());

        // Cargar la imagen del usuario
        Glide.with(this)
                .load(user.getPicture().getLarge())
                .into(userImageView);

        // Cargar la bandera del país
        fetchCountryFlag();
    }

    private void fetchCountryFlag() {
        String country = user.getLocation().getCountry();
        Log.d("COUNTRY_API", "Solicitando datos de: " + country);

        UserProfileApp.getCountryApiService().getCountryInfo(country)
                .enqueue(new Callback<List<CountryResponse>>() {
                    @Override
                    public void onResponse(Call<List<CountryResponse>> call,
                                           Response<List<CountryResponse>> response) {
                        if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                            CountryResponse countryData = response.body().get(0);
                            String flagUrl = countryData.getFlag();
                            Log.d("FLAG_URL", "URL de la bandera: " + flagUrl);
                            loadFlag(flagUrl);
                        } else {
                            Log.e("FLAG_ERROR", "Error al obtener la bandera: " + response.message());
                            Toast.makeText(UserDetailActivity.this,
                                    "Error al obtener la bandera: " + response.message(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CountryResponse>> call, Throwable t) {
                        Log.e("NETWORK_ERROR", "Error de red: " + t.getMessage());
                        Toast.makeText(UserDetailActivity.this,
                                "Error de red: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadFlag(String flagUrl) {
        ImageView flagImageView = findViewById(R.id.flagImageView);
        Glide.with(this)
                .load(flagUrl)
                .into(flagImageView);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        // Obtener la ciudad y el país del usuario
        String location = user.getLocation().getCity() + ", " + user.getLocation().getCountry();

        // Buscar la ubicación con Geocoder
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (!addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                // Agregar un marcador en la ubicación
                map.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(location));

                // Mover la cámara a la ubicación
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
            } else {
                Toast.makeText(this, "No se encontró la ubicación", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error al obtener la ubicación", Toast.LENGTH_SHORT).show();
        }
    }

}
