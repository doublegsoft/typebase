/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.typebase;

import io.doublegsoft.typebase.TypebaseParser.Typebase_anytypeContext;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 */
public class TypebaseSyntaxTest {
  
  @Test
  public void test() {
    String expr = "enum[0: Hello, 99: 中文]";
    TypebaseParser parser = parser(expr);
  }
  
  @Test
  public void test_code_composite() {
    String expr = "code(AA|NNNN)";
    TypebaseParser parser = parser(expr);
    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Assert.assertEquals("AA", ctx.typebase_code().code.valuebase_code_part(0).ansi.getText());
  }
  
  @Test
  public void test_code_treelike() {
    String expr = "code(NNN*)";
    TypebaseParser parser = parser(expr);
    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Assert.assertNotNull(ctx.typebase_code().code.treelike);
    Assert.assertEquals("NNN", ctx.typebase_code().code.number.getText());
    
    expr = "code(AAA|NNN*)";
    parser = parser(expr);
    ctx = parser.typebase_anytype();
    Assert.assertNotNull(ctx.typebase_code().code.treelike);
    Assert.assertEquals("AAA", ctx.typebase_code().code.valuebase_code_part(0).ansi.getText());
    Assert.assertEquals("NNN", ctx.typebase_code().code.number.getText());
  }
  
  private TypebaseParser parser(String expr) {
    TypebaseLexer lexer = new TypebaseLexer(CharStreams.fromString(expr));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    TypebaseParser parser = new TypebaseParser(tokens);
    // parser.setErrorHandler(new BailErrorStrategy());
    return parser;
  }
}
