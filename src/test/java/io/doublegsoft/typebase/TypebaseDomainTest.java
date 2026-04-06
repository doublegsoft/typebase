/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.typebase;

import io.doublegsoft.typebase.TypebaseParser.Typebase_anytypeContext;
import io.doublegsoft.typebase.TypebaseParser.Typebase_nativeContext;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author gg
 */
public class TypebaseDomainTest {

  @Test
  public void test_string() throws Exception {
    String expr = "string(20)";

    TypebaseLexer lexer = new TypebaseLexer(CharStreams.fromString(expr));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    TypebaseParser parser = new TypebaseParser(tokens);
    parser.setErrorHandler(new BailErrorStrategy());
    TypebaseParser.Typebase_anytypeContext anytypeCtxt = parser.typebase_anytype();
    TypebaseParser.Typebase_stringContext ctxt = anytypeCtxt.typebase_string();
    Assert.assertEquals("20", ctxt.anybase_int().getText());
  }

  @Test
  public void test_enum() throws Exception {
    String expr = "enum[RSR: 水库, LEV: 堤防]";

    TypebaseLexer lexer = new TypebaseLexer(new ANTLRInputStream(expr));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    TypebaseParser parser = new TypebaseParser(tokens);
    parser.setErrorHandler(new BailErrorStrategy());
    TypebaseParser.Typebase_anytypeContext anytypeCtxt = parser.typebase_anytype();
    TypebaseParser.Typebase_enumContext ctxt = anytypeCtxt.typebase_enum();
    Assert.assertEquals(2, ctxt.typebase_keytext().size());
//    Assert.assertEquals("RSR", ctxt.typebase_keytext().get(0).ANYBASE_ID(0).getText());
//    Assert.assertEquals("水库", ctxt.typebase_keytext().get(0).ANYBASE_ID(1).getText());
//    Assert.assertEquals("LEV", ctxt.typebase_keytext().get(1).ANYBASE_ID(0).getText());
//    Assert.assertEquals("堤防", ctxt.typebase_keytext().get(1).ANYBASE_ID(1).getText());
  }

  @Test
  public void test_range() throws Exception {
    String expr = "[5000, 9999]";
    TypebaseParser parser = parser(expr);

    TypebaseParser.Typebase_anytypeContext anytypeCtxt = parser.typebase_anytype();
    TypebaseParser.Typebase_rangeContext ctxt = anytypeCtxt.typebase_range();
    Assert.assertEquals("[", ctxt.lower_nlt.getText());
    Assert.assertEquals("5000", ctxt.lower.number.getText());
    Assert.assertEquals("]", ctxt.upper_ngt.getText());
    Assert.assertEquals("9999", ctxt.upper.number.getText());

    expr = "(2.56, 4.998]";
    parser = parser(expr);

    anytypeCtxt = parser.typebase_anytype();
    ctxt = anytypeCtxt.typebase_range();
    Assert.assertEquals("(", ctxt.lower_gt.getText());
    Assert.assertEquals("2.56", ctxt.lower.number.getText());
    Assert.assertEquals("]", ctxt.upper_ngt.getText());
    Assert.assertEquals("4.998", ctxt.upper.number.getText());
    
    expr = "(1970-01-01, 2018-01-01]";
    parser = parser(expr);

    anytypeCtxt = parser.typebase_anytype();
    ctxt = anytypeCtxt.typebase_range();
    Assert.assertEquals("(", ctxt.lower_gt.getText());
    Assert.assertEquals("1970-01-01", ctxt.lower.date.getText());
    Assert.assertEquals("]", ctxt.upper_ngt.getText());
    Assert.assertEquals("2018-01-01", ctxt.upper.date.getText());
    
    expr = "(1970-01-01,]";
    parser = parser(expr);

    anytypeCtxt = parser.typebase_anytype();
    ctxt = anytypeCtxt.typebase_range();
    Assert.assertEquals("(", ctxt.lower_gt.getText());
    Assert.assertEquals("1970-01-01", ctxt.lower.date.getText());
    Assert.assertEquals("]", ctxt.upper_ngt.getText());
  }

