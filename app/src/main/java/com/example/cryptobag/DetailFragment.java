package com.example.cryptobag;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cryptobag.com.example.cryptobag.Entities.Coin;
import com.example.cryptobag.com.example.cryptobag.Entities.CoinLoreResponse;
import com.example.cryptobag.com.example.cryptobag.Entities.CoinService;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class DetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_ITEM_ID = "item_id";
    private Coin mCoin;

    //private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    public DetailFragment() {
    }
    // TODO: Rename and change types of parameters
    //private String mParam1;
    //private String mParam2;

    //private OnFragmentInteractionListener mListener;

    //public DetailFragment() {
    // Required empty public constructor

    //}
    /*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     /* @param param1 Parameter 1.
     /* @param param2 Parameter 2.
     /* @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            //try {
                //create Retrofit instance & parse the retrieved Json using the Gson deserializer
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.coinlore.net/").addConverterFactory(GsonConverterFactory.create()).build();

                //get service and call object for the request
                CoinService service = retrofit.create(CoinService.class);
                Call<CoinLoreResponse> coinsCall = service.getCoins();

                //execute network request
               // Response<CoinLoreResponse> coinsResponse = coinsCall.execute();
               // List<Coin> coins = coinsResponse.body().getData();

            coinsCall.enqueue(new Callback<CoinLoreResponse>() {
                @Override
                public void onResponse(Call<CoinLoreResponse> call, Response<CoinLoreResponse> response) {
                    Log.d(TAG, "onResponse: DO RESPONSE CODE HERE");
                    List<Coin> coins = response.body().getData();
                    for(Coin coin : coins){
                        if(coin.getId().equals(getArguments().getString(ARG_ITEM_ID))){
                            mCoin = coin;
                            break;
                        }
                    } updateUi();
                    DetailFragment.this.getActivity().setTitle(mCoin.getName());
                }

                @Override
                public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: FAILURE IS " + t.getLocalizedMessage());
                }
            });



                //connect recyclerView adapter with the retrieved data
                //mAdapter.setCoins(coins);

            //} catch (IOException e){
                //e.printStackTrace();
            //}

            //Gson gson = new Gson();
            //CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
            //List<Coin> coins = response.getData();

            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        return rootView;
    }
    private void updateUi(){
        View rootView = getView();
        if (mCoin != null) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            ((TextView) rootView.findViewById(R.id.tvName)).setText(mCoin.getName());
            ((TextView) rootView.findViewById(R.id.tvSymbol)).setText(mCoin.getSymbol());
            ((TextView) rootView.findViewById(R.id.tvValueField)).setText(formatter.format(Double.valueOf(mCoin.getPriceUsd())));
            ((TextView) rootView.findViewById(R.id.tvChange1hField)).setText(mCoin.getPercentChange1h() + " %");
            ((TextView) rootView.findViewById(R.id.tvChange24hField)).setText(mCoin.getPercentChange24h() + " %");
            ((TextView) rootView.findViewById(R.id.tvChange7dField)).setText(mCoin.getPercentChange7d() + " %");
            ((TextView) rootView.findViewById(R.id.tvMarketcapField)).setText(formatter.format(Double.valueOf(mCoin.getMarketCapUsd())));
            ((TextView) rootView.findViewById(R.id.tvVolumeField)).setText(formatter.format(Double.valueOf(mCoin.getVolume24())));
            //((TextView) rootView.findViewById(R.id.tvValueField)).setText(formatter.format(mCoin.getValue()));
            //((TextView) rootView.findViewById(R.id.tvChange1hField)).setText(String.valueOf(mCoin.getChange1h()) + " %");
            //((TextView) rootView.findViewById(R.id.tvChange24hField)).setText(String.valueOf(mCoin.getChange24h()) + " %");
            //((TextView) rootView.findViewById(R.id.tvChange7dField)).setText(String.valueOf(mCoin.getChange7d()) + " %");
            //((TextView) rootView.findViewById(R.id.tvMarketcapField)).setText(formatter.format(mCoin.getMarketcap()));
            //((TextView) rootView.findViewById(R.id.tvVolumeField)).setText(formatter.format(mCoin.getVolume()));
            ((ImageView) rootView.findViewById(R.id.ivSearch)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchCoin(mCoin.getName());
                }
            });

        }
    }
    private void searchCoin(String name) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + name));
        startActivity(intent);
    }
}
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detail, container, false);
    //}

    // TODO: Rename method, update argument and hook method into UI event
    //public void onButtonPressed(Uri uri) {
        //if (mListener != null) {
          //  mListener.onFragmentInteraction(uri);
        //}
    //}

    //@Override
    //public void onAttach(Context context) {
        //super.onAttach(context);
        //if (context instanceof OnFragmentInteractionListener) {
            //mListener = (OnFragmentInteractionListener) context;
        //} else {
            //throw new RuntimeException(context.toString()
                    //+ " must implement OnFragmentInteractionListener");
        //}
    //}

    //@Override
    //public void onDetach() {
      //  super.onDetach();
        //mListener = null;
    //}

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
    //public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
       // void onFragmentInteraction(Uri uri);
    //}
//}
