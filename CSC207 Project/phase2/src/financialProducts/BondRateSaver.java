package financialProducts;


public class BondRateSaver implements RateSaver {
    private double semiInterest;
    private double semiCoupon;
    private int period;

    BondRateSaver(int period) {
        this.period = period;
        this.semiCoupon = 0.08/2;
        if (period == 1) {
            this.semiInterest = 0.012/2;
        } else if (period == 2) {
            this.semiInterest = 0.0125/2;
        } else {
            this.semiInterest = 0.016/2;
        }
    }

    @Override
    public double computePayment(double principle) {
        return principle * semiCoupon;
    }

    double computePrice(double principle) {
        double vn = Math.pow(1/(1 + semiInterest), period * 2);
        return (computePayment(principle) * (1- vn) / semiInterest) + principle * vn;
    }

}