  @Test
  public void test_list_and_collection() throws Exception {
//        String expr = "[5]";
//        TypebaseParser parser = parser(expr);
//        TypebaseParser.Typebase_anytypeContext anytypeCtxt = parser.typebase_anytype();
//        TypebaseParser.Typebase_listContext ctxt = anytypeCtxt.typebase_list();
//        Assert.assertEquals("5", ctxt.ANYBASE_INT().getText());
//        
//        expr = "[]";
//        parser = parser(expr);
//        
//        anytypeCtxt = parser.typebase_anytype();
//        TypebaseParser.Typebase_collectionContext ctxCollection = anytypeCtxt.typebase_collection();
//        Assert.assertNotNull(ctxCollection);
  }

  @Test
  public void test_matrix() throws Exception {
//        String expr = "[][]";
//        TypebaseParser parser = parser(expr);
//        
//        TypebaseParser.Typebase_anytypeContext anytypeCtxt = parser.typebase_anytype();
//        TypebaseParser.Typebase_listContext listCtxt = anytypeCtxt.typebase_list();
//        Assert.assertNull(listCtxt);
//        
//        TypebaseParser.Typebase_matrixContext ctxt = anytypeCtxt.typebase_matrix();
//        Assert.assertNotNull(ctxt);
  }

  @Test
  public void test_pair() throws Exception {
    String expr = "<obj.objid, obj>";
    TypebaseParser parser = parser(expr);

    TypebaseParser.Typebase_anytypeContext anytypeCtxt = parser.typebase_anytype();
    TypebaseParser.Typebase_pairContext ctxt = anytypeCtxt.typebase_pair();

    Assert.assertEquals("obj.objid", ctxt.typebase_value(0).getText());
    Assert.assertEquals("obj", ctxt.typebase_value(1).getText());
  }

  @Test
  public void test_tuple() throws Exception {
    String expr = "(abc, bcd)";
    TypebaseParser parser = parser(expr);

    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Assert.assertNotNull(ctx.typebase_tuple() != null);
  }

  @Test
  public void test_protocol() throws Exception {
    String expr = "<abc: 8, bcd: 16, def: 16, fgi: 32>";
    TypebaseParser parser = parser(expr);

    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Assert.assertNotNull(ctx.typebase_protocol() != null);
  }

  @Test
  public void test_hash_0() throws Exception {
    String expr = "#<obj.objid, obj>";
    TypebaseParser parser = parser(expr);

    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Assert.assertNotNull(ctx.typebase_hash() != null);
  }

  @Test
  public void test_hash_1() throws Exception {
    String expr = "#<obj.objid>";
    TypebaseParser parser = parser(expr);

    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Assert.assertNotNull(ctx.typebase_hash() != null);
  }

  @Test
  public void test_native_1() throws Exception {
    String expr = "org.apache.commons.math3.filter.KalmanFilter@org.apache.commons:commons-math3:3.3";
    TypebaseParser parser = parser(expr);

    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Typebase_nativeContext ctxNative = ctx.typebase_native();
    Assert.assertNotNull(ctxNative != null);

    Assert.assertEquals("org.apache.commons.math3.filter.KalmanFilter", ctxNative.name.getText());
    Assert.assertEquals("org.apache.commons", ctxNative.group.getText());
    Assert.assertEquals("commons-math3", ctxNative.artifact.getText());
    Assert.assertEquals("3.3", ctxNative.version.getText());
  }

  @Test
  public void test_native_2() throws Exception {
    String expr = "parser@github.com/antlr/antlr4/go/parser";
    TypebaseParser parser = parser(expr);

    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Typebase_nativeContext ctxNative = ctx.typebase_native();
    Assert.assertNotNull(ctxNative != null);

    Assert.assertEquals("parser", ctxNative.name.getText());
    Assert.assertEquals("github.com/antlr/antlr4/go/parser", ctxNative.group.getText());
  }

