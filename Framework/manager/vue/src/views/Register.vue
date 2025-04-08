<template>
  <div class="login-container">
    <!-- 左侧大标题和背景图 -->
    <div class="login-left">
      <div class="brand-title">BRIOCHE<br />EXPRESS</div>
    </div>

    <!-- 右侧注册框 -->
    <div class="login-right">
      <div class="login-box">
        <div class="login-title">Register Account</div>
        <el-form :model="form" :rules="rules" ref="formRef">
          <el-form-item prop="username">
            <el-input
                placeholder="Please enter account"
                v-model="form.username"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                placeholder="Please enter password"
                show-password
                v-model="form.password"
            ></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass">
            <el-input
                placeholder="Please confirm password"
                show-password
                v-model="form.confirmPass"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
                type="primary"
                class="login-btn"
                @click="register"
            >Register</el-button>
          </el-form-item>
          <el-form-item>
            <div class="register-text">
              Already have an account?
              <span class="link" @click="$router.push('/login')">Login</span>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error("Please confirm password"));
      } else if (value !== this.form.password) {
        callback(new Error("The passwords you entered do not match"));
      } else {
        callback();
      }
    };
    return {
      form: { role: "ADMIN" },
      rules: {
        username: [
          { required: true, message: "Please enter account", trigger: "blur" },
        ],
        password: [
          { required: true, message: "Please enter password", trigger: "blur" },
        ],
        confirmPass: [
          { validator: validatePassword, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    register() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.post("/register", this.form).then((res) => {
            if (res.code === "200") {
              this.$message.success("Registration successful");
              this.$router.push("/login");
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
  inset: 0;
  background-color: white;
  opacity: 0.6;
  z-index: 1;
}

.brand-title {
  position: relative;
  z-index: 2;
  font-size: 110px;
  font-weight: 900;
  color: #000;
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
  width: 500px;
  background: #fff;
  padding: 40px;
  border-radius: 14px;
  box-shadow: 0 8px 45px rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 30px;
  color: #333;
}

.el-form-item {
  margin-bottom: 22px;
}

.el-input__inner {
  font-size: 16px !important;
  padding: 12px 14px !important;
}

.login-btn {
  width: 100%;
  background-color: #000;
  border-color: #000;
  color: #fff;
  font-size: 18px;
  padding: 14px 0;
  border-radius: 6px;
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
