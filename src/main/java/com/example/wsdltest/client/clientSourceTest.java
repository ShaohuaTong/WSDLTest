package com.example.wsdltest.client;

import com.example.wsdltest.pojo.User;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.soap.*;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.ws.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

public class clientSourceTest {
    public static void main(String[] args) throws SOAPException, IOException, JAXBException, TransformerException, XPathExpressionException {
        String ns = "service.wsdltest.example.com";
        String wsdlUrl = "http://localhost:8080/services/UserService?wsdl";

        URL url = new URL(wsdlUrl);
        QName sname = new QName(ns, "UserService");
        Service service = Service.create(url, sname);
        Dispatch<Source> dispatch = service.createDispatch(new QName(ns,"UserServiceImplPort"),
                Source.class, Service.Mode.PAYLOAD);

        User user = new User(2, "hua", "123123");
        JAXBContext ctx = JAXBContext.newInstance(User.class);
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        StringWriter writer = new StringWriter();
        marshaller.marshal(user, writer);

        System.out.println(writer);

        String payload = "<tns:addUser xmlns:tns=\""+ns+"\">"+ writer +"</tns:addUser>";
        System.out.println(payload);

        StreamSource streamSource = new StreamSource(new StringReader(payload));
        Source response =  dispatch.invoke(streamSource);

        System.out.println(response.toString());

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMResult domResult = new DOMResult();
        transformer.transform(response, domResult);

        System.out.println(domResult);

        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nl = (NodeList) xPath.evaluate("//user", domResult.getNode(), XPathConstants.NODESET);
        User user2 = (User) ctx.createUnmarshaller().unmarshal(nl.item(0));

        System.out.println(user2.getName());
    }
}
