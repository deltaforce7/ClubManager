package delta7.clubmanager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {



    @Test
    public void main() {
        int sum = countNumbers(10, 5);
        System.out.println(sum);
    }

    public int countNumbers(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }

    public void prtinName() {
        String firstName = "Brian";
        String lastName = "kim";

        String fullName = firstName + ", " + lastName; // "Brian, Kim"
        System.out.println(fullName);
    }

    public String  fullName(String firstName, String lastName) {
        return firstName + lastName;
    }
}