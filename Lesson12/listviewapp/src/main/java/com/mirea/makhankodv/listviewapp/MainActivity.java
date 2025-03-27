package com.mirea.makhankodv.listviewapp;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        books = new ArrayList<>();
        // Добавляем книги в список
        books.add(new Book("Лев Толстой", "Война и мир"));
        books.add(new Book("Федор Достоевский", "Преступление и наказание"));
        books.add(new Book("Александр Пушкин", "Евгений Онегин"));
        books.add(new Book("Михаил Булгаков", "Мастер и Маргарита"));
        books.add(new Book("Антон Чехов", "Вишневый сад"));
        books.add(new Book("Иван Тургенев", "Отцы и дети"));
        books.add(new Book("Николай Гоголь", "Мертвые души"));
        books.add(new Book("Александр Грибоедов", "Горе от ума"));
        books.add(new Book("Иван Гончаров", "Обломов"));
        books.add(new Book("Михаил Лермонтов", "Герой нашего времени"));
        books.add(new Book("Александр Островский", "Гроза"));
        books.add(new Book("Николай Лесков", "Левша"));
        books.add(new Book("Александр Куприн", "Гранатовый браслет"));
        books.add(new Book("Иван Бунин", "Темные аллеи"));
        books.add(new Book("Александр Блок", "Двенадцать"));
        books.add(new Book("Сергей Есенин", "Анна Снегина"));
        books.add(new Book("Владимир Маяковский", "Облако в штанах"));
        books.add(new Book("Анна Ахматова", "Реквием"));
        books.add(new Book("Борис Пастернак", "Доктор Живаго"));
        books.add(new Book("Михаил Шолохов", "Тихий Дон"));
        books.add(new Book("Александр Солженицын", "Один день Ивана Денисовича"));
        books.add(new Book("Валентин Распутин", "Прощание с Матерой"));
        books.add(new Book("Виктор Астафьев", "Царь-рыба"));
        books.add(new Book("Василий Шукшин", "Калина красная"));
        books.add(new Book("Юрий Трифонов", "Дом на набережной"));
        books.add(new Book("Александр Вампилов", "Старший сын"));
        books.add(new Book("Василий Белов", "Привычное дело"));
        books.add(new Book("Федор Абрамов", "Братья и сестры"));
        books.add(new Book("Валентин Катаев", "Белеет парус одинокий"));
        books.add(new Book("Константин Паустовский", "Мещерская сторона"));
        books.add(new Book("Александр Твардовский", "Василий Теркин"));
        books.add(new Book("Михаил Зощенко", "Голубая книга"));
        books.add(new Book("Илья Ильф и Евгений Петров", "Двенадцать стульев"));
        books.add(new Book("Аркадий Гайдар", "Тимур и его команда"));
        books.add(new Book("Виктор Драгунский", "Денискины рассказы"));
        books.add(new Book("Николай Носов", "Незнайка на Луне"));
        books.add(new Book("Александр Волков", "Волшебник Изумрудного города"));
        books.add(new Book("Корней Чуковский", "Доктор Айболит"));
        books.add(new Book("Самуил Маршак", "Двенадцать месяцев"));
        books.add(new Book("Агния Барто", "Игрушки"));
        books.add(new Book("Сергей Михалков", "Дядя Степа"));

        ListView listView = findViewById(R.id.booksListView);
        BookAdapter adapter = new BookAdapter(this, R.layout.item_book, books);
        listView.setAdapter(adapter);
    }
} 