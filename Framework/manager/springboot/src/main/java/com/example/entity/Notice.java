package com.example.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 公告信息表
*/
public class Notice implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer invoiceId;        // 发票ID
    private String invoiceNumber;     // 发票编号
    private Date issueDate;           // 开票日期

    // 卖方信息
    private String supplierName;
    private String supplierAbn;
    private String supplierAddress;

    // 买方信息
    private String customerName;
    private String customerAddress;

    // 商品明细（存 JSON 串）
    private String items;

    // 总金额 & 是否含GST
    private BigDecimal totalAmount;
    private Boolean gstIncluded;

    // ============== Getter / Setter ==============
    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAbn() {
        return supplierAbn;
    }

    public void setSupplierAbn(String supplierAbn) {
        this.supplierAbn = supplierAbn;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Boolean getGstIncluded() {
        return gstIncluded;
    }

    public void setGstIncluded(Boolean gstIncluded) {
        this.gstIncluded = gstIncluded;
    }

}