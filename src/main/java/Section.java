package ca.andrewmcburney.cs349.a3;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;

public abstract class Section {
  protected enum InteractionMode {
    IDLE,
    DRAGGING,
    SCALING,
    ROTATING
  }

  private Section parent = null;                                         // Pointer to our parent
  private Vector<Section> children = new Vector<Section>();               // Holds all of our children
  private AffineTransform transform = new AffineTransform();            // Our transformation matrix
  protected Point2D lastPoint = null;                                   // Last mouse point
  protected InteractionMode interactionMode = InteractionMode.IDLE;     // current state

  public Section() { }

  public Section(Section parent) {
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

  public abstract boolean pointInside(Point2D p);

  /**
   * Handles a mouse down event, assuming that the event has already
   * been tested to ensure the mouse point is within our sprite.
   */
  protected void handleMouseDownEvent(MouseEvent e) {
    lastPoint = e.getPoint();

    if (parent == null) {
      interactionMode = InteractionMode.DRAGGING;
    } else {
      interactionMode = InteractionMode.IDLE;
    }
  }

  protected void handleMouseDragEvent(MouseEvent e) {
    Point2D oldPoint = lastPoint;
    Point2D newPoint = e.getPoint();

    switch (interactionMode) {
      case IDLE:
      ; // no-op (shouldn't get here)
      break;
    case DRAGGING:
      double x_diff = newPoint.getX() - oldPoint.getX();
      double y_diff = newPoint.getY() - oldPoint.getY();
      transform.translate(x_diff, y_diff);
      break;
    case ROTATING:
      ; // Provide rotation code here
      break;
    case SCALING:
      ; // Provide scaling code here
      break;
    }

    // Save our last point, if it's needed next time around
    lastPoint = e.getPoint();
  }

  protected void handleMouseUp(MouseEvent e) {
    interactionMode = InteractionMode.IDLE;
    // Do any other interaction handling necessary here
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
