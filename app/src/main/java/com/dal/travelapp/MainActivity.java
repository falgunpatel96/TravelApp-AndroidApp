package com.dal.travelapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText depart_date, destination_date;
    AutoCompleteTextView depart_plc, destination_plc;
    String ticket_type;
    private int adult_count = 1, child_count, infant_count, day, month, year, LAUNCH_SECOND_ACTIVITY = 1;
    Button search, adult_mins, adult_pls, child_mins, child_pls, infant_mins, infant_pls;
    TextView adult_cnt, child_cnt, infant_cnt;
    RadioGroup radioGroup;
    RadioButton radioButtonRoundTrip, radioButtonOneWay;

    private String[] countries = {"India","Pakistan","Australia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        depart_plc = (AutoCompleteTextView) findViewById(R.id.depart_plc);
        destination_plc = (AutoCompleteTextView) findViewById(R.id.destination_plc);
        depart_date = (EditText) findViewById(R.id.depart_date);
        destination_date = (EditText) findViewById(R.id.destination_date);
        adult_mins = (Button) findViewById(R.id.adult_mins);
        adult_pls = (Button) findViewById(R.id.adult_pls);
        child_mins = (Button) findViewById(R.id.child_mins);
        child_pls = (Button) findViewById(R.id.child_pls);
        infant_mins = (Button) findViewById(R.id.infant_mins);
        infant_pls = (Button) findViewById(R.id.infant_pls);
        adult_cnt = (TextView) findViewById(R.id.adult_cnt);
        child_cnt = (TextView) findViewById(R.id.child_cnt);
        infant_cnt = (TextView) findViewById(R.id.infant_cnt);
        search = (Button) findViewById(R.id.search);
        radioGroup = (RadioGroup) findViewById(R.id.linType);
        radioButtonRoundTrip = (RadioButton) findViewById(R.id.linTypeRound);
        radioButtonOneWay = (RadioButton) findViewById(R.id.linTypeOneway);
        ticket_type = radioButtonOneWay.getText().toString();

        adult_cnt.setText(String.valueOf(adult_count));
        child_cnt.setText(String.valueOf(child_count));
        infant_cnt.setText(String.valueOf(infant_count));

        Intent i =getIntent();
        destination_plc.setText(i.getStringExtra("destination"));


        adult_mins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adult_count > 1) {
                    adult_cnt.setText(String.valueOf(--adult_count));
                }
            }
        });

        adult_pls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adult_cnt.setText(String.valueOf(++adult_count));
            }
        });

        child_mins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (child_count > 0) {
                    child_cnt.setText(String.valueOf(--child_count));
                }
            }
        });

        child_pls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_cnt.setText(String.valueOf(++child_count));
            }
        });

        infant_mins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (infant_count > 0) {
                    infant_cnt.setText(String.valueOf(--infant_count));
                }
            }
        });

        infant_pls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infant_cnt.setText(String.valueOf(++infant_count));
            }
        });

        depart_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar today = Calendar.getInstance();
                Calendar prev_date = Calendar.getInstance();
                if (depart_date.getText().equals("")) {
                    prev_date = today;
                } else {
                    try {
                        prev_date.setTime(new SimpleDateFormat("dd MMM, yy").parse(depart_date.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        prev_date = today;
                    }
                }
                year = prev_date.get(Calendar.YEAR);
                month = prev_date.get(Calendar.MONTH);
                day = prev_date.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yy");
                        Calendar dat = Calendar.getInstance();
                        dat.set(year, month, dayOfMonth);
                        depart_date.setText(sdf.format(dat.getTime()));
                        try {
                            if(!destination_date.getText().toString().equals("") && dat.getTimeInMillis() > new SimpleDateFormat("dd MMM, yy").parse(destination_date.getText().toString()).getTime())
                            {
                                destination_date.setText("");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        //depart_date.onFocus

        destination_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonRoundTrip.setChecked(true);

                final Calendar today = Calendar.getInstance();
                Calendar prev_date = Calendar.getInstance();
                if (depart_date.getText().equals("")) {
                    prev_date = today;
                } else {
                    try {
                        prev_date.setTime(new SimpleDateFormat("dd MMM, yy").parse(depart_date.getText().toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                year = prev_date.get(Calendar.YEAR);
                month = prev_date.get(Calendar.MONTH);
                day = prev_date.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yy");
                        Calendar dat = Calendar.getInstance();
                        dat.set(year, month, dayOfMonth);
                        destination_date.setText(sdf.format(dat.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(prev_date.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,countries);
        depart_plc.setThreshold(1);
        depart_plc.setAdapter(adapter);
        destination_plc.setThreshold(1);
        destination_plc.setAdapter(adapter);

        depart_plc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //closeKeyboard();
                //depart_plc.onEditorAction(EditorInfo.IME_ACTION_DONE);
                depart_plc.showDropDown();
                return false;
            }
        });

        depart_plc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    //depart_plc.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    closeKeyboard();
                }
            }
        });

        destination_plc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                destination_plc.showDropDown();
                return false;
            }
        });

        radioButtonOneWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destination_date.setText("");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioGroup.getCheckedRadioButtonId() == radioButtonRoundTrip.getId())
                {
                    ticket_type = radioButtonRoundTrip.getText().toString();
                }

                Intent i = new Intent(MainActivity.this,MyBookings.class);
                i.putExtra("adult_no",adult_count);
                i.putExtra("child_no",child_count);
                i.putExtra("infant_no",infant_count);
                i.putExtra("depart_plc",depart_plc.getText().toString());
                i.putExtra("destin_plc",destination_plc.getText().toString());
                i.putExtra("depart_date",depart_date.getText().toString());
                Log.e("Departure Place",depart_plc.getText().toString());
                Log.e("Destination Place",destination_plc.getText().toString());
