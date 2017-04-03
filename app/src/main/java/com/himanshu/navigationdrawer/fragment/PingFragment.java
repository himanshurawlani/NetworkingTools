package com.himanshu.navigationdrawer.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.himanshu.navigationdrawer.R;
import com.himanshu.navigationdrawer.activity.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Activity mActivity;
    private OnFragmentInteractionListener mListener;
    View v;
    ProgressBar spinner;
    String retry,timeout;
    TextView tv;
    String ss,ss2;
    EditText et,et1,et2,et3,ret,time;
    Button n_check;

    public PingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PingFragment newInstance(String param1, String param2) {
        PingFragment fragment = new PingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_ping, container, false);
        setter(v);
        Log.v("MainActivity: ", String.valueOf(getActivity()));
        n_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check(v);
            }
        });
        focus();
        return v;
    }

    private void setter(View v)
    {
        n_check = (Button) v.findViewById(R.id.check_button);
        et          = (EditText)v.findViewById(R.id.first);
        et1         = (EditText)v.findViewById(R.id.second);
        et2         = (EditText)v.findViewById(R.id.third);
        et3         = (EditText)v.findViewById(R.id.fourth);
        tv          = (TextView) v.findViewById(R.id.output);
        ret         = (EditText)v.findViewById(R.id.retry_et);
        time        = (EditText)v.findViewById(R.id.time_out_et);
        n_check     = (Button)v.findViewById(R.id.check_button);
        spinner     = (ProgressBar)v.findViewById(R.id.progressBar1);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        mActivity = (Activity) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /*************************************************************************************/

    int value = 0,cancel=0;

    public void check(View view)
    {
        et.clearFocus();
        et1.clearFocus();
        et2.clearFocus();
        et3.clearFocus();

        new Thread() {
            public void run() {

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("Spinner visible", String.valueOf(spinner.getProgress()));
                        spinner.setVisibility(View.VISIBLE);
                    }
                });
            }
        }.start();

        Log.v("After Spinner visible", String.valueOf(spinner.getProgress()));

        if(cancel==0) {

            n_check.setContentDescription("CANCEL");
            retry       = ret.getText().toString();
            timeout     = time.getText().toString();
            if (et.getText().toString().length() != 0 || et1.getText().toString().length() != 0 || et2.getText().toString().length() != 0 || et3.getText().toString().length() != 0) {
                {
                    ss = et.getText().toString() + "." + et1.getText().toString() + "." + et2.getText().toString() + "." + et3.getText().toString();
                    ss2 = new String(ss);
                }
                value = 1;

                retry = ret.getText().toString();
                timeout = time.getText().toString();

                System.out.println("Upper retry" + retry);
                if (retry == null || retry.equals("") || retry.length() == 0 || retry.equals("0")) {
                    retry = "0.5";
                }
                if (timeout == null || timeout.equals("") || timeout.length() == 0 || timeout.equals("0")) {
                    timeout = "0.1";
                }
                thread tt = new thread(tv, ss, spinner, value, retry, timeout,cancel,mActivity);
                Thread t = new Thread(tt);
                t.start();
            } else {
                new Thread() {
                    public void run() {

                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv.setVisibility(View.VISIBLE);
                                spinner.setVisibility(View.INVISIBLE);
                                tv.setText("Some Ip address spaces are blank");
                            }
                        });
                    }
                }.start();
            }
        }
        else
        {
            new Thread() {
                public void run() {

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setVisibility(View.VISIBLE);
                            spinner.setVisibility(View.INVISIBLE);
                            tv.setText("Cancelled");
                            n_check.setContentDescription("CHECK");
                        }
                    });
                }
            }.start();
            cancel=0;
        }
    }
    boolean empty(EditText tv) {
        String string = tv.getText().toString().trim();
        if(string.isEmpty() || string.length()==0 || string.equals("") || string==null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    void focus() {
        EditText et = (EditText)v.findViewById(R.id.first);
        EditText et1 = (EditText)v.findViewById(R.id.second);
        EditText et2 = (EditText)v.findViewById(R.id.third);
        EditText et3 = (EditText)v.findViewById(R.id.fourth);


        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                EditText et = (EditText)v.findViewById(R.id.first);
                EditText et1 = (EditText)v.findViewById(R.id.second);
                EditText et2 = (EditText)v.findViewById(R.id.third);
                EditText et3 = (EditText)v.findViewById(R.id.fourth);

                if(!hasFocus && !empty(et)) {
                    if (et.getText().toString().length() < 10) {
                        if (Integer.parseInt(et.getText().toString()) < 0) {
                            et.setText("0");
                        }
                        if (Integer.parseInt(et.getText().toString()) > 255) {
                            et.setText("255");
                        }
                    }
                    else
                    {
                        et.setText("255");
                    }
                }

            }
        });
        et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                EditText et = (EditText)v.findViewById(R.id.first);
                EditText et1 = (EditText)v.findViewById(R.id.second);
                EditText et2 = (EditText)v.findViewById(R.id.third);
                EditText et3 = (EditText)v.findViewById(R.id.fourth);
                if(!hasFocus && !empty(et1)) {
                    if (et1.getText().toString().length() < 10) {
                        if (Integer.parseInt(et1.getText().toString()) < 0) {
                            et1.setText("0");
                        }
                        if (Integer.parseInt(et1.getText().toString()) > 255) {
                            et1.setText("255");
                        }
                    }
                    else
                    {
                        et1.setText("255");
                    }
                }
            }
        });
        et2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                EditText et = (EditText)v.findViewById(R.id.first);
                EditText et1 = (EditText)v.findViewById(R.id.second);
                EditText et2 = (EditText)v.findViewById(R.id.third);
                EditText et3 = (EditText)v.findViewById(R.id.fourth);
                if(!hasFocus && !empty(et2)) {
                    if (et2.getText().toString().length() < 10) {
                        if (Integer.parseInt(et2.getText().toString()) < 0) {
                            et2.setText("0");
                        }
                        if (Integer.parseInt(et2.getText().toString()) > 255) {
                            et2.setText("255");
                        }
                    }
                    else
                    {
                        et2.setText("255");
                    }
                }
            }
        });
        et3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                EditText et = (EditText)v.findViewById(R.id.first);
                EditText et1 = (EditText)v.findViewById(R.id.second);
                EditText et2 = (EditText)v.findViewById(R.id.third);
                EditText et3 = (EditText)v.findViewById(R.id.fourth);
                if(!hasFocus && !empty(et3)) {
                    if (et3.getText().toString().length() < 10) {
                        if (Integer.parseInt(et3.getText().toString()) < 0) {
                            et.setText("0");
                        }
                        if (Integer.parseInt(et3.getText().toString()) > 255) {
                            et3.setText("255");
                        }
                    }
                    else
                    {
                        et3.setText("255");
                    }
                }
            }
        });
    }
}

