package com.example.eafatiprovimeve;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;


public class MapsFragment extends Fragment {
    private GoogleMap mMap;
    private ClusterManager<ClustersItem> clusterManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentmap, container, false);
        //initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;
//                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                setUpClusterer();
            }
        });
        return view;
    }

    private void setUpClusterer() {
        // Harita ilk olarak Istanbulda acilir.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.494785, 20.842602), 8));
        // ClusterManager nesnemizi uretiyoruz.Burda activity context ihtiyaci vardir.
        // Eger Actvity kullaniyorsak direk this diyebilirsiniz.
        clusterManager = new ClusterManager<ClustersItem>(getActivity(), mMap);
        // Mapimize artik olusturdugumu clusterManager setliyoruz ve kamerada degisiklikleri izlemesini
        // saglatiyoruz bu sayede olusturdumu
        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        // MarkerList
        setList();
    }

    private void setList() {
        clusterManager.addItem(new ClustersItem(42.648371, 21.166878, "Fakulteti Teknik", "Location of the exam"));
        clusterManager.addItem(new ClustersItem(42.653354, 21.164008, "Lagja e Studenteve", "Students hood"));
        clusterManager.addItem(new ClustersItem(42.657577, 21.163020, "Universiteti i Prishtines", "HQ of UP 'Hasan Prishtina'"));
        clusterManager.addItem(new ClustersItem(42.823891, 20.964670, "Vushtrri", "Current Location"));
    }
}
