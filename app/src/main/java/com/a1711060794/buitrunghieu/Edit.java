package com.a1711060794.buitrunghieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends AppCompatActivity {
    EditText editName, editID, edtDate;
    Button buttonEdit, buttonDelete, buttonCal;
    RadioGroup types;
    TextView txtName, txtBike, txtTotal;
    RadioButton motor, scooter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        DBHelper helper = new DBHelper(this);
        Hirus hirus = new Hirus();

        edtDate =findViewById(R.id.edtDate);
        motor = findViewById(R.id.motor);
        scooter = findViewById(R.id.scooter);
        txtTotal = findViewById(R.id.txtTotal);
        txtBike =findViewById(R.id.txtBike);
        txtName = findViewById(R.id.txtName);
        buttonCal = findViewById(R.id.buttonCal);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);
        editName = findViewById(R.id.editName);
        editID = findViewById(R.id.editID);
        types = findViewById(R.id.editTypes);
        Cursor curList = helper.getAll();

        Spinner editSpinner = findViewById(R.id.editSpinner);

        Intent data = getIntent();
        editName.setText(data.getStringExtra("name"));
        editID.setText(data.getStringExtra("id"));

        if(data.getStringExtra("motorbike").equals("Motorbike")){
            types.check(R.id.motor);
        }
        else if (data.getStringExtra("scooter").equals("Scooter")){
            types.check(R.id.scooter);
        }
        String compareValue = data.getStringExtra("bike");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.list_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        editSpinner.setAdapter(adapter);

        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            editSpinner.setSelection(spinnerPosition);
        }

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getcName = editName.getText().toString();
                hirus.setcName(getcName);

                String getcID = editID.getText().toString();
                hirus.setcID(getcID);
                switch (types.getCheckedRadioButtonId()) {
                    case R.id.motor:
                        hirus.setrType("Motorbike");
                        break;
                    case R.id.scooter:
                        hirus.setrType("Scooter");
                        break;
                    default:
                        break;
                }

                String getrBike = editSpinner.getSelectedItem().toString();
                hirus.setrBike(getrBike);

//                Cursor cur;
//                cur = helper.getReadableDatabase().rawQuery("SELECT rBike FROM bike WHERE rBike =?", new String[]{getrBike});
//                if (cur.getCount() > 0)
//                {
//                    Toast.makeText(Edit.this, "Xe này đã được thuê!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                helper.updateData(hirus.getcName(),hirus.getcID(), hirus.getrType(), hirus.getrBike());
                curList.requery();
                Toast.makeText(Edit.this, "Đã cập nhật!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getcID = editID.getText().toString();
                hirus.setcID(getcID);
                helper.deleteData(hirus.getcID());
                Toast.makeText(Edit.this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                onBackPressed();
                finish();
            }
        });
        buttonCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtName.setText(data.getStringExtra("name"));
                String getBike = edtDate.getText().toString();
                Integer date = Integer.parseInt(edtDate.getText().toString());
                txtBike.setText(String.format("%s - %s Ngày", data.getStringExtra("bike"),getBike));
                int mo = 100000;
                int sc = 200000;
                Integer totalMo = mo * date;
                Integer totalSc = sc * date;

                if(motor.isChecked()){
                   txtTotal.setText(String.format("Tổng cộng: %s", String.valueOf(totalMo)));
                }
                else {
                    txtTotal.setText(String.format("Tổng cộng: %s", String.valueOf(totalSc)));
                }
            }
        });
    }
}