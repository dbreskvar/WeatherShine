package dbug.weathershine.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import dbug.weathershine.R;
import dbug.weathershine.activities.MainActivity;
import dbug.weathershine.models.DayItem;
import dbug.weathershine.models.Forecast;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private Context context;
    private Forecast forecast;

    private String icon_url = "http://openweathermap.org/img/w/";

    public ForecastAdapter(Context context, Forecast forecast) {
        this.context = context;
        this.forecast = forecast;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_info_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DayItem item = forecast.getList().get(position);

        holder.degree.setText(String.format(Locale.getDefault(), "%.2f °C", item.getMain().getTemp()));
        Picasso.with(context).load(String.format(Locale.getDefault(), "%s%s.png", icon_url, item.getWeather().get(0).getIcon())).into(holder.icon);
        holder.description.setText(item.getWeather().get(0).getDescription());
    }

    @Override
    public int getItemCount() {
        return forecast.getList().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView icon;
        TextView degree, description;

        public ViewHolder(View view) {
            super(view);
            icon = (ImageView) view.findViewById(R.id.day_info_icon);
            degree = (TextView) view.findViewById(R.id.day_info_degree);
            description = (TextView) view.findViewById(R.id.day_info_desc);
        }

        @Override
        public void onClick(View view) {
            if (context instanceof MainActivity) {
                ((MainActivity) context).setupExtraInfoFragment(forecast, getAdapterPosition());
            }
        }
    }
}
