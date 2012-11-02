package com.agh.is.systemmonitor.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;

public class ProgressDialog extends Dialog {

	private static final int LAYOUT_ID = R.layout.progress_dialog;
	private TextView dialogLabel = null;
	
	public ProgressDialog(Context context) {
		super(context );
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(LAYOUT_ID);
		dialogLabel = (TextView)findViewById(R.id.progress_dialog_text_label);
	}

	public void setText(String text) {
		dialogLabel.setText(text);
	}
	
}
