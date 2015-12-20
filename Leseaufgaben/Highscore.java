public class Highscore {
    private String name;
    private int rang;

    public Highscore(String name, int rang){
        this.name = name;
        this.rang = rang;
    }

    public String getHighscoreEintrag(){
        return this.rang + ": " + this.name;
    }

    public String getName(){
        return this.name;
    }

    public int getRang(){
        return this.rang;
    }

}