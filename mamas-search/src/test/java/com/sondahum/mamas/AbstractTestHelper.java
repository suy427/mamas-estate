//package com.sondahum.mamas;
//
//import groovy.lang.Closure;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.util.MultiValueMap;
//
//import java.util.*;
//
//public class AbstractTestHelper {
//
//    class RequestValuesHandler{
//
//        PathValues pathValues;
//        ParameterValues paramValues;
//        MultipartValues multipartValues;
//        BodyValues bodyValues;
//        HeaderValues headerValues;
//        ResponseHandler responseHandler;
//
//
//        RequestValuesHandler(){}
//
//        RequestValuesHandler(RequestValues... values) {
//            for (RequestValues valueObject : values) {
//                if (valueObject instanceof PathValues)
//                    this.pathValues = (PathValues) valueObject;
//                else if (valueObject instanceof ParameterValues)
//                    this.paramValues = (ParameterValues) valueObject;
//                else if (valueObject instanceof MultipartValues)
//                    this.multipartValues = (MultipartValues) valueObject;
//                else if (valueObject instanceof BodyValues)
//                    this.bodyValues = (BodyValues) valueObject;
//                else if (valueObject instanceof HeaderValues)
//                    this.headerValues = (HeaderValues) valueObject;
//                else if (valueObject instanceof ResponseHandler)
//                    this.responseHandler = (ResponseHandler) valueObject;
//            }
//        }
//
//        List<Object> getPathArray() {
//            return this.pathValues.values;
//        }
//        MultiValueMap<String, String> getParams(){
//            return generateParams(this.paramValues.values ?: [:]);
//        }
//        String getBodyString(){
//            return toJsonString(this.bodyValues?.values ?: [:]) ;
//        }
//        List<MockMultipartFile> getMultipartFileList(){
//            return this.multipartValues?.values ?: [];
//        }
//        MultiValueMap<String, String> getHeaders(){
//            return generateParams(this.headerValues?.values ?: [:]);
//        }
//        Closure getClosureForResponse(){
//            return responseHandler?.closureForResponse;
//        }
//    }
//    class RequestValues {}
//    class PathValues extends RequestValues { List<Object> values = new ArrayList<>();}
//    class ParameterValues extends RequestValues { MultiValueMap<String, String> values = new MultiValueMap<String, String>() {
//        @Override
//        public String getFirst(String key) {
//            return null;
//        }
//
//        @Override
//        public void add(String key, String value) {
//
//        }
//
//        @Override
//        public void addAll(String key, List<? extends String> values) {
//
//        }
//
//        @Override
//        public void addAll(MultiValueMap<String, String> values) {
//
//        }
//
//        @Override
//        public void set(String key, String value) {
//
//        }
//
//        @Override
//        public void setAll(Map<String, String> values) {
//
//        }
//
//        @Override
//        public Map<String, String> toSingleValueMap() {
//            return null;
//        }
//
//        @Override
//        public int size() {
//            return 0;
//        }
//
//        @Override
//        public boolean isEmpty() {
//            return false;
//        }
//
//        @Override
//        public boolean containsKey(Object key) {
//            return false;
//        }
//
//        @Override
//        public boolean containsValue(Object value) {
//            return false;
//        }
//
//        @Override
//        public List<String> get(Object key) {
//            return null;
//        }
//
//        @Override
//        public List<String> put(String key, List<String> value) {
//            return null;
//        }
//
//        @Override
//        public List<String> remove(Object key) {
//            return null;
//        }
//
//        @Override
//        public void putAll(Map<? extends String, ? extends List<String>> m) {
//
//        }
//
//        @Override
//        public void clear() {
//
//        }
//
//        @Override
//        public Set<String> keySet() {
//            return null;
//        }
//
//        @Override
//        public Collection<List<String>> values() {
//            return null;
//        }
//
//        @Override
//        public Set<Entry<String, List<String>>> entrySet() {
//            return null;
//        }
//    }  }
//    class MultipartValues extends RequestValues { List<Object> values = new ArrayList<>(); }
//    class BodyValues  extends RequestValues { Object values = null; }
//    class HeaderValues extends RequestValues { Object values = null; }
//    class ResponseHandler extends RequestValues { Closure closureForResponse = null; }
//}
