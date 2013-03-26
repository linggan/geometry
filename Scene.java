import java.awt.*;

public class Scene extends BufferedApplet
{
  //initialize transform matrix
  Matrix matrix = new Matrix();

  //initialize shapes
  Geometry cylinder = new Geometry();
  Geometry sphere = new Geometry();
  Geometry cube = new Geometry();

  int width = 0, height = 0;
  double t = 0, startTime = System.currentTimeMillis() / 1000.0; 

  public void render(Graphics g) {

   	//redraws background with every rendering
	  width = getWidth();
	  height = getHeight();
	  g.setColor(Color.white);
	  g.fillRect(0, 0, width, height);

    //time component
	  t = System.currentTimeMillis() / 1000.0 - startTime;
    
    matrix.rotateY(t);
    //set shapes here
    cylinder.Cylinder(15,15);
    cylinder.hasColor(Color.black);
    cylinder.drawShape(g, matrix);

    matrix.identity();
    matrix.rotateX(t);
    sphere.Sphere(10,10);
    sphere.hasColor(Color.blue);
    sphere.drawShape(g, matrix);

    cube.Cube();
    cube.hasColor(Color.red);
    cube.drawShape(g, matrix);

  }

}