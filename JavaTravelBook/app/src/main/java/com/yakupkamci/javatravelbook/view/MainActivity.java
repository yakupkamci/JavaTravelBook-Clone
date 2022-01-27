package com.yakupkamci.javatravelbook.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Database;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.yakupkamci.javatravelbook.R;
import com.yakupkamci.javatravelbook.adaptor.PlaceAdaptor;
import com.yakupkamci.javatravelbook.databinding.ActivityMainBinding;
import com.yakupkamci.javatravelbook.model.Place;
import com.yakupkamci.javatravelbook.roomdb.PlaceDao;
import com.yakupkamci.javatravelbook.roomdb.PlaceDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    ArrayList<Place> places;
    PlaceDatabase db;
    PlaceDao placeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        places = new ArrayList<>();
        db = Room.databaseBuilder(getApplicationContext(),PlaceDatabase.class,"Places").build();
        placeDao = db.placeDao();

        compositeDisposable.add(placeDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));

    }

    private void handleResponse(List<Place> places) {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PlaceAdaptor placeAdaptor = new PlaceAdaptor(places);
        binding.recyclerView.setAdapter(placeAdaptor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.travel_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_travel){
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("Info","new");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}