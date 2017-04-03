package com.himanshu.navigationdrawer.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.himanshu.navigationdrawer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IfconfigFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IfconfigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IfconfigFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8;
    DhcpInfo d;
    WifiManager wifii;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public IfconfigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IfconfigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IfconfigFragment newInstance(String param1, String param2) {
        IfconfigFragment fragment = new IfconfigFragment();
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
        View v =  inflater.inflate(R.layout.fragment_ifconfig, container, false);
        Button btn = (Button) v.findViewById(R.id.ifconfig_btn);
        setter(v);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runcommand();
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

    private void setter(View v)
    {
        txt1 = (TextView) v.findViewById(R.id.textview);
        txt2 = (TextView) v.findViewById(R.id.textview2);
        txt3 = (TextView) v.findViewById(R.id.textview3);
        txt4 = (TextView) v.findViewById(R.id.textview4);
        txt5 = (TextView) v.findViewById(R.id.textview5);
        txt6 = (TextView) v.findViewById(R.id.textview6);
        txt7 = (TextView) v.findViewById(R.id.textview7);
        txt8 = (TextView) v.findViewById(R.id.textview8);
    }

    private void runcommand()
    {
        wifii= (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        d=wifii.getDhcpInfo();

        txt1.setText("Connection Type : "+getNetworkClass(getActivity().getApplicationContext()));
        txt2.setText("IP Address : " + intToIp(d.ipAddress));
        txt3.setText("Subnet Mask : " + intToIp(d.netmask));
        txt4.setText("Gateway : "+ intToIp(d.gateway));
        txt5.setText("DNS Server 1 : " + intToIp(d.dns1));
        txt6.setText("DNS Server 2 : " + intToIp(d.dns2));
        txt7.setText("Lease Time : "+d.leaseDuration + " seconds");
        txt8.setText("Mac Address : " + wifii.getConnectionInfo().getMacAddress());
    }

    private String intToIp(int addr) {
        return  ((addr & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF) + "." +
                ((addr >>>= 8) & 0xFF));
    }

    public static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info==null || !info.isConnected())
            return "-"; //not connected
        if(info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if(info.getType() == ConnectivityManager.TYPE_MOBILE){
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                    return "4G";
                default:
                    return "?";
            }
        }
        return "?";
    }
}
