package com.sondahum.mamas.testutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sondahum.mamas.common.config.SortDeserializer;
import com.sondahum.mamas.common.config.SortSerializer;
import com.sondahum.mamas.domain.bid.model.Action;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Sort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class MultiValueMapConverter {

    private MultiValueMap<String, String> multiValueMap;
    private Object inputBean;
    private Map<String, Object> inputMap;
    private List<Object> inputList;

    private final ObjectMapper mapper = new ObjectMapper();
    private final SimpleModule module = new SimpleModule();

    public MultiValueMapConverter() {
        this.multiValueMap = new LinkedMultiValueMap<>();
        module.addSerializer(Sort.class, new SortSerializer());
        module.addDeserializer(Sort.class, new SortDeserializer());
        mapper.registerModule(module);
    }

    public MultiValueMap convert(Object bean) throws Exception {
        this.inputBean = bean;
        addMultiValueFromBean(multiValueMap, "", this.inputBean);
        return multiValueMap;
    }

    public MultiValueMap convert(List<Object> list) throws Exception {
        this.inputList = list;
        addMultiValueFromIterable(multiValueMap, "", this.inputList);
        return multiValueMap;
    }

    public MultiValueMap convert(Map<String, Object> map) throws Exception {
        this.inputMap = map;
        addMultiValueFromMap(multiValueMap, "", this.inputMap);
        return multiValueMap;
    }

    private boolean isPrimitiveType(Object object) {
        return (object instanceof String) ||
                (object instanceof Integer) ||
                (object instanceof Float) ||
                (object instanceof Boolean) ||
                (object instanceof Long) ||
                (object instanceof Action);
    }

//    private MultiValueMap handleSort(MultiValueMap multiValueMap, Sort sort) throws JsonProcessingException, NoSuchMethodException, IntrospectionException, IllegalAccessException, InvocationTargetException {
//        MultiValueMap mvm = multiValueMap;
//        String sortAsString = mapper.writeValueAsString(sort);
//        Map tmp = mapper.readValue(sortAsString, Map.class);
//
//        List<Map<String, String>> list = (List<Map<String, String>>) tmp.get("sort");
//
//
//        return mvm;
//    }

    private MultiValueMap addMultiValueFromBean(MultiValueMap multiValueMap, String name, Object object)
            throws IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, JsonProcessingException {
        MultiValueMap mvm = multiValueMap;

        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            String _name = (name.equals("")) ? field.getName() : name + "." + field.getName();
            Object value = new PropertyDescriptor(field.getName(), object.getClass()).getReadMethod().invoke(object);

            if (value == null) {

            } else {
                if (isPrimitiveType(value)) { // 기본 자료형 + 순서가 오는 경우
                    value = new PropertyDescriptor(field.getName(), object.getClass()).getReadMethod().invoke(object);
                    mvm.add(_name, value.toString());
                } else {
                    if (value instanceof Map) {
                        mvm = this.addMultiValueFromMap(multiValueMap, _name, (Map) value);
                    } /*else if(value instanceof Sort) {
                        mvm = handleSort(mvm, (Sort)value);
                    } */else if (value instanceof Iterable) {
                        mvm = this.addMultiValueFromIterable(multiValueMap, _name, (Iterable) value);
                    } else if (value instanceof MultipartFile) {
                        MultipartFile multipartFile = (MultipartFile) value;
                        ByteArrayResource resource = null;
                        try {
                            resource = new ByteArrayResource(multipartFile.getBytes()) {
                                @Override
                                public String getFilename() throws IllegalStateException {
                                    return multipartFile.getOriginalFilename();
                                }
                            };
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mvm.add(_name, resource);
                    } else {
                        mvm = this.addMultiValueFromBean(mvm, _name, value);
                    }
                }
            }
        }
        return mvm;
    }

    private MultiValueMap addMultiValueFromIterable(MultiValueMap multiValueMap, String name, Iterable iterable)
            throws NoSuchMethodException, IntrospectionException, IllegalAccessException, InvocationTargetException, JsonProcessingException {
        MultiValueMap mvm = multiValueMap;

        int i = 0;
        for (Object object : iterable) {
            String _name = name + "[" + i + "]";
            if (isPrimitiveType(object)) {
                mvm.add(_name, object.toString());
            } else {
                if (object instanceof Map) {
                    mvm = this.addMultiValueFromMap(mvm, _name, (Map) object);
                } else if (object instanceof Iterable) {
                    mvm = this.addMultiValueFromIterable(mvm, _name, (Iterable) object);
                } else if (object instanceof MultipartFile) {
                    MultipartFile multipartFile = (MultipartFile) object;
                    ByteArrayResource resource = null;
                    try {
                        resource = new ByteArrayResource(multipartFile.getBytes()) {
                            @Override
                            public String getFilename() throws IllegalStateException {
                                return multipartFile.getOriginalFilename();
                            }
                        };
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mvm.add(_name, resource);
                } else {
                    mvm = this.addMultiValueFromBean(mvm, _name, object);
                    i++;
                }
            }
        }
        return mvm;
    }

    private MultiValueMap addMultiValueFromMap(MultiValueMap multiValueMap, String name, Map map)
            throws InvocationTargetException, NoSuchMethodException, IntrospectionException, IllegalAccessException, JsonProcessingException {
        MultiValueMap mvm = multiValueMap;
        Set<String> keys = map.keySet();

        for (String key : keys) {
            String _name = name + "." + key;
            Object value = map.get(key);

            if (isPrimitiveType(value)) {
                mvm.add(_name, value.toString());
            } else {
                if (value instanceof Map) {
                    mvm = this.addMultiValueFromMap(mvm, _name, (Map) value);
                } else if (value instanceof Iterable) {
                    mvm = this.addMultiValueFromIterable(mvm, _name, (Iterable) value);
                } else {
                    mvm = addMultiValueFromBean(mvm, _name, value);
                }
            }
        }
        return mvm;
    }
}