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
public class TypebaseAnyTypeTest {

  @Test
  public void test_domain_0() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("now", "java", "Object");
    Assert.assertEquals("Date", langtype);

    langtype = ctx.typename("email", "java", "Object");
    Assert.assertEquals("String", langtype);

//    langtype = ctx.typename("file", "java", "Object");
//    Assert.assertEquals("InputStream", langtype);
  }

  @Test
  public void test_domain_1() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("lmt", "java");
    Assert.assertEquals("Date", langtype);

    langtype = ctx.typename("email", "java");
    Assert.assertEquals("String", langtype);

//    langtype = ctx.typename("file", "java");
//    Assert.assertEquals("InputStream", langtype);
  }
  
  @Test
  public void test_domain_2() {
    Typebase ctx = new Typebase();
    String langtype = ctx.typename("bool", "java", "String");
    Assert.assertEquals("Boolean", langtype);
    
    ctx = new Typebase();
    langtype = ctx.typename("number(20)", "java", "String");
    Assert.assertEquals("BigDecimal", langtype);
  }
}
