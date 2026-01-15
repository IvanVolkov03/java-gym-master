package ru.yandex.practicum.gym;

public class CoachTrainingCount {
    private Coach coach;
    private int count;

    public CoachTrainingCount(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() { return coach; }

    public int getCount() { return count; }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CoachTrainingCount{" +
                "coach=" + coach +
                '}';
    }
}
