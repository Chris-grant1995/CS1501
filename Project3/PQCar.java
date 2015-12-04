/**
 * Created by Chris on 10/28/15.
 */
public class PQCar implements Comparable<PQCar> {
    int pos;
    int value;
    public PQCar(int p, int v){
        pos = p;
        value = v;
    }

    @Override
    public int compareTo(PQCar c){
       if(value < c.value) return -1;
        if(value == c.value) return 0;
        if(value > c.value) return 1;
        return 0;

    }
}
