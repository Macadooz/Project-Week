import java.util.*;


class StatWizard {
	Random ran = new Random();
	double stanDev, mean;

	public StatWizard(ArrayList<Integer> averageValues) {
        mean = 0;
        for (int i=0; i<averageValues.size(); i++) {
        	mean += averageValues.get(i);
        }
        mean = mean / averageValues.size();

        double tempVar = 0;
        for (int i=0; i<averageValues.size(); i++) {
        	tempVar += Math.pow((averageValues.get(i) - mean), 2);
        }
        tempVar /= averageValues.size()-1;
        stanDev = Math.sqrt(tempVar);
    }

    public double getNextNormalValue() {
    	return (ran.nextGaussian() * stanDev) + mean;
    }
}