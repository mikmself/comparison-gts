class Schedule {
    String subject;
    String time;
    String day;
    String lecturer;
    String room;

    public Schedule(String subject, String time, String day, String lecturer, String room) {
        this.subject = subject;
        this.time = time;
        this.day = day;
        this.lecturer = lecturer;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "subject='" + subject + '\'' +
                ", time='" + time + '\'' +
                ", day='" + day + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
