package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<String>{
	private final Activity context;
	private final String[] numbers;
	public ListAdapter(Activity context, String[] numbers) {
		super(context, R.layout.list_row, numbers);
		this.context = context;
		this.numbers = numbers;
	}
		static class ViewHolder {
			public TextView textViewName;
			public TextView textViewNumber;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = context.getLayoutInflater();
				rowView = inflater.inflate(R.layout.list_row, null, true);
				rowView.setBackgroundResource(R.drawable.bg_white_row);
				holder = new ViewHolder();
				holder.textViewName = (TextView) rowView.findViewById(R.id.textViewName);
				holder.textViewNumber = (TextView) rowView.findViewById(R.id.textViewNumber);
				rowView.setTag(holder);
			} else {
				holder = (ViewHolder) rowView.getTag();
			}
			String[] arrayString = numbers[position].split(";");
			String blockName = arrayString[1];
			String blockNumber = arrayString[0];
			holder.textViewName.setText(blockName);
			holder.textViewNumber.setText(blockNumber);
			return rowView;
		}
}
