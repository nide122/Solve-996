package com.createTemplate.api.util;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.BeanUtils;

public class PropertyUtils
{
  public static String getPropertyNames(Class clazz)
  {
    PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(clazz);
    StringBuffer sb = new StringBuffer();
    if (descriptors.length > 0) {
      for (int i = 0; i < descriptors.length; ++i) {
		if (!("class".equalsIgnoreCase(descriptors[i].getName()))) {
			sb.append(descriptors[i].getName()).append(",");
		}
	}


      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  public static String getPropertyNames(Class clazz, String alias)
  {
    PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(clazz);
    StringBuffer sb = new StringBuffer();
    if (descriptors.length > 0) {
      for (int i = 0; i < descriptors.length; ++i) {
		if (!("class".equalsIgnoreCase(descriptors[i].getName()))) {
			sb.append(alias).append(".").append(descriptors[i].getName()).append(",");
		}
	}


      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }

  public static String getPropertyNames(Class clazz, String alias, String... columnArray)
  {
    Map map = new HashMap();
    for (String tempStr : columnArray) {
		map.put(tempStr, tempStr);
	}

    PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(clazz);
    StringBuffer sb = new StringBuffer();
    if (descriptors.length > 0) {
      for (int k = 0; k < descriptors.length; ++k) {
        if (map.containsKey(descriptors[k].getName())) {
			continue;
		}

        if (!("class".equalsIgnoreCase(descriptors[k].getName()))) {
			sb.append(alias).append(".").append(descriptors[k].getName()).append(",");
		}
      }

      sb.deleteCharAt(sb.length() - 1);
    }
    return sb.toString();
  }
}