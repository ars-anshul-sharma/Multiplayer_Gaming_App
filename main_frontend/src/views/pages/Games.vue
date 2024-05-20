<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Games Played">
          <GamesPlayed></GamesPlayed>
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {};
  },
  components: {
    GamesPlayed: () =>
      import("@/components/vuetifyComponents/tables-simple/GamesPlayed"),
  },
};
</script>
