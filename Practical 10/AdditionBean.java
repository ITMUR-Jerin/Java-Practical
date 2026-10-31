
import java.io.Serializable;

public class AdditionBean implements Serializable {

    private double number1;
    private double number2;
    private double result;

    // Public no-argument constructor
    public AdditionBean() {
    }

    // Getter and setter for number1
    public double getNumber1() {
        return number1;
    }

    public void setNumber1(double number1) {
        this.number1 = number1;
        calculateSum();
    }

    // Getter and setter for number2
    public double getNumber2() {
        return number2;
    }

    public void setNumber2(double number2) {
        this.number2 = number2;
        calculateSum();
    }

    // Getter for result
    public double getResult() {
        return result;
    }

    // Method to calculate addition
    private void calculateSum() {
        result = number1 + number2;
    }
}

