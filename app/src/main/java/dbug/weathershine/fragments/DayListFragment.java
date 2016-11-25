package dbug.weathershine.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import dbug.weathershine.R;
import dbug.weathershine.adapters.ForecastAdapter;
import dbug.weathershine.models.Forecast;
import dbug.weathershine.network.APIs;
import dbug.weathershine.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayListFragment extends Fragment {

    private RecyclerView forecast_list;

    public static DayListFragment newInstance() {

        Bundle args = new Bundle();

        DayListFragment fragment = new DayListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DayListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.days_list_fragment, container, false);

        forecast_list = (RecyclerView) view.findViewById(R.id.forecast_list);
        updateInfo();


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    private void updateInfo() {
        Log.e("updateInfo()", "Method got called");
        final String API_KEY = getActivity().getResources().getString(R.string.weatherAPPID);
        APIs rest = RestClient.setupRestClient();
        Call<Forecast> updateWeather = rest.getWeatherForecastByCity("London,uk", API_KEY, "Metric");
        updateWeather.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Log.e("Response", "Response: " + response.message());
                if (response.isSuccessful()) {
                    forecast_list.setHasFixedSize(true);
                    forecast_list.setLayoutManager(new LinearLayoutManager(getActivity()));
                    forecast_list.setAdapter(new ForecastAdapter(getActivity(), response.body()));
                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.e("Failure", "Error: " + t.getLocalizedMessage() + " Call: " + call.request().url());
            }
        });
    }
}
