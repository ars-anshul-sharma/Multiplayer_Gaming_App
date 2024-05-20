<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard :heading="pageTitle">
          <AdminGamesTableVue :apiEndpoint="apiEndpoint" />
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import AdminGamesTableVue from "../../../components/vuetifyComponents/tables-simple/AdminGamesTable.vue";
export default {
  data() {
    return {};
  },
  components: {
    AdminGamesTableVue,
  },
  props: {
    status: {
      type: String,
      required: true,
    },
  },
  computed: {
    pageTitle() {
      return this.status === "ongoing"
        ? "Ongoing Games"
        : this.status === "all"
        ? "All Games"
        : this.status === "pending"
        ? "Pending Games"
        : this.status === "complete"
        ? "Complete Games"
        : this.status === "cancelled"
        ? "Cancelled Games"
        : "";
    },
    apiEndpoint() {
      return this.$store.getters.getBaseUrl + `/game/${this.status}-games`;
    },
  },
};
</script>