  @Test
  public void test_native_3() throws Exception {
    String expr = "smtplib@smtplib:1.2.0";
    TypebaseParser parser = parser(expr);

    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Typebase_nativeContext ctxNative = ctx.typebase_native();
    Assert.assertNotNull(ctxNative != null);

    Assert.assertEquals("smtplib", ctxNative.name.getText());
    Assert.assertEquals("smtplib", ctxNative.group.getText());
    Assert.assertEquals("1.2.0", ctxNative.version.getText());
  }

  @Test
  public void test_native_4() throws Exception {
    String expr = "java.util.Map<String, org.apache.commons.math3.optimization.fitting.WeightedObservedPoint>";
    TypebaseParser parser = parser(expr);

    Typebase_anytypeContext ctx = parser.typebase_anytype();
    Typebase_nativeContext ctxNative = ctx.typebase_native();
    Assert.assertNotNull(ctxNative != null);

    Assert.assertEquals("java.util.Map", ctxNative.name.getText());
    Assert.assertEquals("String", ctxNative.typebase_native(0).name.getText());
    Assert.assertEquals("org.apache.commons.math3.optimization.fitting.WeightedObservedPoint", ctxNative.typebase_native(1).name.getText());
  }
  
  @Test
  public void test_code_1() throws Exception {
    String expr = "code(YYYY|MM|DD|NNNNNN)";
    TypebaseParser parser = parser(expr);
    
    expr = "code(AAAAAA|YYYY|MM|DD|NNNNNN)";
    parser = parser(expr);
  }
  
  /**
   * @since 4.0
   */
  @Test
  public void test_custom_object() throws Exception {
    String expr = ""
        + "page(title: 'YES')<"
        + "  category: hscroll(hello: 'world'),"
        + "  list: listview,"
        + "  twins: <"
        + "    hello: world(hello: 'world'),"
        + "    world: hello(hello: 'world')"
        + "  >"
        + ">";
    CustomObject co = new Typebase().customObjectType(expr);
    
    Assert.assertEquals("page", co.getName());
    Assert.assertEquals("YES", co.getAttributeValue("title"));
    
    CustomObject first = co.getChildren().get(0);
    CustomObject second = co.getChildren().get(1);
    CustomObject third = co.getChildren().get(2);
    Assert.assertEquals("category", first.getName());
    Assert.assertEquals("hscroll", first.getType().getName());
    Assert.assertEquals("list", second.getName());
    Assert.assertEquals("listview", second.getType().getName());
    Assert.assertEquals("twins", third.getName());
  }
  
  /**
   * @since 4.0
   */
  @Test
  public void test_anonymous_object() throws Exception {
    String expr = ""
        + "<"
        + "  category: hscroll(hello: 'world', world: 'hello'),"
        + "  list: listview,"
        + "  twins: <"
        + "    hello: world(hello: 'world'),"
        + "    world: hello(hello: 'world')"
        + "  >"
        + ">";
    CustomObject co = new Typebase().anonymousObjectType(expr);
    CustomObject first = co.getChildren().get(0);
    CustomObject second = co.getChildren().get(1);
    CustomObject third = co.getChildren().get(2);
    Assert.assertEquals("category", first.getName());
    Assert.assertEquals("hscroll", first.getType().getName());
    Assert.assertEquals("list", second.getName());
    Assert.assertEquals("listview", second.getType().getName());
    Assert.assertEquals("twins", third.getName());
  }

  private TypebaseParser parser(String expr) {
    TypebaseLexer lexer = new TypebaseLexer(CharStreams.fromString(expr));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    TypebaseParser parser = new TypebaseParser(tokens);
    parser.setErrorHandler(new BailErrorStrategy());
    return parser;
  }
}
