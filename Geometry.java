import java.awt.Graphics;
import java.awt.Color;


public class Geometry
{
	double vertices[][];
	int faces[][];
	Color color;

	public void hasColor(Color c){
		color = c;
	}

	public void Sphere(int m, int n) {
       
	vertices = new double[(m+1)*(n+1)][3];
	faces = new int[m*n][4];
	
	// calculate the vertices
	for(int i=0; i<m+1; i++) {
	    for(int j=0; j<n+1; j++) {
		
		int vIndex = i+(m+1)*j;

		//calculates the spherical coordinate
		double theta = (2*(Math.PI)*i/m);
		double phi = (-(Math.PI)/2+((Math.PI)*j/n));

		//stores each component at vertex.
		vertices[vIndex][0] = Math.cos(theta)*Math.cos(phi);
		vertices[vIndex][1] = Math.sin(theta)*Math.cos(phi);
		vertices[vIndex][2] = Math.sin(phi);
	    }
	}
	
	// calculate the faces
	for (int i = 0; i<m; i++) {
	    for (int j = 0; j<n; j++) {

		int fIndex = i*m+j;

		//convention is counterclockwise
		faces[fIndex][0] = i*(m+1)+j; //vIndex // lower left
		faces[fIndex][1] = i*(m+1)+j+1;  //vIndex + 1 //lower right
		faces[fIndex][2] = (i+1)*(m+1)+j+1; //upper right
		faces[fIndex][3] = (i+1)*(m+1)+j; //upper left
	    }
	}


	}//end of sphere

	public void Cube(){
	vertices = new double [][]{
	    {-1, 1,-1, 1}, {-1,-1,-1, 1},
	    { 1,-1,-1, 1}, { 1, 1,-1, 1},
	    {-1, 1, 1, 1}, {-1,-1, 1, 1},
	    { 1,-1, 1, 1}, { 1, 1, 1, 1}
        };
	
	// face array of unit cube
	faces = new int [][] {
	    {0,1,5,4}, {1,2,6,5},
	    {2,3,7,6}, {3,0,4,7},
	    {0,3,2,1}, {5,6,7,4}
	};



	}//end of cube

	public void Cylinder(int m, int n){
		/*
		x(u,v) {return cos (2*pi*u)*r(v);}
		y(u,v) {return sin (2*pi*u)*r(v);}
		z(u,v,) {return v < 0.5 ? -1:1;}
		r(v) = {return v==0||v==1? 0:1;}
		*/

		vertices = new double[(2*(m+1)*(n+1))][3]; 
		faces = new int[2*(m*n)][4];

	// calculate the vertices
	for(int i=0; i<m+1; i++) {
	    for(int j=0; j<n+1; j++) {
		
		int vIndex = i+(m+1)*j;

		//calculate increments
		double u = (2*(Math.PI)*i/m);  //from zero to 2pi
		double v = (-1 +2*j/n); //from -1 to 1
		int r = v== 1? 0:1;  //for some reason, when r {return v==-1||v==1? 0:1;}, it will only form one end cap
							//hence, I just added another end cap to the end of everything

		//stores each component at vertex.
		vertices[vIndex][0] = Math.cos(u)*r;
		vertices[vIndex][1] = Math.sin(u)*r;
		vertices[vIndex][2] = v<0 ? -1:1; 

		//calculates bottom cap
		vertices[(m+1)*(n+1)+vIndex][0] = Math.cos(u)*r; 
		vertices[(m+1)*(n+1)+vIndex][1] = Math.sin(u)*r;
		vertices[(m+1)*(n+1)+vIndex][2] = -1.0;

	    }
	}

	// calculate the faces
	for (int i = 0; i<m; i++) {
	    for (int j = 0; j<n; j++) {

		int fIndex = i*m+j;

		faces[fIndex][0] = i*(m+1)+j; //vIndex // lower left
		faces[fIndex][1] = i*(m+1)+j+1;  //vIndex + 1 //lower right
		faces[fIndex][2] = (i+1)*(m+1)+j+1; //upper right
		faces[fIndex][3] = (i+1)*(m+1)+j; //upper left
			
		//calculates faces for bottom cap
		faces[m*n+fIndex][0] = i*(m+1)+j +(m+1)*(n+1); 
		faces[m*n+fIndex][1] = i*(m+1)+j+1 +(m+1)*(n+1);
		faces[m*n+fIndex][2] = i*(m+1)+j+1+(m+1) +(m+1)*(n+1);
		faces[m*n+fIndex][3] = i*(m+1)+j+(m+1) +(m+1)*(n+1);
	   	 }
		}
	}//end of cylinder

	public void drawShape(Graphics g, Matrix matrix){

	  double[] point0 = new double[3];  //xyz-coordinate
      double[] point1 = new double[3];  //xyz-coordinate
      int[] a = new int[2]; //start point for drawing
      int[] b = new int[2]; //end point for drawing

	  g.setColor(color);
	  for (int i = 0; i < faces.length; i++){
      for (int j = 0; j < faces[i].length; j++){

        matrix.transform(vertices[faces[i][j]], point0);
        matrix.transform(vertices[faces[i][(j+1)%4]], point1);

        projectPoint(point0, a);
        projectPoint(point1, b);

        //draws between edges
        g.drawLine(a[0], a[1], b[0], b[1]);

      }
    }

	}//end of drawShape

	 public void projectPoint(double[] xyz, int[] pxy) {

     int w = 640; 
     int h = 603; 
     double FL = 10.0; 

      // POINT IN 3D
      double x = xyz[0];
      double y = xyz[1];
      double z = xyz[2];

      // OUTPUT: PIXEL COORDINATES TO DISPLAY POINT
      pxy[0] = w / 2 + (int)(h * x / (FL - z));
      pxy[1] = h / 2 - (int)(h * y / (FL - z));
   }//end of projectPoint
}

