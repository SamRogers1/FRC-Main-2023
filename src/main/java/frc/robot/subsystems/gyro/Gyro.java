package frc.robot.subsystems.gyro;

import com.kauailabs.navx.frc.AHRS;
//import frc.robot.Robot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase {
    private AHRS gyro = new AHRS(SPI.Port.kMXP);
    double rollOffset;
    boolean initialized;
    public double getYaw() {
      return gyro.getYaw();
    }
    public double getPitch() {
      return gyro.getPitch();
    }
    
    public Gyro(){
        this.rollOffset = 0;
        this.initialized = false;
        
        gyro.calibrate();
        
        SmartDashboard.putNumber("gyro offset", this.rollOffset);
    }

    public double getRoll(){
        return gyro.getRoll() - this.rollOffset;
    }

    @Override
    public void periodic(){
        if (! this.initialized && gyro.getRoll() != 0){
            this.rollOffset = gyro.getRoll();
            this.initialized = true;
        }
        SmartDashboard.putNumber("Roll",getRoll());
        
    
    
      }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
    /*  public Gyro(){
    calibrateGyro();
    zeroGyro();
    }
    public void calibrateGyro() {
        gyro.calibrate();
      }
      public void zeroGyro() {
        gyro.reset();
      
    }
      public double getYaw() {
        return gyro.getYaw();
      }
      public double getPitch() {
        return gyro.getPitch();
      }
      public double getRoll() {
        return gyro.getRoll();
      }
       @Override
      public void periodic(){    
      SmartDashboard.putNumber("Yaw", getYaw());
      SmartDashboard.putNumber("Roll",getRoll());
      SmartDashboard.putNumber("Pitch", getPitch());
      
    } */
}
