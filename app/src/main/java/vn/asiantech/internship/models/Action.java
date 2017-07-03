package vn.asiantech.internship.models;

/**
 * Created by ducle on 03/07/2017.
 *
 */
enum Action {
    INTENT("INTENT"), START("START"), PAUSE("PAUSE"), RESUME("RESUME"),
    SEEK("SEEK"), SEEK_TO("SEEK_TO"), STOP("STOP");

    public String getmValue() {
        return mValue;
    }

    private String mValue;

    Action(String value) {
        mValue = value;
    }
}
