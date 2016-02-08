package com.tho.mapprippree;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Editprofile extends Fragment {

    public static final String UID = "uid";
    public static final String NAME_USER = "username";
    public static final String PASSWORD_USER = "password";
    public static final String EMAIL_USER = "email";
    public static final String AGE_USER = "age";
    public static final String GENDER_USER = "gender";
    public static final String ID_CAREER = "career";
    public static final String ID_LANGUAGE = "language";
    public static final String ID_INTEREST = "interest";
    public static final String ID_GROUP = "group";

    ImageView ivIcon;
    TextView tvItemName;


    public static final String IMAGE_RESOURCE_ID = "iconResourceID";
    public static final String ITEM_NAME = "itemName";
    private String career,interest,language,group,genderG,uid;
    private int[] id_group;
    private int[] DataIdL,DataIdI,DataIdC;
    private String[] DataTitleL,DataTitleI,DataTitleC;
    private int indexI,indexL,indexC;


    private FunctionLib link;
    private FragmentActivity myContext;

    private boolean status = true;
    private EditText inputUserName,inputPassword,inputAge,inputEmail;
    private RadioGroup inputGender;
    private Spinner inputSpCareer,inputSpInterest,inputSpLanguage;


    public Editprofile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        ivIcon = (ImageView) view.findViewById(R.id.fragedit_icon);
        tvItemName = (TextView) view.findViewById(R.id.fragedit_text);

        tvItemName.setText(getArguments().getString(ITEM_NAME));
        ivIcon.setImageDrawable(view.getResources().getDrawable(getArguments().getInt(IMAGE_RESOURCE_ID)));


        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        link = new FunctionLib();
        super.onAttach(activity);
    }

    @Override
    public void onPause() {
        Log.e("DEBUG", "OnPause of EditProfile "+status);
        status = false;
        super.onPause();
    }

    @Override
    public void onResume() {
        Log.e("DEBUG", "OnResume of EditProfile " + status);
        if(status){

            setUp();

        }
        super.onResume();
    }

    private void setUp(){


        addOldData();
        radioController();
        setButton();

    }

    private void addOldData() {
        inputUserName = (EditText) myContext.findViewById(R.id.txtUsernameE);
        inputEmail = (EditText) myContext.findViewById(R.id.txtEmailE);
        inputPassword = (EditText) myContext.findViewById(R.id.txtPasswordE);
        inputAge = (EditText) myContext.findViewById(R.id.txtAgeE);
        inputGender = (RadioGroup) myContext.findViewById(R.id.rbgGenderE);

        inputSpCareer = (Spinner) myContext.findViewById(R.id.spinCareerE);
        inputSpInterest = (Spinner) myContext.findViewById(R.id.spinInterestE);
        inputSpLanguage = (Spinner) myContext.findViewById(R.id.spinLanguageE);

        genderG = getArguments().getString(GENDER_USER);
        career = String.valueOf(getArguments().getInt(ID_CAREER));
        interest = String.valueOf(getArguments().getInt(ID_INTEREST));
        language = String.valueOf(getArguments().getInt(ID_LANGUAGE));
        group = String.valueOf(getArguments().getInt(ID_GROUP));
        uid = String.valueOf(getArguments().getInt(UID));
        Log.e("Debug Career"," career = "+career);


        inputUserName.setText(getArguments().getString(NAME_USER));
        inputPassword.setText(getArguments().getString(PASSWORD_USER));
        inputEmail.setText(getArguments().getString(EMAIL_USER));
        inputAge.setText(String.valueOf(getArguments().getInt(AGE_USER)));

        if(genderG.toLowerCase().equals("male")) {
            inputGender.check(R.id.MaleE);
        }
        else {
            inputGender.check(R.id.FemaleE);
        }

        setSpinData();

        inputSpCareer.setSelection(getIndex(DataIdC, career));
        inputSpInterest.setSelection(getIndex(DataIdI,interest));
        inputSpLanguage.setSelection(getIndex(DataIdL,language));



    }

    private void edit_profile(final String uid, final String name, final String email,
                              final String password, final String age, final String language,
                              final String interest, final String career, final String gender,
                              final  String group) {

        //Toast.makeText(myContext.getApplicationContext(), "EDIT TEST 1",Toast.LENGTH_LONG).show();

            try {
                String paramiter = "index.php?name=" + name + "&password="
                        + password + "&age=" + age + "&email=" + email
                        + "&career=" + career + "&language=" + language
                        + "&gender=" + gender + "&interest=" + interest
                        + "&group=" + group + "&uid=" + uid + "&tag=editprofile";
                Log.e("Debug", "para Edit = " + paramiter);
                String resultServerg = link.getJSONUrl(paramiter);
                Log.e("link edit", " " + paramiter);
                //Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
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
                        Toast.makeText(myContext.getApplicationContext(), "Edit OK... Hello  " + jsonObj.getString("name"),
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(myContext, Main.class);
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
                        myContext.finish();

                    } else {
                        Toast.makeText(myContext.getApplicationContext(), "Loading.....",
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception er) {
                    Toast.makeText(myContext.getApplicationContext(), er.toString(),
                            Toast.LENGTH_LONG).show();
                }


            } catch (Exception edb) {
                Toast.makeText(myContext.getApplicationContext(), edb.toString(),
                        Toast.LENGTH_LONG).show();
            }




        // pDialog.setMessage("Registering ...");
        // showDialog();

    }

    //private method of your class
    private int getIndex(int[] DataId, String myString)
    {
        int index = 0;

        for (int i=0;i<DataId.length;i++){
            if (DataId[i] == Integer.parseInt(myString)){
                index = i;
                break;
            }
        }
        Log.e("spin num ", "s = " + DataId.length);
        Log.e("getIndex ","i = "+index);
        return index;
    }


    private void radioController() {
        inputGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.MaleE:

                        //Male
                        genderG = "male";

                        break;
                    case R.id.FemaleE:

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

    private void setButton(){
        Button btnBack = (Button) myContext.findViewById(R.id.btnBackedit);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("onClickPL", "back click");
                link.btnBackClick(Editprofile.this);
            }
        });

        Button btnOk = (Button) myContext.findViewById(R.id.btnSummitE);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = inputUserName.getText().toString();
                String password = inputPassword.getText().toString();
                String email = inputEmail.getText().toString();
                String age = inputAge.getText().toString();
                String gender = genderG;

                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()
                        && !age.isEmpty())
                {

//                    Toast.makeText(myContext.getApplicationContext(), "Please Wait!",
//                            Toast.LENGTH_LONG).show();
//                    Toast.makeText(myContext.getApplicationContext(),
//                            "1 username : " + username + "/n2 password :"
//                                    + password + "/n3 email : " + email
//                                    + "/n4 age : " + age + "/n5 language : "
//                                    + language + "/n6 interest : " + interest
//                                    + "/n7 career : " + career
//                                    + "/n8 gender : " + gender
//                                    + "/n9 group : "+ group,
//                            Toast.LENGTH_LONG).show();
                if(password.length() >= 6) {

                    edit_profile(uid,username, email, password, age, language, interest, career, gender, group);

                }
                    else {
                    Toast.makeText(myContext.getApplicationContext(),"Password should be more than six characters.",Toast.LENGTH_LONG).show();
                }



                } else {
                    Toast.makeText(myContext.getApplicationContext(),"Please enter your details!", Toast.LENGTH_LONG).show();
                }

            }
        });//end ok

        Button btnReset = (Button) myContext.findViewById(R.id.btnResetE);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOldData();
            }
        });
    }

    private void setSpinData()
    {

        try {

            String paramiter = "index.php?tag=regis_detail";
            Log.e("detail register ", ""+ paramiter);
            String resultServerg = link.getJSONUrl(paramiter);
            Log.e("detail register ", ""+ resultServerg);
           // Toast.makeText(myContext.getApplicationContext(), resultServerg,Toast.LENGTH_LONG).show();
            Log.d("GET_Data_WEB_HOST", resultServerg);
            try{
                JSONObject c = new JSONObject(resultServerg);
                boolean error = c.getBoolean("error");
                int max_data = 0;


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

                    inputSpLanguage.setAdapter(new MySpinAdapter(myContext, DataTitleL, DataIdL, R.id.spinLanguageE));
                    inputSpLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            language = String.valueOf(DataIdL[i]);
                            //Log.e("Reg lang", "l = " + language);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
//
                   // Log.e("spin", "end 1");

                    //set Interest
                    place_data = c.getJSONArray("datainterest");
                   // Log.e("palce_data JSON I"," "+place_data);
                    max_data = place_data.length();
                    DataTitleI = new String[max_data];
                    DataIdI = new int[max_data];

                    for (int i = 0; i < max_data; i++) {
                        JSONObject jsonObj = place_data.getJSONObject(i);

                        DataIdI[i] = jsonObj.getInt("id_interest");
                        DataTitleI[i] = jsonObj.getString("name_interest");

                    }
                    inputSpInterest.setAdapter(new MySpinAdapter(myContext, DataTitleI, DataIdI, R.id.spinInterestE));
                    inputSpInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            interest = String.valueOf(DataIdI[i]);
                            //  Log.e("Reg interest", "i = " + interest);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                   // Log.e("spin", "end 2");

                    //set Career
                    place_data = c.getJSONArray("datacareer");
                   // Log.e("palce_data JSON C"," "+place_data);
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
                    inputSpCareer.setAdapter(new MySpinAdapter(myContext, DataTitleC, DataIdC, R.id.spinCareerE));
                    inputSpCareer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            career = String.valueOf(DataIdC[i]);
                            group = String.valueOf(id_group[i]);
                           // Log.e("Reg career", "c = " + career + " g = " + group);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
//
                    //Log.e("spin", "end 3");


                }// end if
                else
                {
                    Toast.makeText(myContext.getApplicationContext(), "Can't Connect Server",
                            Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception er){
                Toast.makeText(myContext.getApplicationContext(),er.toString(),
                        Toast.LENGTH_LONG).show();
            }

        } catch (Exception edb) {
            Toast.makeText(myContext.getApplicationContext(), edb.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

}
