package com.tho.mapprippree;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


public class RegisActivity extends Activity {
    // private static final String TAG = Register.class.getSimpleName();
    private String url = "http://tho.ddns.net:2407/prippree/",career,interest,language,group,genderG = "male";
    private FunctionLib link;
    private Spinner inputSpCareer,inputSpInterest,inputSpLanguage;
    int[] id_group;
    private Button btnRegister;
    private Button btnClear;
    private EditText inputUserName;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputAge;
    private RadioGroup inputGender;
    private SessionManager session;
    private String keyString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_-0123456789";
    private char[] keyCheck = keyString.toCharArray();

    // private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        link = new FunctionLib();
        inputUserName = (EditText) findViewById(R.id.txtUsername);
        inputEmail = (EditText) findViewById(R.id.txtEmail);
        inputPassword = (EditText) findViewById(R.id.txtPassword);
        inputAge = (EditText) findViewById(R.id.txtAge);
        inputGender = (RadioGroup) findViewById(R.id.rbgGender);
        inputSpCareer = (Spinner) findViewById(R.id.spinCareer);
        inputSpInterest = (Spinner) findViewById(R.id.spinInterest);
        inputSpLanguage = (Spinner) findViewById(R.id.spinLanguage);


        btnRegister = (Button) findViewById(R.id.btnSummit);
        btnClear = (Button) findViewById(R.id.btnReset);


        // int selectedId = inputGender.getCheckedRadioButtonId();
        // inputGenderBT = (RadioButton) findViewById(selectedId);

