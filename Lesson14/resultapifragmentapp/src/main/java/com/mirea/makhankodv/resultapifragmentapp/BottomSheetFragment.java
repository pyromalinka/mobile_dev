package com.mirea.makhankodv.resultapifragmentapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentResultListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mirea.makhankodv.resultapifragmentapp.R;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    private TextView textViewResult;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String text = bundle.getString("key");
                Log.d(BottomSheetFragment.class.getSimpleName(), "Get text " + text);
                if (textViewResult != null) {
                    textViewResult.setText(text);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        textViewResult = view.findViewById(R.id.textViewResult);
        return view;
    }
} 