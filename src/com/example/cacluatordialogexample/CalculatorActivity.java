package com.example.cacluatordialogexample;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends Activity {
	private float lastValue;
	private static final int INVALID_OPERATION = -1;
	private int lastOperation = INVALID_OPERATION;
	private TextView displayView;
	private String invalidOperation;
	private boolean clearLastValue = true;
	private static final DecimalFormat newFormat = new DecimalFormat("0.00");
	private Button zero, one, two, three, four, five, six, seven, eight, nine, clear, equals, plus, minus, multiply,
			divide, decimal;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);
		invalidOperation = getResources().getString(R.string.invalid_operation);
		displayView = (TextView) findViewById(R.id.calculator_display);
		// Numbers
		one = getButton(R.id.calculator_one);
		two = getButton(R.id.calculator_two);
		three = getButton(R.id.calculator_three);
		four = getButton(R.id.calculator_four);
		five = getButton(R.id.calculator_five);
		six = getButton(R.id.calculator_six);
		seven = getButton(R.id.calculator_seven);
		eight = getButton(R.id.calculator_eight);
		nine = getButton(R.id.calculator_nine);
		zero = getButton(R.id.calculator_zero);
		decimal = getButton(R.id.calculator_decimal);

		// Operations
		plus = getButton(R.id.calculator_plus);
		minus = getButton(R.id.calculator_minus);
		multiply = getButton(R.id.calculator_multiply);
		divide = getButton(R.id.calculator_divide);
		equals = getButton(R.id.calculator_equals);

		clear = getButton(R.id.calculator_clear);
	}

	private void performPreviousOperation() {
		switch (lastOperation) {
		case R.id.calculator_divide:
			handleDivideClick();
			break;
		case R.id.calculator_multiply:
			handleMultiplyClick();
			break;
		case R.id.calculator_minus:
			handleMinusClick();
			break;
		case R.id.calculator_plus:
			handlePlusClick();
			break;
		default:
			// Set the last value to the current one in the case of the first
			// operation pressed
			lastValue = getDisplayValue();
			break;
		}
		clearLastValue = true;
	}

	public void clear() {
		lastValue = 0;
		lastOperation = INVALID_OPERATION;
		setDisplay(lastValue);
	}

	public void handleButtonClick(Button button) {
		int buttonId = button.getId();
		switch (buttonId) {
		case R.id.calculator_clear:
			clear();
			break;
		case R.id.calculator_equals:
			handleEqualsClick();
			break;
		case R.id.calculator_divide:
		case R.id.calculator_multiply:
		case R.id.calculator_minus:
		case R.id.calculator_plus:
			performPreviousOperation();
			lastOperation = buttonId;
			break;
		default:
			updateDisplayValue(button);
			break;
		}
	}

	private Button getButton(int buttonId) {
		Button button = (Button) findViewById(buttonId);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleButtonClick(v);
			}
		});
		return button;
	}

	public void handleButtonClick(View v) {
		if (v instanceof Button) {
			handleButtonClick((Button) v);
		}
	}

	private void setDisplay(float value) {
		String text = newFormat.format(value);
		displayView.setText(text);
	}

	private void updateDisplayValue(Button button) {
		StringBuilder builder = new StringBuilder(displayView.getText());
		if (clearLastValue) {
			builder.delete(0, builder.capacity() - 1);
			clearLastValue = false;
		}
		builder.append(button.getText());
		displayView.setText(builder.toString());
	}

	private float getDisplayValue() {
		return Float.valueOf(displayView.getText().toString());
	}

	private void handlePlusClick() {
		setDisplay(lastValue += getDisplayValue());
	}

	private void handleMinusClick() {
		setDisplay(lastValue -= getDisplayValue());
	}

	private void handleMultiplyClick() {
		setDisplay(lastValue *= getDisplayValue());
	}

	private void handleDivideClick() {
		float displayValue = getDisplayValue();
		if (displayValue == 0) {
			clear();
			Toast.makeText(this, invalidOperation, Toast.LENGTH_SHORT).show();
		} else {
			lastValue /= getDisplayValue();
		}
		setDisplay(lastValue);
	}

	private void handleEqualsClick() {
		performPreviousOperation();
	}
}
