import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.*;

class PostfixExprVisitor extends ExprBaseVisitor<String> {
    @Override
    public String visitProg(ExprParser.ProgContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public String visitExpr(ExprParser.ExprContext ctx) {
        if (ctx.INT() != null && ctx.getChildCount() == 1) {
            return ctx.INT().getText();
        } else if (ctx.ID() != null && ctx.getChildCount() == 1) {
            return ctx.ID().getText();
        } else if (ctx.getChildCount() == 2) {
            if (ctx.getChild(0).getText().matches("\\d+") && Character.isAlphabetic(ctx.getChild(1).getText().charAt(0))) {
                return ctx.getChild(0).getText() + " " + ctx.getChild(1).getText() + " *";
            } else {
                return ctx.getChild(1).getText() + " " + ctx.getChild(0).getText();
            }
        } else if (ctx.getChildCount() == 3) {
            if (ctx.getChild(0).getText().equals("(") && ctx.getChild(2).getText().equals(")")) {
                String center = visit(ctx.expr(0));
                return ctx.getChild(0).getText() + " " + center + " " + ctx.getChild(2).getText();
            } else {
                String left = visit(ctx.expr(0));
                String right = visit(ctx.expr(1));
                return left + " " + right + " " + ctx.getChild(1).getText();
            }
        } else {
            throw new RuntimeException("Unexpected expression context");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main \"<expression>\" | e.g. java Main \"50 + (20 - 5) + 5 * (1)\"");
            System.exit(1);
        }
        String data = String.join(" ", args);
        ExprLexer lexer = new ExprLexer(CharStreams.fromString(data));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokens);
        ParseTree tree = parser.prog();
        PostfixExprVisitor visitor = new PostfixExprVisitor();
        String postfix = visitor.visit(tree);
        System.out.println("Postfix notation: " + postfix);
    }
}

