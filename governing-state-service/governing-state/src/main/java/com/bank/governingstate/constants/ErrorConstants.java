package com.bank.governingstate.constants;

public class ErrorConstants {

    private ErrorConstants() {
        //Default Private Constructor
    }
    public static final String GS_001 = "GS_001";

    public static final String GS_001_DESCRIPTION = "Governing State Not Found for provided ZipCode in the database";

    public static final String GS_002 = "GS_002";

    public static final String GS_002_DESCRIPTION = "Governing State Runtime Exception. Please contact GS Service Technical Support";

    public static final String GS_003 = "GS_003";

    public static final String GS_003_DESCRIPTION = "Input Zipcode is Required";
}
