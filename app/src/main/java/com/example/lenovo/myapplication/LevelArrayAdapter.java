package com.example.lenovo.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.myapplication.database.models.LevelRecord;
import com.example.lenovo.myapplication.models.SLevel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LevelArrayAdapter extends ArrayAdapter<LevelRecord> {
    private Context context;
    private int resource;
    private List<LevelRecord> labels;

    public LevelArrayAdapter(Context context, int resource, List<LevelRecord> labels) {
        super(context, resource, labels);
        this.context = context;
        this.resource = resource;
        this.labels = labels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = ((Activity) context);
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, parent, false);
        }

        TextView textView1 = convertView.findViewById(R.id.item_level_index);
        textView1.setText(labels.get(position).name);

        TextView textView2 = convertView.findViewById(R.id.item_level_name);
        textView2.setText(labels.get(position).description);

        CircleImageView circleImageView = convertView.findViewById(R.id.circle_image_view);
        if(labels.get(position).order==1){
            circleImageView.setImageResource(R.drawable.fruits);
        }
        else if(labels.get(position).order==3){
            circleImageView.setImageResource(R.drawable.university);
        }
        else if(labels.get(position).order==2){
            circleImageView.setImageResource(R.drawable.school);
        }
        else if(labels.get(position).order==4){
            circleImageView.setImageResource(R.drawable.business);
        }

        /*ImageView imageView1 = convertView.findViewById(R.id.lock);
            if(labels.get(position).locked==true) {
                imageView1.setImageResource(R.drawable.unlock);
            }
            else{
                imageView1.setImageResource(R.drawable.lock);
            }*/

       /* ImageView imageView2= convertView.findViewById(R.id.chart);
            if(labels.get(position).expertness>=80){
                imageView2.setImageResource(R.drawable.chart);
            }
            else if(labels.get(position).expertness>=50){
                imageView2.setImageResource(R.drawable.chart2);
            }
            else {
                imageView2.setImageResource(R.drawable.chart1);
            }*/

        return convertView;
    }
}