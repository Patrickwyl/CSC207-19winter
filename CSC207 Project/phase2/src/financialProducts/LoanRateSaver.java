package financialProducts;

public class LoanRateSaver implements RateSaver {

    private double interest;
    private int term;
    private double monthlyPayment;

    LoanRateSaver(int period) {
        this.term = period*12;
        if (period == 1) {
            this.interest = 0.012/12;
        } else if (period == 2) {
            this.interest = 0.0125/12;
        } else if (period == 5) {
            this.interest = 0.02/12;
        } else {
            this.interest = 0.03/12;
        }
    }

    double getInterest() {
        return interest;
    }

    void setTerm(int newTerm) {
        term = newTerm;
    }

    @Override
    public double computePayment(double principle) {
        monthlyPayment = principle / ((1 - Math.pow(1/(1+ interest), term))/interest);
        return monthlyPayment;
    }

    double computePrincipleRepaid(int currentTerm, double principle) {
        double interestN = Math.pow(1 + interest, currentTerm);
        double accumulatePV = principle * interestN;
        double repaid = monthlyPayment * ((interestN - 1)/interest);
        return accumulatePV - repaid;
        //double repaidProportion = (1 - Math.pow(1 / (1 + interest), term - currentTerm)) / interest;
        //return monthlyPayment * repaidProportion;
    }
}
