package utilities;

import java.util.Random;

public class PersonDetails {

    String[] city;
    String[] phoneNumber;

    public PersonDetails() {
        city = new String[]{"Somerset", "Northamptonshire",
                "Antrim",
                "Durham",
                "Dorset",
                "Hertfordshire",
                "Derbyshire",
        };
        phoneNumber = new String[]{
                "01455 841153", "01455 941153", "03455 841153",
                "01455 845153", "01455 946653", "03455 541153",
        };

    }

    public String getCity() {
        Random generator = new Random();
        int randomNumber = generator.nextInt(city.length);
        return city[randomNumber];
    }

    public String getPhoneNumber() {
        Random generator = new Random();
        int randomNumber = generator.nextInt(phoneNumber.length);
        return phoneNumber[randomNumber];
    }

}
