public class Planet {
    /*Define variables for Planet class. */
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
    private String imageToDraw;
    private double g = 6.67e-11;

    /*First constructor of the Planet class. */
	public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

    /**Second constructor of thePlanet class. 
      *What is the purpose of this? */
    public Planet(Planet b){
    	xxPos = b.xxPos;
    	yyPos = b.yyPos;
    	xxVel = b.xxVel;
    	yyVel = b.yyVel;
    	mass = b.mass;
    	imgFileName = b.imgFileName;
    }

    /**Given a Planet, calculate the distance of two bodies 
      *and return radius as double. */
    public double calcDistance(Planet b1){
    	double r, rsquare;
    	rsquare = (this.xxPos - b1.xxPos)*(this.xxPos - b1.xxPos) + 
        (this.yyPos - b1.yyPos)*(this.yyPos - b1.yyPos);
    	r = Math.sqrt(rsquare);

    	return r;
    }

    public double calcForceExertedBy(Planet b1){
    	double f;
    	double r = calcDistance(b1);

    	f = g*this.mass*b1.mass/r/r;

    	return f;
    }

    public double calcForceExertedByX(Planet b1){
    	double fx;
        double dx;
        double r = calcDistance(b1);
        double f = calcForceExertedBy(b1);

        dx = b1.xxPos - this.xxPos;
    	fx = f*dx/r;

    	return fx;
    }

    public double calcForceExertedByY(Planet b1){
    	double fy;
        double dy;
        double r = calcDistance(b1);
        double f = calcForceExertedBy(b1);

        dy = b1.yyPos - this.yyPos;
    	fy = f*dy/r;

    	return fy;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets){
    	double fNetX = 0;

    	for (int i = 0; i < allPlanets.length; i++){
    		if (this.equals(allPlanets[i]) == false){
    			fNetX = fNetX + calcForceExertedByX(allPlanets[i]);
    		}
    	}

    	return fNetX;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets){
    	double fNetY = 0;

    	for (int i = 0; i < allPlanets.length; i++){
    		if (this.equals(allPlanets[i]) == false){
    			fNetY = fNetY + calcForceExertedByY(allPlanets[i]);
    		}
    	}

    	return fNetY;
    }

    public void update(double dt, double fX, double fY){
    	double aX, aY;

    	aX = fX/this.mass;
    	aY = fY/this.mass;

    	this.xxVel = this.xxVel + dt*aX;
    	this.yyVel = this.yyVel + dt*aY;

    	this.xxPos = this.xxPos + dt*this.xxVel;
    	this.yyPos = this.yyPos + dt*this.yyVel;
    }

    public void draw(){
        imageToDraw = "images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, imageToDraw);
    }

}
