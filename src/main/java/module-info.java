module com.markwebert.javafxapp {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;

	requires jxinput;

	opens com.markwebert.javafxapp to javafx.fxml;
	exports com.markwebert.javafxapp;
}
