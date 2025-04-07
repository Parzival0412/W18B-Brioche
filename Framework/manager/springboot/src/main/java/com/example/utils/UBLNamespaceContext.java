package com.example.utils;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import java.util.Iterator;

public class UBLNamespaceContext implements NamespaceContext {

    @Override
    public String getNamespaceURI(String prefix) {
        switch (prefix) {
            case "cbc":
                return "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";
            case "cac":
                return "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
            default:
                return XMLConstants.NULL_NS_URI;
        }
    }

    @Override
    public String getPrefix(String namespaceURI) { return null; }

    @Override
    public Iterator getPrefixes(String namespaceURI) { return null; }
}