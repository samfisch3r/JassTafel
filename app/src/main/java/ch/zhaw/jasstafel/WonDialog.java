package ch.zhaw.jasstafel;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class WonDialog extends DialogFragment {
	
	private int team;
	private int points1;
	private int points2;
	
	public interface WonDialogListener {
		void onWonOkButtonClick();
	}
	
	public WonDialog(int team, int points1, int points2) {
		this.team = team;
		this.points1 = points1;
		this.points2 = points2;
	}
	
	@SuppressLint("InflateParams")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = li.inflate(R.layout.won_popup, null);
		TextView textWon = (TextView)view.findViewById(R.id.text_won);
		textWon.setText("Team " + team + " h\u00E4t gunne");
		TextView textPoints1 = (TextView)view.findViewById(R.id.won_points1);
		textPoints1.setText(String.valueOf(points1));
		TextView textPoints2 = (TextView)view.findViewById(R.id.won_points2);
		textPoints2.setText(String.valueOf(points2));
		builder.setTitle("Gratulier\u00E4")
			.setView(view)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					WonDialogListener activity = (WonDialogListener) getActivity();
					activity.onWonOkButtonClick();
				}
			});
		setCancelable(false);
		return builder.create();
	}

}
