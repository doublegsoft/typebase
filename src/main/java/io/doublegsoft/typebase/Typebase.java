/*
 * DOUBLEGSOFT.COM CONFIDENTIAL
 *
 * Copyright (C) doublegsoft.com
 *
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of doublegsoft.com and its suppliers, if any.
 * The intellectual and technical concepts contained herein
 * are proprietary to doublegsoft.com and its suppliers  and
 * may be covered by China and Foreign Patents, patents in
 * process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from doublegsoft.com.
 */
package io.doublegsoft.typebase;

import com.doublegsoft.jcommons.metabean.type.CollectionType;
import com.doublegsoft.jcommons.metabean.type.DomainType;
import com.doublegsoft.jcommons.metabean.type.ObjectType;
import com.doublegsoft.jcommons.metabean.type.PrimitiveType;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.doublegsoft.typebase.TypebaseParser.Typebase_anonymous_objectContext;
import io.doublegsoft.typebase.TypebaseParser.Typebase_custom_objectContext;
import io.doublegsoft.typebase.TypebaseParser.Typebase_keystringContext;
import io.doublegsoft.typebase.TypebaseParser.Typebase_keytextContext;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.yaml.snakeyaml.Yaml;

/**
 * The typebase context for typebase system.
 *
 * @author <a href="mailto:guo.guo.gan@gmail.com">Christian Gann</a>
 *
 * @since 1.0
 */
public class Typebase {
  
  private static final Map<String, Object> BUILTINS = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

  static {
    Yaml yaml = new Yaml();
    Map<String, Object> data = (Map<String, Object>) yaml.load(Typebase.class.getResourceAsStream("/types.yml"));
    BUILTINS.putAll(data);
  }
  
