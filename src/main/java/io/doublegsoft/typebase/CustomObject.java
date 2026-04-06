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

import com.doublegsoft.jcommons.lang.StringPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 */
public final class CustomObject {
  
  private final List<StringPair> attributes = new ArrayList<>();
  
  private final List<CustomObject> children = new ArrayList<>();
  
  private String name;
  
  private CustomObject type;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CustomObject getType() {
    return type;
  }

  public void setType(CustomObject type) {
    this.type = type;
  }
  
  public void addAttribute(String name, String value) {
    for (StringPair attr : attributes) {
      if (attr.getKey().equals(name)) {
        return;
      }
    }
    StringPair attr = new StringPair();
    attr.setKey(name);
    attr.setValue(value);
    attributes.add(attr);
  }
  
  public String getAttributeValue(String name) {
    for (StringPair attr : attributes) {
      if (attr.getKey().equals(name)) {
        return attr.getValue();
      }
    }
    return null;
  }
  
  public Set<String> getAttributeNames() {
    Set<String> retVal = new HashSet<>();
    for (StringPair attr : attributes) {
      retVal.add(attr.getKey());
    }
    return retVal;
  }
  
  public void addChild(CustomObject child) {
    children.add(child);
  }
  
  public List<CustomObject> getChildren() {
    return Collections.unmodifiableList(children);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 53 * hash + Objects.hashCode(this.attributes);
    hash = 53 * hash + Objects.hashCode(this.children);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final CustomObject other = (CustomObject) obj;
    if (!Objects.equals(this.attributes, other.attributes)) {
      return false;
    }
    if (!Objects.equals(this.children, other.children)) {
      return false;
    }
    return true;
  }
  
  
}