        // Progress dialog
        // pDialog = new ProgressDialog(this);
        // pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        // db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisActivity.this, Main.class);
            startActivity(intent);
            finish();
        }

        radioController();
        setSpinData();
        setupButton();

        ///
    }//end Oncreate.

    // ///////////////////////////////

    // //////////////////////////////

    // /////////////////////////////


    // ////////////////////////////////////



    private void setSpinData()
    {
        try {
            String url = "http://tho.ddns.net:2407/prippree/";
            String paramiter = "index.php?tag=regis_detail";
            Log.e("detail register ", "" + url + paramiter);
            String resultServerg = link.getJSONUrl(paramiter);
			//Toast.makeText(getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
            Log.d("GET_Data_WEB_HOST", resultServerg);
            try{
                JSONObject c = new JSONObject(resultServerg);
                boolean error = c.getBoolean("error");
                int max_data = 0;
                final int[] DataIdL,DataIdI,DataIdC;
                String[] DataTitleL,DataTitleI,DataTitleC;
                JSONArray place_data;
                if(!error){

                    //set Language
                    place_data = c.getJSONArray("datalanguage");
                    Log.e("palce_data JSON L"," "+place_data);
                    max_data = place_data.length();
                    DataTitleL = new String[max_data];
                    DataIdL = new int[max_data];

                    for (int i = 0; i < max_data; i++) {
                        JSONObject jsonObj = place_data.getJSONObject(i);
                        DataIdL[i] = jsonObj.getInt("id_language");
                        DataTitleL[i] = jsonObj.getString("name_language");
                    }

                    inputSpLanguage.setAdapter(new MySpinAdapter(this, DataTitleL, DataIdL, R.id.spinLanguage));
                    inputSpLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            language = String.valueOf(DataIdL[i]);
                            Log.e("Reg lang", "l = " + language);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
//                    inputSpLanguage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            //Toast.makeText(getApplicationContext(), "id_language = " + DataIdL[i] + "name_language = " + DataTitleL[i], Toast.LENGTH_LONG).show();
//                            language = inputSpLanguage.getSelectedItem().toString();
//                            Log.e("Reg lang", "l = " + language);
//                        }
//                    });
                    Log.e("spin", "end 1");

                    //set Interest
                    place_data = c.getJSONArray("datainterest");
                    Log.e("palce_data JSON I"," "+place_data);
                    max_data = place_data.length();
                    DataTitleI = new String[max_data];
                    DataIdI = new int[max_data];

                    for (int i = 0; i < max_data; i++) {
                        JSONObject jsonObj = place_data.getJSONObject(i);

                        DataIdI[i] = jsonObj.getInt("id_interest");
                        DataTitleI[i] = jsonObj.getString("name_interest");

                    }
                    inputSpInterest.setAdapter(new MySpinAdapter(this, DataTitleI, DataIdI, R.id.spinInterest));
                    inputSpInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            interest = String.valueOf(DataIdI[i]);
                            Log.e("Reg interest", "i = " + interest);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

//                    inputSpInterest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            //Toast.makeText(getApplicationContext(),"id_interest = "+DataIdI[i]+"name_interest = "+DataTitleI[i], Toast.LENGTH_LONG).show();
//                            interest = inputSpInterest.getSelectedItem().toString();
//                            Log.e("Reg lang", "i = " + interest);
//                        }
//                    });
                    Log.e("spin", "end 2");

                    //set Career
                    place_data = c.getJSONArray("datacareer");
                    Log.e("palce_data JSON C"," "+place_data);
                    max_data = place_data.length();
                    DataTitleC = new String[max_data];
                    DataIdC = new int[max_data];
                    id_group = new int[max_data];

                    for (int i = 0; i < max_data; i++) {
                        JSONObject jsonObj = place_data.getJSONObject(i);

                        DataIdC[i] = jsonObj.getInt("id_career");
                        DataTitleC[i] = jsonObj.getString("name_career");
                        id_group[i] = jsonObj.getInt("id_group");

                    }
                    inputSpCareer.setAdapter(new MySpinAdapter(this, DataTitleC, DataIdC, R.id.spinCareer));
                    inputSpCareer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            career = String.valueOf(DataIdC[i]);
                            group = String.valueOf(id_group[i]);
                            Log.e("Reg career", "c = " + career + " g = " + group);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
//                    inputSpCareer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                            //Toast.makeText(getApplicationContext(),"id_career = "+DataIdC[i]+"name_career = "+DataTitleC[i]+"id_group = "+id_group[i], Toast.LENGTH_LONG).show();
//                            career = inputSpCareer.getSelectedItem().toString();
//                            group = String.valueOf(id_group[i]);
//                            Log.e("Reg lang", "c = " + career + " g = " + group);
//                        }
//                    });
                    Log.e("spin", "end 3");


                }// end if
                else
                {
                    Toast.makeText(getApplicationContext(), "Loading.....",
                            Toast.LENGTH_LONG).show();
                }


            }
            catch(Exception er){
                Toast.makeText(getApplicationContext(),er.toString(),
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception edb) {
            Toast.makeText(getApplicationContext(), edb.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void registerUser(final String name, final String email,
                              final String password, final String age, final String language,
                              final String interest, final String career, final String gender,final  String group) {

        //Toast.makeText(getApplicationContext(), "REGISTER TEST 1",Toast.LENGTH_LONG).show();
        // Tag used to cancel the request
        // String tag_string_req = "req_register";
        // /////////////////////////////

            try {
                String paramiter = "index.php?name=" + name + "&password="
                        + password + "&age=" + age + "&email=" + email
                        + "&career=" + career + "&language=" + language
                        + "&gender=" + gender + "&interest=" + interest
                        + "&group=" + group + "&tag=register";
                Log.e("Debug", "para Regis = " + paramiter);
                String resultServerg = link.getJSONUrl(paramiter);
                //Toast.makeText(getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
                try {
                    JSONObject c = new JSONObject(resultServerg);
                    boolean error = c.getBoolean("error");
                    JSONObject jsonObj = c.getJSONObject("user");

                    int uidL = c.getInt("uid");
                    String nameL = jsonObj.getString("name");
                    String emailL = jsonObj.getString("email");
                    int ageL = jsonObj.getInt("age");
                    String genderL = jsonObj.getString("gender");
                    int careerL = jsonObj.getInt("career");
                    int languageL = jsonObj.getInt("language");
                    int interestL = jsonObj.getInt("interest");
                    int groupL = jsonObj.getInt("group");

                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Login OK... Hello  " + jsonObj.getString("name"),
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisActivity.this, Main.class);
                        i.putExtra("uid", uidL);
                        i.putExtra("name", nameL);
                        i.putExtra("password", password);
                        i.putExtra("email", emailL);
                        i.putExtra("age", ageL);
                        i.putExtra("gender", genderL);
                        i.putExtra("career", careerL);
                        i.putExtra("language", languageL);
                        i.putExtra("interest", interestL);
                        i.putExtra("group", groupL);

                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Loading.....",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception er) {
                    Toast.makeText(getApplicationContext(), er.toString(),
                            Toast.LENGTH_LONG).show();
                }


            } catch (Exception edb) {
                Toast.makeText(getApplicationContext(), edb.toString(),
                        Toast.LENGTH_LONG).show();
            }


        // pDialog.setMessage("Registering ...");
        // showDialog();

    }

    private void setupButton(){
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String username = inputUserName.getText().toString();
                String password = inputPassword.getText().toString();
                String email = inputEmail.getText().toString();
                String age = inputAge.getText().toString();
                String gender = genderG;

//                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()
//                        && !language && !interest.isEmpty() && !age.isEmpty()
//                        && !career.isEmpty() && !gender.isEmpty() && group.isEmpty())
                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()
                        && !age.isEmpty())
                {

//                    Toast.makeText(getApplicationContext(), "Please Wait!",
//                            Toast.LENGTH_LONG).show();
//                    Toast.makeText(
//                            getApplicationContext(),
//                            "1 username : " + username + "/n2 password :"
//                                    + password + "/n3 email : " + email
//                                    + "/n4 age : " + age + "/n5 language : "
//                                    + language + "/n6 interest : " + interest
//                                    + "/n7 career : " + career
//                                    + "/n8 gender : " + gender
//                                    + "/n9 group : "+ group,
//                            Toast.LENGTH_LONG).show();

                    if(password.length() >= 6) {

                    registerUser(username, email, password, age, language, interest, career, gender, group);

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Password should be more than six characters.",Toast.LENGTH_LONG).show();
                    }





                } else {
                    Toast.makeText(getApplicationContext(),"Please enter your details!", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Clear Text
        btnClear.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                inputUserName.setText(null);
                inputPassword.setText(null);
                inputEmail.setText(null);
                inputAge.setText(null);

                inputSpLanguage.setSelection(0);
                inputSpInterest.setSelection(0);
                inputSpCareer.setSelection(0);

                inputGender.check(R.id.Male);
            }
        });

        //Check Input
        inputUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                char[] user = inputUserName.getText().toString().toUpperCase().toCharArray();
                int X = new checkInput().strCheck(user);//Verify input
                Log.d("Check X","X = "+X);
                if (X == user.length) {
                    Toast.makeText(getApplicationContext(), "Username Correct...", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Username Incorrect...", Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getApplicationContext(), "Username Method", Toast.LENGTH_LONG).show();
            }
        });

    }//set button

    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Back");
        dialog.setIcon(R.drawable.ic_app);
        dialog.setCancelable(true);
        dialog.setMessage("Back to Login?");
        dialog.setPositiveButton("Yes", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        dialog.setNegativeButton("No", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void radioController() {
        inputGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.Male:

                        //Male
                        genderG = "male";

                        break;
                    case R.id.Female:

                        //Female
                        genderG = "female";

                        break;

                    default:

                        //Male
                        genderG = "male";

                        break;
                }

            }
        });


    }

    private boolean checkUsername(){
        boolean status = false;








        return status;
    }
    public void xxR(View v){
        Log.e("OverLap hideKeyBoard", " true view = "+v.toString());
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}