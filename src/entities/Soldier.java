package entities;

import utils.*;
import map.*;

public class Soldier extends Civilian {
    public Soldier(Block block) {
        super(block);
        contacted = true;
        armed = true;
    }
}
