<template>
  <div>
    <el-card style="width: 50%">
      <el-form
        ref="formRef"
        :model="user"
        :rules="rules"
        label-width="100px"
        style="padding-right: 50px"
      >
        <el-form-item label="Current Password" prop="password">
          <el-input
            show-password
            v-model="user.password"
            placeholder="Enter current password"
          ></el-input>
        </el-form-item>
        <el-form-item label="New Password" prop="newPassword">
          <el-input
            show-password
            v-model="user.newPassword"
            placeholder="Enter new password"
          ></el-input>
        </el-form-item>
        <el-form-item label="Confirm New Password" prop="confirmPassword">
          <el-input
            show-password
            v-model="user.confirmPassword"
            placeholder="Confirm new password"
          ></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">Confirm Change</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Password",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("Please confirm your password"));
      } else if (value !== this.user.newPassword) {
        callback(new Error("Passwords do not match"));
      } else {
        callback();
      }
    };

    return {
      user: JSON.parse(localStorage.getItem("xm-user") || "{}"),
      rules: {
        password: [
          {
            required: true,
            message: "Please enter your current password",
            trigger: "blur",
          },
        ],
        newPassword: [
          {
            required: true,
            message: "Please enter a new password",
            trigger: "blur",
          },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: "blur" },
        ],
      },
    };
  },
  created() {},
  methods: {
    update() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.put("/updatePassword", this.user).then((res) => {
            if (res.code === "200") {
              // Successfully updated
              localStorage.removeItem("xm-user"); // Clear cached user information
              this.$message.success("Password changed successfully");
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
.el-card {
  background: #2f334d !important;
  border-radius: 16px;
  padding: 30px;
  color: #f3f4f6;
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.25);
  transition: all 0.3s ease-in-out;
}
.el-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 16px 32px rgba(0, 0, 0, 0.35);
}

.el-form-item__label {
  font-weight: 600;
  color: #cbd5e1;
}

.el-input__inner {
  background: #1f2236;
  border: 1px solid #3e4c7a;
  color: #fff;
  border-radius: 6px;
  transition: all 0.3s ease;
}
.el-input__inner:focus {
  border-color: #60a5fa;
  box-shadow: 0 0 6px rgba(96, 165, 250, 0.5);
}
.el-input__inner::placeholder {
  color: #94a3b8;
}

.el-button {
  background-color: #5c7cfa;
  border: none;
  color: #fff;
  font-weight: 600;
  padding: 12px 24px;
  border-radius: 8px;
  transition: all 0.2s ease;
}
.el-button:hover {
  background-color: #4f69dc;
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(92, 124, 250, 0.3);
}
</style>
