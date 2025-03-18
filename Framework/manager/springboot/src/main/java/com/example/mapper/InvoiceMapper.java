package com.example.mapper;

import com.example.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InvoiceMapper {

    /**
     * 通过 ID 查询发票信息
     */
    @Select("SELECT * FROM notice WHERE invoice_id = #{invoiceId}")
    Notice selectById(Integer invoiceId);
}
