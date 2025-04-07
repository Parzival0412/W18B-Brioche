package com.example.controller;


import com.example.utils.UBLNamespaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;

import org.w3c.dom.NodeList; // 导入 NodeList 接口
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Node;


@RestController
@RequestMapping("/api/invoice")
public class InvoiceUploadController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/upload")
    public Map<String, String> uploadInvoice(@RequestParam("file") MultipartFile file) {
        Map<String, String> result = new HashMap<>();
        try {
            InputStream inputStream = file.getInputStream();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // 重要：使工厂支持命名空间
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            xpath.setNamespaceContext(new UBLNamespaceContext()); // 设置命名空间上下文

            // 解析字段
            result.put("invoiceNumber", xpath.evaluate("//cbc:ID", doc));
            result.put("issueDate", xpath.evaluate("//cbc:IssueDate", doc));
            result.put("supplierName", xpath.evaluate("//cac:AccountingSupplierParty/cac:Party/cac:PartyName/cbc:Name", doc));
            result.put("supplierAbn", xpath.evaluate("//cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme/cbc:CompanyID", doc));
            result.put("supplierAddress", xpath.evaluate("//cac:AccountingSupplierParty/cac:Party/cac:Contact/cbc:ElectronicMail", doc));
            result.put("customerName", xpath.evaluate("//cac:AccountingCustomerParty/cac:Party/cac:PartyName/cbc:Name", doc));
            result.put("customerAddress", xpath.evaluate("//cac:AccountingCustomerParty/cac:Party/cac:Contact/cbc:ElectronicMail", doc));
            //result.put("supplierAddress", extractAddress(xpath, doc, "//cac:AccountingSupplierParty/cac:Party/cac:PostalAddress"));
            //result.put("customerAddress", extractAddress(xpath, doc, "//cac:AccountingCustomerParty/cac:Party/cac:PostalAddress"));
            result.put("totalAmount", xpath.evaluate("//cac:LegalMonetaryTotal/cbc:PayableAmount", doc));    //剩下应付的金额（TaxExclusiveAmount-PrepaidAmount）

            // 检查是否含税 GST
            String taxAmount = xpath.evaluate("//cac:TaxTotal/cbc:TaxAmount", doc);
            result.put("gstIncluded", taxAmount != null && !taxAmount.isEmpty() ? "true" : "false");

            // 解析商品明细
            NodeList invoiceLines = (NodeList) xpath.evaluate("//cac:InvoiceLine", doc, XPathConstants.NODESET);
            JSONArray itemsArray = new JSONArray();
            for (int i = 0; i < invoiceLines.getLength(); i++) {
                Node line = invoiceLines.item(i);
                String name = xpath.evaluate("cac:Item/cbc:Name", line);
                String quantity = xpath.evaluate("cbc:InvoicedQuantity", line);
                String unitCode = xpath.evaluate("cbc:InvoicedQuantity/@unitCode", line);
                String lineAmount = xpath.evaluate("cbc:LineExtensionAmount", line);

                JSONObject item = new JSONObject();
                //item.put("name", name);
                //item.put("quantity", quantity);
                //item.put("unitCode", unitCode);
                //item.put("lineExtensionAmount", lineAmount);
                item.put("items", name + " x" + quantity + " (" + lineAmount + ")");

                itemsArray.put(item);
            }
            result.put("items", itemsArray.toString());

            // 插入数据库
            String sql = "INSERT INTO notice (" +
                    "invoice_id, issue_date, supplier_name, supplier_abn, supplier_email, " +
                    "customer_name, customer_email, total_amount, gst_included, items_summary" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql,
                    result.get("invoiceNumber"),
                    result.get("issueDate"),
                    result.get("supplierName"),
                    result.get("supplierAbn"),
                    result.get("supplierAddress"),
                    result.get("customerName"),
                    result.get("customerAddress"),
                    new BigDecimal(result.get("totalAmount")),
                    "true".equals(result.get("gstIncluded")) ? 1 : 0,
                    result.get("items")
            );

        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", "解析失败: " + e.getMessage());
        }
        return result;
    }
}