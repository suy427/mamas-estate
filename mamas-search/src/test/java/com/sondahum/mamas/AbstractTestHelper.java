package com.sondahum.mamas;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MamasEstateApplicationStarter.class)
public abstract class AbstractTestHelper {

    protected MockMvc mockMvc;
    protected final ObjectMapper mapper = new ObjectMapper();
    protected RestTemplate rt = new RestTemplate();



    private class RequestValues {}
    private class PathValues extends RequestValues { List<Object> pathValueList = new ArrayList<>();}
    private class ParameterValues extends RequestValues {MultiValueMap<String, String> parameterValueMap = new LinkedMultiValueMap<>();} // 얘는 MultiValueMap이 될 아이다.
    private class HeaderValues extends RequestValues {MultiValueMap<String, String> headerValueMap = new LinkedMultiValueMap<>();}
    private class MultipartValues extends RequestValues {List<MockMultipartFile> multiPartValueList = new ArrayList<>();}
    private class BodyValues extends RequestValues {Object values = null;}
    private class ResponseHandler extends RequestValues {Consumer<MockHttpServletResponse> responseConsumer = null;}


    void playground() {




    }


    protected ParameterValues parameterValues(Map<String, String> parameterValueMap){
        ParameterValues parameterValues = new ParameterValues();
        parameterValues.parameterValueMap = convertToMultiValueMap(parameterValueMap);
        return parameterValues;
    }

    protected HeaderValues headerValues(Map<String, String> headerValueMap){
        HeaderValues headerValues = new HeaderValues();
        headerValues.headerValueMap = convertToMultiValueMap(headerValueMap);
        return headerValues;
    }

    protected BodyValues bodyValues(Map<String, Object> valueMap){
        BodyValues o = new BodyValues();
        o.values = valueMap;
        return o;
    }


    // todo map --> multivaluemap
    // multivaluemap은 query로 list를 표현하지 못하기 때문에 쓰는거다...?
    private MultiValueMap<String, String> convertToMultiValueMap(Map<String, String> map) {
        MultiValueMap<String, String> ret = new LinkedMultiValueMap<>();

        map.forEach( (key, value) -> {

        });

        return null;
    }

    protected PathValues pathValues(Object... values){
        PathValues o = new PathValues();
        o.pathValueList = Arrays.asList(values);
        return o;
    }

    protected MultipartValues multipartValues(MockMultipartFile... values){
        MultipartValues o = new MultipartValues();
        o.multiPartValueList = Arrays.asList(values);
        return o;
    }

    protected ResponseHandler responseHandler(Consumer<MockHttpServletResponse> consumer) {
        ResponseHandler o = new ResponseHandler();
        o.responseConsumer = consumer;
        return o;
    }

    static File byteArrayToFile(byte[] buff, String filePath, String fileName) {
        if ((filePath == null || "".equals(filePath))
                || (fileName == null || "".equals(fileName))) { return null; }

        FileOutputStream fos = null;

        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File destFile = new File(filePath + fileName);

        try {
            fos = new FileOutputStream(destFile);
            fos.write(buff);
            fos.close();
        } catch (IOException e) {
            log.error("Exception position : FileUtil - binaryToFile(String binaryFile, String filePath, String fileName)");
        }

        return destFile;
    }


    public class RequestValuesHandler {

        PathValues pathValues;
        ParameterValues paramValues;
        MultipartValues multipartValues;
        BodyValues bodyValues;
        HeaderValues headerValues;
        ResponseHandler responseHandler;

        public RequestValuesHandler() {}

        public RequestValuesHandler(RequestValues... values) {
            for (RequestValues valueObject : values) {
                if (valueObject instanceof PathValues)
                    this.pathValues = (PathValues) valueObject;
                else if (valueObject instanceof ParameterValues)
                    this.paramValues = (ParameterValues) valueObject;
                else if (valueObject instanceof MultipartValues)
                    this.multipartValues = (MultipartValues) valueObject;
                else if (valueObject instanceof BodyValues)
                    this.bodyValues = (BodyValues) valueObject;
                else if (valueObject instanceof HeaderValues)
                    this.headerValues = (HeaderValues) valueObject;
                else if (valueObject instanceof ResponseHandler)
                    this.responseHandler = (ResponseHandler) valueObject;
            }
        }

        List<Object> getPathArray() { return this.pathValues.pathValueList; }
        MultiValueMap<String, String> getParams() { return this.paramValues.parameterValueMap; }
        String getBodyString() throws Exception { return mapper.writeValueAsString(this.bodyValues.values); }
        MultiValueMap<String, String> getHeaders() { return this.headerValues.headerValueMap; }
        List<MockMultipartFile> getMultipartFileList(){ return this.multipartValues.multiPartValueList; }
        Consumer<MockHttpServletResponse> getConsumerForResponse() { return responseHandler.responseConsumer; }
    }

