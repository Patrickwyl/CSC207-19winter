package financialProducts;

public class GICRateSaver implements RateSaver {

    private double interest;
    private int period;

    GICRateSaver(int period) {
        this.period = period;
        if (period == 1) {
            this.interest = 0.012;
        } else {
            this.interest = 0.0125;
        }
    }

    @Override
    public double computePayment(double principle) {
        return principle * Math.pow(1 + interest, period);
    }
}
