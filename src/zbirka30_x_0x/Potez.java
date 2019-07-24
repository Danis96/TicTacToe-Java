
package zbirka30_x_0x;


public class Potez {
    
    
    private int i;  // vrsta poteza
    private int j;  // kolona poteza
    private int v;  // vrijednost pozicije s tim potezom
    
    public Potez(int v, int i, int j){
        this.i = i;
        this.j = j;
        this.v = v;
    }
    
    public int vrsta(){
        return i;
       
    }
    
    public int kolona(){
    
       return j;
        
    }
    
    public int vrednost(){
        return v;
    }
    
}
