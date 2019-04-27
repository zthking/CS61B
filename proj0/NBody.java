public class NBody{
    
    /*Define variables. */
	public static double T;
	public static double dt;
	public static String filename;
	public static double r;
	public static String imageToDraw;

	public static void main (String [] args) {

        /**Collect input time T, increasement of time dt 
          *and file name filenamefrom commend line arguments. 
          *Use try and catch method to handle input exception. */
		try {

            T = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			filename = args[2];

		} catch (Exception e) {

			System.out.println("Input Error!");
      		
		}

		/**Give the value of radius and body array out of try and catch 
		  *to avoid illegal start of expression. */
        r = readRadius(filename);
		Planet[] planets = readPlanets(filename);

        /*Draw background and initial location of all planets.*/
		drawBackground();
		drawAllBodies(planets);

        /** Enables double buffering.
		  * A animation technique where all drawing takes place on the offscreen canvas.
		  * Only when you call show() does your drawing get copied from the
		  * offscreen canvas to the onscreen canvas, where it is displayed
		  * in the standard drawing window. */
		StdDraw.enableDoubleBuffering();

        /**Create simulation.
          *Update location of planets using the time T and delta time dt. */
		int time = (int) Math.round(T);
		int deltaT = (int) Math.round(dt);
		for (int t = 0; t < time; t += deltaT) {

			double xForces[] = new double[planets.length];
            double yForces[] = new double[planets.length];

			for (int j = 0; j < planets.length; j++){
				xForces[j] = planets[j].calcNetForceExertedByX(planets);
				yForces[j] = planets[j].calcNetForceExertedByY(planets);

			}
            for (int i = 0; i < planets.length; i++){
            	planets[i].update(deltaT, xForces[i], yForces[i]);
            }
            
            drawBackground();
		    drawAllBodies(planets);

            /* Shows the drawing to the screen, and waits 10 milliseconds. */
            StdDraw.show();
            StdDraw.pause(10);
		}

        /*Print final location of all planets*/
		StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < planets.length; i++) {
                StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", 
                	planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, 
                	planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }
	}
 
    /*Draw background. */
	public static void drawBackground() {

		StdDraw.setXscale(-r, r);
		StdDraw.setYscale(-r, r);
		imageToDraw = "images/starfield.jpg";
		StdDraw.picture(0, 0, imageToDraw);
	}

    /*Draw all planets. */
	public static void drawAllBodies(Planet[] drawAllPlanets) {
		for (int i = 0; i < drawAllPlanets.length; i++) {
		        drawAllPlanets[i].draw();
		    }
	}

    /*Read the second item in file and return radius as double. */
	public static double readRadius(String link) {

        In in = new In(link);
		int discardFirstItem = in.readInt();
		double radius = in.readDouble();

		return radius;
	}

    /*Read the file and return all bodies as body array. */
	public static Planet[] readPlanets(String link) {

		In in = new In(link);
		int planetAmount = in.readInt();
		double discardSecondItem = in.readDouble();

		Planet[] allPlanets = new Planet[planetAmount];
		for (int row = 0; row < planetAmount; row++) {
				allPlanets[row] = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), 
					in.readDouble(), in.readDouble(), in.readString());
		}
		return allPlanets;
	}

}