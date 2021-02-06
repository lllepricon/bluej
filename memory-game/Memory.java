/*
Man möchte auch mehere Winner möglich machen, was aber mehr Zeit braucht!!!
*/

 

import java.util.Random;

public class Memory {
    private final int gridLines;
    private final int gridColumns;
    public int[][] gameGrid;
    private String[] players = new String[2];
    private final int[]  players_score;
    private String currentPlayer;
    private int rightSolved=0;

    public Memory(int[] size, String[] p_players){
        //size[0] - height, size[1]- width
        //INItIALISIERUNG
        this.gridLines=size[0];
        this.gridColumns=size[1];
        this.players = p_players;
        this.players_score = new int[p_players.length];
        for (int i=0;i<p_players.length;i++){
            this.players_score[i]=0;
        }
        
        this.gameGrid = new int[size[0]][size[1]];
        this.currentPlayer= p_players[0];
        //GRID ERSTELLEN
        this.createGrid(size); 
    }
    
    private void createGrid(int[] size){
        //Wie viele Karten hat das Spiel:
        int cardsNum = size[0]*size[1];
        
        //Erstellen eines Arrays mit Kartenpaaren aus "int" Ziffern
        int[] cards = new int[cardsNum];
        
        
        for(int i=0;i<cardsNum/2;i++){
            cards[i] = (i+1);
            cards[cardsNum-(i+1)]=(i+1);
        }
        //Zufall
        Memory.shuffleArray(cards);
        
        //Karten in den Grid einsetzen
        for (int i=0;i<size[0];i++){
            for (int j=0;j<size[1];j++){
                this.gameGrid[i][j]=cards[i*size[1]+(j+1)-1];
                //System.out.println("Position: " +(i*size[1]+(j+1)-1) + " : " + this.gameGrid[i][j]);
            }
        }
    }

    public void play(int[] a, int[] b){
        if(this.compareCards(a,b)){
            int index=0;
            for (int i=0;i<this.players.length;i++){
                if (players[i].equals(this.currentPlayer)){
                    index=i;
                }
            }
            
            //INCREASE SCORE
            this.players_score[index]++;
            this.gameGrid[a[0]][a[1]]=-(index+1);
            this.gameGrid[a[0]][a[1]]=-(index+1);
            this.gameGrid[b[0]][b[1]]=-(index+1);
            this.gameGrid[b[0]][b[1]]=-(index+1);
            
            //SET NEW CURRENT PLAYER
            /*if(index!=this.players.length-1){
                this.currentPlayer=this.players[index+1];
            } else {
                this.currentPlayer=this.players[0];
            }*/
            
            this.rightSolved++;      
        } else {
            int index=0;
            for (int i=0;i<this.players.length;i++){
                if (players[i].equals(this.currentPlayer)){
                    index=i;
                }
            }
            //Wechsele den Spieler
            if(index!=this.players.length-1){
                this.currentPlayer=this.players[index+1];
            } else {
                this.currentPlayer=this.players[0];
            }
        }
        
    }
    
    public boolean checkEndGame(int lines, int columns){
        for (int i =0;i<lines;i++){
            for (int j =0;j<columns;j++){
                if(this.gameGrid[i][j]>0){ 
                    return false;
                } 
            }
        }
        return true;
    }
    
    public boolean compareCards(int[] first, int[] second){
        if (!(first[0]<0 || first[1]<0) && !(second[0]<0 || second[1]<0)){
            return this.gameGrid[first[0]][first[1]]==this.gameGrid[second[0]][second[1]];
        } else {
            return false;
        }
    }
    
    private static void shuffleArray(int[] ar){
        java.util.Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        //for(int i=0;i<ar.length;i++){
        //    System.out.println(" : "+ar[i]);
        //}
        //System.out.println("END OF SHUFFLE");
    }
    
    //Get Methoden
    
    public int[][]  getGrid(){
        return gameGrid;
    }
    
    public int[]    getGridSize(){
        int[] ar = new int[2];
        ar[0] = this.gridLines;
        ar[1] = this.gridColumns;
        return ar;
    }
    
    public int[]    getScore(){
        return this.players_score;
    }
    
    public String getWinner(){
        boolean flag = true;
        int first = this.getScore()[0];
        for(int i = 1; i < this.getScore().length && flag; i++){
                if (this.getScore()[i] != first)flag = false;
        }
        if(flag){
            return "Unentschieden";
        }
        return this.getPlayers()[this.getBestScoreIndex()];
            
    }
   
    
    public int getBestScoreIndex(){
        int max_index=0;
        //GLEICH 
        //SUCHE DEN GRÖ?TEN SCORE
        int max=this.getScore()[0];

        for(int i=1;i<this.getPlayers().length;i++){
            max= Integer.max(max,this.getScore()[i]);
            if (max== this.getScore()[i]){       
                max_index=i;
                }
            }
            //SETZTE EIN
            return max_index;
            //UNTERSCHIEDLICH -> 3.KLICK NÖTIG      
    }
    
    public String   getCurrentPlayer(){
        return this.currentPlayer;
    }
    
    public String[] getPlayers(){
        return this.players;
    }
    
    public boolean  getSolved(int[] a){
        return (this.getGrid()[a[0]][a[1]]<0);
    }
}
