public class NBody{

/*
	public static void main(String arg []){

	}
*/
	public static double readRadius(String link){

		In in = new In(link);
		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();

		return secondItemInFile;
	}

}