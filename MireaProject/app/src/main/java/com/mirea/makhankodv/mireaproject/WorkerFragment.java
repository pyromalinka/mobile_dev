package com.mirea.makhankodv.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.mirea.makhankodv.mireaproject.databinding.FragmentWorkerBinding;

public class WorkerFragment extends Fragment {

    private FragmentWorkerBinding binding;

    public WorkerFragment() {
        // Required empty public constructor
    }

    public static WorkerFragment newInstance() {
        return new WorkerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWorkerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.solveButton.setOnClickListener(v -> {
            String aStr = binding.aInput.getText().toString();
            String bStr = binding.bInput.getText().toString();
            String cStr = binding.cInput.getText().toString();

            if (!aStr.isEmpty() && !bStr.isEmpty() && !cStr.isEmpty()) {
                try {
                    int a = Integer.parseInt(aStr);
                    int b = Integer.parseInt(bStr);
                    int c = Integer.parseInt(cStr);

                    String equation = formatCoefficient(a) + "x² " + formatTerm(b) + "x " + formatTerm(c) + " = 0";
                    binding.equationText.setText(equation);

                    Data inputData = new Data.Builder()
                            .putInt("a", a)
                            .putInt("b", b)
                            .putInt("c", c)
                            .build();

                    OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(BackgroundTaskWorker.class)
                            .setInputData(inputData)
                            .build();

                    WorkManager.getInstance(requireContext()).enqueue(workRequest);

                    WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(workRequest.getId())
                            .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                                @Override
                                public void onChanged(WorkInfo workInfo) {
                                    if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                                        String result = workInfo.getOutputData().getString("result");
                                        binding.resultText.setText(result);
                                    }
                                }
                            });
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String formatCoefficient(int coefficient) {
        return (coefficient < 0) ? "(" + coefficient + ")" : String.valueOf(coefficient);
    }

    private String formatTerm(int coefficient) {
        if (coefficient > 0) {
            return "+ " + coefficient;
        } else if (coefficient < 0) {
            return "- " + Math.abs(coefficient);
        } else {
            return "";
        }
    }
}
