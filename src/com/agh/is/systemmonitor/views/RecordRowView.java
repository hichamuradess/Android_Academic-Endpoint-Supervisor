package com.agh.is.systemmonitor.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.adapters.RecordClickListener;


public class RecordRowView extends RelativeLayout{

	private static final int LAYOUT_ID = R.layout.record_row_view;
	
	private Record record = null;
	private TextView RecordNameLabel = null;
	
	public RecordRowView(Context context, Record record, final RecordClickListener listener) {
		super(context);
		this.record = record;
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onClick(getRecord());
			}
		});
		initializeViews();
	}
	
	public Record getRecord() {
		return record;
	}

	public void setRecord(Record Record) {
		this.record = Record;
		RecordNameLabel.setText(Record.getName());
	}
	
	private void initializeViews() {
		ViewGroup layout = getLayout();
		RecordNameLabel = (TextView)layout.findViewById(R.id.record_row_view_record_name);
		RecordNameLabel.setText(record.getName());
	}
	
	private ViewGroup getLayout() {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);	
		return (ViewGroup)inflater.inflate(LAYOUT_ID, this, true);
	}	
}
