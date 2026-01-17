package ru.yandex.practicum.gym;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TimetableAdditionalTest {
    private Timetable timetable;
    private Coach coachIvanov;
    private Coach coachPetrov;
    private Coach coachSidorov;
    private Group adultGroup;

    @BeforeEach
    void setUp() {
        timetable = new Timetable();
        coachIvanov = new Coach("Иванов", "Иван", "Иванович");
        coachPetrov = new Coach("Петров", "Петр", "Петрович");
        coachSidorov = new Coach("Сидоров", "Сидор", "Сидорович");
        adultGroup = new Group("Акробатика", Age.ADULT, 90);
    }

    @Test
    void testGetCountByCoachesEmpty() {
        //Проверка работы с абсолютно пустым расписанием
        List<CoachTrainingCount> result = timetable.getCountByCoaches();

        assertNotNull(result, "Метод не должен возвращать null");
        assertTrue(result.isEmpty(), "Для пустого расписания список должен быть пустым");
    }

    @Test
    void testGetCountByCoachesSortingOrder() {
        //Проверка корректности подсчета и сортировки по убыванию

        // Иванов ведет 2 занятия
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coachIvanov, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coachIvanov, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));

        // Петров ведет 3 занятия
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coachPetrov, DayOfWeek.MONDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coachPetrov, DayOfWeek.TUESDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coachPetrov, DayOfWeek.FRIDAY, new TimeOfDay(12, 0)));

        // Сидоров ведет 1 занятие
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coachSidorov, DayOfWeek.SATURDAY, new TimeOfDay(15, 0)));

        List<CoachTrainingCount> result = timetable.getCountByCoaches();

        assertEquals(3, result.size(), "В списке должно быть 3 тренера");

        // Проверяем порядок убывания: Петров (3), Иванов (2), Сидоров (1)
        assertEquals(coachPetrov, result.get(0).getCoach(), "Первым в списке должен быть Петров");
        assertEquals(3, result.get(0).getCount());

        assertEquals(coachIvanov, result.get(1).getCoach(), "Вторым в списке должен быть Иванов");
        assertEquals(2, result.get(1).getCount());

        assertEquals(coachSidorov, result.get(2).getCoach(), "Третьим в списке должен быть Сидоров");
        assertEquals(1, result.get(2).getCount());
    }

    @Test
    void testGetCountByCoachesAggregation() {
        //Проверка занятий одного тренера в один день и в разные дни

        // Добавляем Иванову два занятия в один и тот же день (понедельник) в разное время
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coachIvanov, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coachIvanov, DayOfWeek.MONDAY, new TimeOfDay(19, 0)));

        // Добавляем Петрову одно занятие
        timetable.addNewTrainingSession(new TrainingSession(adultGroup, coachPetrov, DayOfWeek.FRIDAY, new TimeOfDay(18, 0)));

        List<CoachTrainingCount> result = timetable.getCountByCoaches();

        assertEquals(2, result.size(), "В списке должно быть 2 тренера");

        // Иванов должен быть первым, так как у него 2 занятия
        assertEquals(coachIvanov, result.get(0).getCoach());
        assertEquals(2, result.get(0).getCount(), "Счетчик занятий Иванова должен суммироваться корректно");
    }
}