  public DomainType domainType(String domainType) {
    DomainType retVal = new DomainType(domainType);
    
    TypebaseLexer lexer = new TypebaseLexer(CharStreams.fromString(domainType));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    TypebaseParser parser = new TypebaseParser(tokens);
    TypebaseParser.Typebase_anytypeContext ctx = parser.typebase_anytype();
    
    if (ctx.array != null) {
      retVal.setArray(true);
    }
    if (ctx.typebase_id() != null) {
      retVal.setName("id");
    } else if (ctx.typebase_string() != null) {
      retVal.setName("string");
      if (ctx.typebase_string().length != null) {
        retVal.addOption("length", ctx.typebase_string().length.getText());
      }
    } else if (ctx.typebase_code() != null) {
      retVal.setName("code");
      if (ctx.typebase_code().length != null) {
        retVal.addOption("length", ctx.typebase_code().length.getText());
      }
      if (ctx.typebase_code().type != null) {
        retVal.addOption("type", ctx.typebase_code().type.getText());
      }
    } else if (ctx.typebase_name() != null) {
      retVal.setName("name");
      if (ctx.typebase_name().type != null) {
        retVal.addOption("type", ctx.typebase_name().type.getText());
      }
    } else if (ctx.typebase_date() != null) {
      retVal.setName("date");
      if (ctx.typebase_date().type != null) {
        retVal.addOption("type", ctx.typebase_date().type.getText());
      }
    } else if (ctx.typebase_time() != null) {
      retVal.setName("time");
      if (ctx.typebase_time().type != null) {
        retVal.addOption("type", ctx.typebase_time().type.getText());
      }
    } else if (ctx.typebase_datetime() != null) {
      retVal.setName("datetime");
      if (ctx.typebase_datetime().type != null) {
        retVal.addOption("type", ctx.typebase_datetime().type.getText());
      }
    } else if (ctx.typebase_now() != null) {
      retVal.setName("now");
    } else if (ctx.typebase_int() != null) {
      retVal.setName("int");
      if (ctx.typebase_int().length != null) {
        retVal.addOption("length", ctx.typebase_int().length.getText());
      }
    } else if (ctx.typebase_long() != null) {
      retVal.setName("long");
    } else if (ctx.typebase_number() != null) {
      retVal.setName("number");
      if (ctx.typebase_number().precision != null) {
        retVal.addOption("precision", Integer.valueOf(ctx.typebase_number().precision.getText()));
      }
      if (ctx.typebase_number().scale != null) {
        retVal.addOption("scale", Integer.valueOf(ctx.typebase_number().scale.getText()));
      }
    } else if (ctx.typebase_money() != null) {
      retVal.setName("money");
    } else if (ctx.typebase_byte() != null) {
      retVal.setName("byte");
      if (ctx.typebase_byte().length != null) {
        retVal.addOption("length", ctx.typebase_byte().length.getText());
      }
    } else if (ctx.typebase_bit() != null) {
      retVal.setName("bit");
      if (ctx.typebase_bit().length != null) {
        retVal.addOption("length", ctx.typebase_bit().length.getText());
      }
    } else if (ctx.typebase_enum() != null) {
      retVal.setName("enum");
      if (ctx.typebase_enum().typebase_keytext() != null) {
        List<EnumValue> pairs = new ArrayList<>();
        int length = 0;
        for (Typebase_keytextContext ctxTypebaseKeyText : ctx.typebase_enum().typebase_keytext()) {
          EnumValue enumVal = new EnumValue();
          String key = ctxTypebaseKeyText.anybase_key().getText();
          length = Math.max(length, key.length());
          enumVal.setCode(key);
          enumVal.setName(ctxTypebaseKeyText.name.getText());
          if (ctxTypebaseKeyText.text != null) {
            String text = ctxTypebaseKeyText.text.getText();
            enumVal.setText(text.substring(1, text.length() - 1));
          }
          pairs.add(enumVal);
        }
        retVal.addOption("length", String.valueOf(length));
        retVal.addOption("pairs", pairs);
      }
    } else if (ctx.typebase_text() != null) {
      retVal.setName("text");
      if (ctx.typebase_text().type != null) {
        retVal.addOption("type", ctx.typebase_text().type.getText());
      }
    } else if (ctx.typebase_image() != null) {
      retVal.setName("image");
      if (ctx.typebase_image().ANYBASE_ID() != null) {
        List<String> types = new ArrayList<>();
        for (TerminalNode tn : ctx.typebase_image().ANYBASE_ID()) {
          types.add(tn.getText());
        }
        retVal.addOption("types", types);
      }
    } else if (ctx.typebase_email() != null) {
      retVal.setName("email");
    } else if (ctx.typebase_mobile() != null) {
      retVal.setName("mobile");
    } else if (ctx.typebase_phone() != null) {
      retVal.setName("phone");
    } else if (ctx.typebase_address() != null) {
      retVal.setName("address");
    } else if (ctx.typebase_url() != null) {
      retVal.setName("url");
    } else if (ctx.typebase_password() != null) {
      retVal.setName("password");
    } else if (ctx.typebase_bool() != null) {
      retVal.setName("bool");
    }else if (ctx.typebase_any() != null) {
      retVal.setName("any");
    } else if (ctx.reftype != null) {
      retVal.setName("&");
      retVal.addOption("object", ctx.reftype.getText());
      if (ctx.typebase_anybase_id() != null) {
        List<String> attrs = new ArrayList<>();
        for (int i = 0; i < ctx.typebase_anybase_id().size(); i++) {
          attrs.add(ctx.typebase_anybase_id(i).getText());
        }
        retVal.addOption("attributes", attrs);
      }
    } 
    if (retVal.getName() == null) {
      retVal.setName(domainType);
    }
    return retVal;
  }

  public String typename(String domain, String lang) {
    DomainType domainType = domainType(domain);
    return find(domainType, lang);
  }

  /**
   * Gets the type name of specific language according to any source language type name.
   * <p>
   * Should use text recognition to parse any type.
   *
   * @param anytype the any language type name
   *
   * @param lang the specific language
   *
   * @param defaultTypename if the type not found, uses the default typename
   *
   * @return the type name of destination language
   */
  public String typename(String anytype, String lang, String defaultTypename) {
    String lowercasedLang = lang.toLowerCase();
    String retVal = defaultTypename;
    for (Entry<String, Object> entry : BUILTINS.entrySet()) {
      Map<String, Object> inner = (Map<String, Object>) entry.getValue();
      if (entry.getKey().indexOf(anytype) == 0) {
        Map<String, Object> innerTypes = (Map<String, Object>) entry.getValue();
        retVal = (String) innerTypes.get(lang);
        if (retVal != null) {
          return retVal;
        }
      }
      for (Entry<String, Object> innerEntry : inner.entrySet()) {
        String innerType = (String) innerEntry.getValue();
        boolean important = innerType.contains("*");
        innerType = innerType.replace("*", "");
        if (match(anytype, innerType)) {
          if (important) {
            return (String) inner.get(lowercasedLang);
          }
          retVal = (String) inner.get(lowercasedLang);
        }
      }
    }
    retVal = renderTemplate(anytype, retVal, Collections.emptyMap());
    if (retVal == null) {
      return defaultTypename;
    }
    return retVal;
  }
  
