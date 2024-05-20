<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard :heading="pageTitle">
          <AdminTransactionsVue :apiEndpoint="apiEndpoint" />
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import AdminTransactionsVue from "../../../components/vuetifyComponents/tables-simple/AdminTransactions.vue";
export default {
  data() {
    return {};
  },
  components: {
    AdminTransactionsVue,
  },
  props: {
    status: {
      type: String,
      required: true,
    },
  },
  computed: {
    pageTitle() {
      return this.status === "credit"
        ? "Credited Amount"
        : this.status === "all"
        ? "All Transactions"
        : this.status === "debit"
        ? "Debited Amount"
        : this.status === "fail"
        ? "Failed Transactions"
        : "";
    },
    apiEndpoint() {
      return `${this.$store.getters.getBaseUrl}/transactions/${this.status}`;
    },
  },
};
</script>
