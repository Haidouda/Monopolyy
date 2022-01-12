
package monopoly2;
import java.io.PrintWriter;
public class Tax extends Square{
    private int taxAmount;

    public Tax(String name, int taxAmount){
        super(name);
        this.taxAmount = taxAmount;
    }

   
    public void doAction(Player p) {

    }
}