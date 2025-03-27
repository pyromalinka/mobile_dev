package com.mirea.makhankodv.recyclerviewapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Country> countries = getListData();
        RecyclerView recyclerView = this.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new CountryRecyclerViewAdapter(countries));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<Country> getListData() {
        List<Country> list = new ArrayList<Country>();
        Country chile = new Country("Chile", "flag_cl_svgrepo_com", 19458000);
        Country czechRepublic = new Country("Czech Republic", "flag_cz_svgrepo_com", 10700000);
        Country scotland = new Country("Scotland", "flag_gb_sct_svgrepo_com", 5500000);
        Country greece = new Country("Greece", "flag_gr_svgrepo_com", 10678000);
        Country kiribati = new Country("Kiribati", "flag_ki_svgrepo_com", 119000);
        Country macedonia = new Country("North Macedonia", "flag_mk_svgrepo_com", 2065000);
        Country panama = new Country("Panama", "flag_pa_svgrepo_com", 4314000);
        Country puertoRico = new Country("Puerto Rico", "flag_pr_svgrepo_com", 3194000);
        Country russia = new Country("Russia", "flag_ru_svgrepo_com", 146000000);

        list.add(chile);
        list.add(czechRepublic);
        list.add(scotland);
        list.add(greece);
        list.add(kiribati);
        list.add(macedonia);
        list.add(panama);
        list.add(puertoRico);
        list.add(russia);

        return list;
    }
} 