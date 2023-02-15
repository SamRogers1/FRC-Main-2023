package frc.robot.subsystems.gyro;

//import com.kauailabs.navx.frc.AHRS;
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
import frc.robot.subsystems.gyro.Gyro;


public class Balance extends PIDCommand{
    
    
    private Drivetrain drivetrain;//replace with actual drivetrain
    
    Gyro gyro;
  

    


    public Balance(Gyro gyro, double setpoint, Drivetrain drivetrain) {
        super(
            new PIDController(BalanceConstants.kP, BalanceConstants.kI, BalanceConstants.kD),
            gyro::getRoll,
            // Set reference to target
            () -> setpoint,
            // Pipe output to turn robot
            (outputPower) -> {
                SmartDashboard.putNumber("Angular Output", outputPower);
                drivetrain.arcadeDrive(outputPower, 0);
            },
            drivetrain
        );

        SmartDashboard.putNumber("kP", SmartDashboard.getNumber("kP", 0));

    
        this.getController().setTolerance(BalanceConstants.kErrorThreshold);

        SmartDashboard.putBoolean("Balance Running", true);
    
        this.gyro = gyro;
        this.drivetrain = drivetrain;
    
        addRequirements(drivetrain);
    }

    @Override
    public void execute(){
        this.m_controller.setP(SmartDashboard.getNumber("kP", 0));
    }



    @Override
    public void end(boolean interrupted){
        SmartDashboard.putBoolean("Balance Running", false);
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished(){
        return this.getController().atSetpoint();
    }
}