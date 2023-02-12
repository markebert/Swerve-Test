package com.markwebert.javafxapp;

import com.github.strikerx3.jxinput.XInputAxes;
import com.github.strikerx3.jxinput.XInputButtons;
import com.github.strikerx3.jxinput.XInputComponents;
import com.github.strikerx3.jxinput.XInputDevice14;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import static com.markwebert.javafxapp.HelperFunctions.Deadzone_With_Map;
import static com.markwebert.javafxapp.HelperFunctions.Normalize_Gryo_Value;

/**
 *
 * @author Mark Ebert
 *
 */
public class App extends Application {

	private Swerve swerve;
	private UIController uiController;;

	/**
	 * Starting point of the application
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Starting point for the creation and setting up the primary stage of the
	 * JavaFX application
	 *
	 * @param primaryStage The primary stage of the JavaFX application
	 * @throws Exception Any exception not caught within the start method will be
	 *                   passed on.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load in the UI from the FXML file
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/javafxapp.fxml"));
		Scene scene = new Scene((Parent) loader.load());

		// Setup the primary stage
		primaryStage.setTitle("Swerve Test Viewer");
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(e -> {
			try {
				System.out.println("Shutting down...");
				primaryStage.hide();
				System.runFinalization();
				System.gc();
			} finally {
				System.exit(0);
			}
		});

		uiController = loader.getController();
		swerve = new Swerve(uiController);

		// Get the first plugged in controller
		final XInputDevice14 device = XInputDevice14.getDeviceFor(0); // or devices[0]
		// Start a new thread to fetch the data
		new Thread(() -> {
			try {
				while (true) {
					// Poll the device
					if (device.poll()) {
						// Retrieve the components from the controller
						XInputComponents components = device.getComponents();
						XInputButtons buttons = components.getButtons();
						XInputAxes axes = components.getAxes();

						// Get the required values from the components
						final double leftStickX = Deadzone_With_Map(.1, -axes.lx);
						final double leftStickY = Deadzone_With_Map(.1, axes.ly);
						final double rightStickX = Deadzone_With_Map(.1, axes.rx);

						double leftStickAngle, leftStickMagnitude;
						if (leftStickX != 0 || leftStickY != 0) {
							leftStickAngle = Normalize_Gryo_Value(Math.toDegrees(Math.atan2(leftStickY, leftStickX)) - 90);
							leftStickMagnitude = Math.sqrt(Math.pow(leftStickY, 2) + Math.pow(leftStickX, 2));
							if (leftStickMagnitude > 1.0) {
								leftStickMagnitude = 1;
							}
						} else {
							leftStickAngle = 0;
							leftStickMagnitude = 0;
						}

						// Update the controls text of the UI
						uiController.updateControls(leftStickX, leftStickY, rightStickX, leftStickAngle, leftStickMagnitude);

						// Perform the simulation
						swerve.drive(leftStickAngle, leftStickMagnitude, rightStickX);
					}
					// Sleep for 20ms
					Thread.sleep(20);
				}
			} catch (Exception e) {
				// Do Nothing
			}
		}).start();
	}

}
