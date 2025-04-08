<template>
  <div>
    <!-- 欢迎卡片 -->
    <div class="card welcome-card">
      Hello，{{ user?.name }}！Welcome to Brioche Express！
    </div>

    <!-- Announcement 列表 -->
    <div style="display: flex; margin: 10px 0">
      <div style="width: 100%" class="card announcement-card">
        <div class="announcement-title">Announcement list</div>
        <el-timeline reverse>
          <el-timeline-item
              v-for="item in notices"
              :key="item.id"
              :timestamp="item.time"
              placement="top"
          >
            <div class="notice-item-title">{{ item.title }}</div>
            <div class="notice-item-content">{{ item.content }}</div>
          </el-timeline-item>
        </el-timeline>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {
      user: JSON.parse(localStorage.getItem("xm-user") || "{}"),
      notices: [
        {
          id: 1,
          time: "2025-04-10",
          title: "Brioche Express v1.2.0 Released",
          content:
              "New batch import feature added, UI optimized, and PDF export bug fixed.",
        },
        {
          id: 2,
          time: "2025-04-08",
          title: "Scheduled Maintenance",
          content:
              "System maintenance on April 12th, 02:00–04:00. The service may be briefly unavailable.",
        },
        {
          id: 3,
          time: "2025-04-05",
          title: "Permissions Module Launched",
          content:
              "Admins can now assign granular roles and customize approval workflows.",
        },
        {
          id: 4,
          time: "2025-03-30",
          title: "Security Patch Applied",
          content:
              "Login and upload modules have been updated with the latest security patches.",
        },
      ],
    };
  },
};
</script>

<style scoped>
/* 公共卡片样式 */
.card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  font-family: "Helvetica Neue", Arial, sans-serif;
}

/* 欢迎卡片 */
.welcome-card {
  padding: 15px;
  font-size: 15px;
  font-weight: 500;
  color: #333;
  margin-bottom: 10px;
}

/* Announcement 卡片 */
.announcement-card {
  padding: 20px;
}

/* 标题 */
.announcement-title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 16px;
  color: #222;
}

/* 时间线项标题 */
.notice-item-title {
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

/* 时间线项内容 */
.notice-item-content {
  font-size: 12px;
  color: #555;
  margin-top: 4px;
}

/* Element UI 时间戳字体 */
.el-timeline-item__timestamp {
  font-size: 12px !important;
  color: #888 !important;
}
</style>