//                Toast.makeText(getApplicationContext(),depart_date.getText().toString(),Toast.LENGTH_LONG).show();
                startActivity(i);
            }
        });

        //destination_plc.setText((destination_plc.getTextSize()/(this.getResources().getDisplayMetrics().scaledDensity))+"");
    }

    private void closeKeyboard()
    {
        View view = this.getCurrentFocus();
        if(view != null)
        {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionlist_menu,menu);

        MenuItem itemLogInOut = menu.findItem(R.id.login_out);
        MenuItem username = menu.findItem(R.id.username);
        if (Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false"))) == true)
        {
            itemLogInOut.setTitle("Log Out");
            String name = Save.readEmail(getApplicationContext());
            username.setTitle("Hello "+name.split("@")[0]);
        }
        else
        {
            username.setTitle("Hello User");
            itemLogInOut.setTitle("Log In");
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        this.invalidateOptionsMenu();
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.login_out:
                if(item.getTitle().equals("Log In"))
                {
                    Intent intent = new Intent(this,Login.class);
                    startActivity(intent);
                }
                else
                {
                    Save.delete(this);
                }
                return true;
            case R.id.my_tickets:
                Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

                //Toast.makeText(this,""+logged_in,Toast.LENGTH_LONG).show();
                if(!logged_in)
                {
                    Toast.makeText(this,"User not Logged In!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
                    //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                    //Log.d("bol",logged_in+"");
                }
                else
                {
                    Intent intent1 = new Intent(this,MyTickets.class);
                    startActivity(intent1);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LAUNCH_SECOND_ACTIVITY)
        {
            Boolean logged_in = Save.readStatus(getApplicationContext(), new Boolean("false"));

            if (resultCode == Activity.RESULT_OK && logged_in)
            {
                Intent intent1 = new Intent(this,MyTickets.class);
                startActivity(intent1);
            }
            else if (resultCode == Activity.RESULT_CANCELED || !logged_in)
            {
                Toast.makeText(this,"User not Logged In!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivityForResult(intent,LAUNCH_SECOND_ACTIVITY);
                //logged_in = Boolean.valueOf(Save.readStatus(getApplicationContext(), new Boolean("false")));
                //Log.d("bol",logged_in+"");
            }
        }
    }
}
