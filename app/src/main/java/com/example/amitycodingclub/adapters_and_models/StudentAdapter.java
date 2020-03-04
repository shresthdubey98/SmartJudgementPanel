package com.example.amitycodingclub.adapters_and_models;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.amitycodingclub.Interface.ItemClickListner;
import com.example.amitycodingclub.R;

import java.util.ArrayList;
import java.util.List;

class customViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView nameTextView;
    public ImageView profileImageView;
    public ImageView check;

    ItemClickListner itemClickListner;

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public customViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.card_name);
        profileImageView = itemView.findViewById(R.id.card_dp);
        check = itemView.findViewById(R.id.card_check);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition());
    }
}

public class StudentAdapter extends RecyclerView.Adapter<customViewHolder>  {

    List<Student> students;

    public List<Student> getSelectedStudents() {
        return selectedStudents;
    }

    public StudentAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    List<Student> selectedStudents = new ArrayList<>();
    Context context;

    int row_index = -1; //default no row selected index.

    @NonNull
    @Override
    public customViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View studentView = inflater.inflate(R.layout.student_card,parent,false);
        return new customViewHolder(studentView);
    }

    @Override
    public void onBindViewHolder(@NonNull final customViewHolder holder, int position) {
        holder.nameTextView.setText(students.get(position).getName());
        if(!students.get(position).isChecked()) {
            holder.profileImageView.setImageResource(R.drawable.ic_person);
            holder.check.setVisibility(View.INVISIBLE);
        }else{
            holder.profileImageView.setImageResource(R.drawable.ic_person);
            holder.check.setVisibility(View.VISIBLE);
        }

        holder.setItemClickListner(new ItemClickListner() {
            @Override
            public void onClick(View view, int position) {
                if(!students.get(position).isChecked()) {
                    row_index = position; //set row index to selected position
                    selectedStudents.add(students.get(position));//add selected student to the list.
                    students.get(position).setChecked(true);

                    notifyDataSetChanged();
                    Log.i("selected list",selectedStudents.toString());
                }else {
                    row_index = position;
                    int removeId = selectedStudents.indexOf(students.get(position));
                    students.get(position).setChecked(false);
                    selectedStudents.remove(removeId);//remove particular selected student to the list.
                    notifyDataSetChanged();
                    Log.i("selected list",selectedStudents.toString());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
