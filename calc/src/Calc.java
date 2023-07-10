import java.util.Scanner;

public class Calc {
    public static void main(String[] args) throws ScanerException {
        Scanner inputData = new Scanner(System.in);
        CheckData inData = new CheckData();
        //CheckValue sign = new CheckValue();
        System.out.println("Введите данные: ");
        inData.data= inputData.nextLine();      // ввод данных
        inData.checkData();

        //распознание чисел на арабсие и римские
        String value1, value2;
        value1 = checkRimOrNumber(inData.checkData()[0]);
        value2 = checkRimOrNumber(inData.checkData()[1]);
        String sign = inData.choiseOperation;

        // проверка
        String a, b, rezRim;
        int rezNum;

        // проверка от 0 до 10
        if (value1 == "rim" && value2 == "rim"){
            a = String.valueOf(rimToNumConvert(inData.checkData()[0]));
            b = String.valueOf(rimToNumConvert(inData.checkData()[1]));
            if (Integer.parseInt(a) > 10 || Integer.parseInt(a) < 1 || Integer.parseInt(b) >10 || Integer.parseInt(b) < 1){
//                System.out.println("Введены не корректные данные");
//                System.exit(1);
                throw new ScanerException("Введены значения меньше 1 или больше 10");
            }
            //проверка на отрицательный результат
            rezNum = rezultOperation(a, b, sign);
            if (rezNum > 0 ){
                rezRim = numToRimConvert(rezNum);
                System.out.println("Ответ: " + rezRim);
            }else {
//                System.out.println("Введены не корректные данные");
//                System.exit(1);
                throw new ScanerException("в римской системе нет отрицательных чисел");
            }
        } else if (value1 == "num" && value2 == "num") {
            a = inData.checkData()[0];
            b = inData.checkData()[1];
            if (Integer.parseInt(a) > 10 || Integer.parseInt(a) < 1 || Integer.parseInt(b) >10 || Integer.parseInt(b) < 1){
//                System.out.println("Введены не корректные данные");
//                System.exit(1);
                throw new ScanerException("Введены значения меньше 1 или больше 10");
            }
            rezNum = rezultOperation(a, b, sign);
            System.out.println("Ответ: " + rezNum);
        }else {
//            System.out.println("Введены не корректные данные");
//            System.exit(1);
            throw new ScanerException("используются одновременно разные системы счисления");
        }


        inputData.close();
    }

    static int rezultOperation(String value1, String value2, String sign){          //математические действия
        int rezult=0;
        int val1 = Integer.parseInt(value1);
        int val2 = Integer.parseInt(value2);
        switch (sign) {
            case "+":
                rezult = val1 + val2;
                break;
            case "-":
                rezult = val1 - val2;
                break;
            case "*":
                rezult = val1 * val2;
                break;
            case "/":
                rezult = val1 / val2;
                break;
        }
        return rezult;
    }

    static String checkRimOrNumber(String value){  //определение чисел римских или арабских
        String rezult = "";

        try {
            value = String.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException e){
            return rezult = "rim";
        }
        return rezult = "num";
    }

