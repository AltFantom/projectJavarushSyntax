import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DAO {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String fileFrom;
        String fileTo;
        Key key;
        System.out.println("""
                Что ты хочешь сделать с файлом?\s
                1. Зашифровать\s
                2. Расшифровать""");
        int yourChoice = Integer.parseInt(sc.nextLine());
        switch(yourChoice){
            case 1:
                System.out.println("Название файла c исходным текстом");
                fileFrom = sc.nextLine();
                System.out.println("В какой файл поместить зашифрованный текст");
                fileTo = sc.nextLine();
                System.out.println("Выбери сдвиг");
                key = new Key(Integer.parseInt(sc.nextLine()));
                toEncrypt(fileFrom, fileTo, key);
                break;
            case 2:
                System.out.println("Название файла с зашифрованным текстом");
                fileFrom = sc.nextLine();
                System.out.println("В какой файл поместить расшифрованный текст");
                fileTo = sc.nextLine();
                System.out.println("Задай сдвиг ключа");
                key = new Key(Integer.parseInt(sc.nextLine()));
                toDecode(fileFrom, fileTo, key);
                break;
            default:
                System.out.println("Такой команды не существует");
        }
        sc.close();
    }
    public static void toEncrypt(String fileFrom, String fileTo, Key key){
        try(FileReader fileReader = new FileReader(fileFrom);
            FileWriter fileWriter = new FileWriter(fileTo)){
            while (fileReader.ready()){
                char ch = (char) (fileReader.read() + key.getShift());
                while (!isRussCharOrPunctuationMark(ch)){
                    ch += 1;
                }
                fileWriter.write(ch);
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void toDecode(String fileFrom, String fileTo, Key key){
        try(FileReader fileReader = new FileReader(fileFrom);
            FileWriter fileWriter = new FileWriter(fileTo)){
            while (fileReader.ready()){
                char ch = (char) (fileReader.read() + key.getShift());
                while (!isRussCharOrPunctuationMark(ch)){
                    ch -= 1;
                }
                fileWriter.write(ch);
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static boolean isRussCharOrPunctuationMark(char ch){
        if(ch == 32 || ch == 33 || ch == 34 || ch == 44 ||
                ch == 45 || ch == 46 || ch == 58 || ch == 63 || (ch > 1039 && ch < 1104) ){
            return true;
        }
        return false;
    }
}