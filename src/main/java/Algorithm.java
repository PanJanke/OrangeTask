import JSON.ScheduleDataReader;
import JSON.ScheduleRawData;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Algorithm {

    public static List<TimeSlot> generateMeetingAlgorithm(String path1, String path2, Duration meetingDuration) throws IOException {
        ScheduleRawData rawA = ScheduleDataReader.readScheduleDataFromFile(path1);
        ScheduleRawData rawB = ScheduleDataReader.readScheduleDataFromFile(path2);
        ScheduleData A = new ScheduleData(rawA);
        ScheduleData B = new ScheduleData(rawB);

        return generateMeeting(A, B, meetingDuration);
    }

    public static List<TimeSlot> generateMeeting(ScheduleData schedule1, ScheduleData schedule2, Duration meetingDuration) {
        List<TimeSlot> availableTimeSlots = new ArrayList<>();

        LocalTime start1 = schedule1.getStart();
        LocalTime end1 = schedule1.getEnd();
        List<TimeSlot> meetings1 = schedule1.getPlannedMeetings();

        LocalTime start2 = schedule2.getStart();
        LocalTime end2 = schedule2.getEnd();
        List<TimeSlot> meetings2 = schedule2.getPlannedMeetings();

        LocalTime startTime = start1.isAfter(start2) ? start1 : start2;
        LocalTime endTime = end1.isBefore(end2) ? end1 : end2;


        for (LocalTime time = startTime; time.isBefore(endTime); time = time.plus(meetingDuration)) {
            LocalTime meetingEnd = time.plus(meetingDuration);

            if (isAvailable(time, meetingEnd, meetings1) && isAvailable(time, meetingEnd, meetings2)) {
                availableTimeSlots.add(new TimeSlot(time, meetingEnd));
            }
        }

        return mergeTimeSlots(availableTimeSlots);
    }

    public static List<TimeSlot> mergeTimeSlots(List<TimeSlot> timeSlots) {
        List<TimeSlot> mergedSlots = new ArrayList<>();

        if (timeSlots.isEmpty()) {
            return mergedSlots;
        }

        TimeSlot currentSlot = timeSlots.get(0);

        for (int i = 1; i < timeSlots.size(); i++) {
            TimeSlot nextSlot = timeSlots.get(i);

            if (currentSlot.getEnd().equals(nextSlot.getStart())) {
                currentSlot = new TimeSlot(currentSlot.getStart(), nextSlot.getEnd());
            } else {
                mergedSlots.add(currentSlot);
                currentSlot = nextSlot;
            }
        }
        mergedSlots.add(currentSlot);

        return mergedSlots;
    }

    public static boolean isAvailable(LocalTime start, LocalTime end, List<TimeSlot> meetings) {
        for (TimeSlot meeting : meetings) {
            if (start.isBefore(meeting.getEnd()) && end.isAfter(meeting.getStart())) {
                return false; // overlapping
            }
            if (start.equals(meeting.getStart()) || end.equals(meeting.getEnd())) {
                return false; // starts or ends at the same time as another meeting
            }
            if (start.isAfter(meeting.getStart()) && end.isBefore(meeting.getEnd())) {
                return false; // Meeting is entirely inside another meeting
            }
        }
        return true;
    }


    public static void main(String[] args) {

        String filePath = "calendar1.json";
        String filePath2 = "calendar2.json";
        Duration meetingDuration = Duration.ofMinutes(30);

        List<TimeSlot> result = null;

        try {
            result = generateMeetingAlgorithm(filePath, filePath2, meetingDuration);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (TimeSlot t : result) {
            System.out.println(t.toString());
        }


    }
}



