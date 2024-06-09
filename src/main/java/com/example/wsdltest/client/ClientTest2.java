package com.example.wsdltest.client;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class ClientTest2 {
    public static void main(String[] args) throws Exception {
        String wsdlUrl = "http://localhost:8080/services/UserService?wsdl";
        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        Client client = clientFactory.createClient(wsdlUrl);

        Object[] result = client.invoke("echo", "hello");
        System.out.println(result[0]);
    }
}
