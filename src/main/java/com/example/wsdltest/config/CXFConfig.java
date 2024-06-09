package com.example.wsdltest.config;

import com.example.wsdltest.service.IUserService;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CXFConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private IUserService userService;

//    @Bean
//    public ServletRegistrationBean cxfServlet() {
//        return new ServletRegistrationBean(new CXFServlet(), "/shaohua/*"); //webservice 访问的父路径
//    }

    @Bean
    public Endpoint getEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, userService);
        endpoint.publish("/UserService");
        return endpoint;
    }
}
