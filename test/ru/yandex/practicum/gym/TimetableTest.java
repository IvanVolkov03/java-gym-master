package ru.yandex.practicum.gym;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.TreeMap;
import static org.junit.jupiter.api.Assertions.*;

class TimetableTest {
    private Timetable timetable;
    private Coach commonCoach;
    private Group childGroup;
    private Group adultGroup;
    private TimeOfDay time13;

    @BeforeEach
    void setUp() {
        timetable = new Timetable();
        commonCoach = new Coach("Сарычев", "Кирилл", "Игоревич");
        childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        adultGroup = new Group("Акробатика для взрослых", Age.ADULT, 90);
        time13 = new TimeOfDay(13, 0);
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        TrainingSession singleTrainingSession = new TrainingSession(childGroup, commonCoach,
                DayOfWeek.MONDAY, time13);

        timetable.addNewTrainingSession(singleTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        var mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size(), "В понедельник должна быть 1 группа времени");

        // Проверить, что за вторник не вернулось занятий
        var tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        assertTrue(tuesdaySessions.isEmpty(), "Во вторник должно быть пусто");
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        // Четверг 20:00
        TrainingSession thursdayAdult = new TrainingSession(adultGroup, commonCoach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        // Понедельник 13:00, Четверг 13:00, Суббота 10:00
        TrainingSession mondayChild = new TrainingSession(childGroup, commonCoach,
                DayOfWeek.MONDAY, time13);
        TrainingSession thursdayChild = new TrainingSession(childGroup, commonCoach,
                DayOfWeek.THURSDAY, time13);
        TrainingSession saturdayChild = new TrainingSession(childGroup, commonCoach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(thursdayAdult);
        timetable.addNewTrainingSession(mondayChild);
        timetable.addNewTrainingSession(thursdayChild);
        timetable.addNewTrainingSession(saturdayChild);

        // Проверка четверга (упорядоченность по времени)
        TreeMap<TimeOfDay, List<TrainingSession>> thursdaySchedule = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        assertEquals(2, thursdaySchedule.size());

        // Проверяем порядок: первый ключ в TreeMap должен быть 13:00, последний 20:00
        assertEquals(13, thursdaySchedule.firstKey().getHours());
        assertEquals(20, thursdaySchedule.lastKey().getHours());

        // Проверка понедельника
        var mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(1, mondaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        TrainingSession session = new TrainingSession(childGroup, commonCoach, DayOfWeek.MONDAY, time13);
        timetable.addNewTrainingSession(session);

        // Проверить, что за понедельник в 13:00 вернулось именно это занятие
        List<TrainingSession> sessionsAt13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, time13);
        assertEquals(1, sessionsAt13.size());
        assertEquals(childGroup.getTitle(), sessionsAt13.get(0).getGroup().getTitle());

        // Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> sessionsAt14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        assertTrue(sessionsAt14.isEmpty());
    }
}