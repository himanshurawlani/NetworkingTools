package com.himanshu.navigationdrawer.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
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

import static android.R.string.cancel;
import static android.content.Context.WIFI_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WifiLatencyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WifiLatencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WifiLatencyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Activity mActivity;
    View v;
    ProgressBar spinner;
    String retry,timeout;
    TextView tv;
    String ss,ss2;
    EditText et,et1,et2,et3,ret,time;
    Button n_check;
    int value = 0,cancel=0;
    private OnFragmentInteractionListener mListener;

    public WifiLatencyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WifiLatencyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WifiLatencyFragment newInstance(String param1, String param2) {
        WifiLatencyFragment fragment = new WifiLatencyFragment();
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
        v = inflater.inflate(R.layout.fragment_wifi_latency, container, false);
        n_check = (Button) v.findViewById(R.id.check_button_latency);
        tv          = (TextView) v.findViewById(R.id.output);
        ret         = (EditText)v.findViewById(R.id.retry_et);
        time        = (EditText)v.findViewById(R.id.time_out_et);
        n_check     = (Button)v.findViewById(R.id.check_button_latency);
        spinner     = (ProgressBar)v.findViewById(R.id.progressBar1);
        Log.v("MainActivity: ", String.valueOf(getActivity()));
        n_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                latency(v);
            }
        });
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
    public void latency(View view) {


        if (cancel == 0) {
            new Thread() {
                public void run() {

                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinner.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }.start();
            final WifiManager manager = (WifiManager) mActivity.getApplicationContext().getSystemService(WIFI_SERVICE);
            final DhcpInfo dhcp = manager.getDhcpInfo();
            String sss = Formatter.formatIpAddress(dhcp.gateway);
            value = 3;

            retry = ret.getText().toString();
            timeout = time.getText().toString();

            if (retry == null || retry.equals("") || retry.length() == 0 || retry.equals("0")) {
                retry = "0.5";
            }
            if (timeout == null || timeout.equals("") || timeout.length() == 0 || timeout.equals("0")) {
                timeout = "0.1";
            }
            thread tt = new thread(tv, sss, spinner, value, retry, timeout,cancel,mActivity);
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
                            tv.setVisibility(View.VISIBLE);
                            spinner.setVisibility(View.INVISIBLE);
                            tv.setText("Cancelled");
                            cancel =0;
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

}