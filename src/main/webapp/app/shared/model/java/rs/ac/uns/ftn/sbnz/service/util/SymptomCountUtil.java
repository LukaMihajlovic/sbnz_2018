package rs.ac.uns.ftn.sbnz.service.util;

public class SymptomCountUtil {

    public int totalSymptoms;
    public int counted;

    public SymptomCountUtil(int totalSymptoms) {
        this.totalSymptoms = totalSymptoms;
        this.counted = 0;
    }

    public SymptomCountUtil(int counted, int totalSymptoms) {
        this.totalSymptoms = totalSymptoms;
        this.counted = counted;
    }

    public void increment() {
        this.counted += 1;
    }

    public void decrement() {
        this.counted -= 1;
    }

    public int percentage() {
        int percentage = (int)((counted / (float)totalSymptoms) * 100);
        if (counted == totalSymptoms) {
            System.out.println(percentage * counted);
            return percentage * counted;
        }
        return percentage;
    }
}
