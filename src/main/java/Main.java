/**
 * Created by andrewmcburney on 2017-03-08.
 */

package ca.andrewmcburney.cs349.a3;

import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;

public class Main {
  public static void main(String[] args) {
    // add scene graph to the canvas
    Canvas canvas = new Canvas();
    canvas.addSprite(Main.makeSprite());

    // create a frame to hold it
    JFrame frame = new JFrame();
    frame.getContentPane().add(canvas);
    frame.getContentPane().setLayout(new GridLayout(1, 1));

    // Set frame to not be resizable
    frame.setMinimumSize(new Dimension(1280, 800));
    frame.setMaximumSize(new Dimension(1280, 800));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  /**
   * Make sample scene graph for testing purposes.
   */
  private static Sprite makeSprite() {
    // create four different parts
    Sprite firstSprite = new RectangleSprite(80, 50);
    Sprite secondSprite = new RectangleSprite(50, 40);
    Sprite thirdSprite = new RectangleSprite(70, 30);
    Sprite fourthSprite = new RectangleSprite(10, 10);

    // define them based on relative, successive transformations
    firstSprite.transform(AffineTransform.getTranslateInstance(0, 25));
    secondSprite.transform(AffineTransform.getTranslateInstance(80, 5));
    thirdSprite.transform(AffineTransform.getTranslateInstance(50, 5));
    fourthSprite.transform(AffineTransform.getTranslateInstance(70, 30));
    fourthSprite.transform(AffineTransform.getScaleInstance(4, 3));

    // build scene graph
    firstSprite.addChild(secondSprite);
    secondSprite.addChild(thirdSprite);
    thirdSprite.addChild(fourthSprite);

    // return root of the tree
    return firstSprite;
  }
}
