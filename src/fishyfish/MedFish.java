package fishyfish;

import java.awt.Color;

public class MedFish extends Fish {
    
    public MedFish(int x, int y) {
        super(x,y);
        species = "medium fish";
        color = Color.yellow;
        speed = 2;
        size = 75;
    }
}