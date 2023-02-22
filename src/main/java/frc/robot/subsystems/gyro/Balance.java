package frc.robot.subsystems.gyro;

import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class Balance extends PIDCommand {
    public Balance(Drivetrain drivetrain, Gyro gyro, double setpoint) {
        super(
            new PIDController(Constants.Balance.kP, Constants.Balance.kI, Constants.Balance.kD),
            gyro::getRoll,
            // Set reference to target
            () -> setpoint,
            // Pipe output to turn robot
            (outputPower) -> drivetrain.arcadeDrive(outputPower, 0), // TODO: was this negative?
            drivetrain
        );

        SmartDashboard.putBoolean("Balance Running", true);
        SmartDashboard.putNumber("kP", SmartDashboard.getNumber("kP", Constants.Balance.kP));

        this.getController().setTolerance(Constants.Balance.kErrorThreshold);
    
        addRequirements(drivetrain);
    }

    @Override
    public void execute(){
        double output = this.m_controller.calculate(this.m_measurement.getAsDouble(), this.m_setpoint.getAsDouble());

        this.m_useOutput.accept(output);

        // TODO: we can tune this more if necessary, but 0.008 works well
        // this.m_controller.setP(SmartDashboard.getNumber("kP", 0));
    }

    @Override
    public void end(boolean interrupted){
        SmartDashboard.putBoolean("Balance Running", false);
        
        this.m_useOutput.accept(0);
    }

    @Override
    public boolean isFinished(){
        return this.getController().atSetpoint();
    }
}