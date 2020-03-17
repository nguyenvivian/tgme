public class UserProfile{
    private int score;
    private String username;

    UserProfile(String name){
        this.username = name;
        this.score = 0;
    }
    public void updateScore(int score){
        this.score += score;
    }
    public int getScore(){
        return this.score; 
    }
}