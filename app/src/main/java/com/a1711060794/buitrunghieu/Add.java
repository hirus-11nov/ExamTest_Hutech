package com.a1711060794.buitrunghieu;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Add extends AppCompatActivity {
    EditText addName, addID;
    Button add;
    RadioGroup types;
    Cursor curList = null;
    DBHelper helper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        helper = new DBHelper(this);
        curList = helper.getAll();

        Hirus hirus = new Hirus();

        addID = findViewById(R.id.addID);
        add = findViewById(R.id.buttonAdd);
        addName = findViewById(R.id.addName);
        types = findViewById(R.id.types);

        Cursor cursor;
        cursor = helper.getReadableDatabase().rawQuery("SELECT _id FROM bike",null);
        Integer id = cursor.getCount() + 1;

        addID.setText(String.format("CID-%s",id));
        Spinner addSpinner = findViewById(R.id.addSpinner);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getcName = addName.getText().toString();
                hirus.setcName(getcName);

                String getID = addID.getText().toString();
                hirus.setcID(getID);

                switch (types.getCheckedRadioButtonId()) {
                    case R.id.typeMotor:
                        hirus.setrType("Motorbike");
                        break;
                    case R.id.typeScooter:
                        hirus.setrType("Scooter");
                        break;
                    default:
                        break;
                }

                String getrBike = addSpinner.getSelectedItem().toString();
                hirus.setrBike(getrBike);

                Cursor cur;
                cur = helper.getReadableDatabase().rawQuery("SELECT rBike FROM bike WHERE rBike =?", new String[]{getrBike});
                if (cur.getCount() > 0)
                {
                    Toast.makeText(Add.this, "Xe này đã được thuê!", Toast.LENGTH_SHORT).show();
                    return;
                }

                helper.addData(hirus.getcName(),hirus.getcID(),hirus.getrType(), hirus.getrBike());
                curList.requery();
                Toast.makeText(Add.this, "Added!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
            }
        });
    }
}