package com.example.programminglanguage;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.programminglanguage.adapter.LogoListAdapter;
import com.example.programminglanguage.db.LogoDbHelper;
import com.example.programminglanguage.model.LogoItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LogoDbHelper mHelper;
    private SQLiteDatabase mDb;

    private ArrayList<LogoItem> mLogoItemList = new ArrayList<>();
    private LogoListAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new LogoDbHelper(this);
        mDb = mHelper.getReadableDatabase();

        loadDataFromDb();

        mAdapter = new LogoListAdapter(
                this,
                R.layout.item,
                mLogoItemList
        );

        ListView lv = findViewById(R.id.list_view_logo);
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                LogoItem item = mLogoItemList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailLogoActivity.class);
                intent.putExtra("title", mLogoItemList.get(position).title);
                intent.putExtra("picture", mLogoItemList.get(position).picture);
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                String[] items = new String[]{"แก้ไขข้อมูล", "ลบข้อมูล"};

                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) { // แก้ไขข้อมูล
                            LogoItem item = mLogoItemList.get(position);
                            int LogoId = item.id;

                            ContentValues cv = new ContentValues();
                            cv.put(LogoDbHelper.COL_TITLE, "HTML");

                            mDb.update(
                                    LogoDbHelper.TABLE_NAME,
                                    cv,
                                    LogoDbHelper.COL_ID + "=?",
                                    new String[]{String.valueOf(LogoId)}
                            );
                            loadDataFromDb();
                            mAdapter.notifyDataSetChanged();

                        } else if (i == 1) { // ลบข้อมูล
                            LogoItem item = mLogoItemList.get(position);
                            int LogoId = item.id;

                            mDb.delete(
                                    LogoDbHelper.TABLE_NAME,
                                    LogoDbHelper.COL_ID + "=?",
                                    new String[]{String.valueOf(LogoId)}
                            );
                            loadDataFromDb();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                dialog.show();
                return true;

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivityForResult(intent, 123);

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            if (resultCode == RESULT_OK) {
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    private void loadDataFromDb() {
        Cursor cursor = mDb.query(
                LogoDbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        mLogoItemList.clear();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(LogoDbHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(LogoDbHelper.COL_TITLE));
            String picture = cursor.getString(cursor.getColumnIndex(LogoDbHelper.COL_PICTURE));

            LogoItem item = new LogoItem(id, title, picture);
            mLogoItemList.add(item);
        }
    }
}
