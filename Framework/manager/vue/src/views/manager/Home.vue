<template>
  <div class="dashboard-container">
    <!-- Welcome Header -->
    <div class="welcome-card">
      👋 Hello, {{ user?.name || "User" }}! Welcome to the dashboard.
    </div>

    <!-- Main Grid -->
    <div class="grid">
      <!-- Announcement List -->
      <div class="card">
        <h2 class="card-title">📢 Announcement</h2>
        <el-timeline reverse>
          <el-timeline-item
            v-for="item in notices"
            :key="item.id"
            :timestamp="item.time"
            placement="top"
          >
            <el-popover
              placement="right"
              width="250"
              trigger="hover"
              :content="item.content"
            >
              <span class="notice-item" slot="reference">{{ item.title }}</span>
            </el-popover>
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
      notices: [],
    };
  },
  created() {
    this.$request.get("/notice/selectAll").then((res) => {
      this.notices = res.data || [];
    });
  },
};
</script>

<style scoped>
.dashboard-container {
  padding: 30px;
  background: rgba(45, 49, 67, 0.95);
  min-height: 100vh;
  font-family: "Inter", "Segoe UI", sans-serif;
  color: #e5e5e5;
}
.welcome-card {
  background: #ffffff;
  padding: 24px 28px;
  border-radius: 16px;
  font-size: 20px;
  font-weight: 600;
  color: #1f2937;
  box-shadow: 0 6px 24px rgba(0, 0, 0, 0.05);
  margin-bottom: 30px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}
.welcome-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.1);
}

.grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(420px, 1fr));
  gap: 28px;
}

.card {
  background: #ffffff;
  padding: 24px;
  border-radius: 14px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease-in-out;
}
.card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.12);
}

.card-title {
  font-size: 22px;
  font-weight: 700;
  color: #1e293b;
  margin-bottom: 18px;
  display: flex;
  align-items: center;
  gap: 10px;
}

.notice-item {
  font-size: 16px;
  color: #374151;
  cursor: pointer;
  transition: all 0.25s ease;
  position: relative;
}
.notice-item::before {
  content: "";
  position: absolute;
  left: -6px;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 4px;
  background-color: #60a5fa;
  border-radius: 50%;
  opacity: 0;
  transition: all 0.25s ease;
}
.notice-item:hover {
  color: #2563eb;
  font-weight: 500;
}
.notice-item:hover::before {
  opacity: 1;
}
</style>
