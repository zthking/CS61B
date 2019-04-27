public class NBody{

	public static double T;
	public static double dt;
	public static String filename;
	public static double r;
	public static Body[] planets = new Body[5];

	public static String imageToDraw;

	public static void main (String [] args){

		try{

            T = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			filename = args[2];
			r = readRadius(filename);
		    planets = readBodies(filename);

		}catch(Exception e){

			System.out.println("Input Error!");
      		
		}

		drawBackground();
		drawPlanets();

		for (int t = 0; t < Math.round(T); t += dt){

			double xForces[] = new double[planets.length];
            double yForces[] = new double[planets.length];

			for (int j = 0; j < planets.length; j++){
				xForces[j] = planets[j].calcNetForceExertedByX(planets);
				yForces[j] = planets[j].calcNetForceExertedByY(planets);

			}
            for (int i = 0; i < planets.length; i++){
            	planets[i].update(dt, xForces[i], yForces[i]);
            }

            drawBackground();
		    drawPlanets();

		}

	}

	public static void drawBackground(){

		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(-r, r);
		StdDraw.setYscale(-r, r);
		imageToDraw = "images/starfield.jpg";
		StdDraw.picture(0, 0, imageToDraw);

		StdDraw.show();
		StdDraw.pause(10);
	}

	public static void drawPlanets(){
		for (int i = 0; i < planets.length; i++){
		planets[i].draw();
		}
	}

	public static double readRadius(String link){

        In in = new In(link);
		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();

		return secondItemInFile;
	}

	public static Body[] readBodies(String link){

		In in = new In(link);
		int discardFirstItem = in.readInt();
		double discardSecondItem = in.readDouble();

		Body[] allPlanets = new Body[5];
		for (int row = 0; row < 5; row++){

				allPlanets[row] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), 
					in.readDouble(), in.readDouble(), in.readString());
		}
		return allPlanets;
	}

}