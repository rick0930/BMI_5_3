package com.demo.android.bmi_basic;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class Abmi extends AppCompatActivity {

    private Button button_calc;
    private EditText field_feet;
    private EditText field_inch;
    private EditText field_weight;
    private TextView view_result;
    private TextView view_suggest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        findViews();
        setListeners();
    }

    private void findViews() {
        button_calc = (Button) findViewById(R.id.submit);
        field_feet = (EditText) findViewById(R.id.feet);
        field_inch = (EditText) findViewById(R.id.inch);
        field_weight = (EditText) findViewById(R.id.weight);
        view_result = (TextView) findViewById(R.id.result);
        view_suggest = (TextView) findViewById(R.id.suggest);
    }

    private void setListeners() {
        button_calc.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                double BMI = calcBMI();
                showResult(BMI);
            } catch (Exception e) {
                Toast.makeText(Abmi.this, getString(R.string.input_error),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    private double calcBMI() {
        double height = (Double.parseDouble(field_feet.getText() + "") * 12 +
                Double.parseDouble(field_inch.getText().toString())) * 2.54 / 100;
        double weight = Double.parseDouble(field_weight.getText() + "") * 0.45359;
        return weight / (height * height);
    }

    private void showResult(double BMI) {
        DecimalFormat nf = new DecimalFormat("0.00");
        view_result.setText(getText(R.string.bmi_result) + nf.format(BMI));

        //Give health advice
        if (BMI > 27) {
            view_suggest.setText(R.string.advice_fat);
        } else if (BMI > 25) {
            view_suggest.setText(R.string.advice_heavy);
        } else if (BMI < 20) {
            view_suggest.setText(R.string.advice_light);
        } else {
            view_suggest.setText(R.string.advice_average);
        }
    }

    protected static final int MENU_ABOUT = Menu.FIRST;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_ABOUT, 0, R.string.about_label);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case MENU_ABOUT:
                openOptionsDialog();
                break;
        }
        return true;
    }

    private void openOptionsDialog() {
        new AlertDialog.Builder(this)
            .setTitle(R.string.about_title)
            .setMessage(R.string.about_msg)
            .setPositiveButton(R.string.ok_label,
                new DialogInterface.OnClickListener() {
                    public void onClick(
                        DialogInterface dialoginterface, int i) {
                    }
                })
            .show();
    }

}
