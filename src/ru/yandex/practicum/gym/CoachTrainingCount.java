package ru.yandex.practicum.gym;

import java.util.Objects;

public class CoachTrainingCount {
    private Coach coach;
    private int count;

    public CoachTrainingCount(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CoachTrainingCount that = (CoachTrainingCount) o;
        return count == that.count && Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, count);
    }

    @Override
    public String toString() {
        return "CoachTrainingCount{" +
                "coach=" + coach +
                ", count=" + count +
                '}';
    }
}