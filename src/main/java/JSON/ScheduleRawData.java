package JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ScheduleRawData {

    @JsonProperty("working_hours")
    private TextData workingHours;

    @JsonProperty("planned_meeting")
    private List<TextData> plannedMeetings;

    public TextData getWorkingHours() {
        return workingHours;
    }

    public List<TextData> getPlannedMeetings() {
        return plannedMeetings;
    }
}