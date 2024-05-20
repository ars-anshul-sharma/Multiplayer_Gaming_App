<template>
  <v-container fluid class="down-top-padding">
    <v-row>
      <v-col cols="12" sm="12">
        <BaseCard heading="Set Amount">
          <v-text-field v-model="amount" label="Amount"></v-text-field>
          <!-- <v-text-field v-model="roomId" label="Room Id"></v-text-field>
          <v-btn
            class="text-capitalize element-0"
            color="success"
            href="https://ludoking.com/"
            target="_blank"
            >Generate Room ID</v-btn
          >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
          <v-btn
            class="text-capitalize element-0"
            color="success"
            @click="createBattle()"
            >Create</v-btn
          >
        </BaseCard>
        <BaseCard heading="My Battles">
          <MyBattle></MyBattle>
        </BaseCard>
        <BaseCard heading="Open Battles">
          <TableSimpleDarktheme></TableSimpleDarktheme>
        </BaseCard>
        <BaseCard heading="Ongoing Battles">
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
      playerOneUsername: this.$store.getters.getPlayerOneUsername,
      amount: "",
    };
  },
  components: {
    TableSimpleDarktheme: () =>
      import(
        "@/components/vuetifyComponents/tables-simple/TableSimpleDarktheme"
      ),
    TableSimpleDense: () =>
      import("@/components/vuetifyComponents/tables-simple/TableSimpleDense"),
    MyBattle: () =>
      import("@/components/vuetifyComponents/tables-simple/MyBattle"),
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
          window.Swal.fire({
            title: "Battle Created Successfully",
            icon: "success",
          });
        })
        .catch((error) => {
          console.log(error);
          window.Swal.fire({
            title: error.response.data["message"],
            icon: "error",
          });
        });
    },
  },
};
</script>
