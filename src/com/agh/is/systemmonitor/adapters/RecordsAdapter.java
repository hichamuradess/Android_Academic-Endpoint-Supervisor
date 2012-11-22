package com.agh.is.systemmonitor.adapters;

import java.util.List;

import com.agh.is.systemmonitor.views.RecordRowView;
import com.google.common.collect.Lists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class RecordsAdapter extends BaseAdapter {


	private List<Record> records;
	private RecordClickListener listener;

	public RecordsAdapter(Context context, List<Record> records, RecordClickListener listener) {
		this.records = records;
		this.listener = listener;
	}

	@Override
	public int getCount() {
		return records.size();
	}

	public void setRecords(List<Record> records) {
		this.records = Lists.newLinkedList(records);
	}
	
	@Override
	public Object getItem(int position) {
		return records.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RecordRowView rowView;
		if (convertView == null) {
			rowView = new RecordRowView(parent.getContext(), records.get(position), listener);
		} else {
			rowView = (RecordRowView)convertView;
			rowView.setRecord(records.get(position));
		}
		return rowView;
	}

}