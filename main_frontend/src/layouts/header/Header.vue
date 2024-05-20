<template>
  <v-app-bar app clipped-left clipped-right color="primary" dark>
    <v-avatar :tile="true">
      <img src="../../assets/Logos/Ludo_logo.jpeg" alt="logo" />
    </v-avatar>
    <v-toolbar-title class="align-center d-flex">
      <span class="logo-text ml-2">Karni Ludo Club</span>
    </v-toolbar-title>
    <!-- Add spacer to push wallet balance to the right -->

    <v-app-bar-nav-icon
      class="d-block d-md-none"
      @click="
        $vuetify.breakpoint.smAndDown
          ? setSidebarDrawer(!Sidebar_drawer)
          : $emit('input', !value)
      "
    />
    <v-spacer></v-spacer>
    <v-icon class="mr-2">mdi-wallet</v-icon>
    <!-- Wallet Icon -->
    <span>{{ walletBalance }}</span>
    <!-- Display Wallet Balance -->
    <v-menu bottom left transition="scale-transition">
      <template v-slot:activator="{ on }">
        <v-btn dark icon v-on="on">
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </template>

      <v-list>
        <v-list-item
          v-for="(item, i) in userprofile"
          :key="i"
          @click="navigateTo(item)"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import axios from "axios"; // Import axios library for HTTP requests

export default {
  name: "Header",

  props: {
    value: {
      type: Boolean,
      default: false,
    },
  },

  data: () => ({
    userprofile: [
      { title: "My Profile", route: "/dashboard/profile" },
      { title: "My Balance", route: "/dashboard/transactions" },
      { title: "Logout" },
    ],
    walletBalance: null, // Initialize wallet balance as null
  }),

  computed: {
    ...mapState(["Sidebar_drawer"]),
  },

  methods: {
    ...mapMutations({
      setSidebarDrawer: "SET_SIDEBAR_DRAWER",
    }),

    // Method to make API call to fetch wallet balance
    async fetchWalletBalance() {
      axios
        .post(this.$store.getters.getBaseUrl + "/user/get-balance", {
          phoneNumber: "+91" + this.$store.getters.getRegPhoneNumber,
        })
        .then((response) => {
          this.walletBalance = response.data;
        })
        .catch((error) => {
          console.log(error.response);
        });
    },

    // Method to navigate to specified route
    navigateTo(item) {
      if (item.title === "Logout") {
        this.logout();
      } else if (item.route) {
        this.$router.push(item.route);
      }
    },

    // Method to logout
    logout() {
      // Call the logout action from the store
      this.$store.dispatch("logout");

      // Redirect to the login page
      this.$router.push("#/home");
    },
  },

  mounted() {
    // Call the method to fetch wallet balance when component is mounted
    this.fetchWalletBalance();
  },
};
</script>
