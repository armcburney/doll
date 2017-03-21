/**
 * Created by andrewmcburney on 2017-03-08.
 */

package ca.andrewmcburney.cs349.a3;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.*;

public class Main {

  public static void main(String[] args) {
    // Create a frame to hold scene graph
    JFrame frame = new JFrame();

    // Set layout
    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints gridBagConstraints = new GridBagConstraints();

    frame.getContentPane().setLayout(gridBagLayout);

    /*--------------------------------------------------------------------*
     * TopBar
     *--------------------------------------------------------------------*/

    JPanel topBar = new JPanel();
    JMenu menu;
    JMenuBar menuBar;
    JMenuItem resetItem, quitItem;

    resetItem = new JMenuItem("Reset", KeyEvent.VK_T);
    //--------
    quitItem = new JMenuItem("Quit", KeyEvent.VK_T);

    menu = new JMenu("File");
    menu.add(resetItem);
    menu.add(quitItem);

    menuBar = new JMenuBar();
    menuBar.add(menu);

    topBar.setLayout(new FlowLayout(FlowLayout.LEFT));
    topBar.add(menuBar);

    // Grid bag constraints
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 0;
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagLayout.setConstraints(topBar, gridBagConstraints);

    frame.getContentPane().add(topBar);

    /*--------------------------------------------------------------------*
     * Canvas
     *--------------------------------------------------------------------*/

    // Add scene graph to the canvas
    Canvas canvas = new Canvas();
    canvas.addSection(Main.makeSection());

    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagLayout.setConstraints(canvas, gridBagConstraints);

    // Add canvas after menu bar
    frame.getContentPane().add(canvas);

    // Set frame to not be resizable
    frame.setMinimumSize(new Dimension(1000, 700));
    frame.setMaximumSize(new Dimension(1000, 700));

    // Set close operation and set visible
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    /*--------------------------------------------------------------------*
     * Action Listeners
     *--------------------------------------------------------------------*/

    // Reset the paper doll
    resetItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        canvas.resetCanvas();
      }
    });

    // Exit the application
    quitItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        quitApp(frame);
      }
    });

    /*--------------------------------------------------------------------*
     * Key Listener
     *--------------------------------------------------------------------*/

    frame.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyReleased(KeyEvent e) {}

      @Override
      public void keyPressed(KeyEvent e) {
        if (((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) && (e.getKeyCode() == KeyEvent.VK_R)) {
          canvas.resetCanvas();
        } else if (((e.getModifiers() & KeyEvent.CTRL_MASK) != 0) && (e.getKeyCode() == KeyEvent.VK_Q)) {
          quitApp(frame);
        }
      }
    });
  }

  private static void quitApp(JFrame frame) {
    frame.dispose();
    System.exit(0);
  }

  private static Section makeSection() {

    /*--------------------------------------------------------------------*
     * Create graph components with width, height, and color
     *--------------------------------------------------------------------*/

    // Torso and head
    Section torso = new BodySection(TORSO_WIDTH, TORSO_HEIGHT, Color.BLACK, "torso");
    Section head = new BodySection(HEAD_WIDTH, HEAD_HEIGHT, Color.BLACK, "head");

    // Left arm
    Section leftHand = new BodySection(ARM_WIDTH, ARM_HEIGHT/2, Color.BLACK, "leftHand");
    Section leftLowerArm = new BodySection(ARM_WIDTH, ARM_HEIGHT, Color.BLACK, "leftLowerArm");
    Section leftUpperArm = new BodySection(ARM_WIDTH, ARM_HEIGHT, Color.BLACK, "leftUpperArm");

    // Right arm
    Section rightHand = new BodySection(ARM_WIDTH, ARM_HEIGHT/2, Color.BLACK, "rightHand");
    Section rightLowerArm = new BodySection(ARM_WIDTH, ARM_HEIGHT, Color.BLACK, "rightLowerArm");
    Section rightUpperArm = new BodySection(ARM_WIDTH, ARM_HEIGHT, Color.BLACK, "rightUpperArm");

    // Left Leg
    Section leftFoot = new BodySection(LEG_WIDTH, LEG_HEIGHT/2, Color.BLACK, "leftFoot");
    Section leftLowerLeg = new BodySection(LEG_WIDTH, LEG_HEIGHT, Color.BLACK, "leftLowerLeg");
    Section leftUpperLeg = new BodySection(LEG_WIDTH, LEG_HEIGHT, Color.BLACK, "leftUpperLeg");

    // Right Leg
    Section rightFoot = new BodySection(LEG_WIDTH, LEG_HEIGHT/2, Color.BLACK, "rightFoot");
    Section rightLowerLeg = new BodySection(LEG_WIDTH, LEG_HEIGHT, Color.BLACK, "rightLowerLeg");
    Section rightUpperLeg = new BodySection(LEG_WIDTH, LEG_HEIGHT, Color.BLACK, "rightUpperLeg");

    /*--------------------------------------------------------------------*
     * Define them based on relative, successive transformations
     *--------------------------------------------------------------------*/

    // left arm
    leftUpperArm.transform(AffineTransform.getTranslateInstance(-ARM_WIDTH, TORSO_HEIGHT/5));
    leftUpperArm.transform(AffineTransform.getRotateInstance(Math.PI/6));
    leftLowerArm.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT + PADDING/2));
    leftHand.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT + PADDING/2));

    // left leg
    leftUpperLeg.transform(AffineTransform.getTranslateInstance(-PADDING, TORSO_HEIGHT + PADDING));
    leftLowerLeg.transform(AffineTransform.getTranslateInstance(0, LEG_HEIGHT + PADDING/2));
    leftFoot.transform(AffineTransform.getTranslateInstance(0, LEG_HEIGHT + PADDING/2));

    // right arm
    rightUpperArm.transform(AffineTransform.getTranslateInstance(TORSO_WIDTH, TORSO_HEIGHT/5));
    rightUpperArm.transform(AffineTransform.getRotateInstance(-Math.PI/6));
    rightLowerArm.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT + PADDING/2));
    rightHand.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT + PADDING/2));

    // right leg
    rightUpperLeg.transform(AffineTransform.getTranslateInstance(TORSO_WIDTH - LEG_WIDTH + PADDING, TORSO_HEIGHT + PADDING));
    rightLowerLeg.transform(AffineTransform.getTranslateInstance(0, LEG_HEIGHT + PADDING/2));
    rightFoot.transform(AffineTransform.getTranslateInstance(0, LEG_HEIGHT + PADDING/2));

    torso.transform(AffineTransform.getTranslateInstance((TORSO_WIDTH - HEAD_WIDTH/2)/2, -PADDING));
    head.transform(AffineTransform.getTranslateInstance((TORSO_WIDTH - HEAD_WIDTH - 10)/2, -PADDING));
    head.transform(AffineTransform.getRotateInstance(Math.PI));

    /*--------------------------------------------------------------------*
     * Create graph by nesting child components
     *--------------------------------------------------------------------*/

    // left arm
    leftLowerArm.addChild(leftHand);
    leftUpperArm.addChild(leftLowerArm);

    // left leg
    leftLowerLeg.addChild(leftFoot);
    leftUpperLeg.addChild(leftLowerLeg);

    // right arm
    rightLowerArm.addChild(rightHand);
    rightUpperArm.addChild(rightLowerArm);

    // right leg
    rightLowerLeg.addChild(rightFoot);
    rightUpperLeg.addChild(rightLowerLeg);

    // build scene graph
    torso.addChild(head);
    torso.addChild(leftUpperArm);
    torso.addChild(rightUpperArm);
    torso.addChild(leftUpperLeg);
    torso.addChild(rightUpperLeg);

    // return root of the tree
    return torso;

  }

  /*--------------------------------------------------------------------*
   * Doll Constants
   *--------------------------------------------------------------------*/

  // Scene graph node widths and heights
  public static final double HEAD_WIDTH = 35.0;
  public static final double HEAD_HEIGHT = 35.0;

  public static final double TORSO_WIDTH = 100.0;
  public static final double TORSO_HEIGHT = 100.0;

  public static final double ARM_WIDTH = 50.0;
  public static final double ARM_HEIGHT = 50.0;

  public static final double LEG_WIDTH = 50.0;
  public static final double LEG_HEIGHT = 50.0;

  public static final double PADDING = 10.0;

}
