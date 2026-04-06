/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.doublegsoft.typebase;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author gg
 */
public class TypebaseTest {

  @Test
  public void test_number() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("number(10, 2)", "sqlserver");
    Assert.assertEquals("decimal(10,2)", langtype);

    langtype = ctx.typename("number(12)", "sqlserver");
    Assert.assertEquals("decimal(12,0)", langtype);

    langtype = ctx.typename("number", "sqlserver");
    Assert.assertEquals("decimal(12,0)", langtype);

    langtype = ctx.typename("number(12,2)", "sqlite3");
    Assert.assertEquals("numeric", langtype);

//        langtype = ctx.typename("text", "java", "Object");
//        Assert.assertEquals("String", langtype);
  }

  @Test
  public void test_string() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("string(10)", "sqlserver");
    Assert.assertEquals("varchar(10)", langtype);

    langtype = ctx.typename("string(100)", "sqlserver");
    Assert.assertEquals("varchar(100)", langtype);

    langtype = ctx.typename("string", "sqlserver");
    Assert.assertEquals("varchar(100)", langtype);

    langtype = ctx.typename("id", "sql");
    Assert.assertEquals("varchar(64)", langtype);
  }

  @Test
  public void test_code() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("code(10)", "sqlserver");
    Assert.assertEquals("varchar(10)", langtype);

    langtype = ctx.typename("code(1)", "sqlserver");
    Assert.assertEquals("varchar(1)", langtype);

    langtype = ctx.typename("code(40)", "sqlserver");
    Assert.assertEquals("varchar(40)", langtype);
  }

  @Test
  public void test_mail_phone_mobile() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("mail", "sqlserver");
    Assert.assertEquals("varchar(100)", langtype);

    langtype = ctx.typename("mobile", "sqlserver");
    Assert.assertEquals("varchar(100)", langtype);

    langtype = ctx.typename("phone", "sqlserver");
    Assert.assertEquals("varchar(100)", langtype);
  }

  @Test
  public void test_typename() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("varchar", "java", "Object");
    Assert.assertEquals("String", langtype);

    langtype = ctx.typename("decimal", "java", "Object");
    Assert.assertEquals("BigDecimal", langtype);
  }
    
  @Test
  public void test_c_and_cpp() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("string(8)", "c");
    Assert.assertEquals("char", langtype);
    
    langtype = ctx.typename("byte(100)", "c");
    Assert.assertEquals("char", langtype);
  }
  
  @Test
  public void test_c_and_cpp_bit() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("bit(16)", "c");
    Assert.assertEquals("char[2]", langtype);
  }

}