class thread extends MainActivity implements Runnable
{
    BufferedReader br;
    TextView tv;
    String s,ss,retry,timeout;
    StringBuilder sb;
    ProgressBar spinnerr;
    StringBuilder tracker;
    Activity mActivity;
    int value;
    int flag =0,cancel=0;

    @Override
    public View findViewById(@IdRes int id) {
        return super.findViewById(id);
    }

    public thread(TextView tvv,String vss,ProgressBar p,int v,String r,String t,int ct,Activity ac)
    {
        tv = tvv;
        ss = vss;
        spinnerr = p;
        value = v;
        retry = r;
        timeout = t;
        cancel = ct;
        mActivity = ac;
    }

    public void run()
    {
        try
        {
            sb = new StringBuilder();
            tracker = new StringBuilder();
            String command = getCommand();

            System.out.println(command+ss);

            java.lang.Process pp = Runtime.getRuntime().exec(command+ss);
            br = new BufferedReader(new InputStreamReader(pp.getInputStream()));

            while ((s = br.readLine()) != null) {
                if (s.length() > 0 && s.contains("avg")) {
                    break;
                }
                else if(!s.contains("PING"))
                {
                    tracker.append("\n" +s);
                }
            }

            if(s!=null)
            {
                System.out.println("Outside "+s+" "+s.length()+" Retry "+retry);
                Matcher match = Pattern.compile(".*( ?)([0-9]+(\\.[0-9]+))(/?)([0-9]+(\\.[0-9]+))(/?)([0-9]+(\\.[0-9]+))(/?)([0-9]+(\\.[0-9]+)).*").matcher(s);
                if (match.matches()) {
                    Double minm = Double.parseDouble(match.group(2));
                    Double avg = Double.parseDouble(match.group(5));
                    Double max = Double.parseDouble(match.group(8));

                    System.out.println("Inside  "+minm+" "+" "+avg+" "+" "+max);

                    if (value == 1) {
                        sb.append("IPADDRESS OPTION : " + ss + ":\n Avg. Latency :" + avg / 2 + "ms");
                        sb.append("\nMin. Latency :" + minm / 2 + "ms");
                        sb.append("\nMax. Latency :" + max / 2 + "ms");
                    }
                    else {
                        if(!ss.equals("0.0.0.0")) {
                            flag = 1;
                            sb.append("WIFI Latency : " + ss + ":\n Avg. Latency :" + avg / 2 + "ms");
                            sb.append("\nMin. Latency :" + minm / 2 + "ms");
                            sb.append("\nMax. Latency :" + max / 2 + "ms");
                        }
                    }
                }
            }
            else {
                if (value == 1)
                    sb.append("IPADDRESS OPTION :\n" + ss + ": No Connection/Invalid Address");
                else if(value == 3)
                {
                    if(!ss.equals("0.0.0.0")) {
                        flag = 1;
                    }
                }
                else
                    sb.append("Some input fields are not filled.");
            }
            //pp.destroy();
            System.out.println("end");

        } catch (IOException e)
        {
            e.printStackTrace();
        }


        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (value == 1)
                    tv.setText(sb.toString()+tracker.toString()+"\n");
                else
                {
                    if(flag == 1) {
                        tv.setText(sb.toString()/*+"\n"+"Ip :"+ss*/+tracker.toString()+"\n" );
                    }
                    else
                    {
                        tv.setText("WIFI Disconnected");
                    }
                }
                try {
                    spinnerr.setVisibility(View.INVISIBLE);
                    tv.setVisibility(View.VISIBLE);
                }
                catch(Exception e)
                {
                    System.out.println(spinnerr+" Here thread "+tv);
                }
            }
        });

    }


    String getCommand()
    {
        String command;

        if(retry.equals("0.5"))
        {
            command = "/system/bin/ping -c 4 ";
        }
        else
        {
            command = "/system/bin/ping -c "+retry+" ";
        }
        if(timeout.equals("0.1"))
        {
        }
        else
        {
            command = command + "-w "+timeout +" ";
        }
        return command;
    }

}
