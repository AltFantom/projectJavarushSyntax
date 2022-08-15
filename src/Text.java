import java.io.*;
import java.util.Scanner;

public class Text {
private String fileFrom;
private String fileTo;
private Key key;
    public static void main(String[] args) {
        Text text = new Text();
        text.workWithConsole();
    }
        public void workWithConsole(){
            Scanner sc = new Scanner(System.in);
            int yourChoice = -1;
            while (yourChoice != 0) {
                System.out.println("""
                        What do you want to do with file?\s
                        1. Encrypt/Decode\s
                        2. Decode with bruteForce
                        3. Exit""");
                try {
                    yourChoice = Integer.parseInt(sc.nextLine());
                }
                catch(NumberFormatException ignored){
                }
                switch (yourChoice) {
                    case 1 -> {
                        System.out.println("Name FileFrom");
                        fileFrom = sc.nextLine();
                        System.out.println("Name FileTo");
                        fileTo = sc.nextLine();
                        System.out.println("ChooseShift");
                        key = new Key(Integer.parseInt(sc.nextLine()));
                        toEncrypt(fileFrom, fileTo, key);
                    }
                    case 2 -> {
                        System.out.println("Name FileFrom");
                        fileFrom = sc.nextLine();
                        System.out.println("Name FileTo");
                        fileTo = sc.nextLine();
                        key = new Key(bruteForce(fileFrom));
                        if (key.getShift() == 404) {
                            System.out.println("Program doesn't work or you do something wrong");
                        }
                        System.out.println("KeyShift — " + key.getShift());
                        toEncrypt(fileFrom, fileTo, key);
                    }
                    case 3 -> yourChoice = 0;
                    default -> System.out.println("command doesn't exist");
                }
            }
            sc.close();
        }
    public void toEncrypt(String fileFrom, String fileTo, Key key){
        try(FileReader fileReader = new FileReader(fileFrom);
            FileWriter fileWriter = new FileWriter(fileTo)){
            int beginKey = key.getShift();
            char symbol;
            while (fileReader.ready()){
                symbol = (char) fileReader.read();
                while (key.getShift() != 0){
                    if(key.getShift() > 0){
                        do{
                            symbol += 1;
                        } while (isCharWhichNeedByCondition(symbol));
                        key.setShift(key.getShift() - 1);
                    }
                    else if(key.getShift() < 0){
                        do{
                            symbol -= 1;
                        } while (isCharWhichNeedByCondition(symbol));
                        key.setShift(key.getShift() + 1);
                    }
                }
                fileWriter.write(symbol);
                key.setShift(beginKey);
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public boolean isCharWhichNeedByCondition(char ch){
        return ch != 32 && ch != 33 && ch != 34 && ch != 44 &&
                ch != 45 && ch != 46 && ch != 58 && ch != 63 && (ch < 1040 || ch > 1103);
    }
    public int bruteForce(String fileFrom){
        try(FileReader fileReader = new FileReader(fileFrom);
        BufferedReader bufferedReader = new BufferedReader(fileReader)){
            StringBuilder text = new StringBuilder("");
            char symbol;
            for (int key = 1; key < 73; key++) {
                int anotherKey;
                bufferedReader.mark(1000);
                text.delete(0, text.length());
                while(bufferedReader.ready()){
                    anotherKey = key;
                    symbol = (char)(bufferedReader.read());
                    while(anotherKey != 0) {;
                        do {
                            symbol += 1;
                        } while (isCharWhichNeedByCondition(symbol));
                        anotherKey -= 1;
                    }
                    text.append(symbol);
                }
                if(text.indexOf(". ") > 0 && text.indexOf(", ") > 0){
                    return key;
                }
                bufferedReader.reset();
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        return 404;
    }
}
