
package cidadesia;

import java.util.Scanner;

public class CidadesIa {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        Map map = new Map();
        map.fillMap();
        int from, to;
        do{
            System.out.print("From: ");
            from = keyboard.nextInt();
            System.out.print("To: ");
            to = keyboard.nextInt();
            if(from!=-1 && from!=-2) map.cityToCity(from, to);
            if(from==-2) {
                map = new Map();
                map.fillMap();
            }
            map.resetRoad();
        }while(from!=-1);     
    }   
}
