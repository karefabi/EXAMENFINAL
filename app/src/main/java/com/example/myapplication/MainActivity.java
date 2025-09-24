package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements UserAdapter.OnUserClickListener {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.usersRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        fetchUsers();
    }

    private void fetchUsers() {
        RandomUserApiService apiService = UserProfileApp.getRandomUserApiService();
        Call<UserResponse> call = apiService.getUsers(20);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body().getUsers();
                    userAdapter = new UserAdapter(users, MainActivity.this);
                    recyclerView.setAdapter(userAdapter);
                    Log.d("API_RESPONSE", "Usuarios recibidos: " + users.size());
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener usuarios", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Respuesta vacía o no exitosa");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Error al obtener usuarios", t);
            }
        });
    }

    @Override
    public void onUserClick(User user) {
        // Abre el detalle del usuario al hacer clic
        Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
        intent.putExtra("USER", user);  // user ya es Serializable
        startActivity(intent);
    }
}
