package com.agh.is.systemmonitor.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;

public class OperationResultDialog extends Dialog {

	private static final int LAYOUT_ID = R.layout.operation_result_dialog;
	
	private ImageView resultImage;
	private TextView dialogLabel; 
	private TextView okButton;
	
	public OperationResultDialog(Context context) {
		super(context );
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(LAYOUT_ID);
		dialogLabel = (TextView)findViewById(R.id.operation_result_dialog_text);
		resultImage = (ImageView)findViewById(R.id.operation_result_dialog_image);
		okButton = (TextView)findViewById(R.id.operation_result_dialog_button);
	}

	public void showSuccessfulMessage(String message, final android.view.View.OnClickListener okButtonListener) {
		resultImage.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.operation_result_dialog_success_icon));
		showMessage(message, okButtonListener);

	}
	
	public void showFailureMessage(String message, final android.view.View.OnClickListener okButtonListener) {
		resultImage.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.operation_result_dialog_error_icon));
		showMessage(message, okButtonListener);
	}
	
	private void showMessage(String message, final android.view.View.OnClickListener listener) {
		dialogLabel.setText(message);
		setUpOkButtonListener(listener);
	}
		
	private void setUpOkButtonListener(final android.view.View.OnClickListener listener) {
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				OperationResultDialog.this.dismiss();
				listener.onClick(v);
			}
		});
	}
}
