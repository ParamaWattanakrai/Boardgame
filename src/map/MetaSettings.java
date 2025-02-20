package src.map;

public class MetaSettings {
    private int turnLimit;

    private int fieldWidth;
    private int fieldHeight;

    private int hospitalNum;
    private int storeNum;
    private int policeStationNum;
    private int powerPlantNum;

    private int soldierNum;
    private int medicNum;
    private int mechanicNum;
    private int civilianNum;

    public MetaSettings(int turnLimit, int fieldWidth, int fieldHeight, int hospitalNum,
                        int storeNum, int policeStationNum, int powerPlantNum, int soldierNum,
                        int medicNum, int mechanicNum, int civilianNum) {
        this.turnLimit = turnLimit;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.hospitalNum = hospitalNum;
        this.storeNum = storeNum;
        this.policeStationNum = policeStationNum;
        this.powerPlantNum = powerPlantNum;
        this.soldierNum = soldierNum;
        this.medicNum = medicNum;
        this.mechanicNum = mechanicNum;
        this.civilianNum = civilianNum;
    }

    public int getTurnLimit() {
        return turnLimit;
    }
    public int getFieldWidth() {
        return fieldWidth;
    }
    public int getFieldHeight() {
        return fieldHeight;
    }
    public int getHospitalNum() {
        return hospitalNum;
    }
    public int getStoreNum() {
        return storeNum;
    }
    public int getPoliceStationNum() {
        return policeStationNum;
    }
    public int getPowerPlantNum() {
        return powerPlantNum;
    }
    public int getSoldierNum() {
        return soldierNum;
    }
    public int getMedicNum() {
        return medicNum;
    }
    public int getMechanicNum() {
        return mechanicNum;
    }
    public int getCivilianNum() {
        return civilianNum;
    }
}
