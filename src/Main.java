import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.Base64;

public class Main {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        boolean repeat = true;
        while(repeat){
            System.out.println("\n-----Main menu-----");
            System.out.println("Please choose an option");
            System.out.println("1.- Basic calculator");
            System.out.println("2.- Encoder");
            System.out.println("3.- Student average calculator");
            System.out.println("4.- Exit");
            System.out.print("Enter your choice: ");
            int option = sc.nextInt();
            sc.nextLine();
            switch (option){
                case 1:
                    calculator();
                    break;
                case 2:
                    encodeDecode();
                    break;
                case 3:
                    studentCalculator();
                    break;
                case 4:
                    System.out.println("Thank you for using the app\nExiting...");
                    repeat = false;
                    break;
                default:
                    System.out.println("Choose a valid option");
                    break;
            }
        }
    }

    static void calculator(){
        double result = 0;
        System.out.println("Please select an option");
        System.out.println("1.- Sum (a + b)");
        System.out.println("2.- Subtract (a - b)");
        System.out.println("3.- Multiply (a * b)");
        System.out.println("4.- Divide (a / b)");
        System.out.print("Enter your choice: ");
        int calcChoice = sc.nextInt();
        System.out.print("Please enter the value for a: ");
        double a = sc.nextDouble();
        System.out.print("Please enter the value for b: ");
        double b = sc.nextDouble();
        if(calcChoice==4){
            while(b == 0){
                System.out.print("Value for b can't be 0, enter another value: ");
                b = sc.nextInt();
            }
        }
        switch (calcChoice){
            case 1:
                BiFunction<Double, Double, Double> add = (x, y) -> x+y;
                result = add.apply(a,b);
                break;
            case 2:
                BiFunction<Double, Double, Double> minus = (x, y) -> x-y;
                result = minus.apply(a,b);
                break;
            case 3:
                BiFunction<Double, Double, Double> multiply = (x, y) -> x*y;
                result = multiply.apply(a,b);
                break;
            case 4:
                BiFunction<Double, Double, Double> divide = (x, y) -> x/y;
                result = divide.apply(a,b);
                break;
        }
        System.out.printf("The result is: %.2f\n",result);
    }

    static void encodeDecode(){
        System.out.println("Please choose an option: ");
        System.out.println("1.- Encode a String to Base64");
        System.out.println("2.- Decode a String to Base64");
        System.out.print("Option: ");
        int option = sc.nextInt();
        sc.nextLine();
        while(option > 2 || option < 1){
            System.out.println("Select a valid option");
            System.out.print("Option: ");
            option = sc.nextInt();
            sc.nextLine();
        }
        System.out.printf("Please enter the string to %s ",option==1?"encode: ":"decode: ");
        String userString = sc.nextLine();
        String processedString;
        if(option==1){
            processedString = Base64.getEncoder().encodeToString(userString.getBytes());
        }else{
            byte [] decoded = Base64.getDecoder().decode(userString);
            processedString = new String(decoded, StandardCharsets.UTF_8);
        }
        System.out.printf("%s string: is: %s\n",option==1?"Encoded":"Decoded",processedString);
    }

    static void studentCalculator() {
        record Signature(String name, double score){};

        System.out.print("Please enter your name: ");
        String studentName = sc.nextLine();
        System.out.print("Please enter your grade: ");
        String studentGrade = sc.nextLine();
        System.out.print("Please enter the number of signatures to enroll: ");
        int numberSignatures = sc.nextInt();
        sc.nextLine();

        Signature[] data = new Signature[numberSignatures];
        for(int i=0;i<numberSignatures;i++){
            System.out.printf("Please enter the name of the signature %s: ",i+1);
            String tempName = sc.nextLine();
            System.out.printf("Please enter the score for %s: ",tempName);
            double tempScore = sc.nextDouble();
            sc.nextLine();
            data[i] = new Signature(tempName,tempScore);
        }

        OptionalDouble average = Arrays.stream(data)
                .mapToDouble(s->s.score)
                .average();

        String divider = "-------------------------------------------------------------";
        System.out.println(divider);
        System.out.format("Student name: %-36sGrade: %s\n",studentName,studentGrade);
        System.out.println(divider);

        Arrays.stream(data)
                .forEach(s-> System.out.format("\t\tSignature: %-31sScore: %.1f\n",s.name,s.score));
        System.out.println(divider);

        String status = "";
        if(average.getAsDouble() >= 8){
            status = "Passed with good level";
        }
        else if(average.getAsDouble() >= 6 ){
            status = "Passed with low level";
        }
        else{
            status = "Failed. Study more";
        }
        System.out.format("Final average: %-16.2fStatus: %s",average.orElse(0),status);
        System.out.println();
    }
}