package com.example.andrey.luckytube;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public final int TYPE_FIRST_ITEM = 0;
    public final int TYPE_ITEM = 1;

    public static String lastTagValue;
    public static String lastDurValue;
    public static String lastEdtValue;

    public static TextView videoTitle;
    public static ImageView previewImage;
    public static CardView cv;
    public static Spinner tags;
    public static Spinner duration;
    public static Button gen;
    public static EditText myTag;
    public static Space space;
    public static TextView history;

    public class EmptyHistory extends RecyclerView.ViewHolder{

        public EmptyHistory(View itemView) {
            super(itemView);
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        VideoViewHolder(final View itemView) {
            super(itemView);
            itemView.setTag(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            videoTitle = (TextView)itemView.findViewById(R.id.video_title);
            previewImage = (ImageView)itemView.findViewById(R.id.video_preview);
            history = (TextView)itemView.findViewById(R.id.history);
        }
    }

    public class BigViewHolder extends RecyclerView.ViewHolder {

        BigViewHolder(View itemView) {
            super(itemView);

            tags = (Spinner) itemView.findViewById(R.id.tags);
            tags.setSelection(getIndex(tags, lastTagValue));
            duration = (Spinner) itemView.findViewById(R.id.duration);
            duration.setSelection(getIndex(duration, lastDurValue));
            gen = (Button) itemView.findViewById(R.id.gen);
            space = (Space) itemView.findViewById(R.id.space);
            myTag = (EditText) itemView.findViewById(R.id.ownTag);
            myTag.setVisibility(View.INVISIBLE);
            myTag.setText(lastEdtValue);

            tags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (tags.getSelectedItem().equals("Свой тег")){
                        myTag.setVisibility(View.VISIBLE);
                    }else{ myTag.setVisibility(View.INVISIBLE); }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }
    }

    public static int getIndex(Spinner spinner, String myString){
        for (int i=0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_FIRST_ITEM) {
            View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
            return new BigViewHolder(v1);
        }
        View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new VideoViewHolder(v2);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        if(holder.getItemViewType() == TYPE_FIRST_ITEM){
            return;
        }
        try{
            videoTitle.setText(MainActivity.videoMyClass.get(position).title);
            previewImage.setImageBitmap(MainActivity.videoMyClass.get(position).preview);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.videoId = MainActivity.videoMyClass.get(pos).videoId;

                    VideoMyClass item = MainActivity.videoMyClass.get(pos);
                    MainActivity.videoMyClass.remove(pos);
                    MainActivity.videoMyClass.add(1, item);
                    space.callOnClick();
                }
            });
        }
        catch (Exception e) {}
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FIRST_ITEM;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return MainActivity.videoMyClass.size();
    }
}
