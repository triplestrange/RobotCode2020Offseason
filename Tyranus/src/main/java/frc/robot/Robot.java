
package frc.robot;

// import com.ctre.phoenix.sensors.CANCoder;
// import com.ctre.phoenix.sensors.CANCoderConfiguration;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

// import frc.robot.vision.GripPipeline;

// import org.opencv.core.Rect;
// import org.opencv.imgproc.Imgproc;

// import edu.wpi.cscore.UsbCamera;
// import edu.wpi.first.cameraserver.CameraServer;
// import edu.wpi.first.wpilibj.vision.VisionRunner;
// import edu.wpi.first.wpilibj.vision.VisionThread;

public class Robot extends TimedRobot {

  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  // private CANCoder hoodEncoder = new CANCoder(0);
  NetworkTableEntry xEntry;
  NetworkTableEntry yEntry;
  // CANCoderConfiguration _canCoderConfiguration = new CANCoderConfiguration();

  @Override
  public void robotInit() {
    m_robotContainer = new RobotContainer();
    CameraServer.getInstance().startAutomaticCapture().setResolution(320, 160);
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("datatable");
    xEntry = table.getEntry("X");
    yEntry = table.getEntry("Y");
    
    // hoodEncoder.configAllSettings(_canCoderConfiguration);
      }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    // SmartDashboard.putNumber("hoodEncoder", hoodEncoder.getPosition());
    // SmartDashboard.putNumber("hoodVoltage", hoodEncoder.getBusVoltage());
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
    RobotContainer.swerveDrive.resetOdometry(new Pose2d(0, 0, new Rotation2d(-Math.PI / 2.)));
    RobotContainer.theta.reset(-Math.PI / 2.);
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
    // RobotContainer.swerveDrive.resetEncoders();
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    // RobotContainer.swerveDrive.resetEncoders();
  }

  double x = 0;
  double y = 0;
  @Override
  public void teleopPeriodic() {
    xEntry.setDouble(x);
    yEntry.setDouble(y);

    x += 0.05;
    y += 1.0;
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }
}
