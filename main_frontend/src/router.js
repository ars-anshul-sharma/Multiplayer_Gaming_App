// router.js
import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

const router = new Router({
  mode: "hash",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      redirect: "/home",
    },
    {
      path: "/home",
      component: () => import("@/layouts/HomeLayout"),
      children: [
        {
          path: "",
          name: "Home",
          component: () => import("@/views/home/Home"),
        },
        {
          path: "register",
          name: "Register",
          component: () => import("@/views/home/Register"),
        },
        {
          path: "login",
          name: "Login",
          component: () => import("@/views/home/Login"),
        },
      ],
    },
    {
      path: "/dashboard",
      component: () => import("@/layouts/Layout"),
      children: [
        {
          path: "",
          name: "Dashboard",
          component: () => import("@/views/dashboard/BasicDashboard"),
          meta: { requiresAuth: true },
        },
        {
          path: "set_game",
          name: "Game_Status",
          component: () => import("@/views/pages/Game_Status"),
          meta: { requiresAuth: true },
        },
        {
          path: "profile",
          name: "Profile",
          component: () => import("@/views/pages/Profile"),
          meta: { requiresAuth: true },
        },
        {
          path: "icons",
          name: "Icons",
          component: () => import("@/views/pages/Icons"),
          meta: { requiresAuth: true },
        },
        {
          path: "tables-simple",
          name: "TableSimple",
          component: () => import("@/views/pages/TableSimple"),
          meta: { requiresAuth: true },
        },
        {
          path: "wallet",
          name: "Wallet",
          component: () => import("@/views/pages/Wallet"),
          meta: { requiresAuth: true },
        },
        {
          path: "games",
          name: "Games",
          component: () => import("@/views/pages/Games"),
          meta: { requiresAuth: true },
        },
        {
          path: "transactions",
          name: "Transactions",
          component: () => import("@/views/pages/Transactions"),
          meta: { requiresAuth: true },
        },
        {
          path: "referral",
          name: "Referral",
          component: () => import("@/views/pages/Referral"),
          meta: { requiresAuth: true },
        },
      ],
    },
    {
      path: "/admin-login",
      name: "AdminLogin",
      component: () => import("@/views/home/AdminLogin"),
  },
  {
      path: "/admin",
      component: () => import("@/layouts/AdminLayout"),
      meta: { requiresAuth: true },
      children: [
        {
          path: "admin-dashboard",
          name: "AdminDashboard",
          component: () => import("@/views/dashboard/AdminDashboard"),
          meta: { requiresAuth: true },
        },
        {
          path: "user-list",
          name: "UserList",
          component: () => import("@/views/pages/admin/UserData"),
          meta: { requiresAuth: true },
        },
        {
          path: "games",
          redirect: "games/all",
          meta: { requiresAuth: true },
        },
        {
          path: "games/:status",
          name: "Games",
          component: () => import("@/views/pages/admin/AdminGames"),
          props: true,
          meta: { requiresAuth: true },
        },
        {
          path: "transactions",
          redirect: "transactions/all",
          meta: { requiresAuth: true },
        },
        {
          path: "transactions/:status",
          name: "Transactions",
          component: () => import("@/views/pages/admin/AdminTransactions"),
          props: true,
          meta: { requiresAuth: true },
        },
      ],
    },
  ],
});

import store from "./store";

router.beforeEach((to, from, next) => {
  
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);
  const isAuthenticated = store.state.isAuthenticated;

  if (requiresAuth && !isAuthenticated) {
    // If authentication is required and the user is not logged in, redirect to login page
    next("/home/login");
  } else if (to.path.startsWith("/admin/") && store.state.currentUserRole !== "ADMIN") {
    next("/home");
  } else {
    // If authentication is not required or the user is logged in, proceed to the next route
    next();
  }
});

export default router;