  /**
   * Gets the programming language type name according to the object type.
   * <p>
   * And the change logs are listed below:
   * <ul>
   * <li>1. Add freemarker support</li>
   * </ul>
   * 
   * @param type
   *        the object type
   * 
   * @param lang
   *        the programming language
   * 
   * @param defaultTypename
   *        the default type of programming language
   * 
   * @return the real type of programming language
   */
  public String typename(ObjectType type, String lang, String defaultTypename) {
    String retVal = defaultTypename;
    Map<String, Object> model = new HashMap<>();
    model.put("name", type.getName());
    if (type.isPrimitive()) {
      model.put("length", ((PrimitiveType) type).getLength());
      model.put("precision", ((PrimitiveType) type).getPrecision());
      model.put("scale", ((PrimitiveType) type).getScale());
    }
    if (type.isPrimitive()) {
      retVal = renderTemplate(type.getName(), typename(type.getName(), lang, defaultTypename), model);
    } else if (type.isCustom()) {
      retVal = renderTemplate(type.getName(), typename(type.getName(), lang, defaultTypename), model);
    } else if (type.isCollection()) {
      CollectionType colltype = (CollectionType) type;
      retVal = renderTemplate(type.getName(), typename(type.getName(), lang, defaultTypename), model);
    } else if (type.isDomain()) {
      retVal = renderTemplate(type.getName(), typename(type.getName(), lang, defaultTypename), model);
    } 
    if (retVal == null) {
      return defaultTypename;
    }
    return retVal;
//    throw new IllegalArgumentException(type.getName() + "[" + type.toString() + "] is not supported.");
  }

  public List<EnumValue> enumtype(String enumDomain) {
    TypebaseLexer lexer = new TypebaseLexer(CharStreams.fromString(enumDomain));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    TypebaseParser parser = new TypebaseParser(tokens);
    // parser.setErrorHandler(new BailErrorStrategy());
    TypebaseParser.Typebase_enumContext ctx = parser.typebase_enum();
    List<EnumValue> retVal = new ArrayList<>();
    ctx.typebase_keytext().forEach(kt -> {
      String key = kt.anybase_key().getText();
      EnumValue enumVal = new EnumValue();
      enumVal.setCode(key);
      enumVal.setName(kt.name.getText());
      if (kt.text != null) {
        String text = kt.text.getText();
        enumVal.setText(text.substring(1, text.length() - 1));
      }
      retVal.add(enumVal);
    });
    return retVal;
  }

  public List<String> tupletype(String tupleDomain) {
    TypebaseLexer lexer = new TypebaseLexer(CharStreams.fromString(tupleDomain));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    TypebaseParser parser = new TypebaseParser(tokens);
    // parser.setErrorHandler(new BailErrorStrategy());
    TypebaseParser.Typebase_tupleContext ctx = parser.typebase_tuple();
    List<String> retVal = new ArrayList<>();
    ctx.typebase_value().forEach(val -> {
      retVal.add(val.getText());
    });
    return retVal;
  }
  
  /**
   * Parses the type expression and gets the {@link CustomObject} instance.
   * 
   * @param expr
   *        the type expression
   * 
   * @return {@link CustomObject} instance
   * 
   * @since 4.0
   */
  public CustomObject customObjectType(String expr) {
    TypebaseLexer lexer = new TypebaseLexer(CharStreams.fromString(expr));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    TypebaseParser parser = new TypebaseParser(tokens);
    TypebaseParser.Typebase_custom_objectContext ctx = parser.typebase_custom_object();
    return buildCustomObject(ctx);
  }
  
