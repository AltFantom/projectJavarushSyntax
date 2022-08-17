import java.io.*;
import java.util.Scanner;

public class Text {
    private String fileFrom;
    private String fileTo;

    private int shift;

    public void workWithConsole() {
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
            } catch (NumberFormatException ignored) {
            }
            switch (yourChoice) {
                case 1 -> {
                    System.out.println("Name FileFrom");
                    setFileFrom(sc.nextLine());
                    System.out.println("Name FileTo");
                    setFileTo(sc.nextLine());
                    System.out.println("ChooseShift");
                    setShift(Integer.parseInt(sc.nextLine()));
                    toEncrypt(fileFrom, fileTo, getShift());
                }
                case 2 -> {
                    System.out.println("Name FileFrom");
                    setFileFrom(sc.nextLine());
                    System.out.println("Name FileTo");
                    setFileTo(sc.nextLine());
                    setShift(bruteForce(fileFrom));
                    if (shift == 404) {
                        System.out.println("Program doesn't work or you do something wrong");
                    }
                    System.out.println("KeyShift — " + shift);
                    toEncrypt(fileFrom, fileTo, getShift());
                }
                case 3 -> yourChoice = 0;
                default -> System.out.println("command doesn't exist");
            }
        }
        sc.close();
    }

    private void toEncrypt(String fileFrom, String fileTo, int shift) {
        try (FileReader fileReader = new FileReader(fileFrom);
             FileWriter fileWriter = new FileWriter(fileTo)) {
            int beginKey = shift;
            char symbol;
            while (fileReader.ready()) {
                symbol = (char) fileReader.read();
                while (shift != 0) {
                    if (shift > 0) {
                        do {
                            symbol += 1;
                        } while (isCharWhichNeedByCondition(symbol));
                        shift -= 1;
                    } else {
                        do {
                            symbol -= 1;
                        } while (isCharWhichNeedByCondition(symbol));
                        shift += 1;
                    }
                }
                fileWriter.write(symbol);
                shift = beginKey;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isCharWhichNeedByCondition(char ch) {
        return ch != 32 && ch != 33 && ch != 34 && ch != 44 &&
                ch != 45 && ch != 46 && ch != 58 && ch != 63 && (ch < 1040 || ch > 1103);
    }

    private int bruteForce(String fileFrom) {
        try (FileReader fileReader = new FileReader(fileFrom);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String[] countSpaces;
            StringBuilder text = new StringBuilder();
            char symbol;
            for (int key = 1; key < 73; key++) {
                int anotherKey;
                bufferedReader.mark(1000);
                text.delete(0, text.length());
                while (bufferedReader.ready()) {
                    anotherKey = key;
                    symbol = (char) (bufferedReader.read());
                    while (anotherKey != 0) {
                        do {
                            symbol += 1;
                        } while (isCharWhichNeedByCondition(symbol));
                        anotherKey -= 1;
                    }
                    text.append(symbol);
                }
                if (text.indexOf(". ") > 0 && text.indexOf(", ") > 0) {
                    countSpaces = text.toString().split(" ");
                    int averageWordLength = 9;
                    if (text.length() / countSpaces.length < averageWordLength) {
                        return key;
                    }
                }
                bufferedReader.reset();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return 404;
    }

    private void setFileFrom(String fileFrom) {
        this.fileFrom = fileFrom;
    }

    private void setFileTo(String fileTo) {
        this.fileTo = fileTo;
    }

    private int getShift() {
        return shift;
    }

    private void setShift(int shift) {
        this.shift = shift;
    }
}
