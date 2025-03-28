package com.mirea.makhankodv.retrofitapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private static final String TAG = "TodoAdapter";
    private final LayoutInflater layoutInflater;
    private final List<Todo> todos;
    private final ApiService apiService;

    public TodoAdapter(Context context, List<Todo> todoList, ApiService apiService) {
        this.layoutInflater = LayoutInflater.from(context);
        this.todos = todoList;
        this.apiService = apiService;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.textViewTitle.setText(todo.getTitle());
        holder.textViewId.setText("Task #" + todo.getId());
        holder.checkBoxCompleted.setChecked(todo.getCompleted());

        // Загрузка изображения с помощью Picasso
        String imageUrl = "https://picsum.photos/200/200?random=" + todo.getId();
        Picasso.get()
                .load(imageUrl)
                .resize(200, 200)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.imageViewTodo);

        holder.checkBoxCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                todo.setCompleted(isChecked);
                updateTodo(todo);
            }
        });
    }

    private void updateTodo(Todo todo) {
        Call<Todo> call = apiService.updateTodo(todo.getId(), todo);
        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Todo обновлен успешно");
                } else {
                    Log.e(TAG, "Ошибка при обновлении Todo: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Log.e(TAG, "Ошибка при выполнении запроса: " + t.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
} 