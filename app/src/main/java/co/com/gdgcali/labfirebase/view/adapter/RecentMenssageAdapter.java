package co.com.gdgcali.labfirebase.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import co.com.gdgcali.labfirebase.R;
import co.com.gdgcali.labfirebase.model.Mensaje;
import co.com.gdgcali.labfirebase.view.layout.CircleImageView;

/**
 * Created by user on 09/07/2015.
 */
public class RecentMenssageAdapter extends BaseAdapter {

    private List<Mensaje> listData = new ArrayList<Mensaje>();
    Typeface typeFace;
    private SimpleDateFormat customDateFormat = null;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    private LayoutInflater inflater;
    private int layoutResourceId;
    private Context context;

    public RecentMenssageAdapter(List<Mensaje> listData, int layoutResourceId, Context context) {
        super();
        this.listData = listData;
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ViewHolder holder;
        final Mensaje mensaje = listData.get(position);

        if (row == null) {
            inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, null, true);

            holder = new ViewHolder();
            holder.profilePicImage.setImageResource(R.drawable.ic_launcher);
            holder.txtContent.setText(mensaje.getMensaje());
            holder.txtTime.setText(mensaje.getFecha());
            holder.txtUser.setText(mensaje.getAutor());

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        return row;
    }

    /**
     * Class to hold the row child views so we wont have to inflate more them once per row view.
     */
    class ViewHolder {
        CircleImageView profilePicImage;
        TextView txtTime;
        TextView txtContent;
        TextView txtUser;
    }
}
