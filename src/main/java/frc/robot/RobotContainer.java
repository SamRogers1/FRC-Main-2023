package frc.robot;


import com.kauailabs.navx.frc.AHRS;

//import edu.wpi.first.wpilibj.interfaces.Gyro;
//import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.gyro.Balance;
import frc.robot.util.XboxController;
import edu.wpi.first.wpilibj.SPI;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  
  private final XboxController driverController = new XboxController(0);
  AHRS gyro = new AHRS(SPI.Port.kMXP);
  // The robot's subsystems and commands are defined here...

  private final Drivetrain drivetrain = new Drivetrain(driverController);
  
  SendableChooser<Command> autoChooser = new SendableChooser<>();
 
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    
    this.drivetrain.setDefaultCommand(new RunCommand(() ->
      SmartDashboard.putNumber("pitch angle", gyro.getPitch());

      this.drivetrain.arcadeDrive(driverController.getAxisValue(XboxController.Axis.LEFT_Y), driverController.getAxisValue(XboxController.Axis.RIGHT_X)),
    drivetrain));
    driverController.whenPressed(XboxController.Button.X, new Balance(gyro, 0, drivetrain));
  }
}