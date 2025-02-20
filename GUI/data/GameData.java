package gui.data;

public class GameData {
    private int day;
    private int night;
    private int[] task = new int[]{0,0,0,0,0};

    public GameData() {
        this.day = 1;
        this.night = 1;
    }

    public GameData(int day, int night, int[] task) {
        this.day = day;
        this.night = night;
        this.task = task;
    }

    public int getDay() {
        return day;
    }
    
    public void setDay(int day) {
        this.day = day;
    }

    public int getNight() {
        return night;
    }

    public void setNight(int night) {
        this.night = night;
    }

    public int[] getTask() {
        return task;
    }

    public void setTask(int[] task) {
        this.task = task;
    }


}   
