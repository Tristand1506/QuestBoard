package UtilLib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sleeplessstudios.mappapp.R;



public class RecyclerViewFavoriteAdapter extends RecyclerView.Adapter<RecyclerViewFavoriteAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewFavoriteAdapter";
    private Context mContext;

    public RecyclerViewFavoriteAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_favourites_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        String load = AccountManager.getActiveAccountData().getFavorites().get(position);
        holder.itemName.setText(load);
        holder.favorite.setChecked(true);
        //holder.lent.setEnabled(load.isLent());

        holder.favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    AccountManager.getActiveAccountData().getFavorites().remove(load);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return AccountManager.getActiveAccountData().getFavorites().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        CheckBox favorite;
        //ImageButton lent;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.fav_placename_btn);
            favorite = itemView.findViewById(R.id.fav_favorite_btn);

            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}
