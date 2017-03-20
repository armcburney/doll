/**
 * Created by andrewmcburney on 2017-03-08.
 */

package ca.andrewmcburney.cs349.a3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 *
 */
public class BodySection extends Section {
  private Rectangle2D rect = null;
  private Color color;

  /**
   * Creates a rectangle based at the origin with the specified
   * width, height, and color
   */
  public BodySection(int width, int height, Color color) {
    super();
    this.initialize(width, height);
    this.color = color;
  }

  /**
   * Creates a rectangle based at the origin with the specified
   * width, height, and parent
   */
  public BodySection(int width, int height, Section parentSection) {
    super(parentSection);
    this.initialize(width, height);
  }

  private void initialize(int width, int height) {
    rect = new Rectangle2D.Double(0, 0, width, height);
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