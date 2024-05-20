<template>
  <v-navigation-drawer v-model="Sidebar_drawer" :dark="SidebarColor !== 'white'" :color="SidebarColor"
    mobile-break-point="960" clipped :right="$vuetify.rtl" mini-variant-width="70" :expand-on-hover="expandOnHover" app
    id="main-sidebar">
    <v-list dense nav>
      <!---User Area -->
      <v-list-item two-line class="px-0">
        <v-list-item-avatar>
          <img src="../../assets/Logos/Ludo_logo.jpeg" />
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title>Karni Ludo Club</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <!---User Area -->
      <!-- Render "Dashboard" item -->
      <v-list-item :to="dashboardItem.to" :active-class="itemActiveClass(dashboardItem)" link
          @click="handleMenuItemClick(dashboardItem)">
        <v-list-item-icon>
          <v-icon>{{ dashboardItem.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>{{ dashboardItem.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <!-- Render "Users" item -->
      <v-list-item :to="usersItem.to" :active-class="itemActiveClass(usersItem)" link
          @click="handleMenuItemClick(usersItem)">
        <v-list-item-icon>
          <v-icon>{{ usersItem.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>{{ usersItem.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <!---Sidebar Items -->
      <template v-for="(item, index) in filteredItems">
        <!-- Skip "Dashboard" and "Users" items -->
        <template v-if="!item.isSpecial">
          <!-- Dropdown items -->
          <v-list-group :key="`dropdown-${index}`" :prepend-icon="item.icon">
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title>{{ item.title }}</v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item v-for="(child, childIndex) in item.children" :key="childIndex" :to="child.to" link
              @click="handleMenuItemClick(child)">
              <v-list-item-action>
                <v-icon>{{ child.icon }}</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>{{ child.title }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>
        </template>
      </template>
      <!---Sidebar Items -->
      <v-list-item :to="referralItem.to" :active-class="itemActiveClass(referralItem)" link
          @click="handleMenuItemClick(referralItem)">
        <v-list-item-icon>
          <v-icon>{{ referralItem.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>{{ referralItem.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <v-list-item :to="logoutItem.to" :active-class="itemActiveClass(logoutItem)" link
          @click="handleMenuItemClick(logoutItem)">
        <v-list-item-icon>
          <v-icon>{{ logoutItem.icon }}</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-list-item-title>{{ logoutItem.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "Sidebar",
  props: {
    expandOnHover: {
      type: Boolean,
      default: false
    }
  },
  data: () => ({
    dashboardItem: {
      title: "Dashboard",
      icon: "mdi-view-dashboard",
      to: "#/admin/admin-dashboard",
      isSpecial: true // Flag for special items
    },
    usersItem: {
      title: "Users",
      icon: "mdi-account-multiple-plus",
      to: "#/admin/user-list",
      isSpecial: true // Flag for special items
    },
    items: [
      {
        title: "Games",
        icon: "mdi-dice-multiple",
        children: [
          {
            title: "All Games",
            to: "/admin/games/all"
          },
          {
            title: "Running",
            to: "/admin/games/ongoing"
          },
          {
            title: "Completed",
            to: "/admin/games/complete"
          },
          {
            title: "Pending",
            to: "/admin/games/pending"
          },
          {
            title: "Cancelled",
            to: "/admin/games/cancelled"
          }
        ]
      },
      {
        title: "Transactions",
        icon: "mdi-bank-transfer",
        children: [
          {
            title: "All",
            icon: "mdi-cash-sync",
            to: "/admin/transactions/all"
          },
          {
            title: "Credit",
            icon: "mdi-bank-plus",
            to: "/admin/transactions/credit"
          },
          {
            title: "Debit",
            icon: "mdi-bank-minus",
            to: "/admin/transactions/debit"
          }
        ]
      }
    ],
    referralItem: {
        title: "Referrals",
        icon: "mdi-account-multiple-plus",
        to: "#/admin/referral"
      },
    logoutItem: {
        title: "Logout",
        icon: "mdi-logout",
        to: "#/home"
      }
  }),
  computed: {
    ...mapState(["SidebarColor", "SidebarBg"]),
    Sidebar_drawer: {
      get() {
        return this.$store.state.Sidebar_drawer;
      },
      set(val) {
        this.$store.commit("SET_SIDEBAR_DRAWER", val);
      }
    },
    // Filter out "Dashboard" and "Users" items
    filteredItems() {
      return this.items.filter(item => !item.isSpecial);
    }
  },
  watch: {
    "$vuetify.breakpoint.smAndDown"(val) {
      this.$emit("update:expandOnHover", !val);
    }
  },
  methods: {
    itemActiveClass(item) {
      return this.$route.path === item.to ? 'success white--text' : '';
    },
    handleMenuItemClick(item) {
      if (item.title === 'Logout') {
        this.logout();
      }
    },
    ...mapActions(["logout"])
  }
};
</script>

<style lang="scss">
#main-sidebar {
  box-shadow: 1px 0 20px rgba(0, 0, 0, 0.08);
  -webkit-box-shadow: 1px 0 20px rgba(0, 0, 0, 0.08);

  .v-navigation-drawer__border {
    display: none;
  }

  .v-list {
    padding: 8px 15px;
  }

  .v-list-item {

    &__icon--text,
    &__icon:first-child {
      justify-content: center;
      text-align: center;
      width: 20px;
    }
  }
}
</style>
