package ru.yandex.practicum.gym;
import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        // Если для этого дня еще нет TreeMap, создаем ее
        timetable.putIfAbsent(day, new TreeMap<>());
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(day);

        // Если в это время еще нет тренировок, создаем список
        dayMap.putIfAbsent(time, new ArrayList<>());

        // Добавляем занятие
        dayMap.get(time).add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) {
            return Collections.emptyList();
        }
        return dayMap.getOrDefault(timeOfDay, Collections.emptyList());
    }

    public List<CoachTrainingCount> getCountByCoaches() {
        //Создаем таблицу для подсчета
        Map<Coach, Integer> counts = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> dayMap : timetable.values()) {
            // dayMap.values() дает нам все List<TrainingSession> (занятия в разное время)
            for (List<TrainingSession> sessions : dayMap.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    // Увеличиваем счетчик для тренера
                    counts.put(coach, counts.getOrDefault(coach, 0) + 1);
                }
            }
        }

        //Переносим данные в список для сортировки
        List<CoachTrainingCount> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : counts.entrySet()) {
            result.add(new CoachTrainingCount(entry.getKey(), entry.getValue()));
        }

        //Сортируем список по убыванию количества (o2.count - o1.count)
        result.sort((o1, o2) -> Integer.compare(o2.getCount(), o1.getCount()));

        return result;
    }
}
