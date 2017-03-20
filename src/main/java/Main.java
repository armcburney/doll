/**
 * Created by andrewmcburney on 2017-03-08.
 */

package ca.andrewmcburney.cs349.a3;

import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;

public class Main {
  public static void main(String[] args) {
    // Add scene graph to the canvas
    Canvas canvas = new Canvas();
    canvas.addSection(Main.makeSection());

    // Create a frame to hold scene graph
    JFrame frame = new JFrame();
    frame.getContentPane().add(canvas);
    frame.getContentPane().setLayout(new GridLayout(1, 1));

    // Set frame to not be resizable
    frame.setMinimumSize(new Dimension(1280, 800));
    frame.setMaximumSize(new Dimension(1280, 800));

    // Set close operation and set visible
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  /**
   * Make sample scene graph for testing purposes.
   */
  private static Section makeSection() {
    Section torso = new BodySection(TORSO_WIDTH, TORSO_HEIGHT, Color.BLUE);
    Section head = new BodySection(HEAD_WIDTH, HEAD_HEIGHT, Color.GREEN);

    // Left arm
    Section leftHand = new BodySection(ARM_WIDTH, ARM_HEIGHT/2, Color.BLACK);
    Section leftLowerArm = new BodySection(ARM_WIDTH, ARM_HEIGHT, Color.BLACK);
    Section leftUpperArm = new BodySection(ARM_WIDTH, ARM_HEIGHT, Color.BLACK);

    // Right arm
    Section rightHand = new BodySection(ARM_WIDTH, ARM_HEIGHT/2, Color.BLACK);
    Section rightLowerArm = new BodySection(ARM_WIDTH, ARM_HEIGHT, Color.BLACK);
    Section rightUpperArm = new BodySection(ARM_WIDTH, ARM_HEIGHT, Color.BLACK);

    // Left Leg
    Section leftFoot = new BodySection(50, 30, Color.BLACK);
    Section leftLowerLeg = new BodySection(ARM_WIDTH, 50, Color.BLACK);
    Section leftUpperLeg = new BodySection(50, 40, Color.BLACK);

    // Right Leg
    Section rightFoot = new BodySection(50, 30, Color.BLACK);
    Section rightLowerLeg = new BodySection(ARM_WIDTH, 50, Color.BLACK);
    Section rightUpperLeg = new BodySection(50, 40, Color.BLACK);

    /*--------------------------------------------------------------------*
     * Define them based on relative, successive transformations
     *--------------------------------------------------------------------*/

    // left arm
    leftUpperArm.transform(AffineTransform.getTranslateInstance(-ARM_WIDTH, TORSO_HEIGHT/5));
    leftLowerArm.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT));
    leftHand.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT));

    // left leg


    // right arm
    rightUpperArm.transform(AffineTransform.getTranslateInstance(TORSO_WIDTH, TORSO_HEIGHT/5));
    rightLowerArm.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT));
    rightHand.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT));

    // right leg

    torso.transform(AffineTransform.getTranslateInstance(100, 100));
    head.transform(AffineTransform.getTranslateInstance((TORSO_WIDTH - HEAD_WIDTH)/2, -10));

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
    leftUpperLeg.addChild(leftLowerLeg);

    // build scene graph
    torso.addChild(head);
    torso.addChild(leftUpperArm);
    torso.addChild(rightUpperArm);
    torso.addChild(leftUpperLeg);
    torso.addChild(rightUpperLeg);

    // return root of the tree
    return torso;
  }

  // Scene graph node widths and heights
  public static final int HEAD_WIDTH = 10;
  public static final int HEAD_HEIGHT = 10;
  public static final int TORSO_WIDTH = 100;
  public static final int TORSO_HEIGHT = 100;
  public static final int ARM_WIDTH = 50;
  public static final int ARM_HEIGHT = 50;
  public static final int HAND_HEIGHT = 50;
}
