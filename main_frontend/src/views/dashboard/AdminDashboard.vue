<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12" lg="6">
        <AdminCommissionSet></AdminCommissionSet>
        <AdminCreditBalance></AdminCreditBalance>
        <AdminDebitBalance></AdminDebitBalance>
        <ApproveFinalStatus></ApproveFinalStatus>
        <SearchAndDownloadImage></SearchAndDownloadImage>
        <WithdrawlRequests></WithdrawlRequests>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "Alerts",

  data: () => ({}),
  components: {
    AdminCommissionSet: () =>
      import(
        "@/components/vuetifyComponents/textfields/AdminCommissionSet.vue"
      ),
    AdminCreditBalance: () =>
      import(
        "@/components/vuetifyComponents/textfields/AdminCreditBalance.vue"
      ),
    AdminDebitBalance: () =>
      import("@/components/vuetifyComponents/textfields/AdminDebitBalance.vue"),
    ApproveFinalStatus: () =>
      import(
        "@/components/vuetifyComponents/textfields/ApproveFinalStatus.vue"
      ),
    SearchAndDownloadImage: () =>
      import(
        "@/components/vuetifyComponents/textfields/SearchAndDownloadImage.vue"
      ),
    WithdrawlRequests: () =>
      import("@/components/vuetifyComponents/textfields/WithdrawlRequests.vue"),
  },
};
</script>
