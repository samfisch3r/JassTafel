package ch.zhaw.jasstafel;

import ch.zhaw.jasstafel.R;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

public class ChooseLimitDialog extends DialogFragment{
	
	public interface LimitDialogListener {
		void onLimitOkButtonClick(int limit);
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = li.inflate(R.layout.limit_popup, null);
		final NumberPicker pick = (NumberPicker) view.findViewById(R.id.number_picker_in_fragment);
		Limit limit = new Limit();
		pick.setMaxValue(limit.getLength()-1);
		pick.setMinValue(0);
		pick.setDisplayedValues(limit.getLimits());
		pick.setValue(limit.getLength()/2);
		pick.setFocusable(true);
		pick.setFocusableInTouchMode(true);
		
		builder.setTitle("W\u00E4hl s Limit")
			.setView(view)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					LimitDialogListener activity = (LimitDialogListener) getActivity();
					activity.onLimitOkButtonClick(pick.getValue());
				}
			});
		setCancelable(false);
		return builder.create();
	}
}
