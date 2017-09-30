
package utilesGUIxSeguridad;

public class Rectangulo implements java.io.Serializable, Cloneable {

    /**
     * The X coordinate of the upper-left corner of the <code>Rectangulo</code>.
     *
     * @serial
     * @see #setLocation(int, int)
     * @see #getLocation()
     * @since 1.0
     */
    public int x;

    /**
     * The Y coordinate of the upper-left corner of the <code>Rectangulo</code>.
     *
     * @serial
     * @see #setLocation(int, int)
     * @see #getLocation()
     * @since 1.0
     */
    public int y;

    /**
     * The width of the <code>Rectangulo</code>.
     * @serial
     * @see #setSize(int, int)
     * @see #getSize()
     * @since 1.0
     */
    public int width;

    /**
     * The height of the <code>Rectangulo</code>.
     *
     * @serial
     * @see #setSize(int, int)
     * @see #getSize()
     * @since 1.0
     */
    public int height;

    /*
     * JDK 1.1 serialVersionUID
     */
     private static final long serialVersionUID = -4345857070255674764L;


    /**
     * Constructs a new <code>Rectangulo</code> whose upper-left corner
     * is at (0,&nbsp;0) in the coordinate space, and whose width and
     * height are both zero.
     */
    public Rectangulo() {
        this(0, 0, 0, 0);
    }

    /**
     * Constructs a new <code>Rectangulo</code>, initialized to match
     * the values of the specified <code>Rectangulo</code>.
     * @param r  the <code>Rectangulo</code> from which to copy initial values
     *           to a newly constructed <code>Rectangulo</code>
     * @since 1.1
     */
    public Rectangulo(Rectangulo r) {
        this(r.x, r.y, r.width, r.height);
    }

    /**
     * Constructs a new <code>Rectangulo</code> whose upper-left corner is
     * specified as
     * {@code (x,y)} and whose width and height
     * are specified by the arguments of the same name.
     * @param     x the specified X coordinate
     * @param     y the specified Y coordinate
     * @param     width    the width of the <code>Rectangulo</code>
     * @param     height   the height of the <code>Rectangulo</code>
     * @since 1.0
     */
    public Rectangulo(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new <code>Rectangulo</code> whose upper-left corner
     * is at (0,&nbsp;0) in the coordinate space, and whose width and
     * height are specified by the arguments of the same name.
     * @param width the width of the <code>Rectangulo</code>
     * @param height the height of the <code>Rectangulo</code>
     */
    public Rectangulo(int width, int height) {
        this(0, 0, width, height);
    }



    /**
     * Returns the X coordinate of the bounding <code>Rectangulo</code> in
     * <code>double</code> precision.
     * @return the X coordinate of the bounding <code>Rectangulo</code>.
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the Y coordinate of the bounding <code>Rectangulo</code> in
     * <code>double</code> precision.
     * @return the Y coordinate of the bounding <code>Rectangulo</code>.
     */
    public double getY() {
        return y;
    }

    /**
     * Returns the width of the bounding <code>Rectangulo</code> in
     * <code>double</code> precision.
     * @return the width of the bounding <code>Rectangulo</code>.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the bounding <code>Rectangulo</code> in
     * <code>double</code> precision.
     * @return the height of the bounding <code>Rectangulo</code>.
     */
    public double getHeight() {
        return height;
    }
    
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError();
        }
    }


}
