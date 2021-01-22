package com.a1711060794.buitrunghieu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    Cursor curList = null;
    DBHelper helper = null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new DBHelper(this);

        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), Edit.class);
                intent.putExtra("name",helper.getcName(curList));
                intent.putExtra("id",helper.getcID(curList));
                intent.putExtra("motorbike",helper.getrType(curList));
                intent.putExtra("scooter",helper.getrType(curList));
                intent.putExtra("bike",helper.getrBike(curList));
                view.getContext().startActivity(intent);
            }
        });

        curList = helper.getAll();
        startManagingCursor(curList);
        HirusAdapter adapter = new HirusAdapter(curList);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        helper.close();
    }

    class HirusAdapter extends CursorAdapter {
        public HirusAdapter(Cursor c)
        {
            super(MainActivity.this, c);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // TODO Auto-generated method stub
            View row = view;
            ((TextView)row.findViewById(R.id.title)).
                    setText(helper.getcName(cursor));
            ((TextView)row.findViewById(R.id.content)).
                    setText(helper.getrBike(cursor));
            ImageView icon = (ImageView)row.findViewById(R.id.icon);
            String type = helper.getrType(cursor);
            if (type.equals("Motorbike"))
                icon.setImageResource(R.drawable.motorbike);
            else {
                icon.setImageResource(R.drawable.scooter);
            }
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // TODO Auto-generated method stub
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            return row;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.mAdd) {
            startActivity(new Intent(this, Add.class));
        }
        return true;
    }
}