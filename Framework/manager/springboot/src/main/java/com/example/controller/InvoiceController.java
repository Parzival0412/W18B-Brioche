package com.example.controller;

import com.example.entity.Notice;
import com.example.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     * 通过 invoiceId 获取发票详情
     */
    @GetMapping("/getInvoiceById")
    public Notice getInvoiceById(@RequestParam Integer invoiceId) {
        return invoiceService.getInvoiceById(invoiceId);
    }

    /**
     * 发送发票邮件
     */
    @PostMapping("/sendInvoiceEmail")
    public String sendInvoiceEmail(@RequestParam Integer invoiceId, @RequestParam String email) {
        boolean success = invoiceService.sendInvoiceByEmail(invoiceId, email);
        return success ? "Invoice sent successfully to " + email : "Failed to send invoice";
    }
}
