package pnrs.rtrk.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ListElement> mElements;
    private RadioButton rBtn = null;

    public CustomAdapter(Context context){
        mContext = context;
        mElements = new ArrayList<ListElement>();
    }

    public void addElement(ListElement element){
            mElements.add(element);
            notifyDataSetChanged();
    }

    public boolean containsElement(ListElement element){
        boolean ind = false;
        for (ListElement el : mElements) {
            if (el.mText.equals(element.mText))
                ind = true;
        }
        return ind;
    }

    public void removeElement(int position){
        mElements.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mElements.size();
    }

    @Override
    public Object getItem(int position) {
        Object obj = null;
        try {
            obj = mElements.get(position);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }

        return obj;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.element_row, null);
            ViewHolder holder = new ViewHolder();
            holder.tw = (TextView) view.findViewById(R.id.textEl);
            holder.rb = (RadioButton) view.findViewById(R.id.radioBtn);
            holder.rb1 = (Button) view.findViewById(R.id.buttonEl);
            view.setTag(holder);
        }

        ListElement element = (ListElement) getItem(position);
        final ViewHolder holder = (ViewHolder) view.getTag();
        holder.tw.setText(element.mText);
        holder.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(mContext, DetailsActivity.class);
                switchActivity.putExtra("town", holder.tw.getText());
                mContext.startActivity(switchActivity);

                rBtn = (RadioButton) v;
                rBtn.setChecked(false);
            }
        });

        holder.rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchActivity = new Intent(mContext, DetailsActivity.class);
                switchActivity.putExtra("town", holder.tw.getText());
                mContext.startActivity(switchActivity);
            }
        });

        return view;
    }

    private class ViewHolder {
        public TextView tw = null;
        public RadioButton rb = null;
        public Button rb1 = null;
    }
}
