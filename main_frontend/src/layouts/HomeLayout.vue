<template>
  <v-app id="inspire">
    <Header v-model="expandOnHover"></Header>
    <Whatsapp></Whatsapp>
    <v-content>
      <v-container class="fill-height" fluid>
        <router-view />
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import Header from "./header/Home_Header.vue";
import { mapState, mapMutations } from "vuex";
import Whatsapp from "../views/pages/Whatsapp.vue";
export default {
  name: "Layout",

  components: {
    Header,
    Whatsapp,
  },

  props: {
    source: String,
  },
  data: () => ({
    expandOnHover: false,
  }),
  computed: {
    ...mapState(["Customizer_drawer"]),
  },

  methods: {
    ...mapMutations({
      setCustomizerDrawer: "SET_CUSTOMIZER_DRAWER",
    }),
  },
};
</script>
