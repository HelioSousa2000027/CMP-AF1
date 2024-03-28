import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static String ID = "[a-zA-Z][a-zA-Z0-9]*";
    static String NUM = "[0-9]+";
    static String OP = "[\\+\\-\\/\\*]";
    static String LP = "\\(";
    static String RP = "\\)";
    static String pattern = ID + "|" + NUM + "|" + OP + "|" + LP + "|" + RP;
    static Pattern tID = Pattern.compile(ID);
    static Pattern tNUM = Pattern.compile(NUM);
    static Pattern tOP = Pattern.compile(OP);
    static Pattern tLP = Pattern.compile(LP);
    static Pattern tRP = Pattern.compile(RP);
    static String nt = "";
    static String nv = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expr = scanner.nextLine();
        while (!expr.equals("end")) {
            List<String> tokens = tokenize(expr);
            parse(tokens);
            expr = scanner.nextLine();
        }
    }

    public static List<String> tokenize(String e) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile(pattern).matcher(e);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    public static void parse(List<String> tokens) {
        nt = "";
        nv = "";
        nt = nextToken(tokens);
        expr(tokens);
        if (nt.equals("EOF")) {
            System.out.println("End of expression");
        } else {
            System.out.println("Error: Unexpected " + nv);
        }
    }

    public static String nextToken(List<String> t) {
        if (t.isEmpty()) {
            return "EOF";
        }
        String i = t.get(0);
        t.remove(0);
        Matcher tIDMatcher = tID.matcher(i);
        Matcher tNUMMatcher = tNUM.matcher(i);
        Matcher tOPMatcher = tOP.matcher(i);
        Matcher tLPMatcher = tLP.matcher(i);
        Matcher tRPMatcher = tRP.matcher(i);
        if (tIDMatcher.matches()) {
            return "ID";
        } else if (tNUMMatcher.matches()) {
            return "NUM";
        } else if (tOPMatcher.matches()) {
            return "OP";
        } else if (tLPMatcher.matches()) {
            return "LP";
        } else if (tRPMatcher.matches()) {
            return "RP";
        }
        return "";
    }

    public static void factor(List<String> tokens) {
        if (nt.equals("ID") || nt.equals("NUM")) {
            System.out.print(nv + " ");
            nt = nextToken(tokens);
        } else if (nv.equals("-") || nv.equals("+")) {
            String p = nv + "u";
            nt = nextToken(tokens);
            factor(tokens);
            System.out.print(p + " ");
        } else if (nv.equals("(")) {
            nt = nextToken(tokens);
            expr(tokens);
            if (!nv.equals(")")) {
                System.out.println("ERROR: ')' missing");
            } else {
                nt = nextToken(tokens);
            }
        } else {
            System.out.println("ERROR: Unexpected token: " + nv);
        }
    }

    public static void term2(List<String> tokens) {
        if (nv.equals("*") || nv.equals("/")) {
            String p = nv;
            nt = nextToken(tokens);
            factor(tokens);
            System.out.print(p + " ");
            term2(tokens);
        }
    }

    public static void term(List<String> tokens) {
        factor(tokens);
        term2(tokens);
    }

    public static void expr2(List<String> tokens) {
        if (nv.equals("+") || nv.equals("-")) {
            String p = nv;
            nt = nextToken(tokens);
            term(tokens);
            System.out.print(p + " ");
            expr2(tokens);
        }
    }

    public static void expr(List<String> tokens) {
        term(tokens);
        expr2(tokens);
    }
}

