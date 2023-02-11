/**
 * Sample Skeleton for 'javafxapp.fxml' Controller Class
 */

package com.markwebert.javafxapp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UIController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="closeButton"
	private Button closeButton; // Value injected by FXMLLoader

	@FXML // fx:id="frontLeftWheel"
	private Rectangle frontLeftWheel; // Value injected by FXMLLoader

	@FXML // fx:id="frontRightWheel"
	private Rectangle frontRightWheel; // Value injected by FXMLLoader

	@FXML // fx:id="leftAngleText"
	private Text leftAngleText; // Value injected by FXMLLoader

	@FXML // fx:id="leftVelocityText"
	private Text leftVelocityText; // Value injected by FXMLLoader

	@FXML // fx:id="leftXText"
	private Text leftXText; // Value injected by FXMLLoader

	@FXML // fx:id="leftYText"
	private Text leftYText; // Value injected by FXMLLoader

	@FXML // fx:id="lfSlewText"
	private Text lfSlewText; // Value injected by FXMLLoader

	@FXML // fx:id="lfVelocityText"
	private Text lfVelocityText; // Value injected by FXMLLoader

	@FXML // fx:id="lrSlewText"
	private Text lrSlewText; // Value injected by FXMLLoader

	@FXML // fx:id="lrVelocityText"
	private Text lrVelocityText; // Value injected by FXMLLoader

	@FXML // fx:id="rearLeftWheel"
	private Rectangle rearLeftWheel; // Value injected by FXMLLoader

	@FXML // fx:id="rearRightWheel"
	private Rectangle rearRightWheel; // Value injected by FXMLLoader

	@FXML // fx:id="rfSlewText"
	private Text rfSlewText; // Value injected by FXMLLoader

	@FXML // fx:id="rfVelocityText"
	private Text rfVelocityText; // Value injected by FXMLLoader

	@FXML // fx:id="rightXText"
	private Text rightXText; // Value injected by FXMLLoader

	@FXML // fx:id="rrSlewText"
	private Text rrSlewText; // Value injected by FXMLLoader

	@FXML // fx:id="rrVelocityText"
	private Text rrVelocityText; // Value injected by FXMLLoader

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert frontLeftWheel != null
				: "fx:id=\"frontLeftWheel\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert frontRightWheel != null
				: "fx:id=\"frontRightWheel\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert leftAngleText != null
				: "fx:id=\"leftAngleText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert leftVelocityText != null
				: "fx:id=\"leftVelocityText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert leftXText != null : "fx:id=\"leftXText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert leftYText != null : "fx:id=\"leftYText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert lfSlewText != null : "fx:id=\"lfSlewText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert lfVelocityText != null
				: "fx:id=\"lfVelocityText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert lrSlewText != null : "fx:id=\"lrSlewText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert lrVelocityText != null
				: "fx:id=\"lrVelocityText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert rearLeftWheel != null
				: "fx:id=\"rearLeftWheel\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert rearRightWheel != null
				: "fx:id=\"rearRightWheel\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert rfSlewText != null : "fx:id=\"rfSlewText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert rfVelocityText != null
				: "fx:id=\"rfVelocityText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert rightXText != null : "fx:id=\"rightXText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert rrSlewText != null : "fx:id=\"rrSlewText\" was not injected: check your FXML file 'javafxapp.fxml'.";
		assert rrVelocityText != null
				: "fx:id=\"rrVelocityText\" was not injected: check your FXML file 'javafxapp.fxml'.";

	}

	@FXML
	public void closeButtonEvent() {
		try {
			System.out.println("Shutting down...");
			((Stage) closeButton.getScene().getWindow()).hide();
			System.runFinalization();
			System.gc();
		} finally {
			System.exit(0);
		}
	}

	public void updateControls(final double leftStickX, final double leftStickY, final double rightStickX,
			final double leftStickAngle, final double leftStickMagnitude) {
		Platform.runLater(() -> {
			leftXText.setText(String.format("%.2f", leftStickX));
			leftYText.setText(String.format("%.2f", leftStickY));
			rightXText.setText(String.format("%.2f", rightStickX));
			leftAngleText.setText(String.format("%.2f", leftStickAngle));
			leftVelocityText.setText(String.format("%.2f", leftStickMagnitude));
		});
	}

	public void updateWheelAngles(final double leftFrontWheelRotation, final double leftRearWheelRotation,
			final double rightFrontWheelRotation, final double rightRearWheelRotation) {
		Platform.runLater(() -> {
			frontLeftWheel.setRotate(leftFrontWheelRotation);
			rearLeftWheel.setRotate(leftRearWheelRotation);
			frontRightWheel.setRotate(rightFrontWheelRotation);
			rearRightWheel.setRotate(rightRearWheelRotation);

			lfSlewText.setText(String.format("%.2f", leftFrontWheelRotation));
			lrSlewText.setText(String.format("%.2f", leftRearWheelRotation));
			rfSlewText.setText(String.format("%.2f", rightFrontWheelRotation));
			rrSlewText.setText(String.format("%.2f", rightRearWheelRotation));
		});
	}

	public void updateWheelMagnitudes(final double lfMagnitude, final double lrMagnitude, final double rfMagnitude,
			final double rrMagnitude) {
		Platform.runLater(() -> {
			lfVelocityText.setText(String.format("%.2f", lfMagnitude));
			lrVelocityText.setText(String.format("%.2f", lrMagnitude));
			rfVelocityText.setText(String.format("%.2f", rfMagnitude));
			rrVelocityText.setText(String.format("%.2f", rrMagnitude));
		});
	}

	public static double Normalize_Gryo_Value(final double gyroValue) {
		final double gyroAngle = gyroValue % 360;
		return (gyroAngle < -180 ? gyroAngle + 360 : gyroAngle > 180 ? gyroAngle - 360 : gyroAngle);
	}

	/**
	 * Returns the shortest gyro displacement in the best direction from the
	 * currentValue to the desiredValue. This function will normalize, scale the
	 * values from -180.0 degrees to 180.0 degrees, both the currentValue and the
	 * desiredValue and will return the displacement as a normalized value as well.
	 * 
	 * @param currentValue The current gyro value in degrees.
	 * @param desiredValue The desired gyro value in dgerees.
	 * @return The shortest gyro displacement representing the best direction to
	 *         reach the desired value with the least amount of turning.
	 */
	public static double Get_Gyro_Displacement(final double currentValue, final double desiredValue) {
		final double displacement = Normalize_Gryo_Value(desiredValue) - Normalize_Gryo_Value(currentValue);
		return (displacement < -180 ? displacement + 360 : displacement > 180 ? displacement - 360 : displacement);
	}

}
