package com.zju.personinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_com = findViewById(R.id.btn_commit);
        final EditText txt_result = findViewById(R.id.txt_muti_result);
        final EditText txt_name = findViewById(R.id.txt_name);
        final EditText txt_phone = findViewById(R.id.txt_phn);
        final EditText txt_age = findViewById(R.id.txt_age);
        final CheckBox cbox_stu = findViewById(R.id.cbox_stu);

        btn_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("PersonInfo", "Commit");
                String result;
                result = "Name:\n" + txt_name.getText() +
                        "\nAge:\n" + txt_age.getText() +
                        "\nPhone:\n" + txt_phone.getText();
                if(cbox_stu.isChecked())
                {
                    result += "\nThis person is a student.";
                }
                else
                {
                    result += "\nThis person is not a student.";
                }
                txt_result.setText(result);
                Clear(txt_name, txt_phone, txt_age);
            }
        });

    }
    public void Clear(EditText txt_name, EditText txt_phone, EditText txt_age )
    {
        Log.d("PersonInfo", "Clear");
        txt_age.setText("");
        txt_name.setText("");
        txt_phone.setText("");
    }

}
