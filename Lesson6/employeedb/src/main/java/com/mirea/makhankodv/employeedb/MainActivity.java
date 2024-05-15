package com.mirea.makhankodv.employeedb;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mirea.makhankodv.employeedb.databinding.ActivityMainBinding;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private HeroDao heroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        heroDao = App.getInstance().getDatabase().employeeDao();

        binding.addButton.setOnClickListener(v -> addHero());
        binding.updateButton.setOnClickListener(v -> updateHero());
        binding.deleteButton.setOnClickListener(v -> deleteHero());
        binding.loadButton.setOnClickListener(v -> loadHeroes());
    }

    private void addHero() {
        String name = binding.nameInput.getText().toString();
        String power = binding.powerInput.getText().toString();

        if (name.isEmpty() || power.isEmpty()) {
            showToast("Name and Power cannot be empty");
            return;
        }

        Hero hero = new Hero();
        hero.name = name;
        hero.power = power;

        heroDao.insert(hero);
        showToast("Hero added");
    }

    private void updateHero() {
        String idStr = binding.idInput.getText().toString();
        String name = binding.nameInput.getText().toString();
        String power = binding.powerInput.getText().toString();

        if (idStr.isEmpty() || name.isEmpty() || power.isEmpty()) {
            showToast("ID, Name, and Power cannot be empty");
            return;
        }

        long id = Long.parseLong(idStr);

        Hero hero = heroDao.getById(id);
        if (hero != null) {
            hero.name = name;
            hero.power = power;
            heroDao.update(hero);
            showToast("Hero updated");
        } else {
            showToast("Hero not found");
        }
    }

    private void deleteHero() {
        String idStr = binding.idInput.getText().toString();

        if (idStr.isEmpty()) {
            showToast("ID cannot be empty");
            return;
        }

        long id = Long.parseLong(idStr);
        Hero hero = heroDao.getById(id);
        if (hero != null) {
            heroDao.delete(hero);
            showToast("Hero deleted");
        } else {
            showToast("Hero not found");
        }
    }

    private void loadHeroes() {
        List<Hero> heroes = heroDao.getAll();
        for (Hero hero : heroes) {
            Log.d("HeroDB", hero.id + ": " + hero.name + " - " + hero.power);
        }
        showToast("Heroes loaded, check log for details");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}