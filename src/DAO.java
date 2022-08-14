import java.io.*;
import java.util.Scanner;

public class DAO {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fileFrom;
        String fileTo;
        Key key;
        System.out.println("""
                ��� �� ������ ������� � ������?\s
                1. �����������\s
                2. ������������\s
                3. ������������ �������� �����""");
        int yourChoice = Integer.parseInt(sc.nextLine());
        switch (yourChoice) {
            case 1 -> {
                System.out.println("�������� ����� c �������� �������");
                fileFrom = sc.nextLine();
                System.out.println("� ����� ���� ��������� ������������� �����");
                fileTo = sc.nextLine();
                System.out.println("������ �����");
                key = new Key(Integer.parseInt(sc.nextLine()));
                toEncrypt(fileFrom, fileTo, key);
            }
            case 2 -> {
                System.out.println("�������� ����� � ������������� �������");
                fileFrom = sc.nextLine();
                System.out.println("� ����� ���� ��������� �������������� �����");
                fileTo = sc.nextLine();
                System.out.println("����� ����� �����");
                key = new Key(Integer.parseInt(sc.nextLine()));
                toEncrypt(fileFrom, fileTo, key);
            }
            case 3 -> {
                System.out.println("�������� ����� c �������� �������");
                fileFrom = sc.nextLine();
                System.out.println("� ����� ���� ��������� ������������� �����");
                fileTo = sc.nextLine();
                key = new Key(bruteForce(fileFrom));
                System.out.println("���� ����� � " + key.getShift());
                toEncrypt(fileFrom, fileTo, key);
            }
            default -> System.out.println("����� ������� �� ����������");
        }
        sc.close();
    }
    public static void toEncrypt(String fileFrom, String fileTo, Key key){
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
    public static boolean isCharWhichNeedByCondition(char ch){
        return ch != 32 && ch != 33 && ch != 34 && ch != 44 &&
                ch != 45 && ch != 46 && ch != 58 && ch != 63 && (ch < 1040 || ch > 1103);
    }
    public static int bruteForce(String fileFrom){
        try(FileReader fileReader = new FileReader(fileFrom);
        BufferedReader bufferedReader = new BufferedReader(fileReader)){
            StringBuilder text = new StringBuilder("");
            char symbol;
            for (int key = 1; key < 73; key++) {
                int keyBegin = key;
                int anotherKey;
                bufferedReader.mark(1000);
                text.delete(0, text.length());
                while(bufferedReader.ready()){
                    anotherKey = keyBegin;
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
        return 9999;
    }
}