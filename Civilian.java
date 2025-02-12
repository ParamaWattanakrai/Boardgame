public class Civilian {
    protected Block block;
    protected Vitality vitality = Vitality.ALIVE;
    protected boolean contacted = false;
    protected boolean armed = false;

    Civilian(Block block) {
        this.block = block;
    }

    public void infect() {
        vitality = Vitality.COMA;
        disarm();
    }

    public void contact() {
        contacted = true;
    }

    public boolean arm() {
        if (!armed && block.removeGun()) {
            armed = true;
            return true;
        }
        return false;
    }
    public void disarm() {
        if (armed) {
            block.addGun();
        }
    }

    public Vitality getVitality() {
        return vitality;
    }
    public boolean isContacted() {
        return contacted;
    }
    public boolean isArmed() {
        return armed;
    }
}