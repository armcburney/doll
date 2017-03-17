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
    canvas.addSprite(Main.makeSprite());

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
  private static Sprite makeSprite() {
    Sprite torso = new RectangleSprite(TORSO_WIDTH, TORSO_HEIGHT, Color.BLUE);
    Sprite head = new RectangleSprite(HEAD_WIDTH, HEAD_HEIGHT, Color.GREEN);

    // Left arm
    Sprite leftHand = new RectangleSprite(50, 50, Color.BLACK);
    Sprite leftUpperArm = new RectangleSprite(50, 50, Color.BLACK);
    Sprite leftLowerArm = new RectangleSprite(70, 30, Color.BLACK);

    // Right arm
    Sprite rightHand = new RectangleSprite(50, 40, Color.BLACK);
    Sprite rightUpperArm = new RectangleSprite(50, 50, Color.BLACK);
    Sprite rightLowerArm = new RectangleSprite(70, 30, Color.BLACK);

    // define them based on relative, successive transformations
    torso.transform(AffineTransform.getTranslateInstance(100, 100));
    head.transform(AffineTransform.getTranslateInstance((TORSO_WIDTH - HEAD_WIDTH)/2, -10));
    rightUpperArm.transform(AffineTransform.getTranslateInstance(TORSO_WIDTH, TORSO_HEIGHT/5));

    // left arm
    leftLowerArm.addChild(leftHand);
    leftUpperArm.addChild(leftLowerArm);

    // right arm
    rightLowerArm.addChild(rightHand);
    rightUpperArm.addChild(rightLowerArm);

    // build scene graph
    torso.addChild(head);
    torso.addChild(leftUpperArm);
    torso.addChild(rightUpperArm);

    // return root of the tree
    return torso;
  }

  // Scene graph node widths and heights
  public static final int HEAD_WIDTH = 10;
  public static final int HEAD_HEIGHT = 10;
  public static final int TORSO_WIDTH = 100;
  public static final int TORSO_HEIGHT = 100;

}
