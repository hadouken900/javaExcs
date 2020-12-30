import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
Обратная польская запись

Необходимо реализовать функцию:

на вход функция получает строку с математическим выражением;

нужно вычислить значение математического выражения;

функция должна вернуть вычисленный результат;

определены следующие бинарные операции: -, +, *, /;

определен унарный минус.
 */
public class ReversePolishNotation {

    //регулярные выражения для поиска определенных знаков в строке
    static Pattern uminus = Pattern.compile("-{2,}\\d+");
    static Pattern braces = Pattern.compile("\\([^\\(\\)]+?\\)");
    static Pattern mult = Pattern.compile("\\d+\\*\\d+");
    static Pattern div = Pattern.compile("\\d+\\/\\d+");
    static Pattern plus = Pattern.compile("\\d+\\+\\d+");
    static Pattern minus = Pattern.compile("\\d+\\-\\d+");


    public static void main(String[] args) {
        String s = "2+2*(3+4)";
        System.out.println(calculate(s));
    }



    static int calculate(String mathString) {

        //ищем унарный минус
        Matcher m = uminus.matcher(mathString);
        while (m.find()) {
            String gr = m.group();
            if (gr.length() % 2 == 0) {
                mathString = mathString.replace(gr,"-"+gr.charAt(gr.length()-1));
            }
            else {
                mathString = mathString.replace(gr,""+gr.charAt(gr.length()-1));
            }
        }

        //ищем скобки
        m = braces.matcher(mathString);
        while (m.find()) {
            String res = m.group();
            //сокращаем скобку до числа
            res = findSigns(res);
            mathString = mathString.replace(m.group(),res);
            m = braces.matcher(mathString);
        }

        //производим все оставшиеся вычисления
        mathString = findSigns(mathString);
        return Integer.parseInt(mathString);
    }


    
    static public String findSigns(String s) {
        Matcher m = mult.matcher(s);
        while (m.find()) {
            int x = Integer.parseInt(m.group(1))*Integer.parseInt(m.group(2));
            s = s.replace(m.group(),""+x);
            m = mult.matcher(s);
        }
        Matcher m1 = div.matcher(s);
        while (m1.find()) {
            int x = Integer.parseInt(m1.group(1))/Integer.parseInt(m1.group(2));
            s = s.replace(m1.group(),""+x);
            m = div.matcher(s);
        }
        Matcher m2 = plus.matcher(s);
        while (m2.find()) {
            int x = Integer.parseInt(m2.group(1))+Integer.parseInt(m2.group(2));
            s = s.replace(m2.group(),""+x);
            m = plus.matcher(s);
        }
        Matcher m3 = minus.matcher(s);
        while (m3.find()) {
            int x = Integer.parseInt(m3.group(1))-Integer.parseInt(m3.group(2));
            s = s.replace(m3.group(),""+x);
            m = minus.matcher(s);
        }
        s = s.replace("(","");
        s = s.replace(")","");
        return s;
    }
}

/*
Sample Input 1:

2+2*(3+4)
Sample Output 1:

16
Sample Input 2:

72/12-8*(1+4)
Sample Output 2:

-34
Sample Input 3:

2*(3+2*(1+2*(1+3)))
Sample Output 3:

42
 */
