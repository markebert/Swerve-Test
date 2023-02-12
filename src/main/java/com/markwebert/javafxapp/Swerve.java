package com.markwebert.javafxapp;

import static com.markwebert.javafxapp.HelperFunctions.addTwoVectors;
import static com.markwebert.javafxapp.HelperFunctions.findVectorWithLargestMagnitude;
import static com.markwebert.javafxapp.HelperFunctions.Map;

/**
 * @author Mark Ebert
 */
public class Swerve {

    private final UIController uiController;
    private volatile double lfAngle, lrAngle, rfAngle, rrAngle;

    public Swerve(final UIController uiController) {
        this.uiController = uiController;
    }

    public void drive(final double leftStickAngle, final double leftStickMagnitude, final double rightStickMagnitude) {
        final double lfMagnitude, lrMagnitude, rfMagnitude, rrMagnitude;
        if (leftStickMagnitude != 0 || rightStickMagnitude != 0) {
            final double[] leftStickVector = new double[] { leftStickAngle, leftStickMagnitude };

            // Left Front
            final double[] lfResultantVector = addTwoVectors(leftStickVector, new double[] { 45, rightStickMagnitude });
            final double[] lrResultantVector = addTwoVectors(leftStickVector, new double[] { -45, rightStickMagnitude });
            final double[] rfResultantVector = addTwoVectors(leftStickVector, new double[] { 135, rightStickMagnitude });
            final double[] rrResultantVector = addTwoVectors(leftStickVector, new double[] { -135, rightStickMagnitude });

            // Update the angles
            lfAngle = lfResultantVector[0];
            lrAngle = lrResultantVector[0];
            rfAngle = rfResultantVector[0];
            rrAngle = rrResultantVector[0];

            // Find vector with the largest magnitude
            final double[] largestVector = findVectorWithLargestMagnitude(lfResultantVector, lrResultantVector, rfResultantVector, rrResultantVector);

            // Scale the magnitudes if the largest vector exceeds 1.0
            if (largestVector[1] > 1.0) {
                // Update the magnitudes
                lfMagnitude = Map(lfResultantVector[1], 0, largestVector[1], 0, 1.0);
                lrMagnitude = Map(lrResultantVector[1], 0, largestVector[1], 0, 1.0);
                rfMagnitude = Map(rfResultantVector[1], 0, largestVector[1], 0, 1.0);
                rrMagnitude = Map(rrResultantVector[1], 0, largestVector[1], 0, 1.0);
            } else {
                // Update the magnitudes
                lfMagnitude = lfResultantVector[1];
                lrMagnitude = lrResultantVector[1];
                rfMagnitude = rfResultantVector[1];
                rrMagnitude = rrResultantVector[1];
            }
        } else {
            // Keep the current wheel angles but update the magnitudes
            lfMagnitude = 0;
            lrMagnitude = 0;
            rfMagnitude = 0;
            rrMagnitude = 0;
        }

        // Update the UI
        uiController.updateWheelAngles(lfAngle, lrAngle, rfAngle, rrAngle);
        uiController.updateWheelMagnitudes(lfMagnitude, lrMagnitude, rfMagnitude, rrMagnitude);
    }

}
