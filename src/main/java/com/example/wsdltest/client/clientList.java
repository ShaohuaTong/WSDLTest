package com.example.wsdltest.client;

import com.example.wsdltest.pojo.User;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.soap.*;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.net.URL;

public class clientList {
    public static void main(String[] args) throws SOAPException, IOException, JAXBException {
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

        SOAPHeader header = envelope.getHeader();
        if (header == null) {
            header = envelope.addHeader();
        }
        QName headerQName = new QName(ns,"authInfo", "huahua");
        header.addHeaderElement(headerQName).setValue("Give me time");


        QName ename = new QName(ns, "list", "huahua"); //<huahua:add xmlns="ns">
        body.addBodyElement(ename);
        SOAPMessage response = dispatch.invoke(message);

        response.writeTo(System.out);
        System.out.println();

        Document document = response.getSOAPBody().extractContentAsDocument();
        NodeList nl = document.getElementsByTagName("user");
        JAXBContext ctx = JAXBContext.newInstance(User.class);

        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            User u = (User) ctx.createUnmarshaller().unmarshal(node);
            System.out.println(u.getName());
        }

    }
}
