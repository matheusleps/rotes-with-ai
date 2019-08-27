
package cidadesia;

import java.util.Random;

public class Map {
    private static final int MAPSIZE = 8;
    int map[][];
    int distance, roadIndex, failIndex;
    int[][] relations;
    int[] road, failureRoad;
    public Map(){
        map = new int[MAPSIZE][MAPSIZE];
        relations = new int[MAPSIZE][MAPSIZE];
        road = new int[MAPSIZE];
        failureRoad = new int[MAPSIZE];
        distance = 0;
        roadIndex = 0;
        failIndex = 0;
        fillFailRoad();
        fill();
    }
    public void resetRoad(){
        road = new int[MAPSIZE];
        fillRoad();
        distance = 0;
        roadIndex = 0;
    }
    private void fill(){
        for(int i=0; i<MAPSIZE; i++)
            for(int j=0; j<MAPSIZE; j++){
                map[i][j]=0;
                relations[i][j]=-1;
            }  
    }
    private void showMap(){
        for(int i=0; i<MAPSIZE; i++){
            System.out.print(" ");
            if(i==0) System.out.print(" "+i);
            else System.out.print(i);
        }
        System.out.println(" X");
        for(int i=0; i<MAPSIZE; i++){
            System.out.print(i+" ");
            for(int j=0; j<MAPSIZE; j++){
                if(map[i][j]!=0)
                    System.out.print(map[i][j]+" ");
                else System.out.print("  ");
            }
            System.out.print("\n");
        }
        System.out.println("Y");
        System.out.println("--------------------");
    }
    private void showRelations(){
        for(int i=0; i<MAPSIZE; i++){
            System.out.print(i+" ");
            for(int j=0; relations[i][j]!= -1; j++){
                System.out.print(relations[i][j]+" ");
            }
            System.out.print("\n");
        }
        System.out.println("-----------------------");
    }
    private void fillRoad(){
        for(int i=0; i<road.length; i++) road[i] = -1;
    }
    private void fillFailRoad(){
        for(int i=0; i<failureRoad.length; i++) failureRoad[i] = -1;
    }
    public void fillMap(){
        fillRoad();
        Random random = new Random();
        int distance = 0;
        for(int i=0; i<MAPSIZE; i++)
            for(int j=i+1; j<MAPSIZE; j++){
                distance = random.nextInt(9)+1;
                if(distance>3 && j<i+3){
                    map[i][j] = distance;
                    map[j][i] = map[i][j];
                }
            }
        cityRelations();
        showMap();
        showRelations();
    }
    private void cityRelations(){
        for(int i=0; i<MAPSIZE; i++){
            int cont = 0;
            for(int j=0; j<MAPSIZE; j++){
                if(map[i][j]!=0){
                    relations[i][cont] = j;
                    cont++;
                }
            }
        }
    }
    public void cityToCity(int current, int destination){  
        if(current!=-1){
            if(verifyDestination(current, destination)){
                road[roadIndex] = current;
                road[roadIndex+1] = destination;
                current = destination;
                cityToCity(current, destination);
            }
            else if(current==destination){
                calcDistance();
                showRoad();
            }
            else{
                current = defineNextCity(current);
                cityToCity(current, destination);
            }
        }
        else{
            for(int i=0; failureRoad[i]!=-1; i++) System.out.print(failureRoad[i]);
            System.out.println("Impossível encontrar o caminho.");
        }
    }
    private boolean verifyDestination(int currentCity, int destination) {
        for(int i=0; i<relations.length; i++)
           if(relations[currentCity][i]==destination) return true;
       return false;
    }
    private void calcDistance(){
        for(int i=0; road[i+1]!=-1 && i<road.length-1; i++){
            distance = distance+map[road[i]][road[i+1]];
        }
    }
    private int defineNextCity(int city){
        int nextCity = -1;
        if(!verifyRoad(city)){
            road[roadIndex] = city;
            roadIndex++;
            for(int i=0; relations[city][i]!=-1; i++){
                if(!verifyRoad(relations[city][i]) && !verifyFail(relations[city][i])) {
                    nextCity=relations[city][i];
                    break;
                }
            }
            if(nextCity==-1){
                storeFailRoad(city);
                nextCity = road[0];
                resetRoad();
            }
        }
        return nextCity;
    }
    private boolean verifyFail(int city){
        boolean response = false;
        for(int i=0; failureRoad[i]!=-1; i++){
            if(failureRoad[i]==city) response = true;
        }
        return response;
    }
    private boolean verifyRoad(int city){
        boolean response = false;
        for(int i=0; i<road.length; i++){
            if(road[i]==city) response = true;
        }
        return response;
    }
    private void showRoad(){
        for(int i=0; road[i]!=-1; i++){
            if(i==0){
                System.out.println("Você saiu da cidade: " + road[i]);
            }
            else if(road[i+1]==-1){
                System.out.println("E por fim chegou ao seu destino " + road[i] + " após "+distance+" Km!");
            }
            else{
                System.out.println("Passando por: " + road[i]);
            }
        }
    }
    private void storeFailRoad(int city){
        failureRoad[failIndex] = city;
        failIndex++;
    }
}
