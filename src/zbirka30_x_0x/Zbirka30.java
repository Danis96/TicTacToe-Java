
package zbirka30_x_0x;

import java.util.Scanner;


public class Zbirka30 {

    
    
    static final char PRAZNO = ' ';
    static final char RACUNAR = 'X';
    static final char COVEK = 'O';
    
    static final int covek_pobednik = 0;
    static final int nereseno = 1;
    static final int nejasno = 2;
    static final int racunar_pobednik = 3;
    
    private char[][] tabla;   // kreiramo tablu za igranje
    
    public Zbirka30(){
        tabla = new char[3][3]; // kreiramo matricu 3 x 3
        obrisiTablu();
    }
    
    // inicijalizacija table
    public void obrisiTablu() {
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                tabla[i][j] = PRAZNO;
    }

    
//prikazivanje table
    public void pokaziTablu(){
        System.out.println();
        System.out.println("     0  1  2");
        System.out.println("  ---------------");
           for(int i = 0; i < 3; i++){
               System.out.print(i + " |");
               for(int j = 0; j < 3; j++)
                   System.out.print(" " + tabla[i][j] + " |");
               System.out.println();
               System.out.println("  --------------");
           }
    }
    
    
// provjera ispravnosti poteza (ako je i...) onda vrati false, u drugom slucaju true
    public boolean ispravanPotez(int i, int j){
         
        if(i < 0 || i >= 3 || j < 0 || j >=3 || tabla[i][j] != PRAZNO)
            return false;
        else
            return true;
    
    }
    
//provjera da li je dato polje prazno 
    public boolean praznoPolje(int i, int j){
        return tabla[i][j] == PRAZNO;
    }

    
//provjeravanje da li su sva polja popunjenja
    public boolean punaTabla(){
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(praznoPolje(i, j))
                    return false;
        return true;
    }
    
//odredjivanje da li je pozicija pobjednicka za igraca 
    public boolean pobjeda(char igrac) {   
    
        int i, j;
        
//        pregledati vrste
        for(i = 0; i < 3; i++){
           for(j = 0; j < 3; j++)
               if(tabla[i][j] != igrac)
                   break;
           if(i >= 3)
               return true;
        }
//        pregledati kolone
        for(j = 0; j < 3; j++){
            for(i = 0; i < 3; i++)
                if(tabla[i][j] != igrac)
                    break;
            if(j >= 3)
                return true;
        }
        
//        pregledati obe dijagonale

        if(tabla[0][0] == igrac && tabla[1][1] == igrac && tabla[2][2] == igrac)
            return true;
        
        if(tabla[0][2] == igrac && tabla[1][1] == igrac && tabla[2][0] == igrac)
            return true;
        
        
        return false;
    }
    
//    odigravanje jednog poteza (bez provjere ispravnosti)
    
    public void odigrajPotez(int i, int j, char igrac){
        tabla[i][j] = igrac;
    }
    
//    izracunavanje vrijednosti trenutne pozicije
    
    public int rezultatiPozicije() {
        return pobjeda(RACUNAR) ? racunar_pobednik   : 
               pobjeda(COVEK)   ? covek_pobednik     : 
               punaTabla()      ? nereseno           : nejasno;
    }
    
//    nalazenje najboljeg poteza igraca trenutno za poziciju
    
    public Potez izaberiNajboljiPotez(char igrac){
        
        char protivnik;      //protivnicki igrac
        Potez pp;            //najbolji potez protivnika
        int ni = 0, nj = 0;  // kooridinate najboljeg poteza
        int r;               // (privremeni) rezultat date pozicije
        
        r = rezultatiPozicije();
        if(r != nejasno)    // zavrsna pozicija
            return new Potez(r, -1, -1);
        
        if(igrac == RACUNAR) {
            protivnik = COVEK;
            r = covek_pobednik;
        }
        else {
            protivnik = RACUNAR;
            r = racunar_pobednik;
        }
        
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                if(praznoPolje(i, j)){
                    odigrajPotez(i, j, igrac);                 // pokusaj potez
                    pp = izaberiNajboljiPotez(protivnik);
                    odigrajPotez(i, j, PRAZNO);                // vrati potez
                    
                    
                    if((igrac == RACUNAR && pp.vrednost() > r) || 
                       (igrac == COVEK   && pp.vrednost() < r)) {
                    // zapamti najbolji dosadasnji rezultat
                    
                    r = pp.vrednost();
                    ni = i;
                    nj = j;
                    } 
                }
        return new Potez(r, ni, nj);
    }
 
    public static void main(String[] args) {
        
        
        int ci, cj;    //koordinate covjekovog poteza
        int ri, rj;    //koordinate racunarovog poteza
        char naPotezu; //igrac koji je na potezu
        int r;         // rezultati pozicije nakon odigranog poteza
        Potez potez;   //izabrani najbolji potez racunara
        
        Zbirka30 igra = new Zbirka30();
        Scanner tast = new Scanner(System.in);
        
        System.out.print("Hajde da igramo iks-oks: ja sam ");
        System.out.println(RACUNAR + ", ti si " + COVEK + ".");
        System.out.print("Vrste i kolone table su numerisane ");
        System.out.println(" brojevima 0,1,2, ");
        System.out.print("a polja su predstavljena odgovarajucim ");
        System.out.println(" koordinatama. ");
        System.out.print("Npr. polje u sredini ima ");
        System.out.println(" koordinate 1  1.");
        
        
        igra.pokaziTablu();
        System.out.println();
        System.out.print("Ja igram prvi (d/n)? ");
        String odg = tast.next();
        if(odg.toUpperCase().equals("N"))
            naPotezu = COVEK;
        else
            naPotezu = RACUNAR;
        
        
        while(true) {
            if(naPotezu == RACUNAR) {
                potez = igra.izaberiNajboljiPotez(RACUNAR);
                ri = potez.vrsta(); rj = potez.kolona();
                igra.odigrajPotez(ri, rj, RACUNAR);
                System.out.println("Moj potez: " + ri + " " + rj);
                igra.pokaziTablu();
                System.out.println();
                r = igra.rezultatiPozicije();
                if(r == racunar_pobednik) {
                    System.out.println("Ja sam pobjedio!!");
                    break;
                } 
                else if(r == nereseno) {
                    System.out.println("Nereseno!!");
                    break;
                } 
                naPotezu = COVEK;
            }
            
            if(naPotezu == COVEK) {
                do {
                    System.out.print("Tvoj potez: ");
                    ci = tast.nextInt(); cj = tast.nextInt();
                } while (!igra.ispravanPotez(ci, cj));
                igra.odigrajPotez(ci, cj, COVEK);
                igra.pokaziTablu();
                System.out.println();
                r = igra.rezultatiPozicije();
                if(r == covek_pobednik) {
                    System.out.println("Bravo, ti si pobjednik!!");
                    break;
                } 
                else if(r == nereseno) {
                    System.out.println("Nereseno!!");
                    break;
                } 
                naPotezu = RACUNAR;
            }
        }   
        
    }
    
}
