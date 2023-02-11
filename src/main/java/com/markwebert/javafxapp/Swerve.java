package com.markwebert.javafxapp;

public class Swerve {

    private final UIController uiController;

    double lfAngle, lrAngle, rfAngle, rrAngle;

    public Swerve(final UIController uiController) {
        this.uiController = uiController;
    }

    public void drive(final double leftStickX, final double leftStickY, final double rightStickX) {
        final double leftStickAngle = Normalize_Gryo_Value(Math.toDegrees(Math.atan2(leftStickY, -leftStickX)) - 90);
        final double leftStickMagnitude = Math.sqrt(Math.pow(leftStickY, 2) + Math.pow(leftStickX, 2));

        double lfMagnitude, lrMagnitude, rfMagnitude, rrMagnitude;

        if ((leftStickX != 0 || leftStickY != 0) && (rightStickX != 0)) {
            final double[] vectorOne = new double[] { leftStickAngle, leftStickMagnitude };

            // Left Front
            final double[] lfVectorTwo = new double[] { 45, rightStickX };
            final double[] lfResultantVector = addTwoVectors(vectorOne, lfVectorTwo);

            // Left Rear
            final double[] lrVectorTwo = new double[] { -45, rightStickX };
            final double[] lrResultantVector = addTwoVectors(vectorOne, lrVectorTwo);

            // Right Front
            final double[] rfVectorTwo = new double[] { 135, rightStickX };
            final double[] rfResultantVector = addTwoVectors(vectorOne, rfVectorTwo);

            // Right Rear
            final double[] rrVectorTwo = new double[] { -135, rightStickX };
            final double[] rrResultantVector = addTwoVectors(vectorOne, rrVectorTwo);

            // Update the angles
            lfAngle = lfResultantVector[0];
            lrAngle = lrResultantVector[0];
            rfAngle = rfResultantVector[0];
            rrAngle = rrResultantVector[0];

            // Find largest vector
            final double[] largestVector = findVectorWithLargestMagnitude(lfResultantVector, lrResultantVector,
                    rfResultantVector, rrResultantVector);

            // Scale the magnitudes if the largest vector exceeds 1.0
            if (largestVector[1] > 1.0) {
                // Update the magnitudes
                lfMagnitude = App.Map(lfResultantVector[1], 0, largestVector[1], 0, 1.0);
                lrMagnitude = App.Map(lrResultantVector[1], 0, largestVector[1], 0, 1.0);
                rfMagnitude = App.Map(rfResultantVector[1], 0, largestVector[1], 0, 1.0);
                rrMagnitude = App.Map(rrResultantVector[1], 0, largestVector[1], 0, 1.0);
            } else {
                // Update the magnitudes
                lfMagnitude = 0;
                lrMagnitude = 0;
                rfMagnitude = 0;
                rrMagnitude = 0;
            }
        } else if (rightStickX != 0) {
            lfAngle = 45;
            lrAngle = -45;
            rfAngle = 135;
            rrAngle = -135;

            lfMagnitude = rightStickX;
            lrMagnitude = rightStickX;
            rfMagnitude = rightStickX;
            rrMagnitude = rightStickX;
        } else if (leftStickY != 0 || leftStickX != 0) {
            lfAngle = leftStickAngle;
            lrAngle = leftStickAngle;
            rfAngle = leftStickAngle;
            rrAngle = leftStickAngle;

            lfMagnitude = leftStickMagnitude;
            lrMagnitude = leftStickMagnitude;
            rfMagnitude = leftStickMagnitude;
            rrMagnitude = leftStickMagnitude;
        } else {
            lfMagnitude = 0;
            lrMagnitude = 0;
            rfMagnitude = 0;
            rrMagnitude = 0;
        }

        uiController.updateControls(leftStickX, leftStickY, rightStickX, leftStickAngle, leftStickMagnitude);
        uiController.updateWheelAngles(lfAngle, lrAngle, rfAngle, rrAngle);
        uiController.updateWheelMagnitudes(lfMagnitude, lrMagnitude, rfMagnitude, rrMagnitude);
    }

    public static double Normalize_Gryo_Value(final double gyroValue) {
        final double gyroAngle = gyroValue % 360;
        return (gyroAngle < -180 ? gyroAngle + 360 : gyroAngle > 180 ? gyroAngle - 360 : gyroAngle);
    }

    private static double[] vectorToComponent(final double angle, final double magnitude) {
        double xComponent = magnitude * Math.cos(Math.toRadians(angle));
        double yComponent = magnitude * Math.sin(Math.toRadians(angle));
        return new double[] { xComponent, yComponent };
    }

    private static double[] componentToVector(final double xComponent, final double yComponent) {
        double angle = Normalize_Gryo_Value(Math.toDegrees(Math.atan2(yComponent, xComponent)));
        double magnitude = Math.sqrt(Math.pow(yComponent, 2) + Math.pow(xComponent, 2));
        return new double[] { angle, magnitude };
    }

    private static double[] addTwoComponents(final double[] componentOne, final double[] componentTwo) {
        return new double[] { (componentOne[0] + componentTwo[0]), (componentOne[1] + componentTwo[1]) };
    }

    private static double[] addTwoVectors(final double[] vectorOne, final double[] vectorTwo) {
        final double[] componentOne = vectorToComponent(vectorOne[0], vectorOne[1]);
        final double[] componentTwo = vectorToComponent(vectorTwo[0], vectorTwo[1]);
        final double[] addedComponents = addTwoComponents(componentOne, componentTwo);
        return componentToVector(addedComponents[0], addedComponents[1]);
    }

    private static double[] findVectorWithLargestMagnitude(final double[] vector, final double[]... vectors) {
        double[] maxVector = vector;
        for (double[] x : vectors) {
            if (x[1] > maxVector[1]) {
                maxVector = x;
            }
        }

        return maxVector;
    }

}
