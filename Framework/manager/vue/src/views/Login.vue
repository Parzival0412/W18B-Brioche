<template>
  <div class="login-container">
    <!-- 左侧大标题和背景图 -->
    <div class="login-left">
      <div class="brand-title">BRIOCHE<br />EXPRESS</div>
    </div>

    <!-- 右侧登录框 -->
    <div class="login-right">
      <div class="login-box">
        <div class="login-title">Login Details</div>
        <el-form :model="form" :rules="rules" ref="formRef">
          <el-form-item prop="username">
            <el-input
                placeholder="Username, email & phone number"
                v-model="form.username"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                placeholder="Password"
                show-password
                v-model="form.password"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <div class="login-options">
              <span></span>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" class="login-btn" @click="login">Login</el-button>
          </el-form-item>
          <el-form-item>
            <div class="register-text">
              Don’t have an account?
              <span class="link" @click="$router.push('/register')">Register</span>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      form: {role: "ADMIN"},
      rules: {
        username: [{required: true, message: "Please enter the account number", trigger: "blur"}],
        password: [{required: true, message: "Please enter the password.", trigger: "blur"}],
      },
    };
  },
  methods: {
    login() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.post("/login", this.form).then((res) => {
            if (res.code === "200") {
              localStorage.setItem("xm-user", JSON.stringify(res.data));
              this.$router.push("/");
              this.$message.success("Login successfully");
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },
  },
};
</script>

<style scoped>
.login-container {
  display: flex;
  height: 100vh;
  background-color: #fff;
}

.login-left {
  position: relative;
  flex: 1;
  background-image: url("@/assets/imgs/bg-left.jpg");
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.login-left::before {
  content: "";
  position: absolute;
  inset: 0; /* top: 0; bottom: 0; left: 0; right: 0; */
  background-color: white; /* 半透明白色遮罩 */
  opacity: 0.6; /* 越大越“淡”，你可以调成 0.3 ~ 0.8 */
  z-index: 1;
}

.brand-title {
  position: relative;
  z-index: 2;
  font-size: 110px;  /* 左边的 BRIOCHE EXPRESS 更大 */
  font-weight: 900;
  color: #000000;
  text-align: center;
  line-height: 1.2;
}

.login-right {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: white;
}

.login-box {
  width: 500px;         /* 登录框宽度更大 */
  background: #fff;
  padding: 40px;   /* 加大内边距，内容不挤 */
  border-radius: 14px;
  box-shadow: 0 8px 45px rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  font-size: 32px;       /* 登录标题字体更大 */
  font-weight: 700;
  margin-bottom: 30px;
  color: #000;
}

.el-form-item {
  margin-bottom: 22px;
}

.el-input__inner {
  font-size: 16px !important; /* 输入框文字更大 */
  padding: 12px 14px !important;
}

.login-btn {
  width: 100%;
  background-color: #000;
  border-color: #000;
  color: white;
  font-size: 18px;
  padding: 14px 0;
  border-radius: 6px;
}

.login-options {
  display: flex;
  justify-content: space-between;
  font-size: 14px;
  color: #666;
}

.forgot {
  color: #888;
  cursor: pointer;
}

.register-text {
  text-align: center;
  font-size: 15px;
  color: #666;
  margin-top: 10px;
}

.link {
  color: #2a60c9;
  cursor: pointer;
  margin-left: 5px;
  font-weight: 500;
}

</style>