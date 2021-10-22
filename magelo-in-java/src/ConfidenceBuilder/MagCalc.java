package ConfidenceBuilder;





public class MagCalc {

	public static void main(String[] args) {
		var total = 0.0;
		var count = 0;
		
		for(var idx = 1; idx <= 75; idx++) {
			if(idx % 3 == 0 || idx % 7 == 0) {
				total += idx;
				count++;
			}
		}
		System.out.printf("Avg is %f", total / count);

	}

}
