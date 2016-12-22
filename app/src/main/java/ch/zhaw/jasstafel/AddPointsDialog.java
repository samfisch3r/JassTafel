package ch.zhaw.jasstafel;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class AddPointsDialog extends DialogFragment {
	
	private int team;
	private int enteredPoint = 0;
	private int multi = 0;
	private int pointsTeam1 = 0;
	private int pointsTeam2 = 0;
	private boolean special = false;
	private View view;
	private TextView textPointsTeam1;
	private TextView textPointsTeam2;
	
	public interface PointsDialogListener {
		void onAddPointsButtonClick(int pTeam1, int pTeam2);
	}
	
	public AddPointsDialog(int team) {
		this.team = team;
	}
	
	private void calculatePoints() {
		if (enteredPoint > 157 && !special) {
			pointsTeam1 = 0;
			pointsTeam2 = 0;
			return;
		}
		if (team == 1) {
			if (enteredPoint == 157) {
				pointsTeam1 = 257 * multi;
			} else {
				pointsTeam1 = enteredPoint * multi;
				if (special) {
					pointsTeam2 = 0;
					return;
				}
			}
			pointsTeam2 = (157 - enteredPoint) * multi;
		} else {
			if (enteredPoint == 157) {
				pointsTeam2 = 257 * multi;
			} else {
				pointsTeam2 = enteredPoint * multi;
				if (special) {
					pointsTeam1 = 0;
					return;
				}
			}
			pointsTeam1 = (157 - enteredPoint) * multi;
		}
	}
	
	private void setResultText() {
		calculatePoints();
		textPointsTeam1 = (TextView)view.findViewById(R.id.text_points_1);
		textPointsTeam2 = (TextView)view.findViewById(R.id.text_points_2);
		textPointsTeam1.setText(String.valueOf(pointsTeam1));
		textPointsTeam2.setText(String.valueOf(pointsTeam2));
	}
	
	@SuppressLint({ "InflateParams", "ServiceCast" })
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		final Context context = builder.getContext();
		LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = li.inflate(R.layout.points_popup, null);
		
		final TextView textMulti = (TextView)view.findViewById(R.id.text_multi);
		final TextView textPoints = (TextView)view.findViewById(R.id.text_points);
		final EditText editTextPoints = (EditText)view.findViewById(R.id.result_points);
		final Button stoeckButton = (Button)view.findViewById(R.id.button_stoeck);
		final Button weisButton = (Button)view.findViewById(R.id.button_weis);
		final Button matchButton = (Button)view.findViewById(R.id.button_match);
		final Spinner weisSpinner = (Spinner)view.findViewById(R.id.weis_spinner);
		ImageButton imgEichel = (ImageButton)view.findViewById(R.id.image_eichel);
		ImageButton imgRose = (ImageButton)view.findViewById(R.id.image_rose);
		ImageButton imgSchelle = (ImageButton)view.findViewById(R.id.image_schelle);
		ImageButton imgSchild = (ImageButton)view.findViewById(R.id.image_schild);
		final ImageButton imgUp = (ImageButton)view.findViewById(R.id.image_up);
		final ImageButton imgDown = (ImageButton)view.findViewById(R.id.image_down);
		
		stoeckButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				weisButton.setVisibility(View.INVISIBLE);
				matchButton.setVisibility(View.INVISIBLE);
				textPoints.setVisibility(View.GONE);
				editTextPoints.setVisibility(View.GONE);
				textMulti.setVisibility(View.GONE);
				imgUp.setVisibility(View.INVISIBLE);
				imgDown.setVisibility(View.INVISIBLE);
				InputMethodManager iMM = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
				iMM.hideSoftInputFromWindow(editTextPoints.getWindowToken(), 0);
				enteredPoint = 20;
				special = true;
				if (multi > 2) {
					multi = 0;
				}
				setResultText();
			}
		});
		weisButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stoeckButton.setVisibility(View.INVISIBLE);
				matchButton.setVisibility(View.INVISIBLE);
				textPoints.setVisibility(View.INVISIBLE);
				editTextPoints.setVisibility(View.GONE);
				textMulti.setVisibility(View.GONE);
				InputMethodManager iMM = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
				iMM.hideSoftInputFromWindow(editTextPoints.getWindowToken(), 0);
				special = true;
				ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.weis_array, android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				weisSpinner.setAdapter(adapter);
				weisSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						switch (position) {
						case 0:
							enteredPoint = 20;
							break;
						case 1:
							enteredPoint = 50;
							break;
						case 2:
							enteredPoint = 100;
							break;
						case 3:
							enteredPoint = 150;
							break;
						case 4:
							enteredPoint = 200;
							break;
						case 5:
							enteredPoint = 250;
							break;
						case 6:
							enteredPoint = 300;
							break;
						case 7:
							enteredPoint = 100;
							break;
						case 8:
							enteredPoint = 200;
							break;
						case 9:
							enteredPoint = 150;
							break;
						default:
							break;
						}
						setResultText();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						enteredPoint = 20;
					}
				});
				weisSpinner.setVisibility(View.VISIBLE);
			}
		});
		matchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stoeckButton.setVisibility(View.INVISIBLE);
				weisButton.setVisibility(View.INVISIBLE);
				textPoints.setVisibility(View.GONE);
				editTextPoints.setVisibility(View.GONE);
				textMulti.setVisibility(View.GONE);
				InputMethodManager iMM = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
				iMM.hideSoftInputFromWindow(editTextPoints.getWindowToken(), 0);
				enteredPoint = 157;
				special = true;
				setResultText();
			}
		});
		
		imgEichel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textMulti.setText("x1");
				multi = 1;
				if (enteredPoint > 0) {
					setResultText();
				}
			}
		});
		imgRose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textMulti.setText("x1");
				multi = 1;
				if (enteredPoint > 0) {
					setResultText();
				}
			}
		});
		imgSchelle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textMulti.setText("x2");
				multi = 2;
				if (enteredPoint > 0) {
					setResultText();
				}
			}
		});
		imgSchild.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textMulti.setText("x2");
				multi = 2;
				if (enteredPoint > 0) {
					setResultText();
				}
			}
		});
		imgUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textMulti.setText("x3");
				multi = 3;
				if (enteredPoint > 0) {
					setResultText();
				}
			}
		});
		imgDown.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				textMulti.setText("x3");
				multi = 3;
				if (enteredPoint > 0) {
					setResultText();
				}
			}
		});
		
		editTextPoints.setImeActionLabel("Enter", KeyEvent.KEYCODE_ENTER);
		editTextPoints.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
					if (!editTextPoints.getText().toString().matches("")) {
						enteredPoint = Integer.parseInt(editTextPoints.getText().toString());
						setResultText();
						return true;
					}
				}
				return false;
			}
		});
		
		builder.setTitle("Noii P\u00FCnkt - Team " + team)
			.setView(view)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					// Button function is overwritten to only work on correct input
				}
			})
			.setNegativeButton("Abbruch", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					PointsDialogListener listener = (PointsDialogListener) getActivity();
					listener.onAddPointsButtonClick(0, 0);
				}
			});
	setCancelable(false);
	return builder.create();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		AlertDialog d = (AlertDialog)getDialog();
		if (d != null) {
			Button posButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
			posButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					textPointsTeam1 = (TextView)view.findViewById(R.id.text_points_1);
					textPointsTeam2 = (TextView)view.findViewById(R.id.text_points_2);
					if (textPointsTeam1.getText() == "" || textPointsTeam2.getText() == "") {
						pointsTeam1 = 0;
						pointsTeam2 = 0;
					}
					Boolean wantToCloseDialog = false;
					if (pointsTeam1 > 0 || pointsTeam2 > 0) {
						wantToCloseDialog = true;
					}
					if (wantToCloseDialog) {
						PointsDialogListener listener = (PointsDialogListener) getActivity();
						listener.onAddPointsButtonClick(pointsTeam1, pointsTeam2);
						dismiss();
					}
				}
			});
		}
	}
}
