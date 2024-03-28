import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expr = scanner.nextLine();
        String id = "[a-zA-Z][a-zA-Z0-9]*";
        String num = "[0-9]+";
        String op = "[\\+\\-]";
        String pattern = id + "|" + num + "|" + op;
        Pattern tId = Pattern.compile(id);
        Pattern tNum = Pattern.compile(num);
        Pattern tOp = Pattern.compile(op);
        String nt = "";
        String nv = "";

        List<String> tokenize(String e) {
            List<String> tokens = new ArrayList<>();
            Matcher matcher = Pattern.compile(pattern).matcher(e);
            while (matcher.find()) {
                tokens.add(matcher.group());
            }
            return tokens;
        }

        List<String> tokens = tokenize(expr);
        System.out.println(tokens);

        String[] nextToken(List<String> t) {
            if (t.isEmpty()) {
                return new String[]{"EOF", "-1"};
            }
            String i = t.get(0);
            t.remove(0);
            Matcher tIdMatcher = tId.matcher(i);
            Matcher tNumMatcher = tNum.matcher(i);
            Matcher tOpMatcher = tOp.matcher(i);
            if (tIdMatcher.matches()) {
                return new String[]{"ID", i};
            } else if (tNumMatcher.matches()) {
                return new String[]{"NUM", i};
            } else if (tOpMatcher.matches()) {
                return new String[]{"OP", i};
            }
            return null;
        }

        String[] nextTokenResult = nextToken(tokens);
        nt = nextTokenResult[0];
        nv = nextTokenResult[1];
        if (nt.equals("NUM") || nt.equals("ID")) {
            System.out.print(nv + " ");
            nextTokenResult = nextToken(tokens);
            nt = nextTokenResult[0];
            nv = nextTokenResult[1];
        }
        while (!nt.equals("EOF")) {
            if (nt.equals("OP")) {
                String p = nv;
                nextTokenResult = nextToken(tokens);
                nt = nextTokenResult[0];
                nv = nextTokenResult[1];
                if (nt.equals("NUM") || nt.equals("ID")) {
                    System.out.print(nv + " " + p + " ");
                    nextTokenResult = nextToken(tokens);
                    nt = nextTokenResult[0];
                    nv = nextTokenResult[1];
                } else {
                    System.out.println("Error: Unexpected " + nv);
                    nt = "EOF";
                }
            } else {
                System.out.println("Error: Unexpected " + nv);
                nt = "EOF";
            }
        }
        System.out.println();

        while (!expr.equals("end")) {
            expr = scanner.nextLine();
            tokens = tokenize(expr);
            nextTokenResult = nextToken(tokens);
            nt = nextTokenResult[0];
            nv = nextTokenResult[1];
            if (nt.equals("NUM") || nt.equals("ID")) {
                System.out.print(nv + " ");
                nextTokenResult = nextToken(tokens);
                nt = nextTokenResult[0];
                nv = nextTokenResult[1];
            }
            while (!nt.equals("EOF")) {
                if (nt.equals("OP")) {
                    String p = nv;
                    nextTokenResult = nextToken(tokens);
                    nt = nextTokenResult[0];
                    nv = nextTokenResult[1];
                    if (nt.equals("NUM") || nt.equals("ID")) {
                        System.out.print(nv + " " + p + " ");
                        nextTokenResult = nextToken(tokens);
                        nt = nextTokenResult[0];
                        nv = nextTokenResult[1];
                    } else {
                        System.out.println("Error: Unexpected " + nv);
                        nt = "EOF";
                    }
                } else {
                    System.out.println("Error: Unexpected " + nv);
                    nt = "EOF";
                }
            }
            System.out.println();
        }
    }
}

