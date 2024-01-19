package g313.vakulenko.lab06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    // Объяв. элем. интерфейса
    EditText editTitle;
    EditText editContent;

    // Перем. для хран. позиции редактируемой заметки
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Связ. элементов интерфейса с их представлениями в макете
        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);

        Intent i = getIntent();
        // Извлечение данных
        pos = i.getIntExtra("my-note-index", -1);
        editTitle.setText(i.getStringExtra("my-note-title"));
        editContent.setText(i.getStringExtra("my-note-content"));
    }

    public void cancelClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }


    public void saveClick(View v) {
        Intent i = new Intent();
        // Добавление данных
        i.putExtra("my-note-index", pos);
        i.putExtra("my-note-title", editTitle.getText().toString());
        i.putExtra("my-note-content", editContent.getText().toString());

        // Устанавливка результата, закрытие
        setResult(RESULT_OK, i);
        finish();
    }
}