<template>
  <v-app id="karniludoclub" :class="`${!$vuetify.breakpoint.smAndDown ? 'full-sidebar' : 'mini-sidebar'}`">
      <router-view />
  </v-app>

</template>

<script>

export default {
  name: 'App',

  components: {
    
  }

};
</script>
