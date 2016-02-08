package com.tho.mapprippree;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class LoginActivity extends Activity {
    private Button btnLogin;

    private EditText inputUsername;
    private EditText inputPassword;
    private ProgressDialog progress;
    private String username,password,nameForget,uidForget,emailForget;
    private FunctionLib link;
    private Handler handler;

    private Runnable runnable;
    private long delay_time, time = 3000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        link = new FunctionLib();

        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                username = inputUsername.getText().toString();
                password = inputPassword.getText().toString();

                progress = ProgressDialog.show(LoginActivity.this, "Login",
                        "Loading...", true);

                if (!username.isEmpty() && !password.isEmpty()) {
                    // login user
                   // Toast.makeText(getApplicationContext(), username+"  "+password,Toast.LENGTH_LONG).show();

                    checkLogin(username, password);




                    //Toast.makeText(getApplicationContext(), "Login ---- 1",Toast.LENGTH_LONG).show();
                    progress.dismiss();
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                    progress.dismiss();
                }

            }
        });






    } //end onCreate


    // Check login
    private void checkLogin(final String username, final String password) {
       // Toast.makeText(getApplicationContext(), "Logging in ....", Toast.LENGTH_LONG).show();

        try {
            //String url = "http://www.travelapp.pe.hu/";
            String url = "http://tho.ddns.net:2407/prippree/";
            String paramiter = "index.php?name=" + username + "&password="
                    + password + "&tag=login";
            String resultServerg = new FunctionLib().getJSONUrl(paramiter);
            Log.e("LoginWEB_Host", resultServerg);
            //Toast.makeText(getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
            Log.d("LoginWEB_Host", resultServerg);
            for (int i = 0; i <resultServerg.length(); i++) {
                //Log.d("testJS3 & testJS2","JS3 = "+testJS3.charAt(i)+"  JS2 = "+testJS2.charAt(i));
                Log.d("testWEBBBB", "JSWEB = " + resultServerg.charAt(i));

            }

            try{

                JSONObject c = new JSONObject(resultServerg);
                boolean error = c.getBoolean("error");



                //Toast.makeText(getApplicationContext(), String.valueOf(error),Toast.LENGTH_LONG).show();
                if(!error)
                {

                    int uid = c.getInt("uid");
                    String name = c.getString("name_user");
                    String email = c.getString("email_user");
                    int age = c.getInt("age_user");
                    String gender = c.getString("gender_user");
                    int career = c.getInt("id_career");
                    int language = c.getInt("id_language");
                    int interest = c.getInt("id_interest");
                    int group = c.getInt("id_group");




                    Toast.makeText(getApplicationContext(), "Login OK... Hello  "+c.getString("name_user"),
                            Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, Main.class);

                    i.putExtra("uid", uid);
                    i.putExtra("name", name);
                    i.putExtra("password", password);
                    i.putExtra("email", email);
                    i.putExtra("age", age);
                    i.putExtra("gender", gender);
                    i.putExtra("career", career);
                    i.putExtra("language", language);
                    i.putExtra("interest", interest);
                    i.putExtra("group", group);

                    startActivity(i);
                    View view = this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    //finish();

                }
                else
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialog_login_error, null);
                    final TextView AlertTitle = (TextView) view.findViewById(R.id.tvLogin);
                    //AlertTitle.setText("...");
                    builder.setView(view)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                    dialog.cancel();
                                }
                            });


                    builder.create();
                    builder.show();
                    //Toast.makeText(getApplicationContext(), "Loading.....",Toast.LENGTH_LONG).show();
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

    }// end checklogin
    public void reg(View v) {
        Log.e("reg ", " run ");
        Intent i = new Intent(LoginActivity.this, RegisActivity.class);
        startActivity(i);
    }

    public void forgetPass(View v){

        Log.e("forget ", " run ");

        //AlertDialog
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("ForgetPass");

        dialog.setContentView(R.layout.forget_pass);

        final EditText username = (EditText) dialog.findViewById(R.id.usernamef);
        final EditText email = (EditText) dialog.findViewById(R.id.emailf);
        Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
        Button btnNext = (Button) dialog.findViewById(R.id.btnNext);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {
            // Check username email
                /////////////////////////////////////////////////////////////
                try {
                    String paramiter = "index.php?"+"tag=forget_pass"+"&name="+username.getText().toString()+"&email="+email.getText().toString();
                    String resultServerg = link.getJSONUrl(paramiter);
                    Log.e("ForgetPass"," "+paramiter);
                    //Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
                    try{
                        JSONObject c = new JSONObject(resultServerg);
                        boolean error = c.getBoolean("error");
                        JSONObject jsonObject = c.getJSONObject("data");
                        if(!error)
                        {
                            nameForget = jsonObject.getString("name_user");
                            uidForget = String.valueOf(jsonObject.getInt("id_user"));
                            emailForget = jsonObject.getString("email_user");
                            Log.e("ForgetPass", " OK uid= " + uidForget + " name= " + nameForget);
                            if(uidForget != null)
                                dialog.dismiss();
                            resumePass();

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Error",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    catch(Exception er){

                        Toast.makeText(getApplicationContext(),"Email Incorrect",Toast.LENGTH_LONG).show();
                        //Toast.makeText(getApplicationContext(),er.toString(),Toast.LENGTH_LONG).show();
                    }

                } catch (Exception edb) {
                    Toast.makeText(getApplicationContext(), edb.toString(),
                            Toast.LENGTH_LONG).show();
                }
                /////////////////////////////////////////////////////////////

        }
    });

    dialog.show();

        //End Dialog


    }
    private void resumePass(){
        Log.e("resume Pass ", " run ");
        //AlertDialog
        final Dialog dialog2 = new Dialog(LoginActivity.this);
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setTitle("ResumePass");
        dialog2.setContentView(R.layout.resume_pass);
        Log.e("resume Pass ", " run2 ");
        final EditText pass = (EditText) dialog2.findViewById(R.id.repassword1);
        final EditText repass = (EditText) dialog2.findViewById(R.id.repassword2);
        Button btnClose = (Button) dialog2.findViewById(R.id.btnCloseFor);
        Button btnRepass = (Button) dialog2.findViewById(R.id.btnRepass);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        btnRepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("resume Pass ", " repassclick p="+pass.getText().toString()+" rp="+repass.getText().toString());
                if (pass.getText().toString().equals(repass.getText().toString())){
                    /////////////////////////////////////////////////////////////
                    try {
                        String paramiter = "index.php?" + "tag=resume_pass" + "&uid=" + uidForget + "&pass=" + pass.getText().toString();
                        String resultServerg = link.getJSONUrl(paramiter);
                        Log.e("ResumePass", " " + paramiter);
                        try {
                            JSONObject c = new JSONObject(resultServerg);
                            boolean error = c.getBoolean("error");
                            //JSONObject jsonObject = c.getJSONObject("data");
                            if (!error) {
                                Log.e("ResumePass", " OK uid= " + uidForget + " name= " + nameForget);
                                Toast.makeText(getApplicationContext(), "Reset Password OK!",
                                        Toast.LENGTH_LONG).show();
                                dialog2.dismiss();
                            } else {
                                Toast.makeText(getApplicationContext(), "Can't Reset Password.",
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
                /////////////////////////////////////////////////////////////
            }//end pass==pass
                else{
                    //pass.setHintTextColor(getResources().getColor(R.color.red));
                    repass.setHintTextColor(getResources().getColor(R.color.red));

                    //pass.setText("");
                    repass.setText("");

                   // pass.setHint(R.string.forgetpass);
                    repass.setHint(R.string.forgetpass);
                }
            }
        });

        dialog2.show();

        //End Dialog
    }





    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setIcon(R.drawable.ic_app);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                android.os.Process.killProcess(android.os.Process.myPid()); // Kill application on TASK ;

            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        handler.removeCallbacks(runnable);
//        time = delay_time - (System.currentTimeMillis() - time);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        delay_time = time;
//        handler.postDelayed(runnable, delay_time);
//        time = System.currentTimeMillis();

    }
    public void xxL(View v){
        Log.e("OverLap hideKeyBoard", " true view = " + v.toString());
       // v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }






}// end class



