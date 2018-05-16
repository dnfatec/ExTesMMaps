package com.example.consultor.extesm;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.Manifest;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        OnRequestPermissionsResultCallback
{
    private GoogleMap mapa; //recebera um tipo mapas
    private SupportMapFragment mapFragment; //receberá o fragmento que é onde conterá nosso maps
    private GoogleApiClient mGoogleApiClient; //possibilitará o uso de nossa apicliente
    private Button btGPS;
    private LatLng latLng = new LatLng(-23.6026106, -48.0459634);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.nossoMapa); //ira receber o suporte para nosso mapa
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Toast.makeText(getApplicationContext(), "Oi", Toast.LENGTH_LONG).show();
        botoes();
    }

    private void botoes()
    {
        btGPS = (Button)findViewById(R.id.btnPosicao);
        btGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION )
                        == PackageManager.PERMISSION_GRANTED) {
                    mapa.setMyLocationEnabled(true);

                    Location getL = LocationServices.FusedLocationApi.getLastLocation(
                            mGoogleApiClient);
                    LatLng my = new LatLng(getL.getLatitude(), getL.getLongitude());
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(my, 20);
                    mapa.animateCamera(update);
                }
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mapa = googleMap;



        mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        //tipo de mapa

        //Latitude e longitude fatec
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        //como dis o metodo latitude e longitude com o zoom
        mapa.animateCamera(update);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }


    }
    @Override
    protected void onStart()
    {
        super.onStart();
        mGoogleApiClient.connect();
//conecta a api do google
    }


}
