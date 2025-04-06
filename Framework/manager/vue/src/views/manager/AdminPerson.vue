<template>
  <div>
    <el-card style="width: 50%">
      <el-form :model="user" label-width="100px" style="padding-right: 50px">
        <div style="margin: 15px; text-align: center">
          <el-upload
            class="avatar-uploader"
            :action="$baseUrl + '/files/upload'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
          >
            <img v-if="user.avatar" :src="user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="Username" prop="username">
          <el-input
            v-model="user.username"
            placeholder="Username"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="Name" prop="name">
          <el-input v-model="user.name" placeholder="Name"></el-input>
        </el-form-item>
        <el-form-item label="Phone" prop="phone">
          <el-input v-model="user.phone" placeholder="Phone"></el-input>
        </el-form-item>
        <el-form-item label="Email" prop="email">
          <el-input v-model="user.email" placeholder="Email"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">Save</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "AdminPerson",
  data() {
    return {
      user: JSON.parse(localStorage.getItem("xm-user") || "{}"),
    };
  },
  created() {},
  methods: {
    update() {
      // Save the current user information to the database
      this.$request.put("/admin/update", this.user).then((res) => {
        if (res.code === "200") {
          // Successfully updated
          this.$message.success("Save successful");

          // Update the cached user information in the browser
          localStorage.setItem("xm-user", JSON.stringify(this.user));

          // Trigger data update in the parent component
          this.$emit("update:user");
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    handleAvatarSuccess(response, file, fileList) {
      // Update the user's avatar with the uploaded image URL
      this.$set(this.user, "avatar", response.data);
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

.avatar-uploader {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #9ca3af;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
  background-color: #1f2236;
  border: 2px dashed #4b5563;
  transition: border-color 0.3s ease;
}
.avatar-uploader-icon:hover {
  border-color: #5c7cfa;
  box-shadow: 0 0 10px rgba(92, 124, 250, 0.5);
}
.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  border: 2px solid #5c7cfa;
  box-shadow: 0 0 12px rgba(92, 124, 250, 0.4);
  transition: all 0.3s ease-in-out;
}
.avatar:hover {
  transform: scale(1.05);
  box-shadow: 0 0 20px rgba(92, 124, 250, 0.6);
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
