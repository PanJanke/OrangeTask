import JSON.ScheduleRawData;
import JSON.TextData;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
public class ScheduleData {


    private LocalTime start;
    private LocalTime end;
    private List<TimeSlot> plannedMeetings;

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public List<TimeSlot> getPlannedMeetings() {
        return plannedMeetings;
    }

    public ScheduleData(ScheduleRawData scheduleRawData){
        this.start = LocalTime.parse(scheduleRawData.getWorkingHours().getStart());
        this.end = LocalTime.parse(scheduleRawData.getWorkingHours().getEnd());

        this.plannedMeetings = new ArrayList<>();
        for (TextData textData : scheduleRawData.getPlannedMeetings()) {
            LocalTime meetingStart = LocalTime.parse(textData.getStart());
            LocalTime meetingEnd = LocalTime.parse(textData.getEnd());
            this.plannedMeetings.add(new TimeSlot(meetingStart, meetingEnd));
        }
    }
}
