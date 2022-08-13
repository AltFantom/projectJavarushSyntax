import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Key
{
    private int shift;

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public Key(int shift){
        this.shift = shift;
    }

    public static void main(String[] args) {
        System.out.println((int)'"');
    }

    /*если запятая и пробел или точка и пробел встречаются на текст его символов опред кол во раз,
     * то мы правильно подобрали ключ к тексту
     * 1. сделать сплит по тексту разделающий значения его по точкепробелу и любой символ из
     * русского алфавита с большой буквы
     * 2. также проверить встр ли в тексте запятая и пробел на
     * определенное количество символов в тексте
     * 3. */
}