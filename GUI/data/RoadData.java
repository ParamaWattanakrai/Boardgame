package gui.data;

import java.util.Random;

public class RoadData {
    private int panal;
    private int commander;
    private int soldier;
    private int civilian;
    private int medic;
    private int engineer;
    private int road;
    private int dog;
    private int landmark;
    private boolean capture;

    public RoadData(int panal) {
        this(panal,1,1,1,1,1, new Random().nextInt(3),1,new Random().nextInt(5),false);
    }

    public RoadData(int panal, int commander, int soldier, int civilian, int medic, int engineer, int road, int dog, int landmark, boolean capture) {
        this.panal = panal;
        this.commander = commander;
        this.soldier = soldier;
        this.civilian = civilian;
        this.medic = medic;
        this.engineer = engineer;
        this.road = road;
        this.dog = dog;
        this.landmark = landmark;
        this.capture = capture;
    }

    public int getPanal() {
        return panal;
    }

    public void setPanal(int panal) {
        this.panal = panal;
    }

    public int getCommander() {
        return commander;
    }

    public void setCommander(int commander) {
        this.commander = commander;
    }

    public int getSoldier() {
        return soldier;
    }

    public void setSoldier(int soldier) {
        this.soldier = soldier;
    }

    public int getCivilian() {
        return civilian;
    }

    public void setCivilian(int civilian) {
        this.civilian = civilian;
    }

    public int getMedic() {
        return medic;
    }

    public void setMedic(int medic) {
        this.medic = medic;
    }

    public int getEngineer() {
        return engineer;
    }

    public void setEngineer(int engineer) {
        this.engineer = engineer;
    }

    public int getRoad() {
        return road;
    }

    public void setRoad(int road) {
        this.road = road;
    }

    public int getDog() {
        return dog;
    }

    public void setDog(int dog) {
        this.dog = dog;
    }

    public int getLandmark() {
        return landmark;
    }

    public void setLandmark(int landmark) {
        this.landmark = landmark;
    }

    public boolean isCapture() {
        return capture;
    }

    public void setCapture(boolean capture) {
        this.capture = capture;
    }


    @Override
    public String toString() {
        return "Block=" + panal + ", commander=" + commander + ", soldier=" + soldier + ", civilian="
                + civilian + ", medic=" + medic + ", engineer=" + engineer + ", road=" + road + ", dog=" + dog
                + ", landmark=" + landmark + ", capture=" + capture;
    }

    

}

  