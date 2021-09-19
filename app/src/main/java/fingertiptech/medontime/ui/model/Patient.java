package fingertiptech.medontime.ui.model;

public class Patient {
    String _id;
    int patientId;
    int caretakerId;
    String firstname;
    String lastname;
    String email;
    String password;
    String phoneNo;
    int age;
    String medicationIds;
    String perscritpionIds;

    public Patient(int patientId, int caretakerId, String firstname, String lastname, String email, String password, String phoneNo, int age, String medicationIds, String perscritpionIds) {
        this._id = _id;
        this.patientId = patientId;
        this.caretakerId = caretakerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.age = age;
        this.medicationIds = medicationIds;
        this.perscritpionIds = perscritpionIds;
    }
}
