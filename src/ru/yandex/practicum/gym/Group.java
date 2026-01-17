package ru.yandex.practicum.gym;

import java.util.Objects;

public class Group {
    //название группы
    private String title;
    //тип (взрослая или детская)
    private Age age;
    //длительность (в минутах)
    private int duration;

    public Group(String title, Age age, int duration) {
        this.title = title;
        this.age = age;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public Age getAge() {
        return age;
    }

    public int getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return duration == group.duration && Objects.equals(title, group.title) && age == group.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, age, duration);
    }

    @Override
    public String toString() {
        return "Group{" +
                "title='" + title + '\'' +
                ", age=" + age +
                ", duration=" + duration +
                '}';
    }
}
