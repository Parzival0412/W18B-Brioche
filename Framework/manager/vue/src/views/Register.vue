<template>
  <div class="container">
    <div
      style="
        width: 400px;
        padding: 30px;
        background-color: white;
        border-radius: 5px;
      "
    >
      <div
        style="
          text-align: center;
          font-size: 20px;
          margin-bottom: 20px;
          color: #333;
        "
      >
        Welcome to register
      </div>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input
            prefix-icon="el-icon-user"
            placeholder="Please enter account"
            v-model="form.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            prefix-icon="el-icon-lock"
            placeholder="Please enter password"
            show-password
            v-model="form.password"
          ></el-input>
        </el-form-item>
        <el-form-item prop="confirmPass">
          <el-input
            prefix-icon="el-icon-lock"
            placeholder="Please confirm password"
            show-password
            v-model="form.confirmPass"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            style="
              width: 100%;
              background-color: #333;
              border-color: #333;
              color: white;
            "
            @click="register"
            >register</el-button
          >
        </el-form-item>
        <div style="display: flex; align-items: center">
          <div style="flex: 1"></div>
          <div style="flex: 1; text-align: right">
            Already have an account? <a href="/login">log in</a>
          </div>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    // 验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === "") {
        callback(new Error("Please confirm password"));
      } else if (confirmPass !== this.form.password) {
        callback(new Error("The passwords you entered twice do not match"));
      } else {
        callback();
      }
    };
    return {
      form: {},
      rules: {
        username: [
          {
            required: true,
            message: "Please enter account number",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "Please enter password", trigger: "blur" },
        ],
        confirmPass: [{ validator: validatePassword, trigger: "blur" }],
      },
    };
  },
  created() {},
  methods: {
    /*register() {
      this.$refs["formRef"].validate((valid) => {
        if (valid) {
          // 验证通过
          this.$request.post("/register", this.form).then((res) => {
            if (res.code === "200") {
              this.$router.push("/"); // 跳转登录页面
              this.$message.success("Registration successful");
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },*/
    register() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          const { username, password } = this.form;
          this.$request.post("/register", {
            username,
            password,
            role: "ADMIN"  // 新增这行，跟后端的 RoleEnum.ADMIN 对应
          }).then((res) => {
            if (res.code === "200") {
              this.$message.success("Registration successful");
              this.$router.push("/login");
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    }
  },
};
</script>

<style scoped>
.container {
  height: 100vh;
  overflow: hidden;
  background-image: url("@/assets/imgs/bg1.jpg");
  background-size: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
}
a {
  color: #2a60c9;
}
</style>
