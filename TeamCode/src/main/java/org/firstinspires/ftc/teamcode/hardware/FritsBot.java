package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.bosch.BNO055IMU;

public class FritsBot {
    public HDrive drivetrain = new HDrive();
//    private BNO055IMU imu;

    public void init(HardwareMap hwMap) {
        drivetrain.init(hwMap);
//        imu = hwMap.get(BNO055IMU.class, "imu");
//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.angleUnit            = BNO055IMU.AngleUnit.RADIANS;
    }

    public void driveSimple(double x, double y, double rotate) {
        drivetrain.drive(y, x, rotate);
    }
}
