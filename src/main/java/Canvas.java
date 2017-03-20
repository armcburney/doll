/**
 * This code was mostly taken from the sample code provided
 */

package ca.andrewmcburney.cs349.a3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A canvas that draws sprites.
 */
public class Canvas extends JPanel {
  private Vector<Section> sprites = new Vector<Section>();
  private Section interactiveSection = null;

  public Canvas() {
    this.addMouseListener(new MouseAdapter() {
      public void mousePressed(java.awt.event.MouseEvent e) {
        handleMousePress(e);
      }

      public void mouseReleased(MouseEvent e) {
        handleMouseReleased(e);
      }
    });

    this.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        handleMouseDragged(e);
      }
    });
  }

  /**
   * Handle mouse press events
   */
  private void handleMousePress(java.awt.event.MouseEvent e) {
    for (Section sprite : sprites) {
      interactiveSection = sprite.getSectionHit(e);

      if (interactiveSection != null) {
        interactiveSection.handleMouseDownEvent(e);
        break;
      }
    }
  }

  /**
   * Handle mouse released events
   */
  private void handleMouseReleased(MouseEvent e) {
    if (interactiveSection != null) {
      interactiveSection.handleMouseUp(e);
      repaint();
    }
    interactiveSection = null;
  }

  /**
   * Handle mouse dragged events
   */
  private void handleMouseDragged(MouseEvent e) {
    if (interactiveSection != null) {
      interactiveSection.handleMouseDragEvent(e);
      repaint();
    }
  }

  /**
   * Add a top-level sprite to the canvas
   */
  public void addSection(Section s) {
    sprites.add(s);
  }

  /**
   * Paint our canvas
   */
  public void paint(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    g.setColor(Color.BLACK);

    for (Section sprite : sprites) {
      sprite.draw((Graphics2D) g);
    }
  }
}
