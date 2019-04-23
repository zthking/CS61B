public class NBody{

	public static double readRadius(String link){

        In in = new In(link);
		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();

		return secondItemInFile;
	}

	public static Body[] readBodies(String link){

		In in = new In(link);
		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();

		Body[] allPlanets = new Body[5];
		for (int row = 0; row < 5; row++){

				allPlanets[row] = new Body(in.readDouble(), in.readDouble(), in.readDouble(), 
					in.readDouble(), in.readDouble(), in.readString());
		}
		return allPlanets;
	}

}