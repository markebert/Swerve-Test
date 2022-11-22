package com.markwebert.javafxapp;

public class Swerve {

    private final UIController uiController;
    private double leftFrontWheelRotation, leftRearWheelRotation, rightFrontWheelRotation, rightRearWheelRotation;
    private double angle = 0, velocity = 0;

    public Swerve(final UIController uiController) {
        this.uiController = uiController;
    }

    public void drive(double leftStickX, double leftStickY, double rightStickX) {
        uiController.updateControls(leftStickX, leftStickY, rightStickX);

        if (rightStickX != 0) {
            angle = 45;
            velocity = rightStickX;

            leftFrontWheelRotation = angle;
            leftRearWheelRotation = 360 - angle;
            rightFrontWheelRotation = 360 - angle;
            rightRearWheelRotation = angle;
        } else if (leftStickY != 0 || leftStickX != 0) {
            angle = Normalize_Gryo_Value(Math.toDegrees(Math.atan2(leftStickY, -leftStickX)) - 90);
            velocity = Math.sqrt(Math.pow(leftStickY, 2) + Math.pow(leftStickX, 2));

            leftFrontWheelRotation = angle;
            leftRearWheelRotation = angle;
            rightFrontWheelRotation = angle;
            rightRearWheelRotation = angle;
        }

        uiController.updateWheels(leftFrontWheelRotation, leftRearWheelRotation, rightFrontWheelRotation,
                rightRearWheelRotation);
    }

    public static double Normalize_Gryo_Value(final double gyroValue) {
        final double gyroAngle = gyroValue % 360;
        return (gyroAngle < -180 ? gyroAngle + 360 : gyroAngle > 180 ? gyroAngle - 360 : gyroAngle);
    }

}
