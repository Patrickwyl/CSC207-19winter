package user;

public enum CustomerLevel {
    regular(1000), silver(2000),
    gold(5000), platinum(10000);

    private int debtLimit;
    CustomerLevel(int limit){
        this.debtLimit = limit;
    }
    public int getDebtLimit(){
        return debtLimit;
    }
    public static CustomerLevel getLevel(String name){
        for (CustomerLevel level: CustomerLevel.values()){
            if (level.name().equals(name)){
                return level;
            }
        }
        return null;
    }
}
