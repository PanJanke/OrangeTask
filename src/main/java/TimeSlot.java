import java.time.LocalTime;

public class TimeSlot {
    private LocalTime start;
    private LocalTime end;

    public TimeSlot(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }


    @Override
    public String toString() {
        return "[\"" + start.toString() + "\", \"" + end.toString() + "\"]";
    }
}