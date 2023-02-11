package com.markwebert.javafxapp;

/**
 * @author Mark Ebert
 */
public class HelperFunctions {

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

    public static double[] vectorToComponent(final double angle, final double magnitude) {
        double xComponent = magnitude * Math.cos(Math.toRadians(angle));
        double yComponent = magnitude * Math.sin(Math.toRadians(angle));
        return new double[] { xComponent, yComponent };
    }

    public static double[] componentToVector(final double xComponent, final double yComponent) {
        double angle = Normalize_Gryo_Value(Math.toDegrees(Math.atan2(yComponent, xComponent)));
        double magnitude = Math.sqrt(Math.pow(yComponent, 2) + Math.pow(xComponent, 2));
        return new double[] { angle, magnitude };
    }

    public static double[] addTwoComponents(final double[] componentOne, final double[] componentTwo) {
        return new double[] { (componentOne[0] + componentTwo[0]), (componentOne[1] + componentTwo[1]) };
    }

    public static double[] addTwoVectors(final double[] vectorOne, final double[] vectorTwo) {
        final double[] componentOne = vectorToComponent(vectorOne[0], vectorOne[1]);
        final double[] componentTwo = vectorToComponent(vectorTwo[0], vectorTwo[1]);
        final double[] addedComponents = addTwoComponents(componentOne, componentTwo);
        return componentToVector(addedComponents[0], addedComponents[1]);
    }

    public static double[] findVectorWithLargestMagnitude(final double[] vector, final double[]... vectors) {
        double[] maxVector = vector;
        for (double[] x : vectors) {
            if (x[1] > maxVector[1]) {
                maxVector = x;
            }
        }
        return maxVector;
    }

}
