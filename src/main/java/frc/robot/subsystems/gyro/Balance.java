package frc.robot.subsystems.gyro;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.math.controller.PIDController;
//import edu.wpi.first.wpilibj.SPI;
//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.Constants;
import frc.robot.Constants.BalanceConstants;
import frc.robot.subsystems.Drivetrain;
//import frc.robot.util.XboxController;

public class Balance extends PIDCommand{
    
    private Drivetrain drivetrain;//replace with actual drivetrain
    
    AHRS gyro;
  

    public double gyroPitchInit()
    {
        //gets the angle of the robot throughout auto
        double pitch = gyro.getPitch();
        return pitch;
    }


    public Balance(AHRS gyro, double setpoint, Drivetrain drivetrain) {
        super(
            new PIDController(BalanceConstants.kP, BalanceConstants.kI, BalanceConstants.kD),
            gyro::getAngle,
            // Set reference to target
            () -> setpoint,
            // Pipe output to turn robot
            (outputPower) -> {
                SmartDashboard.putNumber("Angular Output", outputPower);
                drivetrain.arcadeDrive(outputPower, 0);
            },
            drivetrain
        );

    
        this.getController().setTolerance(BalanceConstants.kErrorThreshold);
        this.getController().enableContinuousInput(-180.0, 180.0);

        SmartDashboard.putBoolean("Angular Running", true);
    
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);


    }
    @Override
    public void end(boolean interrupted){
        SmartDashboard.putBoolean("Angular Running", false);
        drivetrain.arcadeDrive(0, 0);
 
    }


    @Override
    public boolean isFinished(){
        return this.getController().atSetpoint();
    }


}



