<template>
  <div class="manager-container">
    <!--  头部  -->
    <div class="manager-header">
      <div class="manager-header-left">
        <img src="@/assets/imgs/logo.png" />
        <div class="title-wrap">
          <div class="title-line1">Brioche</div>
          <div class="title-line2">Express</div>
        </div>
      </div>

      <div class="manager-header-center">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }"
            ></el-breadcrumb-item
          >
          <el-breadcrumb-item :to="{ path: $route.path }">{{
            $route.meta.name
          }}</el-breadcrumb-item>
        </el-breadcrumb>
      </div>

      <div class="manager-header-right">
          <div class="avatar">
            <img
              :src="
                user.avatar ||
                'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
              "
            />
            <div>{{ user.name || "administrator" }}</div>
          </div>
      </div>

    </div>

    <!-- 主体 -->
    <div class="manager-main">
      <!-- 左侧菜单栏 -->
      <div class="manager-main-left">
        <div class="menu-wrapper">
          <el-menu
              :default-openeds="['setting']"
              router
              style="border: none"
              :default-active="$route.path"
          >
            <el-menu-item index="/home">
              <i class="el-icon-s-home"></i>
              <span slot="title">Home</span>
            </el-menu-item>

            <el-menu-item index="/notice">
              <i class="el-icon-document"></i>
              <span slot="title">Invoice</span>
            </el-menu-item>

            <el-menu-item index="/admin">
              <i class="el-icon-menu"></i>
              <span slot="title">User information</span>
            </el-menu-item>

            <el-submenu index="setting">
              <template slot="title">
                <i class="el-icon-setting"></i>
                <span>Setting</span>
              </template>
              <el-menu-item index="/person">Personal info</el-menu-item>
              <el-menu-item index="/password">Change password</el-menu-item>
            </el-submenu>
          </el-menu>
        </div>

        <!-- ✅ 固定底部 logout -->
        <div class="logout-container" @click="logout">
          <i class="el-icon-switch-button" style="margin-right: 6px;"></i>
          <span>Logout</span>
        </div>
      </div>


      <!--  数据表格  -->
      <div class="manager-main-right">
        <router-view @update:user="updateUser" />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Manager",
  data() {
    return {
      user: JSON.parse(localStorage.getItem("xm-user") || "{}"),
    };
  },
  created() {
    if (!this.user.id) {
      this.$router.push("/login");
    }
  },
  methods: {
    updateUser() {
      this.user = JSON.parse(localStorage.getItem("xm-user") || "{}"); // 重新获取下用户的最新信息
    },
    goToPerson() {
      if (this.user.role === "ADMIN") {
        this.$router.push("/adminPerson");
      }
    },
    logout() {
      localStorage.removeItem("xm-user");
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
@import "@/assets/css/manager.css";
</style>
