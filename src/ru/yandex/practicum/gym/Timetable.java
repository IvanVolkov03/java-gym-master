package ru.yandex.practicum.gym;
import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();
    private List<TrainingSession> allSessions = new ArrayList<>();

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

        allSessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        return timetable.get(dayOfWeek) == null ? Collections.emptyList() :
                dayMap.getOrDefault(timeOfDay, Collections.emptyList());
    }

    public List<CoachTrainingCount> getCountByCoaches() {
        //Создаем таблицу для подсчета
        Map<Coach, Integer> counts = new HashMap<>();

        for (TrainingSession session : allSessions) {
            Coach coach = session.getCoach();

            // Получаем текущее значение (если его нет, то 0) и прибавляем 1
            int currentCount = counts.getOrDefault(coach, 0);
            counts.put(coach, currentCount + 1);
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
