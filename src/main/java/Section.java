package ca.andrewmcburney.cs349.a3;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public abstract class Section {
  protected enum InteractionMode { IDLE, DRAGGING, SCALING, ROTATING }

  private boolean isLeg = false;
  private final String name;
  private Section parent = null;                                         // Pointer to our parent
  private Vector<Section> children = new Vector<Section>();               // Holds all of our children
  private AffineTransform transform = new AffineTransform();            // Our transformation matrix
  protected Point2D lastPoint = null;                                   // Last mouse point
  protected InteractionMode interactionMode = InteractionMode.IDLE;     // current state

  public Section(String name) {
    this.name = name;
  }

  public Section(Section parent, String name) {
    this.name = name;
    if (parent != null) {
      parent.addChild(this);
    }
  }

  public void addChild(Section s) {
    children.add(s);
    s.setParent(this);
  }

  public Section getParent() {
    return parent;
  }

  private void setParent(Section s) {
    this.parent = s;
  }
  public String getName() { return this.name; }

  public abstract boolean pointInside(Point2D p);

  protected void handleMouseDownEvent(MouseEvent e) {
    lastPoint = e.getPoint();

    if (parent == null) {
      interactionMode = InteractionMode.DRAGGING;
    } else {
      interactionMode = InteractionMode.ROTATING;
    }
  }

  protected void handleMouseDragEvent(MouseEvent e) {
    Point2D oldPoint = lastPoint;
    Point2D newPoint = e.getPoint();

    //System.out.println("old: " + oldPoint.getX() + " | " + oldPoint.getY() + " new: " + newPoint.getX() + " | " + newPoint.getY());

    double x_diff, y_diff, theta = 0.0, lastAngle = 0.0;
    x_diff = newPoint.getX() - oldPoint.getX();
    y_diff = newPoint.getY() - oldPoint.getY();


    switch (interactionMode) {
      case IDLE:
      break;
    case DRAGGING:
      transform.translate(x_diff, y_diff);
      break;
    case ROTATING:
      //System.out.println(getLocalTransform().toString());
      System.out.println(getSectionHit(e).getName());

      // subtract with axis of rotation! Not last point!
      theta = Math.atan2(y_diff, x_diff);
      //System.out.println("x: " + x_diff + " | y: " + y_diff + " | theta: " + theta);

      transform.rotate((theta - lastAngle)/36);
      lastAngle = theta - lastAngle;

      if (!isLeg) {
        break;
      }
    case SCALING:
      System.out.println("leg");
      break;
    }

    lastPoint = e.getPoint();
  }

  protected void handleMouseUp(MouseEvent e) {
    interactionMode = InteractionMode.IDLE;
  }

  /**
   * Locates the sprite that was hit by the given event.
   * You *may* need to modify this method, depending on
   * how you modify other parts of the class.
   *
   * @return The sprite that was hit, or null if no sprite was hit
   */
  public Section getSectionHit(MouseEvent e) {
    for (Section sprite : children) {
        Section s = sprite.getSectionHit(e);

        if (s != null) {
            return s;
        }
    }

    if (this.pointInside(e.getPoint())) {
        return this;
    }

    return null;
  }

  /*
   * Important note: How transforms are handled here are only an example. You will
   * likely need to modify this code for it to work for your assignment.
   */

  /**
   * Returns the full transform to this object from the root
   */
  public AffineTransform getFullTransform() {
    AffineTransform returnTransform = new AffineTransform();
    Section curSection = this;

    while (curSection != null) {
      returnTransform.preConcatenate(curSection.getLocalTransform());
      curSection = curSection.getParent();
    }

    return returnTransform;
  }

  public AffineTransform getLocalTransform() {
    return (AffineTransform)transform.clone();
  }

  public void transform(AffineTransform t) {
    transform.concatenate(t);
  }

  public void draw(Graphics2D g) {
    AffineTransform oldTransform = g.getTransform();

    // Set to our transform
    g.setTransform(this.getFullTransform());

    // Draw the sprite (delegated to sub-classes)
    this.drawSection(g);

    // Restore original transform
    g.setTransform(oldTransform);

    // Draw children
    for (Section sprite : children) {
      sprite.draw(g);
    }
  }

  protected abstract void drawSection(Graphics2D g);
}
