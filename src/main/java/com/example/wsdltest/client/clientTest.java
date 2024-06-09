package com.example.wsdltest.client;

import jakarta.xml.soap.*;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.net.URL;

public class clientTest {
    public static void main(String[] args) throws SOAPException, IOException {
        String ns = "service.wsdltest.example.com";
        String wsdlUrl = "http://localhost:8080/services/UserService?wsdl";

        URL url = new URL(wsdlUrl);
        QName sname = new QName(ns, "UserService");
        Service service = Service.create(url, sname);
        Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(ns,"UserServiceImplPort"),
                SOAPMessage.class, Service.Mode.MESSAGE);

        SOAPMessage message = MessageFactory.newInstance().createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        SOAPBody body = envelope.getBody();

        QName ename = new QName(ns, "add", "huahua"); //<huahua:add xmlns="ns">
        //&lt;a&gt;1&lt;/a&gt;&lt;b&gt;2&lt;/b&gt;
        //body.addBodyElement(qname).setValue("<a>1</a><b>2</b>");
        SOAPBodyElement element = body.addBodyElement(ename);
        element.addChildElement("a").setValue("38");
        element.addChildElement("b").setValue("20");

        message.writeTo(System.out);
        SOAPMessage reply = dispatch.invoke(message);
        System.out.println();
        reply.writeTo(System.out);

        Document document = reply.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
        String str = document.getElementsByTagName("addResult").item(0).getTextContent();
        System.out.println("\n" + str);
    }
}
