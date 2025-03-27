package com.mirea.makhankodv.listviewapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private LayoutInflater inflater;
    private int layout;
    private List<Book> books;

    public BookAdapter(Context context, int resource, List<Book> books) {
        super(context, resource, books);
        this.books = books;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Book book = books.get(position);
        viewHolder.authorView.setText(book.getAuthor());
        viewHolder.bookView.setText(book.getTitle());

        return convertView;
    }

    private static class ViewHolder {
        final TextView authorView;
        final TextView bookView;

        ViewHolder(View view) {
            authorView = view.findViewById(R.id.textAuthor);
            bookView = view.findViewById(R.id.textBook);
        }
    }
} 