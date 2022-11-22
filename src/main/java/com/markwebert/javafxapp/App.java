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

/**
 *
 * @author Mark Ebert
 *
 */
public class App extends Application {

	private Swerve swerve;

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
		primaryStage.setTitle("JavaFX App");
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

		swerve = new Swerve(loader.getController());

		// System.out.println(XInputDevice14.isAvailable());
		final XInputDevice14 device = XInputDevice14.getDeviceFor(0); // or devices[0]
		new Thread(() -> {
			try {
				while (true) {
					// Poll the device
					if (device.poll()) {
						// Retrieve the components
						XInputComponents components = device.getComponents();
						XInputButtons buttons = components.getButtons();
						XInputAxes axes = components.getAxes();

						swerve.drive(Deadzone_With_Map(.1, axes.lx), Deadzone_With_Map(.1, axes.ly),
								Deadzone_With_Map(.1, axes.rx));
					}
					Thread.sleep(20);
				}
			} catch (Exception e) {
				// Do Nothing
			}
		}).start();

	}

	/**
	 * This function returns a value adjusted for the inputed deadzone.
	 * 
	 * @param deadzoneRange The range that will be used when adjusting the inputed
	 *                      value.
	 * @param currentValue  The original unaltered value that should range from -1.0
	 *                      to 1.0.
	 * @return The value adjusted for the given deadzone range.
	 */
	public static double Deadzone(double deadzoneRange, final double currentValue) {
		deadzoneRange = Math.abs(deadzoneRange);
		return (currentValue <= -deadzoneRange || currentValue >= deadzoneRange ? currentValue : 0);
	}

	public static double Deadzone_With_Map(double deadzoneRange, final double currentValue) {
		deadzoneRange = Math.abs(deadzoneRange);
		if (currentValue <= -deadzoneRange || currentValue >= deadzoneRange) {
			if (currentValue > 0) {
				return Map(currentValue, deadzoneRange, 1.0, 0.0, 1.0);
			}
			return Map(currentValue, -1.0, -deadzoneRange, -1.0, 0.0);
		}
		return 0;
	}

	public static int Map(int x, int in_min, int in_max, int out_min, int out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

	public static double Map(double x, double in_min, double in_max, double out_min, double out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

}
