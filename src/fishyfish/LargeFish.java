package fishyfish;

import java.awt.Color;

public class LargeFish extends Fish {
    
    public LargeFish(int x, int y) {
        super(x,y);
        species = "large fish";
        color = Color.green;
        speed = 1;
        size = 100;
    }
}
