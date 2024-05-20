<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Transactions & History">
          <TransactionHistory></TransactionHistory>
        </BaseCard>
        <BaseCard heading="Credit & Debit">
          <TableSimpleDense></TableSimpleDense>
        </BaseCard>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      roomId: "",
      playerOneUsername: "priya",
      amount: "",
    };
  },
  components: {
    TransactionHistory: () =>
      import("@/components/vuetifyComponents/tables-simple/TransactionHistory"),
    TableSimpleDense: () =>
      import("@/components/vuetifyComponents/tables-simple/TableSimpleDense"),
  },
  methods: {
    createBattle() {
      axios
        .post(this.$store.getters.getBaseUrl + "/game/start", {
          roomId: this.roomId,
          playerOneUsername: this.playerOneUsername,
          bettingAmount: this.amount,
        })
        .then((response) => {
          console.log(response);
          // Swal.fire({
          //   title: "Battle Created Successfully",
          //   icon: "success",
          // });
        })
        .catch((error) => {
          console.log(error);
          // Swal.fire({
          //   title: error.response.data["message"],
          //   icon: "error",
          // });
        });
    },
  },
};
</script>
