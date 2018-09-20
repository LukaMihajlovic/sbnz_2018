package rs.ac.uns.ftn.sbnz.domain;

public class SFToClient {
    private Disease disease;
    private int counter;

    public SFToClient() {}

    public SFToClient(Disease disease, int counter) {
        this.disease = disease;
        this.counter = counter;
    }

    public int getCount() {
        return counter;
    }

    public void setCount(int count) {
        this.counter = count;
    }

    public int compareTo(SFToClient o) {
        return (counter - o.counter);
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease d) {
        this.disease = d;
    }


}
