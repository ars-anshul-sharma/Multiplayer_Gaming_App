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
};
</script>
