package fingertiptech.medontime.ui.model;

public class Log {
    private String id, patientID, medicationID, medicationName;

    public Log(String id, String patientID, String medicationID, String medicationName) {
        this.id = id;
        this.patientID = patientID;
        this.medicationID = medicationID;
        this.medicationName = medicationName;
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