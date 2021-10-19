package sample.code.model;

public class DataModel {

    int score;
    int timeSec;
    double speed;

    public DataModel(int timeSec, double speed){
        score = 0;
        this.timeSec = timeSec;
        this.speed = 1 / speed;

    }



    public void incScore(){
        score++;
    }

    public void decTime(){
        timeSec--;
    }

    public void reset(){
        score = 0;
        timeSec = 0;
    }


    public int getScore(){
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(int timeSec) {
        this.timeSec = timeSec;
    }

    public long getSpeedInMillis(){
        return (long)(speed * 1000);
    }
    public void setSpeed(double speed) {
        this.speed = 1 / speed;
    }
}
