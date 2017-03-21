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
  private Section parent = null;
  private Vector<Section> children = new Vector<Section>();
  private AffineTransform transform = new AffineTransform();
  protected Point2D lastPoint = null;
  protected InteractionMode interactionMode = InteractionMode.IDLE;

  /*--------------------------------------------------------------------*
   * Constructors
   *--------------------------------------------------------------------*/

  public Section(String name) {
    this.name = name;
  }

  public Section(Section parent, String name) {
    this.name = name;

    if (parent != null) {
      parent.addChild(this);
    }
  }

  /*--------------------------------------------------------------------*
   * Children and parent code
   *--------------------------------------------------------------------*/

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

  /*--------------------------------------------------------------------*
   * Mouse drag code
   *--------------------------------------------------------------------*/

  protected void handleMouseDragEvent(MouseEvent e) {
    Point2D oldPoint = lastPoint;
    Point2D newPoint = e.getPoint();

    double x_diff, y_diff, theta = 0.0;
    x_diff = newPoint.getX() - oldPoint.getX();
    y_diff = newPoint.getY() - oldPoint.getY();

    switch (interactionMode) {
    case IDLE:
      break;
    case DRAGGING:
      transform.translate(x_diff, y_diff);
      break;
    case ROTATING:
      try {
        Point2D local = getFullTransform().inverseTransform(newPoint, null);
        theta = Math.atan2(local.getX(), local.getY());
        transform.rotate(-theta);
      } catch (Exception e1) {
         System.out.println("oops");
      }

      // scale if leg component
      if (!isLeg) {
        break;
      }
    case SCALING:
      System.out.println("Implement scaling");
      break;
    }

    lastPoint = e.getPoint();
  }

  protected void handleMouseUp(MouseEvent e) {
    interactionMode = InteractionMode.IDLE;
  }

  public Section getSectionHit(MouseEvent e) {
    for (Section sprite : children) {
      Section s = sprite.getSectionHit(e);

      if (s != null) { return s; }
    }

    if (this.pointInside(e.getPoint())) {
      return this;
    }

    return null;
  }

  /*--------------------------------------------------------------------*
   * Affine Transforms
   *--------------------------------------------------------------------*/

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