    static String numToRimConvert(int vol){         //конвертация из арабских в римские
        int number = vol;
        String rezult = "";
        int i = 0;
        int prom = 0;

        try {
            rezult = NumberRim.valueOf("n"+Integer.toString(vol)).getValue();
            return rezult;
        } catch (IllegalArgumentException e){
        }

        while (number > 0){
            if (number/1000 >= 1&& number != 1000){
                i = 1000;
                prom = number/1000;
                if (prom > 3){
                    rezult = "vinculum";
                    return rezult;
                }
            } else if ( number/100 >= 1&& number != 100){
                i = 100;
                prom = number/100;
            } else if (number/10 >= 1 && number != 10){
                i =10;
                prom = number/10;
            } else {
                i=1;
                prom = number;
            }

            if (prom <= 3){
                for(int j = 1; j <= prom; j++){
                    rezult = rezult + NumberRim.valueOf("n"+Integer.toString(1*i)).getValue();
                }
            } else if (prom == 4){
                rezult = rezult + NumberRim.valueOf("n"+Integer.toString(4*i)).getValue();
            } else if (prom == 5) {
                rezult = rezult + NumberRim.valueOf("n"+Integer.toString(5*i)).getValue();
            } else if (prom > 5 && prom < 9) {
                rezult = rezult + NumberRim.valueOf("n"+Integer.toString(5*i)).getValue();
                for (int j = 1; j <= prom - 5; j++) {
                    rezult = rezult + NumberRim.valueOf("n"+Integer.toString(1*i)).getValue();
                }
            } else if (prom == 9){
                rezult = rezult + NumberRim.valueOf("n"+Integer.toString(9*i)).getValue();
            } else if (prom == 10) {
                rezult = rezult + NumberRim.valueOf("n"+Integer.toString(10*i)).getValue();
            }
            number = number - prom*i;

        }
        return rezult;
    }

    static int rimToNumConvert(String vol){  //перевод из римских в арабские
        String rimVolue = vol.toUpperCase();
        char[] rimNumbers = rimVolue.toCharArray();
        int i=0;
        int rezult = 0;
        if (rimNumbers.length == 1){
            rezult = RimNumber.valueOf(Character.toString(rimNumbers[0])).getValue();
        }
        else {
            while ( i<=rimNumbers.length -1){
                if (i == rimNumbers.length - 1) {
                    rezult = rezult + RimNumber.valueOf(Character.toString(rimNumbers[i])).getValue();
                    i++;
                } else {
                    if (RimNumber.valueOf(Character.toString(rimNumbers[i])).getValue() < RimNumber.valueOf(Character.toString(rimNumbers[i+1])).getValue()){
                        rezult = rezult + RimNumber.valueOf(Character.toString(rimNumbers[i+1])).getValue() - RimNumber.valueOf(Character.toString(rimNumbers[i])).getValue();
                        i = i+2;
                    } else {
                        rezult = rezult + RimNumber.valueOf(Character.toString(rimNumbers[i])).getValue();
                        i++;
                    }
                }
            }
        }
        return rezult;
    }
}
//обработка исключений
class ScanerException extends Exception{
    public ScanerException(String message){
        super(message);
    }
}

class CheckData{   //класс для разделения на данные
    String data;    //вводимые данне
    String[] operation = new String[]{"+", "-", "*", "/"};
    String choiseOperation;
    String[] value; // результат 2 отдельных числа и знак математического действия пример [1,2,+]
    int indexOperation = -1, firstIndexOperation = -1, lastIndexOperation = -1;
    String[] checkData() throws ScanerException {           //проверка на лишнее математическое действие, разделение на значения, нахождение математического действия
        for (int i=0; i<4; i++){                        //нахождение нужного математического оператора и его индекс
            if (data.indexOf(operation[i]) != -1){
                firstIndexOperation = data.indexOf(operation[i]);
                indexOperation = i;
                choiseOperation = operation[i];
                break;
            }
        }

        for (int i=0; i<4; i++){                        // нахождение последнего математического оператора для нахождения второго математического действия
            if (data.lastIndexOf(operation[i]) != -1){
                lastIndexOperation = data.lastIndexOf(operation[i]);
            }
        }

        if (indexOperation == -1){   //проверка коректности ввода нанных
//            System.out.println("Введены не корректные данные");
//            System.exit(1);
            throw new ScanerException("строка не является математической операцией");
        } else if(lastIndexOperation != firstIndexOperation){
            throw new ScanerException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        } else {
            value = data.split("\\" + operation[indexOperation]);   //разделение на 2 значения  operation[indexOperation]
            for(int i=0; i<value.length; i++){
                value[i] = value[i].trim();
            }
        }
        return value;
    }
}


