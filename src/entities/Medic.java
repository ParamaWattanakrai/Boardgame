package entities;

import utils.*;
import map.*;

public class Medic extends Civilian {
    public Medic(Block block) {
        super(block);
        contacted = true;
    }
}
