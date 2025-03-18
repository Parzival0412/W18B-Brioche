package com.example.service;

import com.example.entity.Notice;
import com.example.mapper.InvoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceMapper invoiceMapper; // 数据库操作

    @Autowired
    private JavaMailSender mailSender; // 邮件发送

    /**
     * 通过 ID 查询发票
     */
    public Notice getInvoiceById(Integer invoiceId) {
        return invoiceMapper.selectById(invoiceId);
    }

    /**
     * 发送发票邮件
     */
    public boolean sendInvoiceByEmail(Integer invoiceId, String email) {
        // 1️⃣ 获取发票数据
        Notice notice = invoiceMapper.selectById(invoiceId);
        if (notice == null) {
            System.out.println("❌ Invoice not found");
            return false;
        }

        try {
            // 2️⃣ 创建邮件
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ywq603968915@163.com");  // 你的发件邮箱
            helper.setTo(email);
            helper.setSubject("Invoice: " + notice.getInvoiceNumber());
            helper.setText(buildInvoiceEmailContent(notice), true); // 发送 HTML 格式的邮件

            // 3️⃣ 发送邮件
            mailSender.send(message);
            System.out.println("✅ Invoice email sent successfully!");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 生成 HTML 格式的邮件内容
     */
    private String buildInvoiceEmailContent(Notice notice) {
        return "<h2>Invoice Details</h2>" +
                "<p><strong>Invoice Number:</strong> " + notice.getInvoiceNumber() + "</p>" +
                "<p><strong>Issue Date:</strong> " + notice.getIssueDate() + "</p>" +
                "<p><strong>Supplier:</strong> " + notice.getSupplierName() + " (" + notice.getSupplierAbn() + ")</p>" +
                "<p><strong>Customer:</strong> " + notice.getCustomerName() + "</p>" +
                "<p><strong>Total Amount:</strong> $" + notice.getTotalAmount() + "</p>" +
                "<p><strong>GST Included:</strong> " + (notice.getGstIncluded() ? "Yes" : "No") + "</p>";
    }
}
