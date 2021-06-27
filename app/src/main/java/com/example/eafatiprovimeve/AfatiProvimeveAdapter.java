package com.example.eafatiprovimeve;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AfatiProvimeveAdapter extends RecyclerView.Adapter<AfatiProvimeveAdapter.ProvimiHolder> {

    private static final String TAG = "AfatiProvimeveAdapter";
    private static final String BASE_URL = "https://api.npoint.io/d0f87ea74c031cd82ad4";

    private List<AfatiProvimeve> provimet = new ArrayList<>();
    private OnItemClickListener listener;

    private Context mContext;

    Animation translate_anim;

    public AfatiProvimeveAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @NotNull
    @Override
    public ProvimiHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.provimi_item, parent, false);
        return new ProvimiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProvimiHolder holder, int position) {
        AfatiProvimeve currentAfati = provimet.get(position);
        holder.textViewTitle.setText(currentAfati.getName());
        holder.textViewDita.setText(currentAfati.getDita());

        parseJsonData(currentAfati.getName(), holder.imgProvimi);

//        String oldDate = "06-14-2021";
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(mContext);
        String value = (mSharedPreference.getString(CalendarPickerActivity.DATE_SHARED_PREFERENCE, "Default"));

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar c = Calendar.getInstance();

        try {
            c.setTime(sdf.parse(value));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DAY_OF_MONTH, currentAfati.getDiferenca());
        String newDate = sdf.format(c.getTime());
        holder.textViewData.setText(newDate);
    }

    @Override
    public int getItemCount() {
        return provimet.size();
    }

    public void setProvimet(List<AfatiProvimeve> provimet) {
        this.provimet = provimet;
        notifyDataSetChanged();
    }


    public AfatiProvimeve getProvimiAt(int position) {
        return provimet.get(position);
    }

    class ProvimiHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewData;
        private TextView textViewDita;
        private RelativeLayout mainLayout;
        private ImageView imgProvimi;

        public ProvimiHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_emri);
            textViewData = itemView.findViewById(R.id.text_view_datagjeneruar);
            textViewDita = itemView.findViewById(R.id.text_view_dita);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            imgProvimi = itemView.findViewById(R.id.imgProvimi);

            translate_anim = AnimationUtils.loadAnimation(mContext, R.anim.translate_animation);
            mainLayout.setAnimation(translate_anim);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(provimet.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(AfatiProvimeve afatiProvimeve);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void parseJsonData(String imagePoster, ImageView imageView) {
        StringRequest stringRequest = new StringRequest(BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Provimet");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jo = jsonArray.getJSONObject(i);
                                if (jo.get("provimiTitle").equals(imagePoster)) {
                                    Picasso.get().load(String.valueOf(jo.get("provimiPoster"))).into(imageView);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }, error -> Toast.makeText(mContext, "Fail to get data..", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);
    }

}
