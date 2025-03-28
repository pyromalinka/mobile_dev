package com.mirea.makhankodv.resultapifragmentapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import com.mirea.makhankodv.resultapifragmentapp.R;

public class DataFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        Button button = view.findViewById(R.id.buttonOpenBottomSheet);
        button.setOnClickListener(click -> {
            String text = ((EditText) view.findViewById(R.id.editTextInfo)).getText().toString();
            Bundle bundle = new Bundle();
            bundle.putString("key", text);
            getParentFragmentManager()
                    .setFragmentResult("requestKey", bundle);
            BottomSheetFragment bottomSheet = new BottomSheetFragment();
            bottomSheet.show(getParentFragmentManager(),
                    "ModalBottomSheet");
        });
        return view;
    }
} 