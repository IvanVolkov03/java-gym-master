package ru.yandex.practicum.gym;

import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {

    //часы (от 0 до 23)
    private int hours;
    //минуты (от 0 до 59)
    private int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Минуты должны быть в диапазоне от 0 до 59. Передано: " + minutes);
        }
        this.minutes = minutes;
    }

    public void setHours(int hours) {
        if (hours < 0 || hours > 23) { // Для 24-часового формата
            throw new IllegalArgumentException("Часы должны быть в диапазоне от 0 до 23. Передано: " + hours);
        }
        this.hours = hours;
    }

    @Override
    public int compareTo(TimeOfDay o) {
        if (hours != o.hours) return hours - o.hours;
        return minutes - o.minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeOfDay timeOfDay = (TimeOfDay) o;
        return hours == timeOfDay.hours && minutes == timeOfDay.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes);
    }

    @Override
    public String toString() {
        return "TimeOfDay{" +
                "hours=" + hours +
                ", minutes=" + minutes +
                '}';
    }
}
