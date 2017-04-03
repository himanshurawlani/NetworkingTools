package com.himanshu.navigationdrawer.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
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
 * {@link TracerouteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TracerouteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TracerouteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar spinner;
    String retry,timeout;
    TextView tv;
    String ss,ss2;
    EditText et,et1,et2,et3,ret,time;
    Button latency_c,n_check;
    int value = 0,cancel=0;
    View v;
    Activity mActivity;
    private OnFragmentInteractionListener mListener;

    public TracerouteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TracerouteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TracerouteFragment newInstance(String param1, String param2) {
        TracerouteFragment fragment = new TracerouteFragment();
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
        v = inflater.inflate(R.layout.fragment_traceroute, container, false);
        et          = (EditText)v.findViewById(R.id.first);
        et1         = (EditText)v.findViewById(R.id.second);
        et2         = (EditText)v.findViewById(R.id.third);
        et3         = (EditText)v.findViewById(R.id.fourth);
        tv          = (TextView) v.findViewById(R.id.output);
        ret         = (EditText)v.findViewById(R.id.retry_et);
        time        = (EditText)v.findViewById(R.id.time_out_et);
        n_check     = (Button)v.findViewById(R.id.check_button);
        spinner     = (ProgressBar)v.findViewById(R.id.progressBar1);
        latency_c   =  (Button)v.findViewById(R.id.traceroute);
        latency_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                traceroute(v);
            }
        });
        focus();
        return v;
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
        mActivity = (Activity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    /***************************************************************************************/

    public void traceroute(View v) {

        et.clearFocus();
        et1.clearFocus();
        et2.clearFocus();
        et3.clearFocus();

        new Thread() {
            public void run() {

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setVisibility(View.VISIBLE);
                        tv.setText(" ");
                    }
                });
            }
        }.start();

        if (et.getText().toString().length() != 0
                && et1.getText().toString().length() != 0
                && et2.getText().toString().length() != 0
                && et3.getText().toString().length() != 0)
        {
            ss = et.getText().toString() + "." + et1.getText().toString() + "." + et2.getText().toString() + "." + et3.getText().toString();
            threadtrace tt = new threadtrace(tv,ss,spinner,mActivity);
            Thread t = new Thread(tt);
            t.start();
        }
        else
        {
            new Thread() {
                public void run() {

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinner.setVisibility(View.INVISIBLE);
                            tv.setText("Some Ip address spaces are blank");
                        }
                    });
                }
            }.start();
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

class threadtrace extends MainActivity implements Runnable
{
    BufferedReader br;
    TextView tv;
    String s2,destination_addr,intermediate_addr;
    ProgressBar spinner;
    StringBuilder display;
    java.lang.Process p;
    int hops = 1,flag =0;
    Activity mActivity;
    threadtrace(TextView ttv,String ds,ProgressBar pb,Activity ac)
    {
        tv                  = ttv;
        destination_addr    = ds;
        spinner             = pb;
        mActivity           = ac;
    }

    @Override
    public void run() {
        System.out.println("Inside run");
        display = new StringBuilder();

        if(destination_addr!="0.0.0.0" || ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isConnected()) {
            do {
                try {
                    System.out.println("ping -t "+hops+" "+destination_addr);   //checker
                    p = Runtime.getRuntime().exec("ping -t "+hops+" "+destination_addr);
                    br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String s = "aa";
                    while ((s = br.readLine()) != null || intermediate_addr != destination_addr){
                        if (s.contains("From") || s.contains("from")) {
                            display.append(s);
                            break;
                        }
                    }
                    System.out.println("OUTSIDE LOOP " +hops);
                    p.destroy();
                    if(s!=null) {
                        Matcher match = Pattern.compile(".*( )(([0-9]+)(.)([0-9]+)(.)([0-9]+)(.)([0-9]+))(:).*").matcher(s);
                        if (match.matches()) {
                            s2 = match.group(2);
                            intermediate_addr   =   s2;
                            new Thread() {
                                public void run() {//updating ips

                                    mActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String st = (String) tv.getText();
                                            tv.setText(st+s2+"\n");
                                        }
                                    });
                                }
                            }.start();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                hops++;
                System.out.println("Destination "+destination_addr+" Intermediate "+intermediate_addr+" hops "+hops);
                display.delete(0,display.capacity()-1);
            } while (!intermediate_addr.equals(destination_addr) && hops < 30);
        }
        else
        {
            flag =1;
        }

        new Thread() {
            public void run() {

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setVisibility(View.INVISIBLE);
                        if(flag == 1)
                        {
                            tv.setText("WiFi Disconnected");
                        }
                        else if(hops>=30 || !intermediate_addr.equals(destination_addr))
                        {
                            tv.setText("Destination unreachable/Hop limit exceeded");
                        }
                    }
                });
            }
        }.start();
    }
}
