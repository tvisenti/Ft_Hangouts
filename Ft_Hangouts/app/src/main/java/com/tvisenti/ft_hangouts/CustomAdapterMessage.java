package com.tvisenti.ft_hangouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tvisenti on 5/26/17.
 */

public class CustomAdapterMessage extends ArrayAdapter<Message> implements View.OnClickListener {
    private ArrayList<Message> arraySms;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtMessage;
        TextView txtDate;
    }

    public CustomAdapterMessage(ArrayList<Message> data, Context context) {
        super(context, R.layout.row_list_layout, data);
        this.arraySms = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message sms = getItem(position);
        CustomAdapterMessage.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new CustomAdapterMessage.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_list_message_layout, parent, false);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.dateTextView);
            viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.messageTextView);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapterMessage.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtDate.setText(sms.getDate());
        viewHolder.txtMessage.setText(sms.getMessage());
        return convertView;
    }
}
