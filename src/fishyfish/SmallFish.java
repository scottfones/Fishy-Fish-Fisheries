package fishyfish;

import java.awt.Color;

public class SmallFish extends Fish {
    
    public SmallFish(int x, int y) {
        super(x,y);
        species = "small fish";
        color = Color.red;
        speed = 1;
        size = 50;
    }
}
