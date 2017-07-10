package gu.wen.bulletmullet;

/**
 * Created by angelwen on 7/9/17.
 */

public class BulletItem implements Comparable<BulletItem>{
    private int position;
    private String text;
    public BulletItem(String myText, int myPosition){
        text = myText;
        position = myPosition;
    }
    public String toString(){
        return text;
    }
    public void setPosition(int pos){
        position = pos;
    }
    public int getPosition(){
        return position;
    }
    @Override
    public int compareTo(BulletItem o){
        if (o.position > this.position){
            return -1;
        }
        if (o.position < this.position){
            return 1;
        }
        return 0;
    }
}
