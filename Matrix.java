public class Matrix implements IMatrix
{  
	double array[][] = new double[4][4];
   Matrix temp;

	//sets values as the identity matrix
   public void identity(){
   for (int j = 0; j<4; j++){
   	for (int i = 0; i<4; i++){
   		if (i == j)
   			array[i][j] = 1;
   		else
   			array[i][j] = 0;
 	  }
   	 }
	}

   //set a particular index (minus one because of zero-indexing)
   public void set(int row, int col, double value){
   	array[row-1][col-1] = value;
   }

   //get a particular index
   public double get(int row, int col){
   	return array[row-1][col-1];
   }

   //translate by x, y, and/or z
   public void translate(double x, double y, double z){
      
   	Matrix trans = new Matrix();
   	trans.identity();
   	trans.set(1,4,x);
   	trans.set(2,4,y);
   	trans.set(3,4,x);
   	this.leftMultiply(trans);

      /* this doesn't work yet
      this.temp.identity();
      temp.set(1,4,x);
      temp.set(2,4,y);
      temp.set(3,4,x);
      this.leftMultiply(temp); */
   }

   //rotate around x-axis
   public void rotateX(double radians){
      
   	Matrix rotX = new Matrix();
   	rotX.identity();
   	rotX.set(2,2,Math.cos(radians));
   	rotX.set(2,3,-Math.sin(radians));
   	rotX.set(3,2,Math.sin(radians));
   	rotX.set(3,3,Math.cos(radians));
   	this.leftMultiply(rotX);

      /*

      temp.identity();
      temp.set(2,2,Math.cos(radians));
      temp.set(2,3,-Math.sin(radians));
      temp.set(3,2,Math.sin(radians));
      temp.set(3,3,Math.cos(radians));
      this.leftMultiply(temp);*/
   }

   //rotate around y-axis
   public void rotateY(double radians){
	Matrix rotY = new Matrix();
   	rotY.identity();
   	rotY.set(1,1,Math.cos(radians));
   	rotY.set(1,3,Math.sin(radians));
   	rotY.set(3,1,-Math.sin(radians));
   	rotY.set(3,3,Math.cos(radians));
   	this.leftMultiply(rotY);
   }

   //rotate around z-axis
   public void rotateZ(double radians){
	Matrix rotZ = new Matrix();
   	rotZ.identity();
   	rotZ.set(1,1,Math.cos(radians));
   	rotZ.set(1,2,-Math.sin(radians));
   	rotZ.set(2,1,Math.sin(radians));
   	rotZ.set(2,2,Math.cos(radians));
   	this.leftMultiply(rotZ);
   }

   //scale by x, y, and/or z scalar
   public void scale(double x, double y, double z){
   	Matrix scale = new Matrix();
   	scale.identity();
   	scale.set(1,1,x);
   	scale.set(2,2,y);
   	scale.set(3,3,z);
   	this.leftMultiply(scale);

   }

   //left multiply by taking the dot-product of every row of this matrix
   //and every column of the other matrix
   public void leftMultiply(Matrix other){
   	 double[][] temp = new double[4][4];
    //record copy of this matrix   
	for (int i = 0; i < 4; i++) {
	    for (int j = 0; j < 4; j++) {
		temp[i][j] = array[i][j];
	    }
	}
	//user copy of matrix to left multiply while writing dot products into matrix values
	for (int i = 0; i < 4; i++) {
	    for (int j = 0; j < 4; j++) {
		array[i][j] = 0;
		for (int k = 0 ; k < 4 ; k++) {
		    array[i][j] += temp[i][k] * other.array[k][j];
		}
	    }
	}
   }

   //same as left-multiply but different order of multiplication
   //result not necessarily commutative
   public void rightMultiply(Matrix other){
   	double[][] temp = new double[4][4];

 //record copy of this matrix   
	for (int i = 0; i < 4; i++) {
	    for (int j = 0; j < 4; j++) {
		temp[i][j] = array[i][j];
	    }
	}
	//user copy of matrix to left multiply while writing dot products into matrix values
	for (int i = 0; i < 4; i++) {
	    for (int j = 0; j < 4; j++) {
		array[i][j] = 0;
		for (int k = 0 ; k < 4 ; k++) {
   		    array[i][j] += other.array[i][k] * temp[k][j];
		}
	    }
	}
   }

   //apply the matrix transformation to individual points.
   public void transform(double[] src, double[] dst){
	for (int i = 0; i < dst.length; i++) {
	    dst[i] = 0;
	    for (int j = 0; j < src.length; j++)
		dst[i] += array[i][j] * src[j]; 
	}
    }

}