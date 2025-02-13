package GUI.utils;

public class GameData {
    private int panal;
    private int soldier;
    private int civilian;
    private int medic;
    private int engineer;
    private boolean seize;

    public GameData(int panal) {
        this(panal,1,1,1,1,false);
    }

    public GameData(int panal,int soldier, int civilian, int medic, int engineer, boolean seize) {
        this.panal = panal;
        this.soldier = soldier;
        this.civilian = civilian;
        this.medic = medic;
        this.engineer = engineer;
        this.seize = seize;
    }

    public int getEngineer() {
        return engineer;
    }

    public int setEngineer(int engineer) {
        return this.engineer = engineer;
    }

    public void setPanal(int panal) {
        this.panal = panal;
    }


    public void setSoldier(int soldier) {
        this.soldier = soldier;
    }


    public void setCivilian(int civilian) {
        this.civilian = civilian;
    }


    public void setMedic(int medic) {
        this.medic = medic;
    }


    public int getPanal() {
        return panal;
    }


    public int getSoldier() {
        return soldier;
    }


    public int getCivilian() {
        return civilian;
    }


    public int getMedic() {
        return medic;
    }

    @Override
    public String toString() {
        return "panal=" + panal + ", soldier=" + soldier + ", civilian=" + civilian + ", medic=" + medic
                + ", engineer=" + engineer + ", seize=" + seize;
    }


    public String toHTML(){
        return "<HTML>panal=" + panal + "<br> soldier=" + soldier + "<br> civilian=" + civilian + "<br> medic=" + medic
                + "<br> engineer=" + engineer + "<br> seize=" + seize + "</HTML>";
    }

    public boolean getSeize() {
        return seize;
    }

    public void setSeize(boolean seize) {
        this.seize = seize;
    }
}
