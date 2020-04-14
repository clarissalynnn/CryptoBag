package com.example.cryptobag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.view.View;
import android.util.Log;
import android.widget.Button;

import com.example.cryptobag.com.example.cryptobag.Entities.Coin;
import com.example.cryptobag.com.example.cryptobag.Entities.CoinLoreResponse;
import com.example.cryptobag.com.example.cryptobag.Entities.CoinService;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    public boolean mTwoPane;
    private CoinAdapter mAdapter;
    private String TAG = "MainActivity";

    //public static final String EXTRA_MESSAGE = ("hello");
    //private RecyclerView mRecyclerView;
    //private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager mLayoutManager;
// private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
        }
        RecyclerView mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);




        //Gson gson = new Gson();
        //CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
        //List<Coin> coins = response.getData();
        mAdapter = new CoinAdapter(this, new ArrayList<Coin>(), mTwoPane);
        mRecyclerView.setAdapter(mAdapter);
        new GetCoinTask().execute();
        //try {
            //create Retrofit instance & parse the retrieved Json using the Gson deserializer
            //Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.coinlore.net/").addConverterFactory(GsonConverterFactory.create()).build();

            //get service and call object for the request
           // CoinService service = retrofit.create(CoinService.class);
            //Call<CoinLoreResponse> coinsCall = service.getCoins();
           // new GetCoinTask().execute();
            //execute network request
            //Response<CoinLoreResponse> coinsResponse = coinsCall.execute();
            //List<Coin> coins = coinsResponse.body().getData();
            /*coinsCall.enqueue(new Callback<CoinLoreResponse>() {
                @Override
                public void onResponse(Call<CoinLoreResponse> call, Response<CoinLoreResponse> response) {
                    //if(response.isSuccessful()){
                        Log.d(TAG, "onResponse: DO RESPONSE CODE HERE");
                        List<Coin> coins = response.body().getData();
                        mAdapter.setCoins(coins);
                    //} else {
                        Log.d(TAG, "onResponse: ERROR IS:" + response.errorBody());
                    }
                //}

                @Override
                public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: FAILURE IS " + t.getLocalizedMessage());
                }
            });*/



            //connect recyclerView adapter with the retrieved data
            //mAdapter.setCoins(coins);

       // } catch (IOException e){
            //e.printStackTrace();
        //}


        //mRecyclerView.setAdapter(mAdapter);
    }
    private class GetCoinTask extends AsyncTask<Void, Void, List<Coin>>{
        @Override
        protected List<Coin> doInBackground(Void... voids){
            try{
                Log.d(TAG, "soInBackground: SUCCESS");
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.coinlore.net/").addConverterFactory(GsonConverterFactory.create()).build();

                //get service and call object for the request
                CoinService service = retrofit.create(CoinService.class);
                Call<CoinLoreResponse> coinsCall = service.getCoins();

                //execute network request
                Response<CoinLoreResponse> coinResponse = coinsCall.execute();
                List<Coin> coins = coinResponse.body().getData();
                return coins;
            }catch (IOException e){
                Log.d(TAG, "onFailure: FAILURE");
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(List<Coin> coins){
            mAdapter.setCoins(coins);
        }
    }
}
    /*private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_MESSAGE, position);
        startActivity(intent);
    }
    //button = findViewById(R.id.launchbutton);
    //button.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick (View v) {

        // }
    // });
    // button.setOnClickListener(new View.OnClickListener() {
        // @Override
        // public void onClick(View v) {
            // launchDetailActivity ("Hello from Detail Activity");
        // }
    // });

    // }
    // public void launchDetailActivity (String message){
        // Intent intent = new Intent ( this, DetailActivity.class);
        // intent.putExtra(EXTRA_MESSAGE, message);
        // startActivity(intent);
    //}
}*/
