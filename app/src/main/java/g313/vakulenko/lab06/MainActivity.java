package g313.vakulenko.lab06;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Note> adp;
    int sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Созд. массива
        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);
        ListView lst = findViewById(R.id.ListV);
        lst.setAdapter(adp);

        // Слуш. для обработки кликов
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sel = position;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            // Получение данных, возвращаемых из редактирования заметки
            int pos = data.getIntExtra("my-note-index", -1);
            if (pos != -1) {
                // Если позиция корректная -> обновление данных в списке
                String title = data.getStringExtra("my-note-title");
                String content = data.getStringExtra("my-note-content");

                Note n = adp.getItem(pos);
                n.title = title;
                n.content = content;

                adp.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void butNew_click(View v) {
        Note n = new Note();
        n.title = "New note";
        n.content = "Some content";

        // Добавление новой заметки + полусение позиции
        adp.add(n);
        int pos = adp.getPosition(n);

        // Запуск активность редактирования заметки с передачей данных
        Intent i = new Intent(this, MainActivity2.class);
        i.putExtra("my-note-index", pos);
        i.putExtra("my-note-title", n.title);
        i.putExtra("my-note-content", n.content);

        startActivityForResult(i, 12345);
    }

    public void butEdit_click(View v) {
        if (sel != -1) {
            // Редактирование
            Note selectedNote = adp.getItem(sel);

            Intent i = new Intent(this, MainActivity2.class);
            i.putExtra("my-note-index", sel);
            i.putExtra("my-note-title", selectedNote.title);
            i.putExtra("my-note-content", selectedNote.content);

            startActivityForResult(i, 12345);
        }
    }

    public void butDel_click(View v) {
        if (sel != -1) {
            // Создание диал. окна
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Note");
            builder.setMessage("Вы уверены, что хотите удалить заметку?");
            builder.setPositiveButton("Да", (dialog, which) -> {
                // Удаление заметки, обновл. адаптера
                adp.remove(adp.getItem(sel));
                adp.notifyDataSetChanged();
            });
            builder.setNegativeButton("Нет", (dialog, which) -> {
            });
            builder.show();
        }
    }
}