package io.doublegsoft.typebase;

public class EnumValue {

  private String code;

  private String name;

  private String text;

  public EnumValue() {

  }

  public EnumValue(String code, String name, String text) {
    this.code = code;
    this.name = name;
    this.text = text;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    if (text == null) {
      return name;
    }
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
