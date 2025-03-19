<template>
  <div>
    <div class="search">
      <el-input placeholder="Enter invoice number" style="width: 200px" v-model="invoiceNumber"></el-input>
      <el-button type="info" plain style="margin-left: 10px" @click="load(1)">Search</el-button>
      <el-button type="warning" plain style="margin-left: 10px" @click="reset">Reset</el-button>
    </div>

    <div class="operation">
      <el-button type="primary" plain @click="handleAdd">Add</el-button>
      <el-button type="danger" plain @click="delBatch">Batch Delete</el-button>
    </div>

    <div class="table">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"></el-table-column>
        <el-table-column prop="invoiceId" label="Invoice ID" width="80" align="center" sortable></el-table-column>
        <el-table-column prop="invoiceNumber" label="Invoice Number" show-overflow-tooltip></el-table-column>
        <el-table-column prop="issueDate" label="Issue Date">
          <template v-slot="scope">
            {{ scope.row.issueDate ? scope.row.issueDate.split("T")[0] : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="supplierName" label="Supplier"></el-table-column>
        <el-table-column prop="customerName" label="Customer"></el-table-column>
        <el-table-column label="Items">
          <template v-slot="scope">
            {{ scope.row.items ? JSON.parse(scope.row.items).item + " - ¥" + JSON.parse(scope.row.items).price : "No data" }}
          </template>
        </el-table-column>
        <el-table-column prop="totalAmount" label="Total Amount"></el-table-column>
        <el-table-column prop="gstIncluded" label="GST Included">
          <template v-slot="scope">
            {{ scope.row.gstIncluded ? "Yes" : "No" }}
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="220">
          <template v-slot="scope">
            <!-- 发送邮件按钮 -->
            <el-button type="primary" size="mini" @click="sendInvoice(scope.row)">Send Email</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="pagination">
      <el-pagination
          background
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="Invoice Information" :visible.sync="fromVisible" width="40%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="100px" style="padding-right: 50px" :model="form" ref="formRef">
        <el-form-item prop="invoiceNumber" label="Invoice Number">
          <el-input v-model="form.invoiceNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="issueDate" label="Issue Date">
          <el-date-picker v-model="form.issueDate" type="date" placeholder="Select Date"></el-date-picker>
        </el-form-item>
        <el-form-item prop="supplierName" label="Supplier">
          <el-input v-model="form.supplierName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="customerName" label="Customer">
          <el-input v-model="form.customerName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item prop="totalAmount" label="Total Amount">
          <el-input v-model="form.totalAmount" type="number"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">Cancel</el-button>
        <el-button type="primary" @click="save">Confirm</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Notice",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      invoiceNumber: null,
      fromVisible: false,
      form: {},
      ids: []
    }
  },
  created() {
    this.load(1);
  },
  methods: {
    // 加载数据
    load(pageNum) {
      if (pageNum) this.pageNum = pageNum;
      this.$request.get('/notice/selectPage', {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          invoiceNumber: this.invoiceNumber,
        }
      }).then(res => {
        console.log("API Response:", res.data);
        this.tableData = res.data?.list || [];
        this.total = res.data?.total || 0;
      });
    },
    reset() {
      this.invoiceNumber = null;
      this.load(1);
    },
    handleCurrentChange(pageNum) {
      this.load(pageNum);
    },
    handleSelectionChange(rows) {
      this.ids = rows.map(row => row.invoiceId);
    },
    handleAdd() {
      this.form = {};
      this.fromVisible = true;
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request({
            url: this.form.invoiceId ? '/notice/update' : '/notice/add',
            method: this.form.invoiceId ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200') {
              this.$message.success('Saved successfully');
              this.load(1);
              this.fromVisible = false;
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },
    // 发送邮件功能
    sendInvoice(row) {
      this.$prompt('Please enter email', 'Send Email', {
        confirmButtonText: 'confirm',
        cancelButtonText: 'cancel',
        inputPattern: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
        inputErrorMessage: 'The mailbox format is incorrect'
      }).then(({ value }) => {
        const url = `/invoice/sendInvoiceEmail?invoiceId=${row.invoiceId}&email=${encodeURIComponent(value)}`;
        this.$request.post(url).then(res => {
          this.$message.success(res.data);
        }).catch(() => {
          this.$message.error('Mail sending error');
        });
      }).catch(() => {});
    },
    delBatch() {
      if (!this.ids.length) {
        this.$message.warning("Please select invoices to delete");
        return;
      }
      this.$confirm("Are you sure you want to delete these invoices?", "Confirm Deletion", { type: "warning" })
          .then(() => {
            this.$request.delete("/notice/delete/batch", { data: this.ids }).then(res => {
              if (res.code === "200") {
                this.$message.success("Batch deletion successful");
                this.load(1);
              } else {
                this.$message.error(res.msg);
              }
            });
          })
          .catch(() => {});
    }
  }
}
</script>

<style scoped>
</style>
