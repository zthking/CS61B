public class NBody{

	public static double T;
	public static double dt;
	public static String filename;
	public static double r;

	public static String imageToDraw;

	public static void main (String [] args){

		try{

            T = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			filename = args[2];

		}catch(Exception e){

			System.out.println("Input Error!");
      		
		}

        r = readRadius(filename);
		Body[] planets = readBodies(filename);

		drawBackground();
		drawAllBodies(planets);

		int time = (int) Math.round(T);
		int deltaT = (int) Math.round(dt);
		drawUpdateBodies(time, planets, deltaT);
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

	public static void drawAllBodies(Body[] drawAllPlanets){
		for (int i = 0; i < drawAllPlanets.length; i++){
		        drawAllPlanets[i].draw();
		    }
	}

	public static void drawUpdateBodies(int time, Body[] drawAllPlanets, int deltaT){

		for (int t = 0; t < time; t += deltaT){

			double xForces[] = new double[drawAllPlanets.length];
            double yForces[] = new double[drawAllPlanets.length];

			for (int j = 0; j < drawAllPlanets.length; j++){
				xForces[j] = drawAllPlanets[j].calcNetForceExertedByX(drawAllPlanets);
				yForces[j] = drawAllPlanets[j].calcNetForceExertedByY(drawAllPlanets);

			}
            for (int i = 0; i < drawAllPlanets.length; i++){
            	drawAllPlanets[i].update(deltaT, xForces[i], yForces[i]);
            }

            drawBackground();
		    drawAllBodies(drawAllPlanets);

		}
	}

	public static double readRadius(String link){

        In in = new In(link);
		int discardFirstItem = in.readInt();
		double radius = in.readDouble();

		return radius;
	}

	public static Body[] readBodies(String link){

		In in = new In(link);
		int planetAmount = in.readInt();
		double discardSecondItem = in.readDouble();

		Body[] allPlanets = new Body[planetAmount];
		for (int row = 0; row < planetAmount; row++){

				allPlanets[row] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), 
					in.readDouble(), in.readDouble(), in.readString());
		}
		return allPlanets;
	}

}