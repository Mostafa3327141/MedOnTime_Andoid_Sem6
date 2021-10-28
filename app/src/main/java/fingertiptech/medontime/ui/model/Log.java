package fingertiptech.medontime.ui.model;

import java.util.Calendar;
import java.util.Date;

public class Log {
    private String id, patientID, medicationID, medicationName;
    private String timeTake;

    public Log(String id, String patientID, String medicationID, String medicationName) {
        this.id = id;
        this.patientID = patientID;
        this.medicationID = medicationID;
        this.medicationName = medicationName;
        this.timeTake = new Date().toString();
    }

    public String getId() {
        return id;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getMedicationID() {
        return medicationID;
    }

    public String getMedicationName() {
        return medicationName;
    }
}