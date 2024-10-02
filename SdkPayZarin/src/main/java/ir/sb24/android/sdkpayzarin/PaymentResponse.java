package ir.sb24.android.sdkpayzarin;

import java.util.List;

public class PaymentResponse {
    private Data data;
    private List<String> errors;

    public class Data {
        public String getAuthority() {
            return authority;
        }

        public int getFee() {
            return fee;
        }

        public String getFee_type() {
            return fee_type;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        private String authority;
        private int fee;
        private String fee_type;
        private int code;
        private String message;
        // Constructor, Getters and Setters
    }

    public Data getData() {
        return data;
    }
}
