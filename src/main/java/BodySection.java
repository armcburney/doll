/**
 * Created by andrewmcburney on 2017-03-08.
 */

package ca.andrewmcburney.cs349.a3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;

public class BodySection extends Section {
  private RoundRectangle2D rect = null;
  private Color color;

  /**
   * Creates a rectangle based at the origin with the specified
   * width, height, and color
   */
  public BodySection(double width, double height, Color color, String name) {
    super(name);

    if (name == "torso") {
      this.initialize(width, height, -25, 0, 50);
    } else if (name == "head") {
      this.initialize(width, height, -width/2, 0, 50);
    } else {
      this.initialize(width, height, -width/2, 0, 40);
    }

    this.color = color;
  }

  /**
   * Creates a rectangle based at the origin with the specified
   * width, height, and parent
   */
  public BodySection(double width, double height, Section parentSection, String name) {
    super(parentSection, name);

    if (name == "torso") {
      this.initialize(width, height, -25, 0, 20);
    } else if (name == "head") {
      this.initialize(width, height, -width/2, 0, 50);
    } else {
      this.initialize(width, height, -width/2, 0, 40);
    }
  }

  private void initialize(double width, double height, double pivotX, double pivotY, double r) {
    rect = new RoundRectangle2D.Double(pivotX, pivotY, width, height, r, r);
  }

  /**
   * Test if our rectangle contains the point specified.
   */
  public boolean pointInside(Point2D p) {
    AffineTransform fullTransform = this.getFullTransform();
    AffineTransform inverseTransform = null;

    try {
      inverseTransform = fullTransform.createInverse();
    } catch (NoninvertibleTransformException e) {
      e.printStackTrace();
    }

    Point2D newPoint = (Point2D)p.clone();
    inverseTransform.transform(newPoint, newPoint);
    return rect.contains(newPoint);
  }

  protected void drawSection(Graphics2D g) {
    g.setColor(color);
    g.draw(rect);
  }

  public String toString() {
    return "BodySection: " + rect;
  }
}