  public CustomObject anonymousObjectType(String expr) {
    TypebaseLexer lexer = new TypebaseLexer(CharStreams.fromString(expr));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    TypebaseParser parser = new TypebaseParser(tokens);
    TypebaseParser.Typebase_anonymous_objectContext ctx = parser.typebase_anonymous_object();
    return buildCustomObject(ctx);
  }
  
  private CustomObject buildCustomObject(Typebase_custom_objectContext ctx) {
    CustomObject retVal = new CustomObject();
    retVal.setName(ctx.name.getText());
    for (Typebase_keystringContext ctxKeystr : ctx.typebase_keystring()) {
      retVal.addAttribute(ctxKeystr.key.getText(), ctxKeystr.str.getText().substring(1,  ctxKeystr.str.getText().length() - 1));
    }
    if (ctx.typebase_anonymous_object() != null) {
      for (TypebaseParser.Typebase_attrdeclContext ctxAttrdecl : ctx.typebase_anonymous_object().typebase_attrdecl()) {
       retVal.addChild(buildCustomObject(ctxAttrdecl));
      }
    }
    return retVal;
  }
  
  private CustomObject buildCustomObject(Typebase_anonymous_objectContext ctx) {
    CustomObject retVal = new CustomObject();
    for (TypebaseParser.Typebase_attrdeclContext ctxAttrdecl : ctx.typebase_attrdecl()) {
      retVal.addChild(buildCustomObject(ctxAttrdecl));
    }
    return retVal;
  }
  
  private CustomObject buildCustomObject(TypebaseParser.Typebase_attrdeclContext ctx) {
    CustomObject retVal = new CustomObject();
    retVal.setName(ctx.typebase_anybase_id(0).getText());
    if (ctx.typebase_anybase_id().size() == 2) {
      CustomObject type = new CustomObject();
      type.setName(ctx.typebase_anybase_id(1).getText());
      retVal.setType(type);
    } else if (ctx.typebase_anytype().typebase_anonymous_object() != null) {
      retVal.setType(buildCustomObject(ctx.typebase_anytype().typebase_anonymous_object()));
    } else if (ctx.typebase_anytype().typebase_custom_object() != null) {
      retVal.setType(buildCustomObject(ctx.typebase_anytype().typebase_custom_object()));
    } else {
      CustomObject type = new CustomObject();
      type.setName(ctx.typebase_anytype().getText());
      retVal.setType(type);
    }
    return retVal;
  }

  /**
   * Finds the matching type for the specific language.
   *
   * @param domain the domain type
   *
   * @param lang the specific language
   *
   * @return found type or {@code null}
   */
  private String find(DomainType domainType, String lang) {
    Map<String, Object> builtin = (Map<String, Object>) BUILTINS.get(domainType.getName());
    if (builtin == null) {
      return null;
    }
    String expr = (String) builtin.get(lang.toLowerCase());
    if (expr != null) {
      return renderTemplate(domainType.getName(), expr, domainType.getOptions()); 
    }
    return null;
  }

  private String replace(String langtype, int index, String replace) {
    return langtype.replace("$" + index, replace);
  }

  private boolean match(String type1, String type2) {
    int index1 = type1.indexOf("(");
    type1 = index1 == -1 ? type1 : type1.substring(0, index1);
    int index2 = type2.indexOf("(");
    type2 = index2 == -1 ? type2 : type2.substring(0, index2);
    return type1.equalsIgnoreCase(type2);
  }
  
  private String renderTemplate(String name, String template, Map<String, Object> model) {
    if (template == null) {
      return null;
    }
    Configuration freemarker = new Configuration(Configuration.VERSION_2_3_34);
    StringTemplateLoader loader = new StringTemplateLoader();
    freemarker.setTemplateLoader(loader);
    loader.putTemplate(name, template);
    StringWriter retVal = new StringWriter();
    try {
      Template tpl = freemarker.getTemplate(name);
      tpl.process(model, retVal);
      retVal.close();
      return retVal.toString();
    } catch (IOException | TemplateException ex) {
      return null;
    }
  }
}
