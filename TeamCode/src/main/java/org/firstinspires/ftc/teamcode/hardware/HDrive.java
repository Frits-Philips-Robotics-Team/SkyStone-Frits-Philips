package org.firstinspires.ftc.teamcode.hardware;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class HDrive {
    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor midDrive;
    private ElapsedTime cycleTime = new ElapsedTime();

    private static final int CYCLE_MS = 40;
    private double maxSpeed;
    private double increment;

    double leftPowerCurrent;
    double rightPowerCurrent;
    double midPowerCurrent;

    public void init(HardwareMap hwMap) {
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        midDrive = hwMap.get(DcMotor.class, "mid_drive");

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        midDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        midDrive.setDirection(DcMotor.Direction.FORWARD);

        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        midDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        maxSpeed = 0.7;
        increment = 0.08;
        leftPowerCurrent = 0;
        rightPowerCurrent = 0;
        midPowerCurrent = 0;
    }

    public void drive(double forwardSpeed, double rightSpeed, double rotateSpeed) {
        double LDriveSpeed = Range.clip(forwardSpeed + rotateSpeed, -maxSpeed, maxSpeed);
        double RDriveSpeed = Range.clip(forwardSpeed - rotateSpeed, -maxSpeed, maxSpeed);
        double midDriveSpeed = Range.clip(rightSpeed, -maxSpeed, maxSpeed);

        accelToSpeed(LDriveSpeed, RDriveSpeed, midDriveSpeed);
    }

    private void accelToSpeed(double LDriveSpeed, double RDriveSpeed, double midDriveSpeed) {
        if (cycleTime.milliseconds() > CYCLE_MS) {

            if (LDriveSpeed > leftPowerCurrent) {
                leftPowerCurrent = Range.clip(leftPowerCurrent + increment, -1, LDriveSpeed);
                leftDrive.setPower(maxSpeed * leftPowerCurrent);
            } else if (LDriveSpeed < leftPowerCurrent) {
                leftPowerCurrent = Range.clip(leftPowerCurrent - increment, LDriveSpeed, 1);
                leftDrive.setPower(maxSpeed * leftPowerCurrent);
            } else {
                leftDrive.setPower(maxSpeed * leftPowerCurrent);
            }

            if (RDriveSpeed > rightPowerCurrent) {
                rightPowerCurrent = Range.clip(rightPowerCurrent + increment, -1, RDriveSpeed);
                rightDrive.setPower(maxSpeed * rightPowerCurrent);
            } else if (RDriveSpeed < rightPowerCurrent) {
                rightPowerCurrent = Range.clip(rightPowerCurrent - increment, RDriveSpeed, 1);
                rightDrive.setPower(maxSpeed * rightPowerCurrent);
            } else {
                rightDrive.setPower(maxSpeed * rightPowerCurrent);
            }

            if (midDriveSpeed > midPowerCurrent) {
                midPowerCurrent = Range.clip(midPowerCurrent + increment, -1, midDriveSpeed);
                midDrive.setPower(maxSpeed * midPowerCurrent);
            } else if (RDriveSpeed < midPowerCurrent) {
                midPowerCurrent = Range.clip(midPowerCurrent - increment, midDriveSpeed, 1);
                midDrive.setPower(maxSpeed * midPowerCurrent);
            } else {
                midDrive.setPower(maxSpeed * midPowerCurrent);
            }
        }
    }

}
