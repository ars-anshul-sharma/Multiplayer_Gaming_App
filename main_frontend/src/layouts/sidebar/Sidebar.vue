<template>
  <v-navigation-drawer
    v-model="Sidebar_drawer"
    :dark="SidebarColor !== 'white'"
    :color="SidebarColor"
    mobile-break-point="960"
    clipped
    :right="$vuetify.rtl"
    mini-variant-width="70"
    :expand-on-hover="expandOnHover"
    app
    id="main-sidebar"
  >
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
      <!---Sidebar Items -->
      <v-list-item
        v-for="item in items"
        :key="item.title"
        :to="item.to"
        :active-class="itemActiveClass(item)"
        link
        @click="handleMenuItemClick(item)"
      >
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <!---Sidebar Items -->
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
    items: [
      {
        title: "Dashboard",
        icon: "mdi-view-dashboard",
        to: "/dashboard"
      },
      {
        title: "Game History",
        icon: "mdi-dice-multiple",
        to: "/dashboard/games"
      },
      {
        title: "Transactions History",
        icon: "mdi-bank-transfer",
        to: "/dashboard/transactions"
      },
      {
        title: "My Wallet",
        icon: "mdi-wallet",
        to: "/dashboard/wallet"
      },
      {
        title: "Profile",
        icon: "mdi-account-circle",
        to: "/dashboard/profile"
      },
      {
        title: "Refer & Earn",
        icon: "mdi-account-cash",
        to: "/dashboard/referral"
      },
      {
        title: "Logout",
        icon: "mdi-logout",
        to: "/home"
      }
    ]
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
main-sidebar {
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
