package cn.jike1501_36.androidUI;


import android.content.SharedPreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import cn.jike1501_36.R;
/**
 * Created by DoonZhang on 2018/6/19.
 */
public class SettingActivity extends AppCompatActivity {
    private EditText editText;
    int recallTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        editText = (EditText) findViewById(R.id.editText);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("recallTime", MODE_PRIVATE);
        int recallTime = sp.getInt("recallTime", 10);//设置重拨时间，默认为10秒
        editText.setText(recallTime + "");
    }

    public void sure(View view) {
        recallTime = Integer.parseInt(editText.getText().toString().trim());
        SharedPreferences sp = getSharedPreferences("recallTime", MODE_PRIVATE);
        //拿到sp的编辑器
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("recallTime", recallTime);
        //提交
        ed.apply();
        Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
        finish();
    }

}
