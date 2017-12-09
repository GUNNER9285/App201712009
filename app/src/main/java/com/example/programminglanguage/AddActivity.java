package com.example.programminglanguage;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.programminglanguage.db.LogoDbHelper;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final EditText editText = findViewById(R.id.edit);
        Button btn = findViewById(R.id.btn);



       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               LogoDbHelper dbHelper = new LogoDbHelper(AddActivity.this);
               SQLiteDatabase db = dbHelper.getWritableDatabase();
               ContentValues cv = new ContentValues();
               String title = String.valueOf(editText.getText());
               cv.put(LogoDbHelper.COL_TITLE, title);
               cv.put(LogoDbHelper.COL_PICTURE, "html.png");
               db.insert(LogoDbHelper.TABLE_NAME, null, cv);

               Intent intent = new Intent();
               setResult(RESULT_OK, intent);
               finish();
           }
       });



    }
}
