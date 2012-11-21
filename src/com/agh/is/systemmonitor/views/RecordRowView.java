package com.agh.is.systemmonitor.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.adapters.RecordClickListener;
import com.agh.is.systemmonitor.domain.Agent;


public class RecordRowView extends RelativeLayout{

	private static final int LAYOUT_ID = R.layout.record_row_view;
	
	private Record record = null;
	private TextView recordNameLabel = null;
	private ImageView recordThumbImage = null;
	private TextView recordDomainLabel = null;
	
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
		recordNameLabel.setText(Record.getName());
		setClassSpecificInformation();
	}
	
	public void setClassSpecificInformation() {
		if (record instanceof Agent) {
			Agent a = (Agent)record;
			recordThumbImage.setBackgroundResource(R.drawable.agent_icon);
			recordDomainLabel.setVisibility(View.VISIBLE);
			recordDomainLabel.setText(a.getDomain());
		} else {
			recordThumbImage.setBackgroundResource(R.drawable.cloud);
			recordDomainLabel.setVisibility(View.GONE);
		}
	}
	
	private void initializeViews() {
		ViewGroup layout = getLayout();
		recordNameLabel = (TextView)layout.findViewById(R.id.record_row_view_record_name);
		recordThumbImage = (ImageView)layout.findViewById(R.id.record_row_view_record_photo);
		recordDomainLabel = (TextView)layout.findViewById(R.id.record_row_view_record_domain);
		setRecord(record);
	}
	
	private ViewGroup getLayout() {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);	
		return (ViewGroup)inflater.inflate(LAYOUT_ID, this, true);
	}	
}
