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

    /*--------------------------------------------------------------------*
     * Create graph components with width, height, and color
     *--------------------------------------------------------------------*/

    // Torso and head
    Section torso = new BodySection(TORSO_WIDTH, TORSO_HEIGHT, Color.BLUE, "torso");
    Section head = new BodySection(HEAD_WIDTH, HEAD_HEIGHT, Color.GREEN, "head");

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
    Section rightFoot = new BodySection(LEG_WIDTH, LEG_HEIGHT/2, Color.GREEN, "rightFoot");
    Section rightLowerLeg = new BodySection(LEG_WIDTH, LEG_HEIGHT, Color.GREEN, "rightLowerLeg");
    Section rightUpperLeg = new BodySection(LEG_WIDTH, LEG_HEIGHT, Color.GREEN, "rightUpperLeg");

    /*--------------------------------------------------------------------*
     * Define them based on relative, successive transformations
     *--------------------------------------------------------------------*/

    // left arm
    leftUpperArm.transform(AffineTransform.getTranslateInstance(-ARM_WIDTH, TORSO_HEIGHT/5));
    leftLowerArm.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT));
    leftHand.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT));

    // left leg
    leftUpperLeg.transform(AffineTransform.getTranslateInstance(0, TORSO_HEIGHT));
    leftLowerLeg.transform(AffineTransform.getTranslateInstance(0, LEG_HEIGHT));

    // right arm
    rightUpperArm.transform(AffineTransform.getTranslateInstance(TORSO_WIDTH, TORSO_HEIGHT/5));
    rightLowerArm.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT));
    rightHand.transform(AffineTransform.getTranslateInstance(0, ARM_HEIGHT));

    // right leg
    rightUpperLeg.transform(AffineTransform.getTranslateInstance(TORSO_WIDTH - LEG_WIDTH, TORSO_HEIGHT));
    rightLowerLeg.transform(AffineTransform.getTranslateInstance(0, LEG_HEIGHT));

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
  public static final double HEAD_WIDTH = 10.0;
  public static final double HEAD_HEIGHT = 10.0;

  public static final double TORSO_WIDTH = 100.0;
  public static final double TORSO_HEIGHT = 100.0;

  public static final double ARM_WIDTH = 50.0;
  public static final double ARM_HEIGHT = 50.0;

  public static final double LEG_WIDTH = 50.0;
  public static final double LEG_HEIGHT = 50.0;

}
