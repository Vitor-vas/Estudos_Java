import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        double[] temps = new double[12], repeated = new double[12];
        String[] months = {"janeiro", "fevereiro", "marco", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"};
        double temp, highest, lowest;
        int countRep = 0;
        String hMonth = "", lmonth = "";
        Scanner input = new Scanner(System.in);
        boolean repeats;

        for (int i = 0; i < temps.length; i++){
            System.out.println("Type in the " + months[i] + " month temperature");
            temps[i] = input.nextDouble();
        }
        for (int i = 0; i < temps.length; i++){
            temp = temps[i];
            for (int j = i + 1; j < temps.length; j++){
                if (temp == temps[j]){
                    repeated[countRep] = temp;
                    countRep += 1;
                    break;
                }
            }
        }

        highest = Double.NEGATIVE_INFINITY;
        lowest = Double.POSITIVE_INFINITY;

        for (int i = 0; i < temps.length; i++){
            repeats = false;
            for (int j = 0; j < countRep; j++){
                if (temps[i] == repeated[j]){
                    repeats = true;
                    break;
                }
            }
            if (!repeats){
                if (highest < temps[i]){
                    highest = temps[i];
                    hMonth =  months[i];
                }
                if (lowest > temps[i]){
                    lowest = temps[i];
                    lmonth = months[i];
                }
            }
        }
        if ((!hMonth.isEmpty()) && (!lmonth.isEmpty())){
            System.out.println(hMonth + " " +  highest +" " + lmonth +" " + lowest);
        }
        else {
            System.out.println("error");
        }

    }
}