    MockHttpServletResponse requestGet(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler) throws Exception {
        //- Request
        ResultActions resultActions = mockMvc
                .perform(
                        RestDocumentationRequestBuilders
                                .get(url, valuesHandler.getPathArray())
                                .params(valuesHandler.getParams())
                                .headers(new HttpHeaders(valuesHandler.getHeaders()))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        //- Document or Something
        if (resultHandlerForDocument != null){
            resultActions.andDo(resultHandlerForDocument);
        }
        //- Response
        MvcResult mvcResult = resultActions.andReturn();
        if (valuesHandler.getConsumerForResponse() != null)
            valuesHandler.getConsumerForResponse().accept(mvcResult.getResponse());

        return mvcResult.getResponse();
    }

    MockHttpServletResponse requestPost(String url, ResultHandler resultHandlerForDocument, RequestValuesHandler valuesHandler) throws Exception {
        //Request
        ResultActions resultActions = mockMvc
                .perform(
                        post(url, valuesHandler.getPathArray())
                                .params(valuesHandler.getParams())
                                .content(valuesHandler.getBodyString())
                                .headers(new HttpHeaders(valuesHandler.getHeaders()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
//                    .with(securityContext(secc))
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        //- Document or Something
        if (resultHandlerForDocument != null) {
            resultActions.andDo(resultHandlerForDocument);
        }
        //Response
        MvcResult mvcResult = resultActions.andReturn();

        return mvcResult.getResponse();
    }

    public class MultiValueMapConverter {
        private MultiValueMap<String, Object> multiValueMap;

        private Object bean;

        public MultiValueMapConverter(Object bean) {
            this.multiValueMap = new LinkedMultiValueMap<>();
            this.bean = bean;
        }

        public MultiValueMap convert() throws Exception {
            this.addMultiValueFromBean(this.multiValueMap, "", this.bean);
            return this.multiValueMap;
        }


        private boolean isPrimitiveType(Object object) {
            if ((object instanceof String) ||
                    (object instanceof Integer) ||
                    (object instanceof Float) ||
                    (object instanceof Void) ||
                    (object instanceof Boolean) ||
                    (object instanceof Long)) {
                return true;
            } else {
                return false;
            }
        }

        private MultiValueMap addMultiValueFromBean( /* IntrospectionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException */
                MultiValueMap multiValueMap, String name, Object object) throws Exception{
            MultiValueMap mvm = multiValueMap;

            Field[] fields = object.getClass().getDeclaredFields();

            for (Field field : fields) {
                String _name = (name.equals("")) ? field.getName() : name + "." + field.getName();
                Object value = new PropertyDescriptor(field.getName(), object.getClass()).getReadMethod().invoke(object);

                if (value == null) {
//                return mvm;
                } else {

//            if (!this.isPrimitiveType(value)) {
//                mvm = this.addMultiValueFromBean(mvm, _name, value);
//            } else {
                    if (value instanceof Map) {
                        mvm = this.addMultiValueFromMap(multiValueMap, _name, (Map) value);
                    } else if (value instanceof Iterable) {
                        mvm = this.addMultiValueFromIterable(multiValueMap, _name, (Iterable) value);
                    } else if (value instanceof MultipartFile) {
                        MultipartFile multipartFile = (MultipartFile) value;
                        ByteArrayResource resource = null;

//                        static File byteArrayToFile(byte[] buff, String filePath, String fileName) {
//                            if ((filePath == null || "".equals(filePath))
//                                    || (fileName == null || "".equals(fileName))) { return null; }
//
//                            FileOutputStream fos = null;
//
//                            File fileDir = new File(filePath);
//                            if (!fileDir.exists()) {
//                                fileDir.mkdirs();
//                            }
//                            File destFile = new File(filePath + fileName);
//
//                            try {
//                                fos = new FileOutputStream(destFile);
//                                fos.write(buff);
//                                fos.close();
//                            } catch (IOException e) {
//                                log.error("Exception position : FileUtil - binaryToFile(String binaryFile, String filePath, String fileName)");
//                            }
//
//                            return destFile;
//                        }
                        try {
                            resource = new ByteArrayResource(multipartFile.getBytes()){
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
                        value = new PropertyDescriptor(field.getName(), object.getClass()).getReadMethod().invoke(object);
                        mvm.add(_name, value);
                    }
//            }
                }
            }


            return mvm;
        }

        private MultiValueMap addMultiValueFromIterable( /*NoSuchMethodException, IntrospectionException, IllegalAccessException, InvocationTargetException */
                MultiValueMap multiValueMap, String name, Iterable iterable) throws Exception {
            MultiValueMap mvm = multiValueMap;

            int i = 0;
            for (Object object : iterable) {
                String _name = name + "[" + i + "]";
                if (object instanceof Map) {
                    mvm = this.addMultiValueFromMap(mvm, _name, (Map) object);
                } else if (object instanceof Iterable) {
                    mvm = this.addMultiValueFromIterable(mvm, _name, (Iterable) object);
                } else {
                    mvm = this.addMultiValueFromBean(mvm, _name, object);
                    i++;
                }

            }
            return mvm;
        }

        private MultiValueMap addMultiValueFromMap(MultiValueMap multiValueMap, String name, Map map) {
            MultiValueMap mvm = multiValueMap;
            Set<String> keys = map.keySet();

            for (String key : keys) {
                String _name = name + "." + key;

                Object value = map.get(key);
                if (value instanceof Map) {
                    mvm = this.addMultiValueFromMap(mvm, _name, (Map) value);
                } else if (value instanceof Iterable) {
                } else {
                    mvm.add(_name, value);
                }
            }

            return mvm;
        }
    }
}
