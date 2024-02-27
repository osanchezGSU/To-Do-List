package com.example.to_dolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.Calendar;

/*
Context: Hover over class name for info. It allows us to access resources. It allows us to
        interact with other Android components by sending messages. It gives you information
        about your app environment.

 */

public class ToDoAdapter extends RecyclerView.Adapter {
    private ArrayList <Memo> memoData;

    // Holds the OnClickListener object passed from the activity
    private View.OnClickListener mOnItemClickListener;

    private boolean isDeleting;

    /* Needed to open the database so the memo can be deleted and to display the message
        if the deletion fails */
    private Context parentContext;

    // This inner class is pretty much like onCreate
    public class MemoViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewMemo;
        public TextView textViewDate;
        public Button deleteButton;
        public ImageView dot1;

        public ImageView dot2;
        public ImageView dot3;


        /* @NonNull indicates that the parameter cannot contain a null value or, if it's before
            a method, that the method cannot return a null value  */
        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMemo = itemView.findViewById(R.id.subjectText);
            textViewDate = itemView.findViewById(R.id.dateText);
            deleteButton = itemView.findViewById(R.id.buttonDeleteMemo);
            dot1 = itemView.findViewById(R.id.dot1);
            dot2 = itemView.findViewById(R.id.dot2);
            dot3 = itemView.findViewById(R.id.dot3);




            // Sets tag so we can identify which item was clicked
            itemView.setTag(this);

            // Sets the ViewHolder's OnClickListener to the listener passed from the activity
            itemView.setOnClickListener(mOnItemClickListener);
        }
        public ImageView getDot1(){
            return dot1;
        }
        public ImageView getDot2(){
            return dot2;
        }
        public ImageView getDot3(){
            return dot3;
        }

        public TextView getTextViewDate() {
            return textViewDate;
        }

        // Will be used by the adapter to set and change the displayed text
        public TextView getMemoTextView() {
            return textViewMemo;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }

    }

    public ToDoAdapter (ArrayList<Memo> arrayList, Context context) {
        memoData = arrayList;
        parentContext = context;
    }

    // Sets up an adapter method so that we can pass the listener from the activity to the adapter
    public void setOnItemClickListener (View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }


    //  Gives layout for each of our rows.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent, false);
        return new MemoViewHolder(v);
    }

    // Called by the RecyclerView to display the data at the s position
    // Tells Adapter to update data on each of our rows based on the RecyclerView position
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {

        // Change value within holder that is passed in
        MemoViewHolder mvh = (MemoViewHolder) holder;
        mvh.getMemoTextView().setText(memoData.get(position).getSubjectInput());
        Calendar date = memoData.get(position).getDate();
        mvh.getTextViewDate().setText(DateFormat.format("MM/dd/yyyy", date));
        Integer criticality = memoData.get(position).getCriticality();


        if (criticality == 1){
           mvh.getDot1().setImageResource(R.drawable.low_priority);
           mvh.getDot2().setImageResource(R.drawable.circle_outline);
           mvh.getDot3().setImageResource(R.drawable.circle_outline);
        }
        else if (criticality == 2){
            mvh.getDot1().setImageResource(R.drawable.medium_priority);
            mvh.getDot2().setImageResource(R.drawable.medium_priority);
            mvh.getDot3().setImageResource(R.drawable.circle_outline);

        }
        else if (criticality == 3){
            mvh.getDot1().setImageResource(R.drawable.high_priority);
            mvh.getDot2().setImageResource(R.drawable.high_priority);
            mvh.getDot3().setImageResource(R.drawable.high_priority);

        }

        if (isDeleting) {
            mvh.getDeleteButton().setVisibility(View.VISIBLE);
            mvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(position); // replace with holder.getHolderPosition
                }
            });
        }
        else {
            mvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }

    }

    // Used to determine how many times the other two methods need to be executed
    // Returns the number of items in the data set
    // Wants to know how much data we have to display to the user
    @Override
    public int getItemCount() {
        return memoData.size();
    }


    private void deleteItem (int position) {
        Memo memo = memoData.get(position);
        ToDoDataSource ds = new ToDoDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteMemo(memo.getId());
            ds.close();

            if (didDelete) {
                memoData.remove(position);
                notifyDataSetChanged(); // refresh display
                Toast.makeText(parentContext, "Delete Success!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
        }
    }

    // A setter since the isDeleting is private
    public void setDelete (boolean b) {
        isDeleting = b;
    }

}
