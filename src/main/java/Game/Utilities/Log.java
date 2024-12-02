package Game.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private String message;
    private final String date;

    public Log(String message) {
        this.message = message;
        this.date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Log{" +
                "message='" + message + '\'' +
                ", timestamp='" + date + '\'' +
                '}';
    }
}